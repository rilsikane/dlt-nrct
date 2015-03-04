package com.nrct.application.dto;

import java.io.Serializable;
import java.util.List;

public class MenuDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2890620319547724618L;
	private String id;
	private String name;
	private String description;
	private String parent_id;
	private String url;
	private boolean forBlog;
	private boolean publish;
	private String imgpath;
	private String selected;
	private List<MenuDto> children;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isForBlog() {
		return forBlog;
	}
	public void setForBlog(boolean forBlog) {
		this.forBlog = forBlog;
	}
	public boolean isPublish() {
		return publish;
	}
	public void setPublish(boolean publish) {
		this.publish = publish;
	}
	public String getImgpath() {
		return imgpath;
	}
	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	public List<MenuDto>  getChildren() {
		return children;
	}
	public void setChildren(List<MenuDto> children) {
		this.children = children;
	}
	
	
}
