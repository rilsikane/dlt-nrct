package com.dlt.application.model;

import java.util.List;


import com.dlt.application.dto.MemberDto;
import com.dlt.application.dto.MenuDto;
import com.roscopeco.ormdroid.Entity;

public class User extends Entity {
	public int id;
	public String username;
	public String token;
	public String user_id;
	public String firstname;
	public String lastname;

	public User() {
		this(null, null, null, null, null);
	}
	
	
	


	public User(String username, String token, String user_id, String firstname, String lastname) {
		this.username = username;
		this.token = token;
		this.user_id = user_id;
		this.firstname = firstname;
		this.lastname = lastname;
	}





	public void removeAll() {
		List<User> list = query(User.class).executeMulti();
		for (User mag : list) {
			mag.delete();
		}
	}
	public int getPk(){
		List<User> list = null;
		int key = 1;
		list = query(User.class).executeMulti();
		if(list!=null && !list.isEmpty()){
			key = list.size()+1;
		}
		return key;
		
	}
	
	public boolean isNotEmpty(){
		List<User> list = null;
		boolean isNotEmpty = false;
		list = query(User.class).executeMulti();
		if(list!=null && !list.isEmpty()){
			isNotEmpty = true ;
		}
		return isNotEmpty;
	}
	
	public User getUserLogin(){
		List<User> list = null;
		User user = null;
		list = query(User.class).executeMulti();
		if(list!=null && !list.isEmpty()){
			user = list.get(0);
		}
		
		return user;
	}
	
	public MemberDto convertToMember(User user){
		MemberDto dto = new MemberDto();
		dto.setFirstname(user.firstname);
		dto.setLastname(user.lastname);
		dto.setUsername(user.username);
		
		return dto;
	}
}
