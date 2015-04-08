package com.dlt.application.main;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint({ "SetJavaScriptEnabled", "NewApi" }) public class ContactUsActivity extends Activity {
//	private GoogleMap googleMap;
	private double latitude = 13.798966;
	private double longitude = 100.553194;
	private final Handler handler = new Handler();
	WebView wv;
	private static final String MAP_URL = "http://gmaps-samples.googlecode.com/svn/trunk/articles-android-webmap/simple-android-map.html";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cotact_us);
		final int color = Color.parseColor("#1B89CA");
		changeColor(color,getIntent().getStringExtra("ActionBarTitle"));
		try{
			initilizeMap();
		}catch(Exception e){
			e.printStackTrace();
		}
//		getLocation();
//	    setupWebView();
	}
	private void initilizeMap() {
//        if (googleMap == null) {
//            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
//                        
//            // create marker
//            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("");
//            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.rounded_corners_pin));
//            // adding marker
//            googleMap.addMarker(marker);
//            
//            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(17).build();
//            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//            
//            googleMap.setMyLocationEnabled(false);
//            // check if map is created successfully or not
//            if (googleMap == null) {
//                Toast.makeText(getApplicationContext(),"Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
//            }
//        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
 
	@SuppressLint("NewApi") public void changeColor(int newColor,String str) {

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] {colorDrawable, bottomDrawable });

			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				ld.setCallback(drawableCallback);
			} else {
				getActionBar().setBackgroundDrawable(ld);
				getActionBar().setDisplayShowHomeEnabled(false);
				getActionBar().setDisplayShowCustomEnabled(true);
				getActionBar().setDisplayShowTitleEnabled(false);
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LayoutParams layout = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				View view = inflater.inflate(R.layout.actionbar_layout_news_page_2, null);
				getActionBar().setCustomView(view,layout);
				TextView txtView = (TextView) view.findViewById(R.id.textView1);
//				Typeface type = Typeface.createFromAsset(getAssets(), "fonts/EDPenSook.ttf");
//				txtView.setTypeface(type);
				txtView.setText(str);
				ImageView leftMenu = (ImageView) view.findViewById(R.id.imageView1);
				leftMenu.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(ContactUsActivity.this,RightMenuActivity.class);
						startActivity(i);
						finish();
					}
				});
				
			}

		}

	}
	
	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@SuppressLint("NewApi") @Override
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
