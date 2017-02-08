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
			log.debug("新增商品入参数：item={}"+item +"desc ={}" +desc);
			
			if(StringUtils.isEmpty(item.getTitle())){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();//400
			}
			
			Boolean resulet=this.itemService.addItem(item,desc);
			if(!resulet){	
				log.debug("新增商品失败：item={}"+item +"desc ={}" +desc);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();	//500
			}
			log.debug("新增商品成功，商品id为：" +item.getId());
		} catch (Exception e) {
			log.error("新增商品信息出错:"+e);
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();//201
	}

}
