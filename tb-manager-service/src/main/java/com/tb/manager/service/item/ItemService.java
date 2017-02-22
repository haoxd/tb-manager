package com.tb.manager.service.item;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tb.common.bean.EasyUIResult;
import com.tb.manager.dao.item.ItemDao;
import com.tb.manager.pojo.Item;
import com.tb.manager.pojo.ItemDesc;
import com.tb.manager.pojo.ItemParamItem;
import com.tb.manager.service.base.BaseService;
import com.tb.manager.system.Constant;
@Service("itemService")
public class ItemService extends BaseService<Item> {
	
	
	/**
	 * 商品描述服务
	 */
	@Resource(name="itemDescService")
	private ItemDescService itemDescService;
	
	/**
	 * 商品dao
	 */
	@Autowired
	private ItemDao dao;
	
	/**
	 * 商品规格数据服务
	 */
	@Resource(name="itemParamItemService")
	private ItemParamItemService ipiService;
	
	/**
	 * 私有商品描述类
	 */
	private ItemDesc iDesc;
	
	/**
	 * 私有商品规格类
	 */
	private ItemParamItem itemParamItem;

	/**
	 * 增加商品信息
	 * @param item 商品信息
	 * @param desc 商品描述
	 * @param itemParams :商品规格参数
	 * @return
	 */
	public boolean addItem(Item item, String desc, String itemParams) {
		
		item.setStatus(1);//设置默认状态	
		item.setId(null);//处于安全考虑，强制设置id为null，通过数据库自增长添加得到，防止客户端攻击
		Integer resultiItem =  super.add(item);
		
		//处理商品信息
		iDesc = new ItemDesc();
		iDesc.setItemDesc(desc);
		iDesc.setItemId(item.getId());//获取商品id
		Integer resultDesc= this.itemDescService.add(iDesc);
		//商品规格参数
		itemParamItem = new ItemParamItem();
		itemParamItem.setItemId(item.getId());
		itemParamItem.setParamData(itemParams);
		Integer resultItemParam=this.ipiService.add(itemParamItem);
		
		return resultiItem.intValue() == 1 && resultDesc.intValue() == 1 && resultItemParam.intValue() == 1;
	}

	/**
	 * 查询商品列表
	 * @param page:分页页面号
	 * @param rows：页面数量
	 * @return
	 */
	public EasyUIResult queryItemList(Integer page, Integer rows) {
		//设置分页参数
		PageHelper.startPage(page, rows);
		
		//按照创建时间排序
		Example example = new Example(Item.class);
		
		example.setOrderByClause("createtime DESC");
		List<Item> itemList=this.dao.selectByExample(example);
		
		PageInfo<Item> pageInfo = new PageInfo<Item>(itemList);
			
		return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
	}

	/**
	 * 更具商品id查询商品类目id
	 * @param id:商品id
	 * @return
	 */
	public Long queryCidById(Long id) {
		Item item = new Item();
		item.setId(id);
		return this.dao.selectOne(item).getCid();
	}

	/**更新商品信息描述
	 * @param item：商品主体信息
	 * @param desc:商品描述
	 * @return
	 */
	public Boolean updateItem(Item item, String desc) {
		item.setStatus(null);//强制设置状态不可修改
		Integer resultItem = super.updateSelective(item);
		
		iDesc = new  ItemDesc();
		iDesc.setItemId(item.getId());
		iDesc.setItemDesc(desc);
			
		Integer resultDesc =this.itemDescService.updateSelective(iDesc);
		return resultItem.intValue() == 1 && resultDesc.intValue() == 1;
	}

	/**
	 * 删除商品信息（物理删除）
	 * @param listIds：删除条件
	 * @return
	 */
	public boolean delItemInfos(List<Object> listIds) {
		
		//删除商品信息
		Integer itemResult =super.delByIds(listIds, Item.class, Constant.ItemAttribute.ITEM_ID);
		//删除商品描述信息
		Integer itemDescResult = this.itemDescService.delByIds(listIds, ItemDesc.class, Constant.ItemDescAttribute.ITEM_DESC_ID);
		//删除商品规格信息
		Integer itemParamItemResult = this.ipiService.delByIds(listIds, ItemParamItem.class, Constant.ItemParamItemAttribute.ITEM_PARAM_ITEM_ID);
	
		int count = itemResult.intValue()+itemDescResult.intValue()+itemParamItemResult.intValue();
		
		return count>0 ? true :false;
		
	}

	

}
