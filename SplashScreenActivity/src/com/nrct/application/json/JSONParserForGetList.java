package com.nrct.application.json;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.nrct.application.dto.AttachDto;
import com.nrct.application.dto.BlogDto;
import com.nrct.application.dto.CommentDto;
import com.nrct.application.dto.CommonDto;
import com.nrct.application.dto.GalleryDto;
import com.nrct.application.dto.MemberDto;
import com.nrct.application.dto.MenuDto;
import com.nrct.application.dto.ReferenceDto;
import com.nrct.application.dto.SlideDto;
import com.nrct.application.main.NrctMainActivity;
import com.nrct.application.main.R;
import com.nrct.application.model.User;
import com.nrct.application.utils.GlobalVariable;

import android.app.Activity;
import android.text.Html;
import android.util.Log;


public class JSONParserForGetList {
	public Activity a;
	public String news_cat;
	private static JSONParserForGetList instance = null;
	private static HttpClient httpclient = null;
	// constructor
	protected JSONParserForGetList() {
	      // Exists only to defeat instantiation.
	}
	public static JSONParserForGetList getInstance() {
	      if(instance == null) {
	         instance = new JSONParserForGetList();
	      }
	      return instance;
	}
	public static HttpClient getClientInstance(){
		  if(httpclient == null) {
			  httpclient = new DefaultHttpClient();
		      }
		      return httpclient;
	}
	
