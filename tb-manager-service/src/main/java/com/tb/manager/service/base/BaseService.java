package com.tb.manager.service.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tb.manager.pojo.base.BasePojo;
import com.tb.manager.util.DateUtils;

/**
 *  作者：haoxd
 * @param <T>
* 创建时间：2017年1月20日 下午4:38:41  
* 项目名称：tb-manager-service   
* 文件名称：baseService.java  
* 类说明：基于泛型化开发的通用service
 */
public abstract class BaseService<T extends BasePojo> {
	
	//采用依赖泛型注入方式实现，在子类当中，继承的通用mapper，
	//会更具你需要的类型在spring容器当中寻找到你的实现dao接口进行注入到子类当中
	@Autowired
	private Mapper<T> dao; 

	// 由于父接口不可注入，所以才有下面的抽象方法获取具体的DAo实例
	//单由于spring4.0以上版本增加了，依赖泛型注入功能，所以我们不采用抽象方法的实现
	//public abstract Mapper<T> getDao();
	
	/**
	 * 更具id查询 获取T类型数据
	 * @param id
	 * @return
	 */
	public  T queryById (Long id){
		return this.dao.selectByPrimaryKey(id);
	}
	
	/**
	 * 获取全部T类型数据
	 * @return
	 */
	public List<T> queryAll(){
		return this.dao.select(null);
	}
	
	/**
	 * 更具条件查询一条数据，如果获取到多条数据则抛出异常
	 * @param t
	 * @return
	 */
	public T queryOne(T t){
		return this.dao.selectOne(t);
	}
	
	/**
	 * 更具条件查询多条数据
	 * @param t
	 * @return
	 */
	public List<T> queryListByWhere(T t){
		return this.dao.select(t);
	}
	
	/**
	 * 更具条件分页查询数据
	 * @param t :T类型数据
	 * @param pageNum ：用户想哪一页
	 * @param pageSize ：每页多少条数据  
	 * @return
	 */
	public PageInfo<T> queryPageListByWhere(T t,Integer pageNum,Integer pageSize ){
		//设置分页参数
		PageHelper.startPage(pageNum, pageSize);
		List<T> list = this.dao.select(t);
		return new PageInfo<T>(list);
	}
	
	/**
	 * 新增T类型数据
	 * @param t
	 * @return
	 */
	public Integer add(T t){
		t.setCreatetime(DateUtils.getDate());
		t.setUpdatetime(t.getCreatetime());
		return this.dao.insert(t);
		
	}
	
	/**
	 * 新增选择不为null的字段进行新增
	 * @param t
	 * @return
	 */
	public Integer addSelective(T t){
		t.setCreatetime(DateUtils.getDate());
		t.setUpdatetime(t.getCreatetime());
		return this.dao.insertSelective(t);
		
	}
	
	/**
	 * 更新数据
	 * @param t
	 * @return
	 */
	public Integer update(T t) {
		t.setUpdatetime(DateUtils.getDate());
		return this.dao.updateByPrimaryKey(t);
		
	}
	
	/**
	 * 更新选择不为null的字段进行更新
	 * @param t
	 * @return
	 */
	public Integer updateSelective(T t) {
		t.setUpdatetime(DateUtils.getDate());
		t.setCreatetime(null);//创建时间永远不会被更新
		return this.dao.updateByPrimaryKeySelective(t);		
	}
	
	

	
	
	/**
	 * 更具主键删除数据（物理删除）
	 * @param id
	 * @return
	 */
	public Integer delById(Long id) {
		return this.dao.deleteByPrimaryKey(id);		
	}
	


	/**
	 *  批量删除
	 * @param ids 要删除的条件
	 * @param clazz 要删除的T类型数据
	 * @param property 主键id
	 * @return
	 */
	public Integer delByIds(List<Object> ids,Class clazz,String  property) {
		Example example = new Example(clazz);
		//设置删除条件
		example.createCriteria().andIn(property, ids);		
		return this.dao.deleteByExample(example);
	}
	
	/**
	 * 更具条件删除数据
	 * @param t
	 * @return
	 */
	public Integer delByWhere (T t){
		return this.dao.delete(t);
	}
	

}
