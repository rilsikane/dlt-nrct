package com.dlt.application.main;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import com.aphidmobile.flip.FlipViewController;
import com.dlt.application.adapter.FlipViewAdapter;
import com.dlt.application.dto.MenuDto;
import com.dlt.application.dto.SlideDto;
import com.dlt.application.json.JSONParserForGetList;
import com.dlt.application.model.Menu;
import com.dlt.application.model.User;
import com.jakewharton.salvage.ProgressHUD;
import com.roscopeco.ormdroid.ORMDroidApplication;

@SuppressLint("NewApi")
public class NrctMainActivity extends Activity {
	private final Handler handler = new Handler();
	private FlipViewController flipView;
	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	 private long backPressedTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_nrct_main);
		ORMDroidApplication.initialize(NrctMainActivity.this);
		System.gc();
		int color = Color.parseColor("#1B89CA");
		changeColor(color);
		flipView = new FlipViewController(this);
		flipView.setAnimationBitmapFormat(Bitmap.Config.RGB_565);
		new InitFlipView().execute();		
		setContentView(flipView);
	}

	private void changeColor(int newColor) {

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] {colorDrawable, bottomDrawable });

			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
				ld.setCallback(drawableCallback);
			} else {
				getActionBar().setBackgroundDrawable(ld);
				getActionBar().setDisplayShowHomeEnabled(false);
//				getActionBar().setIcon(R.drawable.actionbar_logo);
				getActionBar().setDisplayShowCustomEnabled(true);
				getActionBar().setDisplayShowTitleEnabled(false);
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LayoutParams layout = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				View view = inflater.inflate(R.layout.actionbar_layout, null);
				getActionBar().setCustomView(view,layout);
				ImageView leftArrow = (ImageView) view.findViewById(R.id.imageView1);
				leftArrow.setVisibility(View.GONE);
				ImageView rightMenu = (ImageView) view.findViewById(R.id.rightMenu);
				rightMenu.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(NrctMainActivity.this,RightMenuActivity.class);
						startActivity(intent);
						NrctMainActivity.this.finish();
						overridePendingTransition (R.anim.open_next, R.anim.close_main);
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
	protected void onResume() {
		super.onResume();
		flipView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		flipView.onPause();
	}
	public class InitFlipView extends AsyncTask<String, Void, List<Menu>> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		List<SlideDto> list;
		
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(NrctMainActivity.this,"Loading Content...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected List<Menu> doInBackground(String... params) {
			// TODO Auto-generated method stub
			//JSONParserForGetList json =  JSONParserForGetList.getInstance();
			Menu menu = new Menu();
			List<Menu> menuList = menu.getMenuSelected();
			//for refresh
			if(menuList!=null){
				String token = "";
				User user = new User();
				if(user.getUserLogin()!=null){
					
					token = user.getUserLogin().token;
				}
				
				Map<String,MenuDto> menuTmpMap =  JSONParserForGetList.getInstance().getRefreshMenus(token);
				
				for(Menu menuTmp : menuList){
				
					MenuDto dto = menuTmpMap.get(menuTmp.menu_id);
					menuTmp = menu.converMenuDtoToMenu(dto);
					menuTmp.save();
				}
			}
			Menu temp = new Menu();
			temp.id=888;
			temp.menu_id="888";
			temp.name="";
			menuList.add(temp);
			list = JSONParserForGetList.getInstance().getSlides();
			return menuList;
		}
		
		@Override
		protected void onPostExecute(List<Menu> result) {
			super.onPostExecute(result); 
			if (result != null) {
				flipView.setAdapter(new FlipViewAdapter(NrctMainActivity.this,result,list));
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
	@Override
    public void onBackPressed() {        // to prevent irritating accidental logouts
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, "Press back again to logout",
                                Toast.LENGTH_SHORT).show();
        } else {    // this guy is serious
            // clean up
            super.onBackPressed();       // bye
        }
    }
}
