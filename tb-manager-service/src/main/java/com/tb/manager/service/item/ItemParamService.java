package com.tb.manager.service.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tb.common.bean.EasyUIResult;
import com.tb.manager.dao.item.ItemCatDao;
import com.tb.manager.dao.item.ItemParamDao;
import com.tb.manager.pojo.ItemCat;
import com.tb.manager.pojo.ItemParam;
import com.tb.manager.service.base.BaseService;

@Service("itemParamService")
public class ItemParamService extends BaseService<ItemParam> {

	
	/**
	 * 商品类目模板数据库访问
	 * */
	@Autowired
	private ItemParamDao dao;
	
	/**
	 * 商品类目数据库访问
	 * */
	@Autowired
	private ItemCatDao catDao;

	/**
	 * 查询商品类目模板列表
	 * @param page:分页页面号
	 * @param rows：页面数量
	 * @return
	 */
	public EasyUIResult queryItemParamList(Integer page, Integer rows) {
		// 设置分页参数
		PageHelper.startPage(page, rows);

		// 按照创建时间排序
		Example example = new Example(ItemParam.class);

		example.setOrderByClause("createtime DESC");
		List<ItemParam> itemList = this.dao.selectByExample(example);

		PageInfo<ItemParam> pageInfo = new PageInfo<ItemParam>(itemList);

		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}

	/**更具商品类目id查询商品类目
	 * @param itemCatId：商品类目id
	 * @return
	 */
	public ItemCat queryItemCatByItemCatId(Long itemCatId) {
		
		ItemCat inParam = new ItemCat();
		inParam.setId(itemCatId);
		
		return this.catDao.selectOne(inParam);
	}

}
