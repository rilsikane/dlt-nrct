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

import com.dlt.application.adapter.TaxonomyListAdapter;
import com.dlt.application.dto.BlogDto;
import com.dlt.application.dto.MenuDto;
import com.dlt.application.model.User;
import com.jakewharton.salvage.ProgressHUD;
import com.jakewharton.salvage.ScreenSizeUntil;
import com.roscopeco.ormdroid.Entity;

@SuppressLint("NewApi") 
public class TaxonomyActivity extends Activity {
	private ListView menuList;
	private MenuDto menuDto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_taxonomy_menu);
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
		
		Intent intent = getIntent();
		menuDto = (MenuDto) intent.getSerializableExtra("menuDto");
		
		new FetchMenuList((60*height)/100).execute();
		
		
		
		ImageView contentInthisCate = (ImageView) findViewById(R.id.imageView2);
		contentInthisCate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		gobackIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				overridePendingTransition(R.anim.open_main, R.anim.close_next);
				Intent intent = new Intent(getApplicationContext(), InformDetailSubMainActivity.class);
				intent.putExtra("backAnimation", "fade");
				intent.putExtra("ActionBarText", menuDto.getName());
				intent.putExtra("menu_id", menuDto.getId());
				startActivity(intent);
				
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.open_main, R.anim.close_next);
		Intent intent = new Intent(this,InformDetailSubMainActivity.class);
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
	public class FetchMenuList extends AsyncTask<String, Void, List<MenuDto>> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		private int _listHeight;
		
		public FetchMenuList(int listheight){
			this._listHeight = listheight/5;
		}
		
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(TaxonomyActivity.this,"Loading Content...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected List<MenuDto> doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			
			List<MenuDto> newList = new ArrayList<MenuDto>();
			if(menuDto!=null){
				newList = menuDto.getChildren();
			}
			return newList;
		}
		
		@Override
		protected void onPostExecute(List<MenuDto> result) {
			super.onPostExecute(result);
			if (result != null) {
				TaxonomyListAdapter adapter = new TaxonomyListAdapter(TaxonomyActivity.this, result,_listHeight);
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
