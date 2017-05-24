package com.tb.manager.pojo.base;


import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public abstract class BasePojo {
	
	
	@Column(name="create_time")
    private Date createTime;

	@Column(name="update_time")
    private Date updateTime;
	
	
	public BasePojo() {
		super();
		
	}
	public BasePojo(Date createTime, Date updateTime) {
		super();
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

    
    

}
