package com.nrct.application.main;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.jakewharton.salvage.ProgressHUD;
import com.jakewharton.salvage.ScreenSizeUntil;
import com.nrct.application.adapter.KmsMenuListAdapter;
import com.nrct.application.dto.MenuDto;
import com.nrct.application.json.JSONParserForGetList;
import com.nrct.application.model.User;

@SuppressLint("NewApi") public class KMSMenuActivity extends Activity {

	private ListView kmsMenuList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kms_menu);
		final int color = Color.parseColor("#1B89CA");

		new ScreenSizeUntil(getApplicationContext(), this, RightMenuActivity.class).changeColor(color,getIntent().getStringExtra("ActionBarTitle"));
		
		kmsMenuList = (ListView) findViewById(R.id.kmsmenuList);
		
		new FetchKMSMenu().execute();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
//		overridePendingTransition(R.anim.open_main, R.anim.close_next);
		back(null);
	}
	public void back(View v) {
		Intent i = null ;
		if(getIntent().getIntExtra("StateFrom", 0)==0){
			i= new Intent(this,RightMenuActivity.class);
		}else{
			i= new Intent(this,NrctMainActivity.class);
		}
		startActivity(i);
		this.finish();
	}
	public class FetchKMSMenu extends AsyncTask<String, Void, List<MenuDto>> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(KMSMenuActivity.this,"Loading Content...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected List<MenuDto> doInBackground(String... params) {
			// TODO Auto-generated method stub
			JSONParserForGetList json =  JSONParserForGetList.getInstance();
			String token = "";
			User user = new User();
			if(user.getUserLogin()!=null){
				
				token = user.getUserLogin().token;
			}
			
			List<MenuDto> menuList = json.getMenus(token);
//			MenuDto menu = new MenuDto();
//			menu.setId("999");
//			menu.setName(getResources().getString(R.string.professor_menu));
//			menuList.add(menu);
			return menuList;
		}
		
		@Override
		protected void onPostExecute(List<MenuDto> result) {
			super.onPostExecute(result);
			if (result != null) {
				KmsMenuListAdapter jmla = new KmsMenuListAdapter(KMSMenuActivity.this, result);
				kmsMenuList.setAdapter(jmla);
				mProgressHUD.dismiss();
			} else {
				mProgressHUD.dismiss();
				(Toast.makeText(getApplicationContext(), "An error occured..", Toast.LENGTH_LONG)).show();
			}
			
		}
		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mProgressHUD.dismiss();
		}

	}
	public class MockKMSMenu {
		private String menuName;

		public String getMenuName() {
			return menuName;
		}

		public void setMenuName(String menuName) {
			this.menuName = menuName;
		}
		
	}
}
