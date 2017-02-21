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
* 创建时间：2017年2月21日 下午2:18:59  
* 项目名称：tb-manager-pojo  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：ItemParamItem.java  
* 类说明：商品规格和商品的关系中间表
 */
@Table(name = "tb_item_param_item")
public class ItemParamItem extends BasePojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id")//商品id
    private Long itemId;

    @Column(name = "param_data")
    private String paramData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getParamData() {
        return paramData;
    }

    public void setParamData(String paramData) {
        this.paramData = paramData;
    }

}
