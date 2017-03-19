package com.tb.manager.dao.content;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.tb.manager.pojo.Content;

public interface ContentDao extends Mapper<Content> {
	
	/**
	 * 更具内容id和时间倒叙排列，进行查询内容列表
	 * @param ContentId:内容id
	 * @return
	 */
	public List<Content> queryListByContentIdDesc(Map params);

}
