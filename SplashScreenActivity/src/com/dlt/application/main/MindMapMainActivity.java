package com.dlt.application.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jakewharton.salvage.ScreenSizeUntil;

@SuppressLint("SetJavaScriptEnabled") public class MindMapMainActivity extends Activity {

	private WebView mindMapView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mind_map_main);
		final int color = Color.parseColor("#1B89CA");
		new ScreenSizeUntil(getApplicationContext(), this).changeColor(color,getIntent().getStringExtra("ActionBarTitle"));
		final String json = getIntent().getStringExtra("json");
		mindMapView = (WebView) findViewById(R.id.mindMapView);
		WebSettings webSettings = mindMapView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		mindMapView.loadUrl("file:///android_asset/index.html");
		mindMapView.setWebViewClient(new WebViewClient(){
		    public void onPageFinished(WebView view, String url){   
		    	mindMapView.loadUrl("javascript:init('" + json + "')"); 
		    }           
		});
	}

	@Override
	public void onBackPressed() {
		back(null);
	}

	public void back(View v) {
		this.finish();
	}
}
