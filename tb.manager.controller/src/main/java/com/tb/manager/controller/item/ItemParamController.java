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
import org.springframework.web.bind.annotation.RequestParam;


import com.tb.common.bean.EasyUIResult;
import com.tb.manager.pojo.ItemCat;
import com.tb.manager.pojo.ItemParam;
import com.tb.manager.service.item.ItemParamService;

/**
 * @author acer11
 *  作者：haoxud
* 创建时间：2017年2月21日 下午2:36:12  
* 项目名称：tb.manager.controller  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：ItemParamController.java  
* 类说明：商品类目参数规格（模板）controller
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	
	private static final Logger log  = LoggerFactory.getLogger(ItemParamController.class);
	
	
	@Resource(name="itemParamService")
	private ItemParamService itemParamService;
	
	/**
	 * 更具商品类目id查询商品规格参数模板
	 * @param itemCatId:商品类目id
	 * @return
	 */
	@RequestMapping(value="{itemCatId}",method=RequestMethod.GET)
	public  ResponseEntity<ItemParam> queryByItemCatId(@PathVariable("itemCatId") Long itemCatId){
		
		log.debug("更具商品类目id查询商品规格参数模板入参 itemCatID:"+itemCatId);
		try {
			ItemParam param = new ItemParam();
			param.setItemCatId(itemCatId);
			ItemParam itemParam =this.itemParamService.queryOne(param);
			if(itemParam == null){			
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);	//404 
			}
			return ResponseEntity.ok(itemParam);
		} catch (Exception e) {
			log.error("更具商品类目id查询商品规格参数模板错误："+e);
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);//500
	}
	
	
	/**
	 * 增加商品类目的模板数据
	 * @param itemCatId:商品类目id
	 * @param paramData：模板数据（JSON）
	 * @return
	 */
	@RequestMapping(value="/addItemParam",method={RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<Void> addItemParam(
			@RequestParam("itemCatId") Long itemCatId,
			@RequestParam("paramData") String paramData
			){
		
		log.debug("增加商品类目的模板数据入参：id={"+itemCatId+"},模板数据：paramData：{"+paramData+"}");
		try {
			//更具商品类目id获取到商品类目
			ItemCat itemCat = this.itemParamService.queryItemCatByItemCatId(itemCatId);
			
			ItemParam inParam = new ItemParam();
			inParam.setItemCatName(itemCat.getName());
			inParam.setItemCatId(itemCatId);
			inParam.setId(null);
			inParam.setParamData(paramData);
			Integer result = this.itemParamService.add(inParam);
			if(result.intValue()>0){
				return ResponseEntity.status(HttpStatus.CREATED).build();
			}
		} catch (Exception e) {
			log.error("增加商品类目的模板数据错误："+e);			
		}				
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);//500
		
	}
	
	/**
	 * 分页查询商品类目模板数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="/queryItemParamList",method=RequestMethod.GET)
	public  ResponseEntity<EasyUIResult> queryItemParamList(
			@RequestParam(value="page",defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="30") Integer rows
			){
		log.debug("查询商品类目模板列表入参："+"{page:"+page+"},{rows:"+rows+"}");
		
		EasyUIResult easyUIResult =null;
		try {
			easyUIResult = this.itemParamService.queryItemParamList(page,rows);
			return ResponseEntity.ok(easyUIResult);
		} catch (Exception e) {
			log.error("查询商品类目模板列表错误："+e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);//500
	}

}
