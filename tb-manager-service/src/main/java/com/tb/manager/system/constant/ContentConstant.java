package com.tb.manager.system.constant;

/**
 * @author acer11
 *  作者：haoxud
* 创建时间：2017年3月8日 下午10:24:48  
* 项目名称：tb-manager-service  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：ContentConstant.java  
* 类说明：内容类目静态常量
 */
public class ContentConstant {

	/**
	 * 内容类目状态
	 * */
	public interface ContentCategoryStatus{
		
		/**
		 * 正常
		 */
		Integer YES_STATUS =1;
		/**
		 * 删除
		 */
		Integer NO_STATUS  =0;
	}
	/**
	 * 
	 * 内容类目属性
	 * */
	public interface ContentCategoryAttribute{
		
		/**
		 * 主键id
		 * */
		String CONTENT_CATEGORY_ID ="id";
	}
}
