package com.tb.manager.controller.webservice;

import java.net.URLDecoder;
import java.nio.charset.Charset;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.common.bean.api.ItemCatResult;
import com.tb.manager.service.item.ItemCatService;
import com.tb.manager.util.StringUtil;


/**
 * @author acer11
 *  作者：haoxud
* 创建时间：2017年2月27日 下午10:56:44  
* 项目名称：tb.manager.controller  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：ApiItemCatController.java  
* 类说明：对外提供的商品类目webservic接口
 */
@Controller
@RequestMapping("/api/item/cat")
public class ApiItemCatController {
	
	private static final Logger log = LoggerFactory.getLogger(ApiItemCatController.class);
	
	
	private static final ObjectMapper OMapper = new ObjectMapper();
	/**
	 * 商品类目服务
	 */
	@Resource(name="itemCatService")
	private ItemCatService  itemCatService;
	
	/**
	 * 对外提供接口：查询商品类目树数据
	 * 通过jsonp解决跨域问题（
	 * 	通过回掉方式解决拼接js代码解决
	 * 		）
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<String> queryItemCatData(
			@RequestParam(value="callback",required=false)String callback ){
		
		try {
			ItemCatResult  itemCatResult=this.itemCatService.queryAllToItemCat();
			
			//判断回掉函数名称是否传
			/*
			 * isEmpty：" " 不为空
			 * isBlank：" " 为空
			 * */
			String itemString=OMapper.writeValueAsString(itemCatResult);
		
			//String itemString = URLDecoder.decode(OMapper.writeValueAsString(itemCatResult),"GBK");
			if(StringUtils.isEmpty(callback)){
				return ResponseEntity.ok(itemString);
			}
			return ResponseEntity.ok(callback+"("+itemString+");");
		} catch (Exception e) {
			log.error("查询商品类目树错误"+e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);//500
	}
	 

}
