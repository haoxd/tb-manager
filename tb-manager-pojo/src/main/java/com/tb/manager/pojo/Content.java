package com.tb.manager.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.tb.manager.pojo.base.BasePojo;

/**
 * @author acer11
 *  作者：haoxud
* 创建时间：2017年3月7日 下午9:55:05  
* 项目名称：tb-manager-pojo  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：Content.java  
* 类说明：内容
 */
@Table(name = "tb_content") 
public class Content extends BasePojo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "category_id")
    private Long categoryId;

    private String title;

    @Column(name = "sub_title")
    private String subTitle;


    @Column(name = "title_desc")
    private String titleDesc;

    private String url;

    private String pic;

    private String pic2;

    private String content;
    

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTitleDesc() {
        return titleDesc;
    }

    public void setTitleDesc(String titleDesc) {
        this.titleDesc = titleDesc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	@Override
	public String toString() {
		return "[id=" + id + ", categoryId=" + categoryId + ", title=" + title + ", subTitle=" + subTitle
				+ ", titleDesc=" + titleDesc + ", url=" + url + ", pic=" + pic + ", pic2=" + pic2 + ", content="
				+ content + ", createTime=" + getCreateTime() + ", updateTime=" + getUpdateTime() + "]";
	}

	
    
}
