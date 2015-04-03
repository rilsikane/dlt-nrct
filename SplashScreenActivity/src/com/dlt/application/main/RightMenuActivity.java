package com.dlt.application.main;


import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dlt.application.adapter.RightMenuListAdapter;
import com.dlt.application.model.User;
import com.jakewharton.salvage.ProgressHUD;
import com.jakewharton.salvage.ScreenSizeUntil;
import com.roscopeco.ormdroid.Entity;

@SuppressLint("NewApi") 
public class RightMenuActivity extends Activity {
	private ListView menuList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_right_menu);
		int height = new ScreenSizeUntil(getApplicationContext(),this).getScreenHeight();
		menuList = (ListView) findViewById(R.id.menuList);
		LayoutParams lp = menuList.getLayoutParams();
		lp.height = (60*height)/100;
		menuList.setLayoutParams(lp);
		
		RelativeLayout relative = (RelativeLayout) findViewById(R.id.relativeBack);
		LayoutParams ilp = relative.getLayoutParams();
		ilp.height = (40*height)/100;
		relative.setLayoutParams(ilp);
		
		ImageView gobackIcon = (ImageView) findViewById(R.id.gobackIcon);
		LayoutParams glp = gobackIcon.getLayoutParams();
		glp.height = (30*((40*height)/100))/100;
		glp.width = (30*((40*height)/100))/100;
		gobackIcon.setLayoutParams(glp);
		
		new FetchMenuList((60*height)/100).execute();
		
		gobackIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {												
				Intent intent = new Intent(getApplicationContext(),NrctMainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.open_main, R.anim.close_next);
				finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.open_main, R.anim.close_next);
		Intent intent = new Intent(this,NrctMainActivity.class);
		startActivity(intent);
		this.finish();
	}

	public class MockMenuList {
		private String menuName;
		private int imgDrawable;

		public int getImgDrawable() {
			return imgDrawable;
		}

		public void setImgDrawable(int imgDrawable) {
			this.imgDrawable = imgDrawable;
		}

		public String getMenuName() {
			return menuName;
		}

		public void setMenuName(String menuName) {
			this.menuName = menuName;
		}
		
	}
	public class FetchMenuList extends AsyncTask<String, Void, List<MockMenuList>> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		private int _listHeight;
		
		public FetchMenuList(int listheight){
			this._listHeight = listheight/5;
		}
		
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(RightMenuActivity.this,"Loading Content...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected List<MockMenuList> doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			
			List<MockMenuList> newList = new ArrayList<MockMenuList>();
			MockMenuList mock;
			if(Entity.query(User.class).executeMulti() != null && Entity.query(User.class).executeMulti().size()>0){
				User user = Entity.query(User.class).executeMulti().get(0);
				
				mock = new MockMenuList();
				mock.setMenuName(user.firstname!=null && !"".equals(user.firstname) ? user.firstname+" "+user.lastname : user.username);
				mock.setImgDrawable(R.drawable.comment_profile);
				newList.add(mock);
				
			}else{
				mock = new MockMenuList();
				mock.setMenuName(getApplicationContext().getResources().getString(R.string.login_menu_text));
				mock.setImgDrawable(R.drawable.login_icon);
				newList.add(mock);
			}
			mock = new MockMenuList();
			mock.setMenuName(getApplicationContext().getResources().getString(R.string.search_menu_text));
			mock.setImgDrawable(R.drawable.search_icon);
			newList.add(mock);
			mock = new MockMenuList();
			mock.setMenuName(getApplicationContext().getResources().getString(R.string.add_kms_menu_text));
			mock.setImgDrawable(R.drawable.add_detail_kms);
			newList.add(mock);
			mock = new MockMenuList();
			mock.setMenuName(getApplicationContext().getResources().getString(R.string.contact_menu_text));
			mock.setImgDrawable(R.drawable.contact_us);
			newList.add(mock);
			mock = new MockMenuList();
			mock.setMenuName(getApplicationContext().getResources().getString(R.string.about_menu_text));
			mock.setImgDrawable(R.drawable.about_us);
			newList.add(mock);
			return newList;
		}
		
		@Override
		protected void onPostExecute(List<MockMenuList> result) {
			super.onPostExecute(result);
			if (result != null) {
				RightMenuListAdapter adapter = new RightMenuListAdapter(RightMenuActivity.this, result,_listHeight);
				menuList.setAdapter(adapter);
				mProgressHUD.dismiss();
			} else {
				mProgressHUD.dismiss();
				(Toast.makeText(getApplicationContext(), "An error occured.", Toast.LENGTH_LONG)).show();
			}
			
		}
		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mProgressHUD.dismiss();
		}

	}
}
