package com.dlt.application.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;

import com.jakewharton.salvage.ScreenSizeUntil;

@SuppressLint("SetJavaScriptEnabled") public class AboutPageActivity extends Activity {
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_page);
		final int color = Color.parseColor("#1B89CA");

		new ScreenSizeUntil(getApplicationContext(), this).changeColor(color,getIntent().getStringExtra("ActionBarTitle"));
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(getResources().getString(R.string.about_link_page));
	}


}
