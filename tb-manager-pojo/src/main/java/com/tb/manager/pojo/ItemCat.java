package com.tb.manager.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tb.manager.pojo.base.BasePojo;

/**
 *  作者：haoxd
* 创建时间：2017年1月23日 上午9:47:35  
* 项目名称：tb-manager-pojo  
* @version 1.0   
* 文件名称：ItemCat.java  
* 类说明：商品类目
 */
@Table(name = "tb_item_cat")
public class ItemCat extends BasePojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parentId;

    private String name;

    private Integer status;

    private Integer sortOrder;

    private Boolean isParent;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }
    /*
     * 为前台easyui
     * 的tree组件text属性显示拓展
     * */
    public String getText(){
    	return this.getName();
    }
    /*
     * 拓展前台tree组件，显示是文件夹还是文件 Close：文件夹，open：文件
     * isParent ,true 父节点，false为最小节点
     * */
    public String getState(){
    	return this.getIsParent() ?"closed":"open";
    }

}
