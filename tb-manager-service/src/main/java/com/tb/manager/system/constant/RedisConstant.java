package com.tb.manager.system.constant;

/**
 * @author acer11
 *  作者：haoxud
* 创建时间：2017年5月10日 下午9:37:48  
* 项目名称：tb-manager-service  
* 文件名称：RedisConstant.java  
* 类说明：redis 静态私有常量定义
 */
public class RedisConstant {
	
	/*
	 * 前台对应后台的缓存键
	 * */
	public interface REDISAPITBMANAGERTOWEB{
		
		/**
		 * 商品类目值(缓存)
		 */
		String TB_MANAGER_ITEM_CAT_REDIS_KEY="RedisItemCat";
		/**
		 * 商品类目存在时间(缓存) 
		 */
		Integer TB_MANAGER_ITEM_CAT_REDIS_KEY_SECONDS=60*60*24*30*3;
		
		/**
		 * 商品内容大广告缓存
		 */
		String TB_MANAGER_CONTENT_BIGAD_REDIS_KEY ="RedisContentBigAd";
		
		/**
		 * 商品内容小广告缓存
		 */
		String TB_MANAGER_CONTENT_MINAD_REDIS_KEY ="RedisContentMinAd";
		
		/**
		 * 广告时间
		 */
		Integer TB_MANAGER_CONTENT_AD_REDIS_KEY_SECONDS=60*60*24*30*3;
		
	}

}