	public List<MenuDto> getMenus(String token) {
		List<MenuDto> menuList = new ArrayList<MenuDto>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("token", token));
		JSONArray json = getJsonArrayFromUrlDoPost(GlobalVariable.URL_MENU_ALL,nameValuePairs,"list");
		if (json != null) {
			JSONArray one = json;
			try {
				for (int i = 0; i < one.length(); i++) {
					
					JSONObject menuJson = one.getJSONObject(i);
					if(menuJson!=null){
					if("62".equals(menuJson.getJSONObject("Menu").get("id"))){
						JSONObject menuObj = menuJson.getJSONObject("Menu");
						MenuDto menu = new MenuDto();
						menu.setId(menuObj.getString("id"));
						menu.setName(menuObj.getString("name"));
						menu.setParent_id(menuObj.getString("parent_id"));
						menu.setPublish(menuObj.getBoolean("publish"));
						menu.setUrl(menuObj.getString("url"));
						menu.setForBlog(menuObj.getBoolean("forBlog"));
						menu.setImgpath("http://dlt.agter-technology.co.th/api/app/webroot/img/menus/"+menuObj.getString("id")+".jpg");
						
						if(menuJson.has("Children")){
							List<MenuDto> childList = new ArrayList<MenuDto>();
							JSONArray chilArray = menuJson.getJSONArray("Children");
							if(chilArray!=null && chilArray.length()>0){
								for (int j = 0; j < chilArray.length(); j++) {
									MenuDto childDto = new MenuDto();
									JSONObject child = chilArray.getJSONObject(j);
									JSONObject childMenu = child.getJSONObject("Menu");
									childDto.setId(childMenu.getString("id"));
									childDto.setName(childMenu.getString("name"));
									childDto.setParent_id(childMenu.getString("parent_id"));
									childDto.setPublish(childMenu.getBoolean("publish"));
									childDto.setUrl(childMenu.getString("url"));
									childDto.setForBlog(childMenu.getBoolean("forBlog"));
									childList.add(childDto);
								}
								menu.setChildren(childList);
							}
						}
						menuList.add(menu);
					}else{
						
						JSONObject menuObj = menuJson.getJSONObject("Menu");
						if(!"18".equals(menuObj.getString("id")) && !"19".equals(menuObj.getString("id"))){
							MenuDto menu = new MenuDto();
							menu.setId(menuObj.getString("id"));
							menu.setName(menuObj.getString("name"));
							menu.setParent_id(menuObj.getString("parent_id"));
							menu.setPublish(menuObj.getBoolean("publish"));
							menu.setUrl(menuObj.getString("url"));
							menu.setForBlog(menuObj.getBoolean("forBlog"));
							menu.setImgpath("http://dlt.agter-technology.co.th/api/app/webroot/img/menus/"+menuObj.getString("id")+".jpg");
							menuList.add(menu);
						}
					}
					}

				}
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		return menuList;
	}
	public MenuDto getMenuById(String menu_id) {
		MenuDto menuDto = new MenuDto();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		String token = "";
		User user = new User();
		if(user.getUserLogin()!=null){
			
			token = user.getUserLogin().token;
		}
		nameValuePairs.add(new BasicNameValuePair("token", token));
		JSONArray json = getJsonArrayFromUrlDoPost(GlobalVariable.URL_MENU_ALL,nameValuePairs,"list");
		if (json != null) {
			JSONArray one = json;
			try {
				for (int i = 0; i < one.length(); i++) {
					
					JSONObject menuJson = one.getJSONObject(i);
					if(menuJson!=null){
					if(menu_id.equals(menuJson.getJSONObject("Menu").get("id"))){
						JSONObject menuObj = menuJson.getJSONObject("Menu");
						MenuDto menu = new MenuDto();
						menu.setId(menuObj.getString("id"));
						menu.setName(menuObj.getString("name"));
						menu.setParent_id(menuObj.getString("parent_id"));
						menu.setPublish(menuObj.getBoolean("publish"));
						menu.setUrl(menuObj.getString("url"));
						menu.setForBlog(menuObj.getBoolean("forBlog"));
						menu.setImgpath("http://dlt.agter-technology.co.th/api/app/webroot/img/menus/"+menuObj.getString("id")+".jpg");
						menu.setDescription(menuObj.getString("description"));
						
						if(menuJson.has("Children")){
							List<MenuDto> childList = new ArrayList<MenuDto>();
							JSONArray chilArray = menuJson.getJSONArray("Children");
							if(chilArray!=null && chilArray.length()>0){
								for (int j = 0; j < chilArray.length(); j++) {
									MenuDto childDto = new MenuDto();
									JSONObject child = chilArray.getJSONObject(j);
									JSONObject childMenu = child.getJSONObject("Menu");
									childDto.setId(childMenu.getString("id"));
									childDto.setName(childMenu.getString("name"));
									childDto.setParent_id(childMenu.getString("parent_id"));
									childDto.setPublish(childMenu.getBoolean("publish"));
									childDto.setUrl(childMenu.getString("url"));
									childDto.setForBlog(childMenu.getBoolean("forBlog"));
									childList.add(childDto);
								}
								menu.setChildren(childList);
							}
						}
						menuDto = menu;
						break;
					}
					

				}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		return menuDto;
	}
	public Map<String,MenuDto> getRefreshMenus(String token) {
		Map<String,MenuDto> menuList = new HashMap<String, MenuDto>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("token", token));
		JSONArray json = getJsonArrayFromUrlDoPost(GlobalVariable.URL_MENU_ALL,nameValuePairs,"list");
		if (json != null) {
			JSONArray one = json;
			try {
				for (int i = 0; i < one.length(); i++) {
					
					JSONObject menuJson = one.getJSONObject(i);
					if(menuJson!=null){
					if("62".equals(menuJson.getJSONObject("Menu").get("id"))){
						JSONObject menuObj = menuJson.getJSONObject("Menu");
						MenuDto menu = new MenuDto();
						menu.setId(menuObj.getString("id"));
						menu.setName(menuObj.getString("name"));
						menu.setParent_id(menuObj.getString("parent_id"));
						menu.setPublish(menuObj.getBoolean("publish"));
						menu.setUrl(menuObj.getString("url"));
						menu.setForBlog(menuObj.getBoolean("forBlog"));
						menu.setImgpath("http://dlt.agter-technology.co.th/api/app/webroot/img/menus/"+menuObj.getString("id")+".jpg");

						menuList.put(menuObj.getString("id"),menu);
					}else{
						
						JSONObject menuObj = menuJson.getJSONObject("Menu");
						if(!"18".equals(menuObj.getString("id")) && !"19".equals(menuObj.getString("id"))){
							MenuDto menu = new MenuDto();
							menu.setId(menuObj.getString("id"));
							menu.setName(menuObj.getString("name"));
							menu.setParent_id(menuObj.getString("parent_id"));
							menu.setPublish(menuObj.getBoolean("publish"));
							menu.setUrl(menuObj.getString("url"));
							menu.setForBlog(menuObj.getBoolean("forBlog"));
							menu.setImgpath("http://dlt.agter-technology.co.th/api/app/webroot/img/menus/"+menuObj.getString("id")+".jpg");
							menuList.put(menuObj.getString("id"),menu);
						}
					}
					}

				}
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		return menuList;
	}

	public List<BlogDto> getBlog(String menu_id) {
		List<BlogDto> blogList = new ArrayList<BlogDto>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		String token = "";
		User user = new User();
		if(user.getUserLogin()!=null){
			
			token = user.getUserLogin().token;
		}
		nameValuePairs.add(new BasicNameValuePair("menu_id", menu_id));
		nameValuePairs.add(new BasicNameValuePair("token", token));
		nameValuePairs.add(new BasicNameValuePair("order", "desc"));
		JSONArray json = getJsonfromURLDoPost(GlobalVariable.URL_BLOG, nameValuePairs, "list");
		if (json != null) {
			JSONArray one = json;
			try {
				for (int i = 0; i < one.length(); i++) {
					
					JSONObject listJson = one.getJSONObject(i);
					if(listJson!=null){
						JSONObject blogJson = listJson.getJSONObject("Blog");
						if(blogJson!=null){
							BlogDto blogDto = new BlogDto();
							blogDto.setId(blogJson.getString("id"));
							blogDto.setTitle(blogJson.getString("title"));
							blogDto.setContent(blogJson.getString("content"));
							blogDto.setCreate_date(new Date(blogJson.getLong("update_date")* 1000));
							blogDto.setImgUrl(getImageSrc(blogJson.getString("content")));
							JSONObject authorJson = listJson.getJSONObject("Author");
							if(authorJson!=null){
								blogDto.setAuthor_name(authorJson.getString("firstname")+"  "+authorJson.getString("lastname"));
							}
							JSONObject mindMapJson = listJson.getJSONObject("Mindmap");
							blogDto.setMindmap(mindMapJson.getString("json"));
							
							blogList.add(blogDto);
						}
					}

				}
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		return blogList;
	}
	public BlogDto getBlogById(String blogId) {
		BlogDto blogDto = new BlogDto();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("blog_id", blogId));
		JSONObject json = getJsonObjfromURLDoPost(GlobalVariable.URL_GETBLOG, nameValuePairs);
		if (json != null) {
			try {
					
						JSONObject blogJson = json.getJSONObject("Blog");
						if(blogJson!=null){
							blogDto.setId(blogJson.getString("id"));
							blogDto.setTitle(blogJson.getString("title"));
							blogDto.setContent(blogJson.getString("content"));
							blogDto.setCreate_date(new Date(blogJson.getLong("update_date")* 1000));
							JSONObject authorJson = json.getJSONObject("Author");
							if(authorJson!=null){
								blogDto.setAuthor_name(authorJson.getString("firstname")+"  "+authorJson.getString("lastname"));
							}
							JSONObject mindMapJson = json.getJSONObject("Mindmap");
							blogDto.setMindmap(mindMapJson.getString("json"));
							

							JSONArray refJsonArray = json.getJSONArray("References");
							List<ReferenceDto> refList = new ArrayList<ReferenceDto>();
							if(refJsonArray!=null){
								for (int j = 0; j < refJsonArray.length(); j++) {
									ReferenceDto refDto = new ReferenceDto();
									JSONObject refJson = refJsonArray.getJSONObject(j);
									refDto.setUrl(refJson.getString("url"));
									refDto.setTitle(refJson.getString("title"));
									refList.add(refDto);
								}
							}
							blogDto.setRefList(refList);
						}
					

				
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		return blogDto;
	}
	public CommonDto writeBlog(String menu_id,String title,String content) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		CommonDto commonDto = new CommonDto();
		String token = "";
		User user = new User();
		if(user.getUserLogin()!=null){
			
			token = user.getUserLogin().token;
		}
		nameValuePairs.add(new BasicNameValuePair("menu_id", menu_id));
		nameValuePairs.add(new BasicNameValuePair("title", title));
		nameValuePairs.add(new BasicNameValuePair("content", content));
		nameValuePairs.add(new BasicNameValuePair("token", token));
		JSONObject json = getJsonObjfromURLDoPost(GlobalVariable.URL_WRITE_BLOG, nameValuePairs);
		if (json != null) {
			try {
				commonDto.setSuccess(json.getBoolean("success"));
				if(!commonDto.isSuccess()){
					JSONObject cause = json.getJSONObject("cause");
					try{
						JSONArray passwords =  cause.getJSONArray("content");
						String pass = (String) passwords.get(0);
						commonDto.setDescription(pass);
					}catch (Exception e) {
						e.printStackTrace();
						try{
							JSONArray firstnames =  cause.getJSONArray("title");
							String first = (String) firstnames.get(0);
							commonDto.setDescription(first);
						}catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}

		}
		return commonDto;
	}
	public CommonDto registerProfile(String username,String email,String password,String confirm_password,String firstname,String lastname) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		CommonDto commonDto = new CommonDto();
		String token = "";
		User user = new User();
		if(user.getUserLogin()!=null){
			
			token = user.getUserLogin().token;
		}
		nameValuePairs.add(new BasicNameValuePair("username", username));
		nameValuePairs.add(new BasicNameValuePair("email", email));
		nameValuePairs.add(new BasicNameValuePair("password", password));
		nameValuePairs.add(new BasicNameValuePair("confirm_password", confirm_password));
		nameValuePairs.add(new BasicNameValuePair("firstname", firstname));
		nameValuePairs.add(new BasicNameValuePair("lastname", lastname));
		JSONObject json = getJsonObjfromURLDoPost(GlobalVariable.URL_REGISTER, nameValuePairs);
		if (json != null) {
			try {
				commonDto.setSuccess(json.getBoolean("success"));
				if(!commonDto.isSuccess()){
					
					try{
						String desc = json.getString("description");
						commonDto.setDescription(desc);
						
					}catch (Exception e) {
						e.printStackTrace();
						JSONObject cause = json.getJSONObject("cause");
						try{
							JSONArray emails =  cause.getJSONArray("email");
							String temp1 = (String) emails.get(0);
							commonDto.setDescription(temp1);
						}catch (Exception ex) {
							ex.printStackTrace();
							try{
								JSONArray usernames =  cause.getJSONArray("username");
								String temp = (String) usernames.get(0);
								commonDto.setDescription(temp);
							}catch (Exception ex1) {
								ex1.printStackTrace();
								try{
									JSONArray passwords =  cause.getJSONArray("password");
									String temp = (String) passwords.get(0);
									commonDto.setDescription(temp);
								}catch (Exception ee) {
									ee.printStackTrace();
								}
							}
						}
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}

		}
		return commonDto;
	}
	public List<CommentDto> getComments(String blog_id) {
		List<CommentDto> comList = new ArrayList<CommentDto>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("blog_id", blog_id));
		JSONArray json = getJsonfromURLDoPost(GlobalVariable.URL_COMMENT, nameValuePairs, "list");
		if (json != null) {
			JSONArray one = json;
			try {
				for (int i = 0; i < one.length(); i++) {
					
					JSONObject listJson = one.getJSONObject(i);
					if(listJson!=null){
						JSONObject blogJson = listJson.getJSONObject("Comment");
						if(blogJson!=null){
							CommentDto dto = new CommentDto();
							dto.setId(blogJson.getString("id"));
							dto.setComments(blogJson.getString("comments"));
							dto.setCreate_date(new Date(blogJson.getLong("create_date")*1000));
							JSONObject memberJson = listJson.getJSONObject("Member");
							if(memberJson!=null){
								dto.setCommentName(memberJson.getString("firstname")+"  "+memberJson.getString("lastname"));
							}
							comList.add(dto);
						}
					}

				}
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		return comList;
	}
	public List<AttachDto> getFiles() {
		List<AttachDto> attList = new ArrayList<AttachDto>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		JSONArray json = getJsonfromURLDoPostForAttach(GlobalVariable.URL_FILES, nameValuePairs, "list");
		if (json != null) {
			JSONArray one = json;
			try {
				for (int i = 0; i < one.length(); i++) {
					JSONObject listJson = one.getJSONObject(i);
					if(listJson!=null){
						JSONObject attJson = listJson.getJSONObject("File");
						AttachDto attDto = new AttachDto();
						if(attJson!=null){
							
							attDto.setId(attJson.getString("id"));
							attDto.setFilename(attJson.getString("filename"));
							attDto.setOwner_id(attJson.getString("owner_id"));
							attDto.setSize(Double.parseDouble(attJson.getString("size_kb")));
							attDto.setCreate_date(new Date(attJson.getLong("create_date")*1000));
							attDto.setUrl(attJson.getString("url"));
						}
						JSONObject memJson = listJson.getJSONObject("Member");
						if(memJson!=null){
							attDto.setOwner_name(memJson.getString("firstname")+"  "+memJson.getString("lastname"));
							attList.add(attDto);
						}
					}
				}

				
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		return attList;
	}
	public List<AttachDto> getFilesForBlog(String blogId) {
		List<AttachDto> attList = new ArrayList<AttachDto>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("blog_id", blogId));
		JSONArray json = getJsonfromURLDoPostForAttachBlog(GlobalVariable.URL_FILES, nameValuePairs, "list");
		if (json != null) {
			JSONArray one = json;
			try {
				for (int i = 0; i < one.length(); i++) {
					JSONObject listJson = one.getJSONObject(i);
					if(listJson!=null){
						JSONObject attJson = listJson.getJSONObject("File");
						AttachDto attDto = new AttachDto();
						if(attJson!=null){
							
							attDto.setId(attJson.getString("id"));
							attDto.setFilename(attJson.getString("filename"));
							attDto.setOwner_id(attJson.getString("owner_id"));
							attDto.setSize(Double.parseDouble(attJson.getString("size_kb")));
							attDto.setCreate_date(new Date(attJson.getLong("create_date")*1000));
							attDto.setUrl(attJson.getString("url"));
						}
						JSONObject memJson = listJson.getJSONObject("Member");
						if(memJson!=null){
							attDto.setOwner_name(memJson.getString("firstname")+"  "+memJson.getString("lastname"));
							attList.add(attDto);
						}
					}
				}

				
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		return attList;
	}
	public List<AttachDto> getMyFiles(String userId) {
		List<AttachDto> attList = new ArrayList<AttachDto>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		JSONArray json = getJsonfromURLDoPostForAttachBlog(GlobalVariable.URL_FILES, nameValuePairs, "list");
		if (json != null) {
			JSONArray one = json;
			try {
				for (int i = 0; i < one.length(); i++) {
					JSONObject listJson = one.getJSONObject(i);
					if(listJson!=null){
						JSONObject memJson = listJson.getJSONObject("Member");
						if(memJson!=null){
							if(userId.equals(memJson.get("id"))){
							JSONObject attJson = listJson.getJSONObject("File");
							AttachDto attDto = new AttachDto();
							if(attJson!=null){
								
								attDto.setId(attJson.getString("id"));
								attDto.setFilename(attJson.getString("filename"));
								attDto.setOwner_id(attJson.getString("owner_id"));
								attDto.setSize(Double.parseDouble(attJson.getString("size_kb")));
								attDto.setCreate_date(new Date(attJson.getLong("create_date")*1000));
								attDto.setUrl(attJson.getString("url"));
							}
							attDto.setOwner_name(memJson.getString("firstname")+"  "+memJson.getString("lastname"));
							attList.add(attDto);
							}
						}
						
					}
				}

				
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		return attList;
	}
	public List<GalleryDto> getGalleryForBlog(String blogId) {
		List<GalleryDto> attList = new ArrayList<GalleryDto>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("blog_id", blogId));
		JSONArray json = getJsonfromURLDoPost(GlobalVariable.URL_GALLERY, nameValuePairs, "images");
		if (json != null) {
			JSONArray one = json;
			try {
				for (int i = 0; i < one.length(); i++) {
					JSONObject listJson = one.getJSONObject(i);
					if(listJson!=null){
						GalleryDto galleryDto = new GalleryDto();
						galleryDto.setThumbnail(listJson.getString("thumbnail"));
						galleryDto.setUrl(listJson.getString("url"));
						attList.add(galleryDto);
					}
				}

				
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		return attList;
	}
	public CommonDto writeComment(String blog_id,String comments,String token){
		CommonDto commonDto = new CommonDto();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("blog_id", blog_id));
		nameValuePairs.add(new BasicNameValuePair("comments", comments));
		nameValuePairs.add(new BasicNameValuePair("token", token));
		JSONObject json = getJsonObjfromURLDoPost(GlobalVariable.URL_WRITE_COMMENT, nameValuePairs);
		
		if (json != null) {
			try {
				commonDto.setSuccess(json.getBoolean("success"));
				if(!commonDto.isSuccess()){
					commonDto.setDescription(json.getString("description"));
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return commonDto;
	}
	public CommonDto editComment(String comment_id,String token,String comments){
		CommonDto commonDto = new CommonDto();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("comment_id", comment_id));
		nameValuePairs.add(new BasicNameValuePair("comments", comments));
		nameValuePairs.add(new BasicNameValuePair("token", token));
		JSONObject json = getJsonObjfromURLDoPost(GlobalVariable.URL_UPDATE_COMMENT, nameValuePairs);
		
		if (json != null) {
			try {
				commonDto.setSuccess(json.getBoolean("success"));
				if(!commonDto.isSuccess()){
					commonDto.setDescription(json.getString("description"));
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return commonDto;
	}
	public CommonDto delComment(String comment_id,String token){
		CommonDto commonDto = new CommonDto();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("comment_id", comment_id));
		nameValuePairs.add(new BasicNameValuePair("token", token));
		JSONObject json = getJsonObjfromURLDoPost(GlobalVariable.URL_DELETE_COMMENT, nameValuePairs);
		
		if (json != null) {
			try {
				commonDto.setSuccess(json.getBoolean("success"));
				if(!commonDto.isSuccess()){
					commonDto.setDescription(json.getString("description"));
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return commonDto;
	}
	public CommonDto getAuthorize(String email,String password){
		CommonDto commonDto = new CommonDto();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("email", email));
		nameValuePairs.add(new BasicNameValuePair("password", password));
		JSONObject json = getJsonObjfromURLDoPost(GlobalVariable.URL_AUTHORIZE, nameValuePairs);
		
		if (json != null) {
			try {
				commonDto.setSuccess(json.getBoolean("success"));
				if(!commonDto.isSuccess()){
					commonDto.setDescription(json.getString("description"));
				}else{
					commonDto.setToken(json.getString("token"));
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return commonDto;
	}
	public CommonDto getUpdateProfile(String password,String confirm_password,String firstname,String lastname){
		CommonDto commonDto = new CommonDto();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		String token = "";
		User user = new User();
		if(user.getUserLogin()!=null){
			
			token = user.getUserLogin().token;
		}
		if(isNotEmpty(password)){
		nameValuePairs.add(new BasicNameValuePair("password", password));
		}
		if(isNotEmpty(confirm_password)){
		nameValuePairs.add(new BasicNameValuePair("confirm_password", confirm_password));
		}
		if(isNotEmpty(firstname)){
		nameValuePairs.add(new BasicNameValuePair("firstname", firstname));
		}
		if(isNotEmpty(lastname)){
		nameValuePairs.add(new BasicNameValuePair("lastname", lastname));
		}
		nameValuePairs.add(new BasicNameValuePair("token", token));
		JSONObject json = getJsonObjfromURLDoPost(GlobalVariable.URL_UPDATE_PROFILE, nameValuePairs);
		
		if (json != null) {
			try {
				commonDto.setSuccess(json.getBoolean("success"));
				if(!commonDto.isSuccess()){
					JSONObject cause = json.getJSONObject("cause");
					try{
						JSONArray passwords =  cause.getJSONArray("password");
						String pass = (String) passwords.get(0);
						commonDto.setDescription(pass);
					}catch (Exception e) {
						e.printStackTrace();
						try{
							JSONArray firstnames =  cause.getJSONArray("firstname");
							String first = (String) firstnames.get(0);
							commonDto.setDescription(first);
						}catch (Exception ex) {
							ex.printStackTrace();
							try{
								JSONArray lastnames =  cause.getJSONArray("lastname");
								String last = (String) lastnames.get(0);
								commonDto.setDescription(last);
							}catch (Exception x) {
								x.printStackTrace();
							}
						}
					}
					//commonDto.setDescription();
				}else{
					commonDto.setToken(json.getString("token"));
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return commonDto;
	}

	public MemberDto getProfile(String token){
		CommonDto commonDto = new CommonDto();
		MemberDto memberDto = new MemberDto();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("token", token));
		JSONObject json = getJsonObjfromURLDoPost(GlobalVariable.URL_PROFILE, nameValuePairs);
		
		if (json != null) {
			try {
				commonDto.setSuccess(json.getBoolean("success"));
				if(!commonDto.isSuccess()){
					commonDto.setDescription(json.getString("description"));
				}else{
					JSONObject memberJson = json.getJSONObject("Member");
					if(memberJson!=null){
					memberDto.setId(memberJson.getString("id"));
					memberDto.setUsername(memberJson.getString("username"));
					memberDto.setEmail(memberJson.getString("email"));
					memberDto.setFirstname(memberJson.getString("firstname"));
					memberDto.setLastname(memberJson.getString("lastname"));
					memberDto.setRole_id(memberJson.getString("role_id"));
					memberDto.setCreate_date(new Date(memberJson.getLong("create_date")*1000));
					memberDto.setUpdate_date(new Date(memberJson.getLong("update_date")*1000));
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return memberDto;
	}
	
	public List<SlideDto> getSlides() {
		List<SlideDto> menuList = new ArrayList<SlideDto>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		JSONArray json = getJsonArrayFromUrlDoGet(GlobalVariable.URL_GETSLIDE,"list");
		if (json != null) {
			JSONArray one = json;
			try {
				for (int i = 0; i < one.length(); i++) {
					
					JSONObject menuJson = one.getJSONObject(i);
					if(menuJson!=null){
						JSONObject menuObj = menuJson.getJSONObject("Slider");
						SlideDto menu = new SlideDto();
						menu.setId(menuObj.getString("id"));
						menu.setOrder(menuObj.getString("order"));
						menu.setUrl(menuObj.getString("url"));
						menu.setImg_url(menuObj.getString("img_url"));
						menuList.add(menu);
					
					}

				}
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		return menuList;
	}

	
	
	public JSONObject getJsonObjfromURLDoPost(String url,
			List<NameValuePair> nameValuePairs) {

		InputStream is = null;
		String result = "";
		JSONObject jObject = null;
		try {
			
			HttpClient httpclient = getClientInstance();
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				// System.out.println("Show Result::::"+line);
			}
			is.close();
			result = sb.toString();
			jObject = new JSONObject(result);
			
	
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}
	

		return jObject;
	}
	public JSONArray getJsonfromURLDoPostForAttachBlog(String url,
			List<NameValuePair> nameValuePairs,String jsonArray) {

		InputStream is = null;
		String result = "";
		JSONObject jObject = null;
		JSONArray jArray = null;
		try {
			
			HttpClient httpclient = getClientInstance();
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				// System.out.println("Show Result::::"+line);
			}
			is.close();
			result = sb.toString();
			jObject = new JSONObject(result);
			
	
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}
		try {
			jArray=jObject.getJSONArray(jsonArray);
		} catch (Exception e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}

		return jArray;
	}
	
	public JSONArray getJsonfromURLDoPostForAttach(String url,
			List<NameValuePair> nameValuePairs,String jsonArray) {

		InputStream is = null;
		String result = "";
		JSONObject jObject = null;
		JSONArray jArray = null;
		try {
			
			HttpClient httpclient = getClientInstance();
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				// System.out.println("Show Result::::"+line);
			}
			is.close();
			result = sb.toString();
			jObject = new JSONObject(result);
			
	
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}
		try {
			JSONObject json = jObject.getJSONObject(jsonArray);
			jArray=json.getJSONArray("admin");
		} catch (Exception e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}

		return jArray;
	}
	
	public JSONArray getJsonfromURLDoPost(String url,
			List<NameValuePair> nameValuePairs,String jsonArray) {

		InputStream is = null;
		String result = "";
		JSONObject jObject = null;
		JSONArray jArray = null;
		try {
			
			HttpClient httpclient = getClientInstance();
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				// System.out.println("Show Result::::"+line);
			}
			is.close();
			result = sb.toString();
			jObject = new JSONObject(result);
			
	
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}
		try {
			jArray = jObject.getJSONArray(jsonArray);
		} catch (Exception e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}

		return jArray;
	}
	

	public JSONArray getJsonArrayFromUrlDoGet(String url,String jsonArray) {

		InputStream is = null;
		String result = "";
		JSONArray jArray = null;
		JSONObject jObject = null;
		try {
			HttpClient httpclient = getClientInstance();
			HttpGet httppost = new HttpGet(url);

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				// System.out.println("Show Result::::"+line);
			}
			is.close();
			result = sb.toString();
			jObject = new JSONObject(result);
			
	
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		try {
			jArray = jObject.getJSONArray(jsonArray);
		} catch (Exception e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return jArray;
	}

	public JSONArray getJsonArrayFromUrlDoPost(String url,List<NameValuePair> nameValuePairs,String jsonArray) {

		InputStream is = null;
		String result = "";
		JSONArray jArray = null;
		JSONObject jObject = null;
		try {
			HttpClient httpclient = getClientInstance();
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				// System.out.println("Show Result::::"+line);
			}
			is.close();
			result = sb.toString();
			jObject = new JSONObject(result);
			
	
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		try {
			jArray = jObject.getJSONArray(jsonArray);
		} catch (Exception e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return jArray;
	}

	public String getImageSrc(String s){
		String word0= "";
		if(s != null && !"".equals(s)){
		Pattern p=null;
		Matcher m= null;
		
		String word1= null;
		p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
		m = p.matcher(s);
		if (m.find()) {
			word0 = m.group(1);
		}
		}
		return word0;
	}
	public String getLinkHref(String s){
		Pattern p = Pattern.compile("href=\"(.*?)\"");
		Matcher m = p.matcher(s);
		String url = null;
		if (m.find()) {
		    url = m.group(1); // this variable should contain the link URL
		}
		return url;
	}
	
	public long getDiffDay(Date d){
		 long diffDays = 0L;
		if(d!=null){
			Calendar calendar1 = Calendar.getInstance();
		    Calendar calendar2 = Calendar.getInstance();
		    calendar1.set(calendar1.get(Calendar.YEAR), d.getMonth(), d.getDate());
		    long milliseconds1 = calendar1.getTimeInMillis();
		    long milliseconds2 = calendar2.getTimeInMillis();
		    long diff = milliseconds2 - milliseconds1;
		    diffDays = diff / (24 * 60 * 60 * 1000);
		}
		return diffDays;
	}
	
	public String stripHtml(String html) {
	    return Html.fromHtml(html).toString();
	}
	public boolean isNotEmpty(String s){
		boolean isNotEmpty = false;
		if(s!=null && !"".equals(s)){
			isNotEmpty = true;
		}
		return isNotEmpty;
	}
}