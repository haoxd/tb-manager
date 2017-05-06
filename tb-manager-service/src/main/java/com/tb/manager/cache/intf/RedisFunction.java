package com.tb.manager.cache.intf;

/**
 * @author acer11
 *  作者：haoxd
 * @param <T>：输出类型
 * @param <E>：输入类型 
* 创建时间：2017年5月6日 下午9:19:14  
* 项目名称：tb-manager-service  
* 文件名称：RedisFunction.java  
* 类说明：redis封装 通用实现回掉接口，因为不确定输入和输出，固位泛型类型
 */
public interface RedisFunction<T,E> {
	
	/**
	 * redis 回掉实现
	 * @param e ：
	 * @return
	 */
	public T redisCallBack(E e);

}
