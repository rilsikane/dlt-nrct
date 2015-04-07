package com.dlt.application.main;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlt.application.adapter.KmsMenuListAdapter;
import com.dlt.application.dto.MenuDto;
import com.dlt.application.json.JSONParserForGetList;
import com.dlt.application.model.User;
import com.jakewharton.salvage.ProgressHUD;
import com.jakewharton.salvage.ScreenSizeUntil;

@SuppressLint("NewApi") public class KMSMenuActivity extends Activity {

	private ListView kmsMenuList;
	private final Handler handler = new Handler();
	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kms_menu);
		final int color = Color.parseColor("#1B89CA");

		changeColor(color,getIntent().getStringExtra("ActionBarTitle"));
		
		kmsMenuList = (ListView) findViewById(R.id.kmsmenuList);
		
		new FetchKMSMenu().execute();
	}
	
	private void changeColor(int newColor,String getintent) {

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(
					R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] {
					colorDrawable, bottomDrawable });

			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				ld.setCallback(drawableCallback);
			} else {
				getActionBar().setBackgroundDrawable(ld);
				getActionBar().setDisplayShowHomeEnabled(false);
				getActionBar().setDisplayShowCustomEnabled(true);
				getActionBar().setDisplayShowTitleEnabled(false);
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LayoutParams layout = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				View view = inflater.inflate(R.layout.actionbar_layout_menu_page, null);
				getActionBar().setCustomView(view, layout);
				TextView title = (TextView) view.findViewById(R.id.textView1);
				title.setText(getintent);
				
				TextView backIcon = (TextView) view.findViewById(R.id.imageView1);
				backIcon.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(KMSMenuActivity.this,RightMenuActivity.class);
						startActivity(i);
						finish();
					}
				});
			}

			oldBackground = ld;

		}

		currentColor = newColor;

	}

	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@Override
		public void invalidateDrawable(Drawable who) {
			getActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			handler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
			handler.removeCallbacks(what);
		}
	};
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
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
