package com.nrct.application.main;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("SetJavaScriptEnabled") public class PubDocContentActivity extends Activity {
	private final Handler handler = new Handler();
	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pub_doc_content);
		final int color = Color.parseColor("#1B89CA");
		changeColor(color,getIntent().getStringExtra("ActionBarText"));
		
		WebView webview = (WebView) findViewById(R.id.webView1);
		webview.setWebViewClient(new AppWebViewClients());
		String url = "http://docs.google.com/gview?embedded=true&url=" + getIntent().getStringExtra("WEB_URL");
		webview.getSettings().setPluginsEnabled(true);
		webview.getSettings().setJavaScriptEnabled(true); 
		webview.loadUrl(url);
		
	}
	public class AppWebViewClients extends WebViewClient {



	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        // TODO Auto-generated method stub
	        view.loadUrl(url);
	        return true;
	    }

	    @Override
	    public void onPageFinished(WebView view, String url) {
	        // TODO Auto-generated method stub
	        super.onPageFinished(view, url);

	    }
	}
	@SuppressLint("NewApi") public void changeColor(int newColor,String str) {

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getApplicationContext().getResources().getDrawable(R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] {colorDrawable, bottomDrawable });

			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
				ld.setCallback(drawableCallback);
			} else {
				getActionBar().setBackgroundDrawable(ld);
				getActionBar().setDisplayShowHomeEnabled(false);
				getActionBar().setDisplayShowCustomEnabled(true);
				getActionBar().setDisplayShowTitleEnabled(false);
				LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LayoutParams layout = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				View view = inflater.inflate(R.layout.actionbar_inform_detail, null);
				getActionBar().setCustomView(view,layout);
				TextView txtView = (TextView) view.findViewById(R.id.title);
				Typeface type = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/EDPenSook.ttf");
				txtView.setTypeface(type);
				txtView.setText(str);
				ImageView leftMenu = (ImageView) view.findViewById(R.id.leftMenu);
				leftMenu.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						PubDocContentActivity.this.finish();
					}
				});
				
			}

			oldBackground = ld;

		}

		currentColor = newColor;

	}
	@SuppressLint("NewApi") private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@SuppressLint("NewApi") @Override
		public void invalidateDrawable(Drawable who) {
			PubDocContentActivity.this.getActionBar().setBackgroundDrawable(who);
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
