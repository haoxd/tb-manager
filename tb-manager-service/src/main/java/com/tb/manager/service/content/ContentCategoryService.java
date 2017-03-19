package com.tb.manager.service.content;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tb.manager.pojo.ContentCategory;
import com.tb.manager.service.base.BaseService;
import com.tb.manager.system.constant.ContentConstant;

/**
 * @author acer11
 *  作者：郝旭东
* 创建时间：2017年3月7日 下午10:02:23  
* 项目名称：tb-manager-service  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：ContentCategoryService.java  
* 类说明：内容类目服务
 */
@Service("contentCategoryService")
public class ContentCategoryService extends BaseService<ContentCategory>{

	/**
	 * 增加内容类目实体
	 * @param contentCategory
	 */
	public boolean addContentCategory(ContentCategory contentCategory) {
		contentCategory.setId(null);
		contentCategory.setIsParent(false);
		contentCategory.setSortOrder(1);
		contentCategory.setStatus(ContentConstant.ContentCategoryStatus.YES_STATUS);
		Integer addResult =super.add(contentCategory);
		
		//判断该节点的父节点是否为true，不是修改为true
		ContentCategory parentContentCategory=super.queryById(contentCategory.getParentId());
		if(!parentContentCategory.getIsParent()){
			parentContentCategory.setIsParent(true);
			super.update(parentContentCategory);
		}

		return addResult.intValue()>0 ? true:false;
		
	}

	/**删除内容类目
	 * @param contentCategory
	 * @return
	 */
	public boolean delContentCategory(ContentCategory contentCategory) {
		List<Object> ids = new LinkedList<Object>();
		ids.add(contentCategory.getId());
		
		//递归查找该id节点下的所有子节点id
		this.findChildNode(ids, contentCategory.getId());
		
		Integer delResult =super.delByIds(ids, ContentCategory.class, ContentConstant.ContentCategoryAttribute.CONTENT_CATEGORY_ID);
		
		//判断该节点是否还有兄弟节点 ，如果没有则修改父节点的isParen的属性
		ContentCategory  inParam = new ContentCategory();
		inParam.setParentId(contentCategory.getParentId());
		List<ContentCategory> list = super.queryListByWhere(inParam);
		if(list.isEmpty()|| null==list){
			ContentCategory  contentCategoryParam = new ContentCategory();
			contentCategoryParam.setId(contentCategory.getParentId());
			contentCategoryParam.setIsParent(false);
			super.updateSelective(contentCategoryParam);
			
		}
		return delResult.intValue()>0 ? true:false;
	}
	
	
	/**
	 * 查找子节点
	 */
	private void findChildNode(List<Object> ids,Long pid){
		ContentCategory  inParam = new ContentCategory();
		inParam.setParentId(pid);
		List<ContentCategory> contentCategoryList = super.queryListByWhere(inParam);
		for (ContentCategory contentCategory : contentCategoryList) {
			ids.add(contentCategory.getId());
			//判断该节点是否为父节点，是：继续 否：停止
			if(contentCategory.getIsParent()){
				//继续递归
				findChildNode(ids,contentCategory.getId());
			}
		}
	}

}
