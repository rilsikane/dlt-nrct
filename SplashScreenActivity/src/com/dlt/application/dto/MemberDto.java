package com.dlt.application.dto;

import java.io.Serializable;
import java.util.Date;

public class MemberDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7370203877558895626L;
	private CommonDto commonDto;
	private String id;
	private String username;
	private String email;
	private String firstname;
	private String lastname;
	private String role_id;
	private Date create_date;
	private Date update_date;
	
	public CommonDto getCommonDto() {
		return commonDto;
	}
	public void setCommonDto(CommonDto commonDto) {
		this.commonDto = commonDto;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	
	
	
}
