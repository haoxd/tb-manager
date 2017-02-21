package com.tb.manager.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.tb.manager.pojo.base.BasePojo;

/**
 * @author acer11
 *  作者：haoxd
* 创建时间：2017年2月21日 下午2:18:52  
* 项目名称：tb-manager-pojo  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：ItemParam.java  
* 类说明：商品类目参数规格表
 */
@Table(name = "tb_item_param")
public class ItemParam extends BasePojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_cat_id")
    private Long itemCatId;//商品类目ID

    @Column(name = "param_data")
    private String paramData;
    
    @Column(name="item_cat_name")
    private String itemCatName;//商品类目名称

    public String getItemCatName() {
		return itemCatName;
	}

	public void setItemCatName(String itemCatName) {
		this.itemCatName = itemCatName;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemCatId() {
        return itemCatId;
    }

    public void setItemCatId(Long itemCatId) {
        this.itemCatId = itemCatId;
    }

    public String getParamData() {
        return paramData;
    }

    public void setParamData(String paramData) {
        this.paramData = paramData;
    }

}
