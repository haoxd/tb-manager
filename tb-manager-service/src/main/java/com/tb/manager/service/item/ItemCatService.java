package com.tb.manager.service.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.mapper.Mapper;
import com.tb.manager.dao.item.ItemCatDao;
import com.tb.manager.dao.item.ItemDao;
import com.tb.manager.pojo.Item;
import com.tb.manager.pojo.ItemCat;
import com.tb.manager.service.base.BaseService;

@Service("itemCatService")
public class ItemCatService  extends BaseService<ItemCat>{

		@Autowired
		private ItemCatDao itemCatDao;
		
	
		
		
	
	/**
	 * 更具商品类目id查询商品类目名称
	 * @param cid：商品类目id
	 * @return
	 */
	public ItemCat queryItemNameByCid(Long cid) {
		ItemCat icat = new ItemCat();
		icat.setId(cid);
		
		icat = this.itemCatDao.selectOne(icat);
		return icat;
	}

		
}
