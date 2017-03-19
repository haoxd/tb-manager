package com.tb.manager.controller.content;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tb.common.bean.EasyUIResult;
import com.tb.manager.pojo.Content;
import com.tb.manager.service.content.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	
	private static final Logger log = LoggerFactory.getLogger(ContentController.class);
	
	/**
	 * 内容服务
	 */
	@Resource(name="contentService")
	private ContentService contentService;
	
	
	/**
	 * 新增商品内容
	 * @param content
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> addContent(Content content){
		log.debug("新增商品内容入参："+content.toString());
		try {
			content.setId(null);
			
			
		Integer result =	this.contentService.add(content);
		if(result.intValue()>0){
			return ResponseEntity.status(HttpStatus.CREATED).body(null);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			log.error("新增商品内容错误："+e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
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
		log.debug("分页查询内容入参：{ContentId：}"+categoryId +"page{："+"page}"+"{rows:"+rows+"}");
		try {
			EasyUIResult result=this.contentService.queryListByContentId(categoryId,page,rows);
			if(null!=result){
				return ResponseEntity.ok(result);
			}
			return ResponseEntity.status(404).body(null);
		} catch (Exception e) {
			log.error("分页查询内容错误："+e);;
			return ResponseEntity.status(500).body(null);
		}
	
	}

}
