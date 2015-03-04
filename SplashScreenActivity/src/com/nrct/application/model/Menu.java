package com.nrct.application.model;

import java.util.List;


import com.nrct.application.dto.MenuDto;
import com.roscopeco.ormdroid.Entity;

public class Menu extends Entity {
	public int id;
	public String name;
	public String description;
	public String parent_id;
	public String url;
	public String selected;
	public String imgpath;
	public String menu_id;
	public String childFlag;

	public Menu() {
		this(null, null, null, null, null, null,null,null);
	}
	
	
	public Menu(String name, String description, String parent_id, String url,
			String selected, String imgpath, String menu_id, String childFlag) {
		this.name = name;
		this.description = description;
		this.parent_id = parent_id;
		this.url = url;
		this.selected = selected;
		this.imgpath = imgpath;
		this.menu_id = menu_id;
	}


	public void removeAll() {
		List<Menu> list = query(Menu.class).executeMulti();
		for (Menu mag : list) {
			mag.delete();
		}
	}
	public int getPk(){
		List<Menu> list = null;
		int key = 1;
		list = query(Menu.class).executeMulti();
		if(list!=null && !list.isEmpty()){
			key = list.size()+1;
		}
		return key;
		
	}
	
	public boolean isNotEmpty(){
		List<Menu> list = null;
		boolean isNotEmpty = false;
		list = query(Menu.class).executeMulti();
		if(list!=null && !list.isEmpty()){
			isNotEmpty = true ;
		}
		return isNotEmpty;
	}
	public List<Menu> getMenuSelected(){
		List<Menu> list = query(Menu.class).where("selected").eq("T").executeMulti();
		
		return list;
	}
	public boolean isMenuSelected(String id){
		List<Menu> list = query(Menu.class).where("menu_id").eq(id).executeMulti();
		boolean isSelected = false;
		if(list!=null && list.size()>0){
			for(Menu menu : list){
				if("T".equals(menu.selected)){
					isSelected = true;
				}
			}
		}
		return isSelected;
	}
	public void deleteMenuSelected(String id){
		Menu menu = query(Menu.class).where("menu_id").eq(id).execute();
		if(menu!=null){
			menu.delete();
		}
	}
	public Menu converMenuDtoToMenu(MenuDto dto){
		Menu menu = new Menu();
		menu.id = menu.getPk();
		menu.description = dto.getDescription();
		menu.imgpath = dto.getImgpath();
		menu.name = dto.getName();
		menu.menu_id = dto.getId();
		menu.parent_id = dto.getParent_id();
		menu.selected = dto.getSelected();
		menu.childFlag = dto.getChildren()!=null && !dto.getChildren().isEmpty() ? "T" :"F";
		
		return menu;
	}
}
