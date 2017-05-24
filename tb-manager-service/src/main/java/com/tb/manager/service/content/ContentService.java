package com.tb.manager.service.content;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tb.common.bean.EasyUIResult;
import com.tb.common.org.gtmd.frame.tools.paramData;
import com.tb.manager.cache.RedisCacheService;
import com.tb.manager.dao.content.ContentDao;
import com.tb.manager.pojo.Content;
import com.tb.manager.pojo.base.BasePojo;
import com.tb.manager.service.base.BaseService;
import com.tb.manager.system.constant.RedisConstant;
import com.tb.manager.util.StringUtil;


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
	
	@Resource(name = "redis")
	private RedisCacheService redis;

	private static final ObjectMapper oMapper = new ObjectMapper();
	
	private static final Logger log = LoggerFactory.getLogger(ContentService.class);
	
	static{
			//解决数据库字段和javabean不统一的情况，例如数据库字段带下划线 ，javabean驼峰
		oMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		 	//配置该objectMapper在反序列化时，忽略目标对象没有的属性。
		oMapper.setDateFormat(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"));
	}

	/**
	 * 分页查询内容
	 * @param contentId:内容id  5：小广告id  4：大广告id
	 * @param page
	 * @param rows
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public EasyUIResult queryListByContentId(Long contentId, Integer page, Integer rows) {
		
		// 存缓存中命中
		String contentBigADCacheData = this.redis.get(RedisConstant.REDISAPITBMANAGERTOWEB.TB_MANAGER_CONTENT_BIGAD_REDIS_KEY) ;
		String contentMinADCacheData =this.redis.get(RedisConstant.REDISAPITBMANAGERTOWEB.TB_MANAGER_CONTENT_MINAD_REDIS_KEY);
		contentBigADCacheData = null;
		contentMinADCacheData= null;
		try {
			//小广告
			if("5".equals(contentId.toString())){
				if(StringUtil.isNotEmpty(contentMinADCacheData)){	
					List<Content> list = JSONArrayTOList(contentMinADCacheData);
					List<Content> stuListss = oMapper.readValue(contentMinADCacheData,
							oMapper.getTypeFactory().constructParametricType(ArrayList.class, Content.class));				
					PageInfo<Content> pageInfo = new PageInfo<>(stuListss);	
					return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());// 返回自定义对象的反序列化
				}
				//大广告	
			}else if("4".equals(contentId.toString())){
				if(StringUtil.isNotEmpty(contentBigADCacheData)){
					List<Content> list = JSONArrayTOList(contentBigADCacheData);
					List<Content> stuListss = oMapper.readValue(contentBigADCacheData,
							oMapper.getTypeFactory().constructParametricType(ArrayList.class, Content.class));
				
					PageInfo<Content> pageInfo = new PageInfo<>(list);
					return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());// 返回自定义对象的反序列化
				}
			}
		} catch (Exception e) {
			log.error("缓存返回自定义对象(商品类目数据)的反序列化错误：" + e);
		}
		paramData<String, Object> inParam = new paramData<String ,Object>();
		inParam.put("categoryId", contentId);
		PageHelper.startPage(page, rows);		
		List<Content> contentList = this.dao.queryListByContentIdDesc(inParam);
		try {
			if("4".equals(contentId.toString())){
				this.redis.set(RedisConstant.REDISAPITBMANAGERTOWEB.TB_MANAGER_CONTENT_BIGAD_REDIS_KEY,
						oMapper.writeValueAsString(contentList),
						RedisConstant.REDISAPITBMANAGERTOWEB.TB_MANAGER_CONTENT_AD_REDIS_KEY_SECONDS);
			}else if("5".equals(contentId.toString())){
				this.redis.set(RedisConstant.REDISAPITBMANAGERTOWEB.TB_MANAGER_CONTENT_MINAD_REDIS_KEY,
						oMapper.writeValueAsString(contentList),
						RedisConstant.REDISAPITBMANAGERTOWEB.TB_MANAGER_CONTENT_AD_REDIS_KEY_SECONDS);
			}
			
		} catch (Exception e) {
			log.error("商品类目数据存入缓存错误：" + e);
		}
		
		PageInfo<Content> pageInfo = new PageInfo<>(contentList);
		
		return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
	}
	
	private static List<Content> JSONArrayTOList (String jsonStr){
		List<Content> contentList = new ArrayList<Content>();
		try {
			JSONArray jsonArray = JSONArray.parseArray(jsonStr);
			 Iterator<Object> it = jsonArray.iterator();
			 
			 while (it.hasNext()) {
				  JSONObject jsonObject = (JSONObject) it.next();
				  Content content = new Content();
				  content.setCategoryId(Long.parseLong(jsonObject.getString("category_id")));
				  content.setContent(jsonObject.getString(""));
				  content.setCreateTime(DateUtils.parseDate(jsonObject.getString("create_time"), StringUtil.createStringDate()));
				  content.setId(Long.parseLong(jsonObject.getString("id")));
				  content.setPic(jsonObject.getString("pic"));
				  content.setPic2(jsonObject.getString("pic2"));
				  content.setSubTitle(jsonObject.getString("sub_title"));
				  content.setTitle(jsonObject.getString("title"));
				  content.setTitleDesc(jsonObject.getString("title_desc"));
				  content.setUpdateTime(DateUtils.parseDate(jsonObject.getString("update_time"), StringUtil.createStringDate()));
				  content.setUrl(jsonObject.getString("url"));;
			
				  contentList.add(content);
			  }
		} catch (Exception e) {
			log.error("广告内容解析JSONArray错误："+e);
		} 
		
		 return contentList;
	}

}
