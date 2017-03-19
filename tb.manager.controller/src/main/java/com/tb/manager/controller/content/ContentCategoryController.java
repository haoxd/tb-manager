package com.tb.manager.controller.content;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tb.manager.pojo.ContentCategory;
import com.tb.manager.service.content.ContentCategoryService;
import com.tb.manager.system.constant.ContentConstant;

/**
 * @author acer11 作者：haoxud 创建时间：2017年3月8日 下午8:56:10 项目名称：tb.manager.controller
 * @author daniel
 * @version 1.0
 * @since JDK 1.6.0_21 文件名称：ContentCategoryController.java 类说明：内容类目controller
 */
@Controller
@RequestMapping("content/category")
public class ContentCategoryController {

	private static final Logger log = LoggerFactory.getLogger(ContentCategoryController.class);

	/**
	 * 内容类目服务
	 */
	@Resource(name = "contentCategoryService")
	private ContentCategoryService contentCategoryService;

	/**
	 * 更具父节点parentId查询内容分类列表 获取内容类目list
	 * 
	 * @param pId:内容类目id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ContentCategory>> queryContentCategoryListByParentId(
			@RequestParam(value = "id", defaultValue = "0") Long pId) {
		log.debug("更具parentID获取内容类目集合数据入参{" + pId + "}");
		try {
			ContentCategory inParam = new ContentCategory();
			inParam.setParentId(pId);
			List<ContentCategory> contentCateList = this.contentCategoryService.queryListByWhere(inParam);
			if (null != contentCateList) {
				return ResponseEntity.ok(contentCateList);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);// 404
		} catch (Exception e) {
			log.error("更具parentID获取内容类目集合数据错误：" + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);// 500
		}

	}

	/**
	 * 新增内容类目数据实体
	 * 
	 * @param contentCategory:内容类目pojo
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/addContentCategory")
	public ResponseEntity<ContentCategory> addContentCategory(ContentCategory contentCategory) {
		try {
			log.debug("新增内容类目数据实体 入参：" + contentCategory.toString());

			boolean result = this.contentCategoryService.addContentCategory(contentCategory);
			if (result) {
				return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);// 500

		} catch (Exception e) {
			log.debug("新增内容类目数据实体错误：" + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);// 500
		}

	}

	/**
	 * 修改内容类目名称(重新命名)
	 * 
	 * @param Id:内容类目节点id
	 * @param name：修改的名称
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateContentCategoryName(@RequestParam("id") Long Id,
			@RequestParam("name") String name) {
		log.debug("修改内容类目名称入参{ id:" + Id + "name:" + name + "}");
		try {
			ContentCategory inParam = new ContentCategory();
			inParam.setId(Id);
			inParam.setName(name);
			Integer result = this.contentCategoryService.updateSelective(inParam);
			if (result.intValue() > 0) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();// 204
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();// 400
		} catch (Exception e) {
			log.error("修改内容类目名称错误:" + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);// 500
		}

	}

	/**
	 * 删除内容类目
	 * 
	 * @param parentId:父节点id
	 * @param id:主键id
	 * @return
	 */
	@RequestMapping(method=RequestMethod.DELETE)
	public ResponseEntity<Void> delContentCategoryById(ContentCategory contentCategory) {
		log.debug("删除内容类目入参：" + contentCategory.toString());

		try {
			boolean result = this.contentCategoryService.delContentCategory(contentCategory);
			if(result){
				return ResponseEntity.ok(null);//200
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();//404
		} catch (Exception e) {
			log.debug("删除内容类目错误：" + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();// 500
		}		
	}
}
