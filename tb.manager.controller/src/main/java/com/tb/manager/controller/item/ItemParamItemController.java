package com.tb.manager.controller.item;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tb.manager.pojo.ItemParamItem;
import com.tb.manager.service.item.ItemParamItemService;

/**
 * @author acer11
 *  作者：haoxd
* 创建时间：2017年2月21日 下午2:38:00  
* 项目名称：tb.manager.controller  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：ItemParamItemController.java  
* 类说明：商品规格和商品controller
 */
@Controller
@RequestMapping("/item/param/item")
public class ItemParamItemController {
	
	private  static final Logger log = LoggerFactory.getLogger(ItemParamItemController.class);

	/**
	 * 商品规格参数controller
	 */
	@Resource(name="itemParamItemService")
	private ItemParamItemService  ipiService;
	
	
	/**
	 * 更具商品itemid查询商品规格数据
	 * @param itemId:商品itemid
	 * @return
	 */
	@RequestMapping(value="{itemId}",method=RequestMethod.GET)
	public ResponseEntity<ItemParamItem> queryItemParamItemByItemId(
			@PathVariable("itemId") Long itemId ){
		
		try {
			log.debug("更具商品itemid查询商品规格数据入参：itemid="+itemId);
			
			ItemParamItem  inParam = new ItemParamItem();
			inParam.setItemId(itemId);
			ItemParamItem itemParamItem = this.ipiService.queryOne(inParam);
			if(null == itemParamItem){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);//404
			}else{
				return ResponseEntity.ok(itemParamItem);
			}
		} catch (Exception e) {
			log.error("更具商品itemid查询商品规格数据错误"+e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);//500
		
	} 
}
