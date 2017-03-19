package com.tb.manager.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tb.manager.pojo.base.BasePojo;

/**
 * @author acer11
 *  作者：
* 创建时间：2017年3月7日 下午9:56:05  
* 项目名称：tb-manager-pojo  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：ContentCategory.java  
* 类说明：内容类目表
 */
@Table(name = "tb_content_category")
public class ContentCategory extends BasePojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
	@Column(name = "parent_id")
    private Long parentId;

    private String name;

    private Integer status;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "is_parent")
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

    // 扩展字段，用于EasyUI中tree结构
    public String getText() {
        return getName();
    }

    public String getState() {
        return getIsParent() ? "closed" : "open";
    }
    @Override
   	public String toString() {
   		return "ContentCategory [id=" + id + ", parentId=" + parentId + ", name=" + name + ", status=" + status
   				+ ", sortOrder=" + sortOrder + ", isParent=" + isParent + "]";
   	}


}
