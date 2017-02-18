package com.tb.manager.controller.item;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tb.common.bean.EasyUIResult;
import com.tb.manager.controller.upload.PicUploadController;
import com.tb.manager.pojo.Item;
import com.tb.manager.pojo.ItemDesc;
import com.tb.manager.service.item.ItemDescService;
import com.tb.manager.service.item.ItemService;

@Controller
@RequestMapping(value="/item")
public class ItemController {
	
	
	private static final Logger log =  LoggerFactory.getLogger(ItemController.class);
	
	@Resource(name="itemService")
	private ItemService itemService;
	

	
	
	/**
	 * 新增商品信息
	 * @param item 商品
	 * @param desc 商品描述
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> addItem(Item item,@RequestParam("desc") String desc){
		try {
			if(log.isDebugEnabled()){
				log.debug("新增商品入参：item={}"+item +"desc ={}" +desc);	
			}
					
			if(StringUtils.isEmpty(item.getTitle())){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();//400
			}
			
			Boolean resulet=this.itemService.addItem(item,desc);
			if(!resulet){
				if(log.isDebugEnabled()){
				log.debug("新增商品失败：item={}"+item +"desc ={}" +desc);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();	//500
				}
			}
			if(log.isDebugEnabled()){
			log.debug("新增商品成功，商品id为：" +item.getId());
			}
		} catch (Exception e) {
			log.error("新增商品信息出错:"+e);
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();//201
	}
	
	
	/**
	 * 查询商品列表
	 * @param page 页面
	 * @param rows：页面数量
	 * @return
	 */
	@RequestMapping(value="/queryItemList",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<EasyUIResult>   queryItemList (
			@RequestParam(value="page",defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="30") Integer rows
			){
		
		log.debug("查询商品列表入参："+"{page:"+page+"},{rows:"+rows+"}");
		EasyUIResult easyUIResult =null;
		try {
			easyUIResult = this.itemService.queryItemList(page,rows);
			return ResponseEntity.ok(easyUIResult);
		} catch (Exception e) {
			log.error("查询商品列表错误："+e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);//500
		
		
	}
	
	
	/**
	 * 更新商品信息
	 * @param item：商品主体信息
	 * @param desc：商品描述
	 * @return
	 */
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Void>  updateItem(Item item,@RequestParam("desc") String desc){

		try {
			if(log.isDebugEnabled()){
				log.debug("更新商品入参：item={}"+item +"desc ={}" +desc);	
			}
					
			if(StringUtils.isEmpty(item.getTitle())){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();//400
			}
			
			Boolean resulet=this.itemService.updateItem(item,desc);
			if(!resulet){
				if(log.isDebugEnabled()){
				log.debug("更新商品失败：item={}"+item +"desc ={}" +desc);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();	//500
				}
			}
			if(log.isDebugEnabled()){
			log.debug("更新商品成功，商品id为：" +item.getId());
			}
		} catch (Exception e) {
			log.error("新增商品信息出错:"+e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();//204
	
	}
	
	

}
