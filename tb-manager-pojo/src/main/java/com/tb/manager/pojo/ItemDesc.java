package com.tb.manager.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

import com.tb.manager.pojo.base.BasePojo;

/**
*  作者：haoxd
* 创建时间：2017年1月23日 上午9:46:30  
* 项目名称：tb-manager-pojo  
* @version 1.0   
* 类说明：商品描述
 */
@Table(name = "tb_item_desc")
public class ItemDesc extends BasePojo{
    
    @Id//对应tb_item中的id
    private Long itemId;
    
    private String itemDesc;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
    
    

}
