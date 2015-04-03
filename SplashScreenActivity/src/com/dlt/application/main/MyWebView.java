package com.dlt.application.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebView extends Fragment {
	
	private WebView webView;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// get the url to open
		Bundle args = getArguments();
		String url = args.getString("url");
		// set up the WebView
		webView = (WebView) getView().findViewById(R.id.webView); 
		webView.getSettings().setLoadsImagesAutomatically(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDefaultTextEncodingName("utf-8");
		webView.getSettings().setAllowFileAccess(true);
		webView.setWebChromeClient(new WebChromeClient());
		String loadWebWithCss = "<html><style type='text/css'>img { max-width:300px;  max-height:300px; height: auto; } table{max-width:300px;} iframe {max-width:305px; height: auto; }</style><body>"+url+"</body></html>";
//		webView.loadUrl("www.google.co.th");
		webView.loadDataWithBaseURL("",loadWebWithCss, "text/html", "UTF-8","");
		Log.d("nrct.string.html", url);
 	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
	    View view = inflater.inflate(R.layout.webview, container, false);
	    return view;		
	}	

}
