package com.tb.manager.service.item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.common.bean.api.ItemCatData;
import com.tb.common.bean.api.ItemCatResult;
import com.tb.manager.cache.RedisCacheService;
import com.tb.manager.dao.item.ItemCatDao;
import com.tb.manager.pojo.ItemCat;
import com.tb.manager.service.base.BaseService;
import com.tb.manager.system.constant.RedisConstant;
import com.tb.manager.util.StringUtil;

@Service("itemCatService")
public class ItemCatService extends BaseService<ItemCat> {

	@Autowired
	private ItemCatDao itemCatDao;

	@Resource(name = "redis")
	private RedisCacheService redis;

	private static final ObjectMapper oMapper = new ObjectMapper();

	private static final Logger log = LoggerFactory.getLogger(ItemCatService.class);

	/**
	 * 更具商品类目id查询商品类目名称
	 * 
	 * @param cid：商品类目id
	 * @return
	 */
	public ItemCat queryItemNameByCid(Long cid) {
		ItemCat icat = new ItemCat();
		icat.setId(cid);

		icat = this.itemCatDao.selectOne(icat);
		return icat;
	}

	/**
	 * 查询全部商品类目信息
	 * 
	 * @return
	 */
	public ItemCatResult queryAllToItemCat() {

		ItemCatResult result = new ItemCatResult();

		// 存缓存中命中
		String itemCatsCacheData = this.redis.get(RedisConstant.REDISAPITBMANAGERTOWEB.TB_MANAGER_ITEM_CAT_REDIS_KEY);
		try {
		if (StringUtil.isNotEmpty(itemCatsCacheData)) {
			
				return oMapper.readValue(itemCatsCacheData, ItemCatResult.class);// 返回自定义对象的反序列化
			}	
		}catch (Exception e) {
				log.error("缓存返回自定义对象(商品类目数据)的反序列化错误：" + e);
			}
		
		List<ItemCat> itemCats = super.queryAll();

		// 转为map存储，key为父节点ID，value为数据集合
		Map<Long, List<ItemCat>> itemCatMap = new HashMap<Long, List<ItemCat>>();
		for (ItemCat itemCat : itemCats) {
			// 如果不存在，这给itemCatMap 放入该数据
			if (!itemCatMap.containsKey(itemCat.getParentId())) {
				itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
			}
			itemCatMap.get(itemCat.getParentId()).add(itemCat);// 添加对象
		}

		// 分装一级类目
		List<ItemCat> firstItemCat = itemCatMap.get(0L);
		for (ItemCat itemCat : firstItemCat) {
			ItemCatData itemCatData = new ItemCatData();
			itemCatData.setUrl("/products/" + itemCat.getId() + ".html");
			itemCatData.setName("<a href='" + itemCatData.getUrl() + "'>" + itemCat.getName() + "</a>");
			result.getItemCats().add(itemCatData);

			// 如果是父类节点则继续 true：是父类目 ，false：不是
			if (!itemCat.getIsParent()) {
				continue;
			}

			// 封装二级对象
			List<ItemCat> secondItemCat = itemCatMap.get(itemCat.getId());
			List<ItemCatData> secondItemCatData = new ArrayList<ItemCatData>();
			itemCatData.setItems(secondItemCatData);
			for (ItemCat secondItemCatTwo : secondItemCat) {
				ItemCatData itemCatDataTwo = new ItemCatData();
				itemCatDataTwo.setName(secondItemCatTwo.getName());
				itemCatDataTwo.setUrl("/products/" + secondItemCatTwo.getId() + ".html");
				secondItemCatData.add(itemCatDataTwo);

				// 如果是父类节点则组装三级类目
				if (secondItemCatTwo.getIsParent()) {
					// 封装三级对象
					List<ItemCat> thirdItemCatList = itemCatMap.get(secondItemCatTwo.getId());
					List<String> itemCatDataThree = new ArrayList<String>();
					itemCatDataTwo.setItems(itemCatDataThree);
					for (ItemCat thirdItemCatThree : thirdItemCatList) {
						itemCatDataThree
								.add("/products/" + thirdItemCatThree.getId() + ".html|" + thirdItemCatThree.getName());
					}
				}
			}
		}
		// 写入缓存
		try {
			this.redis.set(RedisConstant.REDISAPITBMANAGERTOWEB.TB_MANAGER_ITEM_CAT_REDIS_KEY,
					oMapper.writeValueAsString(result),
					RedisConstant.REDISAPITBMANAGERTOWEB.TB_MANAGER_ITEM_CAT_REDIS_KEY_SECONDS);
		} catch (Exception e) {
			log.error("商品类目数据存入缓存错误：" + e);
		}

		return result;
	}

}
