package com.tb.manager.service.content;

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

}
