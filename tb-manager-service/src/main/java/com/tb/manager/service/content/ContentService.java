package com.tb.manager.service.content;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tb.common.bean.EasyUIResult;
import com.tb.common.org.gtmd.frame.tools.paramData;
import com.tb.manager.dao.content.ContentDao;
import com.tb.manager.pojo.Content;
import com.tb.manager.service.base.BaseService;

/**
 * @author acer11
 *  作者：haoxd
* 创建时间：2017年3月19日 下午9:14:38  
* 项目名称：tb-manager-service  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：ContentService.java  
* 类说明：内容服务
 */
@Service("contentService")
public class ContentService extends BaseService<Content>{
	
	@Autowired
	private ContentDao dao;

	/**
	 * 分页查询内容
	 * @param contentId:内容id
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUIResult queryListByContentId(Long contentId, Integer page, Integer rows) {
		paramData<String, Object> inParam = new paramData<String ,Object>();
		inParam.put("categoryId", contentId);
		PageHelper.startPage(page, rows);		
		List<Content> contentList = this.dao.queryListByContentIdDesc(inParam);
		PageInfo<Content> pageInfo = new PageInfo<>(contentList);
		
		return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
	}

}
