package com.tb.manager.service.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.mapper.Mapper;
import com.tb.manager.dao.item.ItemCatDao;
import com.tb.manager.pojo.ItemCat;
import com.tb.manager.service.base.BaseService;

@Service("itemCatService")
public class ItemCatService  extends BaseService<ItemCat>{

		/*@Autowired
		private ItemCatDao itemCatDao;*/
		/*
		@Override
		public Mapper<ItemCat> getDao() {
			return this.itemCatDao;
		}
*/
		/*public List<ItemCat> queryItemCatListByParentId(Long parentId) {	
			ItemCat itemcat = new ItemCat();
			itemcat.setParentId(parentId);
			return this.getDao().select(itemcat);
		}*/

		
}
