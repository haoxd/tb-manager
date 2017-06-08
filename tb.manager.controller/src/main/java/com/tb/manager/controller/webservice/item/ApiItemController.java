package com.tb.manager.controller.webservice.item;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tb.manager.pojo.Item;
import com.tb.manager.pojo.ItemDesc;
import com.tb.manager.pojo.ItemParamItem;
import com.tb.manager.service.item.ItemDescService;
import com.tb.manager.service.item.ItemParamItemService;
import com.tb.manager.service.item.ItemService;

/**
 * @author acer11
 *  作者：haoxd
* 创建时间：2017年5月17日 下午4:24:07  
* 项目名称：tb.manager.controller  
* 文件名称：ApiItemController.java  
* 类说明：商品对外接口
 */
@Controller
@RequestMapping("api/item")
public class ApiItemController {
	
	private static final Logger log = LoggerFactory.getLogger(ApiItemController.class);
	
	/**
	 * 商品服务
	 */
	@Resource(name="itemService")
	private ItemService itemService;
	
	/**
	 * 商品描述服务
	 */
	@Resource(name="itemDescService")
	private ItemDescService itemDescService;
	
	/**
	 * 商品规格参数controller
	 */
	@Resource(name="itemParamItemService")
	private ItemParamItemService  ipiService;
	
	
	/**
	 * 更具商品id获取商品信息
	 * @return
	 */
	@RequestMapping(value="/getItemDetailInfoByItemId/{itemId}",method=RequestMethod.GET,headers = "HaiTao=1")
	public ResponseEntity<Item> getItemDetailInfoByItemId(@PathVariable("itemId")Long itemId ){
		try {
			Item item = this.itemService.queryById(itemId);
			if(null != item){
				return ResponseEntity.ok(item);
				
			}else{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} catch (Exception e) {
			log.error("更具商品id获取商品信息错误："+e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		
	}
	
	/**
	 * 更具商品id获取商品描述信息
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value="/getItemDescByItemId/{itemId}",method=RequestMethod.GET,headers = "HaiTao=1")
	public ResponseEntity<ItemDesc> getItemDescByItemId(@PathVariable("itemId")Long itemId){
		try {
			ItemDesc itemDesc = this.itemDescService.queryById(itemId);
			if(null != itemDesc){
				return ResponseEntity.ok(itemDesc);
				
			}else{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} catch (Exception e) {
			log.error("更具商品id获取商品信息错误："+e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 * 更具商品itemid查询商品规格数据
	 * @param itemId:商品itemid
	 * @return
	 */
	@RequestMapping(value="/getItemParamItemByItemId/{itemId}",method=RequestMethod.GET,headers = "HaiTao=1")
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
