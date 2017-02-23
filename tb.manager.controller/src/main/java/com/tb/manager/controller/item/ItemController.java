package com.tb.manager.controller.item;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
import com.tb.manager.system.Constant;
import com.tb.manager.util.DateUtils;

/**
 * @author acer11
 *  作者：haoxd
* 创建时间：2017年2月21日 下午7:25:47  
* 项目名称：tb.manager.controller  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：ItemController.java  
* 类说明：商品controller
 */
@Controller
@RequestMapping(value="/item")
public class ItemController {
	
	
	private static final Logger log =  LoggerFactory.getLogger(ItemController.class);
	
	
	
	/**
	 * 商品服务
	 */
	@Resource(name="itemService")
	private ItemService itemService;
	
	
	
	

	
	
	/**
	 * 新增商品信息
	 * @param item 商品
	 * @param desc 商品描述
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> addItem(
			Item item,
			@RequestParam("desc") String desc,
			@RequestParam("itemParams") String itemParams
			){
		try {
			if(log.isDebugEnabled()){
				log.debug("新增商品入参：item={}"+item +"desc ={}" +desc+"itemParams={}"+itemParams);	
			}
					
			if(StringUtils.isEmpty(item.getTitle())){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();//400
			}
			
			Boolean resulet=this.itemService.addItem(item,desc,itemParams);
			if(!resulet){
				if(log.isDebugEnabled()){
				log.debug("新增商品失败：item={}"+item +"desc ={}" +desc+"itemParams={}"+itemParams);
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
	public ResponseEntity<Void>  updateItem(
			Item item,
			@RequestParam("desc") String desc,
			@RequestParam("itemParams") String itemParams){

		try {
			if(log.isDebugEnabled()){
				log.debug("更新商品入参：item={}"+item +"desc ={}" +desc+"itemParams={}"+itemParams);	
			}
					
			if(StringUtils.isEmpty(item.getTitle())){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();//400
			}
			
			Boolean resulet=this.itemService.updateItem(item,desc,itemParams);
			if(!resulet){
				if(log.isDebugEnabled()){
				log.debug("更新商品失败：item={}"+item +"desc ={}" +desc+"itemParams="+itemParams);
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
	
	/**
	 * 更具商品id物理删除商品
	 * @param Id:商品id
	 * @return
	 */
	@RequestMapping(value="/delItemById" ,method=RequestMethod.POST)
	public ResponseEntity<Void> delItemById(@RequestParam("Id") String Ids){
		
		log.debug("更具商品id物理删除商品入参 id："+Ids);
		try {
			String [] stringArr= Ids.split(",");  //转为数组
			List<Object> listIds = new LinkedList<Object>();
			for (int i = 0; i < stringArr.length; i++) {
				listIds.add(stringArr[i]);
			}
			//删除商品主体信息
			boolean result =this.itemService.delItemInfos(listIds);
								
			if(result){
				return  ResponseEntity.ok(null);//200
			}else{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();//404
			}
		} catch (Exception e) {
			log.error("更具商品id删除商品错误"+e);
		}
		return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500
	}
	
	/**
	 * 上架商品
	 * @param Id：商品id
	 * @return
	 */
	@RequestMapping(value="/upperItemById",method=RequestMethod.POST)
	public ResponseEntity<Void> upperItemById(@RequestParam("Id")Long Id){
		
		try {
			log.debug("上架商品入参 id："+Id);	
			
			Item inParam = new Item();
			
			inParam.setId(Id);
			inParam.setStatus(Constant.ItemStatus.UPPER_STATUS);
			
			
			Integer resulet=this.itemService.updateSelective(inParam);
			if(resulet.intValue()>0){
				return  ResponseEntity.ok(null);//200
			}		
		} catch (Exception e) {
			log.error("上架商品信息出错:"+e);			
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500
	}
	
	/**
	 * 下架商品
	 * @param Id：商品id
	 * @return
	 */
	@RequestMapping(value="/lowerItemById",method=RequestMethod.POST)
	public ResponseEntity<Void> lowerItemById(@RequestParam("Id")Long Id){
		
		try {
			log.debug("下架商品入参 id："+Id);	
			
			Item inParam = new Item();
			
			inParam.setId(Id);
			inParam.setStatus(Constant.ItemStatus.LOWER_STATUS);
			
			Integer resulet=this.itemService.updateSelective(inParam);
			if(resulet.intValue()>0){
				return  ResponseEntity.ok(null);//200
			}		
		} catch (Exception e) {
			log.error("下架商品信息出错:"+e);			
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500
	}
	
	

}
