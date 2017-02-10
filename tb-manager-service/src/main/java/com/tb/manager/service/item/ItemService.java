package com.tb.manager.service.item;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tb.manager.pojo.Item;
import com.tb.manager.pojo.ItemDesc;
import com.tb.manager.service.base.BaseService;
@Service("itemService")
public class ItemService extends BaseService<Item> {
	
	
	@Resource(name="itemDescService")
	private ItemDescService itemDescService;
	
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

}
