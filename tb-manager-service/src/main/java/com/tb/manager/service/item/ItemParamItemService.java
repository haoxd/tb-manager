package com.tb.manager.service.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.tb.manager.dao.item.ItemParamItemDao;
import com.tb.manager.pojo.ItemParamItem;
import com.tb.manager.service.base.BaseService;
import com.tb.manager.util.DateUtil;
import com.tb.manager.util.DateUtils;

@Service("itemParamItemService")
public class ItemParamItemService extends BaseService<ItemParamItem>{
	
	
	/**
	 *商品规格数据库访问
	 */
	@Autowired
	private ItemParamItemDao dao;
	

	/**
	 * 通过定制条件更新商品规格数据
	 * @param itemId:要更新的条件
	 * @param itemParams：更新数据
	 * @return
	 */
	public Integer updateItemParamItem(Long itemId,String itemParams) {
		// 更新的数据
		ItemParamItem  inParam = new ItemParamItem();
		inParam.setParamData(itemParams);;
		inParam.setUpdatetime(DateUtils.getDate());
		
		//更新条件
		Example example = new Example(ItemParamItem.class);
		example.createCriteria().andEqualTo("itemId", itemId);
				
		return this.dao.updateByExampleSelective(inParam, example);
	}

}
