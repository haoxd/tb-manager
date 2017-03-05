package com.tb.manager.service.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tb.common.bean.api.ItemCatData;
import com.tb.common.bean.api.ItemCatResult;
import com.tb.manager.dao.item.ItemCatDao;
import com.tb.manager.pojo.ItemCat;
import com.tb.manager.service.base.BaseService;

@Service("itemCatService")
public class ItemCatService extends BaseService<ItemCat> {

	@Autowired
	private ItemCatDao itemCatDao;

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
	 * @return
	 */
	public ItemCatResult queryAllToItemCat(){
		
		ItemCatResult result = new ItemCatResult();
		
		List<ItemCat> itemCats = super.queryAll();
		
		// 转为map存储，key为父节点ID，value为数据集合
		Map<Long ,List<ItemCat>> itemCatMap = new HashMap<Long, List<ItemCat>>();		
		for (ItemCat itemCat : itemCats) {
			//如果不存在，这给itemCatMap 放入该数据
			if(!itemCatMap.containsKey(itemCat.getParentId())){
				itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>() );
			}
			itemCatMap.get(itemCat.getParentId()).add(itemCat);//添加对象
		}
		
		//分装一级类目
		List<ItemCat> firstItemCat = itemCatMap.get(0L);
		for (ItemCat itemCat : firstItemCat) {
			ItemCatData itemCatData = new ItemCatData();
			itemCatData.setUrl("/products/"+itemCat.getId()+".html");
			itemCatData.setName("<a href='"+itemCatData.getUrl()+"'>"+itemCat.getName()+"</a>");
			result.getItemCats().add(itemCatData);
			
			//如果是父类节点则继续 true：是父类目 ，false：不是
			if(!itemCat.getIsParent()){
				continue;
			}
			
			// 封装二级对象
			List<ItemCat> secondItemCat = itemCatMap.get(itemCat.getId());
			List<ItemCatData> secondItemCatData = new ArrayList<ItemCatData>();
			itemCatData.setItems(secondItemCatData);
			for (ItemCat secondItemCatTwo : secondItemCat) {
				ItemCatData itemCatDataTwo = new ItemCatData();
				itemCatDataTwo.setName(secondItemCatTwo.getName());
				itemCatDataTwo.setUrl("/products/"+secondItemCatTwo.getId()+".html");
				secondItemCatData.add(itemCatDataTwo);
				
				//如果是父类节点则组装三级类目
				if(secondItemCatTwo.getIsParent()){					
					// 封装三级对象
					List<ItemCat> thirdItemCatList = itemCatMap.get(secondItemCatTwo.getId());
					List<String> itemCatDataThree = new ArrayList<String>();
					itemCatDataTwo.setItems(itemCatDataThree);
					for (ItemCat thirdItemCatThree : thirdItemCatList) {
						itemCatDataThree.add("/products/" + thirdItemCatThree.getId() + ".html|"+thirdItemCatThree.getName());
					}
				}
			}	
			/*if(result.getItemCats().size()>=14){
				break;
			}*/
			
		}

		return result;	
	}
	
}
