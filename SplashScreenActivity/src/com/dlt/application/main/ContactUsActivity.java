package com.dlt.application.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jakewharton.salvage.ScreenSizeUntil;

@SuppressLint("SetJavaScriptEnabled") public class ContactUsActivity extends Activity implements LocationListener {
	private Location mostRecentLocation;
	WebView wv;
	private static final String MAP_URL = "http://gmaps-samples.googlecode.com/svn/trunk/articles-android-webmap/simple-android-map.html";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cotact_us);
		final int color = Color.parseColor("#1B89CA");
		new ScreenSizeUntil(getApplicationContext(), this).changeColor(color,
				getIntent().getStringExtra("ActionBarTitle"));
		getLocation();
	    setupWebView();
	}

	/**
	 * The Location Manager manages location providers. This code searches for
	 * the best provider of data (GPS, WiFi/cell phone tower lookup, some other
	 * mechanism) and finds the last known location.
	 **/
	private void getLocation() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String provider = locationManager.getBestProvider(criteria, true);

		// In order to make sure the device is getting location, request
		// updates. locationManager.requestLocationUpdates(provider, 1, 0,
		// this);
		mostRecentLocation = locationManager.getLastKnownLocation(provider);
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		mostRecentLocation = arg0;
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	/** Sets up the WebView object and loads the URL of the page **/
	private void setupWebView() {
		final String centerURL = "javascript:centerAt(13.798966,100.553194)";
		wv = (WebView) findViewById(R.id.webView1);
		
		wv.getSettings().setJavaScriptEnabled(true);
		// Wait for the page to load then send the location information
		wv.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				wv.loadUrl(centerURL);
			}

		});
		wv.setInitialScale(100);
		wv.loadUrl(MAP_URL);

	}
}
