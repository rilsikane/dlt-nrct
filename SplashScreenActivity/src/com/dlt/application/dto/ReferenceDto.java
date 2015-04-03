package com.dlt.application.dto;

import java.io.Serializable;

public class ReferenceDto implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1006011493866939872L;
	private String url;
	private String title;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
