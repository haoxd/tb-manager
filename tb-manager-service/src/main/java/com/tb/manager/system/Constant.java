package com.tb.manager.system;

/**
 * @author acer11
 *  作者：
* 创建时间：2017年2月21日 下午7:44:37  
* 项目名称：tb-manager-service  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：Constant.java  
* 类说明：系统静态类常量类
 */
public class Constant {
	
	/**
	 * 商品状态
	 * */
	public interface ItemStatus{
		
		/**
		 * 删除
		 */
		Integer DEL_STATUS=3;
		
		/**
		 * 正常上架状态
		 */
		Integer UPPER_STATUS=1;
		
		/**
		 * 下架状态
		 */
		Integer LOWER_STATUS=2;
	}

}
