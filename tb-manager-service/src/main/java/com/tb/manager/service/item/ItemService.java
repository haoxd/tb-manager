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
import com.tb.manager.service.base.BaseService;
@Service("itemService")
public class ItemService extends BaseService<Item> {
	
	
	@Resource(name="itemDescService")
	private ItemDescService itemDescService;
	
	@Autowired
	private ItemDao dao;
	
	private ItemDesc iDesc;

	/**
	 * 增加商品信息
	 * @param item 商品信息
	 * @param desc 商品描述
	 * @return
	 */
	public boolean addItem(Item item, String desc) {
		
		item.setStatus(1);//设置默认状态	
		item.setId(null);//处于安全考虑，强制设置id为null，通过数据库自增长添加得到，防止客户端攻击
		Integer resultiItem =  super.add(item);
		
		//处理商品信息
		iDesc = new ItemDesc();
		iDesc.setItemDesc(desc);
		iDesc.setItemId(item.getId());//获取商品id
		Integer resultDesc= this.itemDescService.add(iDesc);
		
		return resultiItem.intValue() == 1 && resultDesc.intValue() == 1;
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
		item.setStatus(null);//强制设置时间不可修改
		Integer resultItem = super.updateSelective(item);
		
		iDesc = new  ItemDesc();
		iDesc.setItemId(item.getId());
		iDesc.setItemDesc(desc);
			
		Integer resultDesc =this.itemDescService.updateSelective(iDesc);
		return resultItem.intValue() == 1 && resultDesc.intValue() == 1;
	}

	

}
