package com.nrct.application.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.truba.touchgallery.GalleryWidget.BasePagerAdapter.OnItemChangeListener;
import ru.truba.touchgallery.GalleryWidget.GalleryViewPager;
import ru.truba.touchgallery.GalleryWidget.UrlPagerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidGalleryMainActivity extends Activity {
	private GalleryViewPager mViewPager;
	private TextView init,total;
	private ImageView backIcon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_android_gallery_main);
		mViewPager = (GalleryViewPager)findViewById(R.id.viewer);
//		init = (TextView) findViewById(R.id.init);
//		total = (TextView) findViewById(R.id.total);
		
		backIcon = (ImageView) findViewById(R.id.imageView1);
		Intent intent = getIntent();
		ArrayList<String> attList =intent.getStringArrayListExtra("attList");
        UrlPagerAdapter pagerAdapter = new UrlPagerAdapter(this, attList);
        pagerAdapter.setOnItemChangeListener(new OnItemChangeListener()
		{
			@Override
			public void onItemChange(int currentPosition)
			{
				
				//Toast.makeText(AndroidGalleryMainActivity.this, (currentPosition+1)+" of "+attList.length, Toast.LENGTH_SHORT).show();
				
			}
		});       
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(pagerAdapter);
        
//        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
//			
//			@Override
//			public void onPageSelected(int arg0) {
//				// TODO Auto-generated method stub
//				init.setText(arg0);
//			}
//			
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
        backIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AndroidGalleryMainActivity.this.finish();
			}
		});
	}


}
