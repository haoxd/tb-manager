package com.tb.manager.controller.webservice.content;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tb.common.bean.EasyUIResult;
import com.tb.manager.service.content.ContentService;

/**
 * @author acer11
 *  作者：haoxud
* 创建时间：2017年3月20日 下午9:40:34  
* 项目名称：tb.manager.controller  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：ApiContentController.java  
* 类说明：对外接口地址
 */
@Controller
@RequestMapping("api/content")
public class ApiContentController {
	
	
	private static final Logger log = LoggerFactory.getLogger(ApiContentController.class);
	
	/**
	 * 内容服务
	 */
	@Resource(name="contentService")
	private ContentService contentService;
	
	
	
	/**
	 * 分页查询内容
	 * @param ContentId:内容id
	 * @param page：第几页
	 * @param rows：页面数量
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<EasyUIResult> queryListByContentId(
			@RequestParam("categoryId") Long categoryId,
			@RequestParam(value="page" ,defaultValue="1") Integer page,
			@RequestParam(value="rows",defaultValue="20") Integer rows	
			){
		log.debug("对外接口分页查询内容入参：{ContentId：}"+categoryId +"page{："+"page}"+"{rows:"+rows+"}");
		try {
			EasyUIResult result=this.contentService.queryListByContentId(categoryId,page,rows);
			if(null!=result){
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.status(404).body(null);
		} catch (Exception e) {
			log.error("对外接口分页查询内容错误："+e);;
			return ResponseEntity.status(500).body(null);
		}
	
	}
	
	

}
