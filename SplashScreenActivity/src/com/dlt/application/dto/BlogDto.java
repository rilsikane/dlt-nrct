package com.dlt.application.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BlogDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8254378820243677966L;
	private String title;
	private String content;
	private String author_name;
	private String id;
	private Date create_date;
	private String mindmap;
	private String imgUrl;
	private List<ReferenceDto> refList;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor_name() {
		return author_name;
	}
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getMindmap() {
		return mindmap;
	}
	public void setMindmap(String mindmap) {
		this.mindmap = mindmap;
	}
	public List<ReferenceDto> getRefList() {
		return refList;
	}
	public void setRefList(List<ReferenceDto> refList) {
		this.refList = refList;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
	
}
