package com.dlt.application.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 1000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		System.gc();
//		ImageView logo = (ImageView) findViewById(R.id.logo);
//		int h = (20*new ScreenSizeUntil(getApplicationContext(),this).getScreenHeight())/100;
//		int w = (40*new ScreenSizeUntil(getApplicationContext(),this).getScreenWidth())/100;
//		logo.setMinimumWidth(w);
//		logo.setMinimumHeight(h); 
		/* New Handler to start the Menu-Activity 
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreenActivity.this,NrctMainActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
	}

}
