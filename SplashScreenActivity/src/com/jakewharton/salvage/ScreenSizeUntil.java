package com.jakewharton.salvage;

import com.dlt.application.main.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi") public class ScreenSizeUntil {
	private Context _con;
	private final Handler handler = new Handler();
	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	private Activity _act;
	private Class backAct;
	public ScreenSizeUntil(Context con,Activity act) {
		// TODO Auto-generated constructor stub
		this._con = con;
		this._act = act;
	}
	public ScreenSizeUntil(Context con,Activity act,Class backAct) {
		// TODO Auto-generated constructor stub
		this._con = con;
		this._act = act;
		this.backAct = backAct;
	}
	public int getScreenWidth(){
		WindowManager wm = (WindowManager) _con.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size.x;
	}
	public int getScreenHeight(){
		WindowManager wm = (WindowManager) _con.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size.y;
	}
	@SuppressWarnings("unused")
	public void changeColor(int newColor,String str) {

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = _con.getResources().getDrawable(R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] {colorDrawable, bottomDrawable });

			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				ld.setCallback(drawableCallback);
			} else {
				_act.getActionBar().setBackgroundDrawable(ld);
				_act.getActionBar().setDisplayShowHomeEnabled(false);
				_act.getActionBar().setDisplayShowCustomEnabled(true);
				_act.getActionBar().setDisplayShowTitleEnabled(false);
				LayoutInflater inflater = (LayoutInflater) _con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LayoutParams layout = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				View view = inflater.inflate(R.layout.actionbar_inform_detail, null);
				_act.getActionBar().setCustomView(view,layout);
				TextView txtView = (TextView) view.findViewById(R.id.title);
				Typeface type = Typeface.createFromAsset(_con.getAssets(), "fonts/EDPenSook.ttf");
				txtView.setTypeface(type);
				txtView.setText(str);
				ImageView leftMenu = (ImageView) view.findViewById(R.id.leftMenu);
				leftMenu.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						back(null);
					}
				});
				
			}

			oldBackground = ld;

		}

		currentColor = newColor;

	}
	public void back(View v) {
		_act.finish();
		if(backAct!=null){
			Intent intent = new Intent(_act,backAct);
			_act.startActivity(intent);
		}

	}
	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@Override
		public void invalidateDrawable(Drawable who) {
			_act.getActionBar().setBackgroundDrawable(who);
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
}
