package com.dlt.application.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.jakewharton.salvage.ScreenSizeUntil;

@SuppressLint("SetJavaScriptEnabled") 
public class ReferenceDetailMainActivity extends Activity {
    private WebView webview;
    private static final String TAG = "Main";
    private ProgressDialog progressBar; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reference_detail_main);
		final int color = Color.parseColor("#1B89CA");
		new ScreenSizeUntil(getApplicationContext(), this).changeColor(color,getIntent().getStringExtra("ActionBarTitle"));
		
		webview = (WebView) findViewById(R.id.webView1);
		 WebSettings settings = webview.getSettings();
	     settings.setJavaScriptEnabled(true);
	     webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
	     final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

	        progressBar = ProgressDialog.show(ReferenceDetailMainActivity.this, "Getting Content", "Loading...");

	        webview.setWebViewClient(new WebViewClient() {
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {
	                view.loadUrl(url);
	                return true;
	            }

	            public void onPageFinished(WebView view, String url) {
	                if (progressBar.isShowing()) {
	                    progressBar.dismiss();
	                }
	            }

	            @SuppressWarnings("deprecation")
				public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
	                Log.e(TAG, "Error: " + description);
	                Toast.makeText(ReferenceDetailMainActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
	                alertDialog.setTitle("Error");
	                alertDialog.setMessage(description);
	                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        return;
	                    }
	                });
	                alertDialog.show();
	            }
	        });
	        webview.loadUrl("http://"+getIntent().getStringExtra("RefDetail"));
		
	}
	@Override
	public void onBackPressed() {
		back(null);
	}

	public void back(View v) {
		this.finish();
	}

}
