package com.dlt.application.main;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.dlt.application.dto.AttachDto;
import com.dlt.application.dto.BlogDto;
import com.dlt.application.dto.GalleryDto;
import com.dlt.application.json.JSONParserForGetList;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.jakewharton.salvage.ScreenSizeUntil;

@SuppressLint("NewApi")
public class ContentMainActivity extends FragmentActivity {
	private MyPageAdapter pageAdapter = null;
	private ViewPager pager = null;

	private List<Fragment> fragments;
	private List<String> titles;
	private ImageView commentIcon,shareIcon,likeIcon,mindMapBtn,photoBtn,documentBtn,referenceBtn;
	private int i ;
	private BlogDto blogDto;
	private UiLifecycleHelper uiHelper;
	private ArrayList<String> paths;
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	
	 private Session.StatusCallback callback = new Session.StatusCallback() {
		 @Override
	        public void call(Session session, SessionState state,
	                Exception exception) {
	            if (state.isOpened()) {
	               // buttonsEnabled(true);
	                Log.d("FacebookSampleActivity", "Facebook session opened");
	            } else if (state.isClosed()) {
	                //buttonsEnabled(false);
	                Log.d("FacebookSampleActivity", "Facebook session closed");
	            }
	        }
	    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT > 9) { 
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_main);
		final int color = Color.parseColor("#1B89CA");
		
		pageAdapter = new MyPageAdapter(getSupportFragmentManager());
		
		// Add any number of items to the list of your Fragment
		
		
		Intent intent = getIntent();
		blogDto = (BlogDto) intent.getSerializableExtra("blogDto");
		ArrayList<String> test = intent.getStringArrayListExtra("test");
		pageAdapter.addItem(blogDto.getContent(), blogDto.getTitle());
		if(test!=null && test.size()>0){
			for(String s : test){
				pageAdapter.addItem(s, blogDto.getTitle());
			}
		}
		String titleTemp = "";
		if(titles!=null && !"".equals(titles.get(0))){
			titleTemp = titles.get(0).length() >35 ? titles.get(0).substring(0, 35)+"...": titles.get(0);
		}
		new ScreenSizeUntil(getApplicationContext(), this).changeColor(color,titleTemp);
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setOffscreenPageLimit(pageAdapter.getCount() - 1);
		pager.setAdapter(pageAdapter);
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				new ScreenSizeUntil(getApplicationContext(), ContentMainActivity.this).changeColor(color,titles.get(position));
				i=position;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		photoBtn = (ImageView) findViewById(R.id.photoBtn);
		final List<GalleryDto> galleryList = JSONParserForGetList.getInstance().getGalleryForBlog(blogDto.getId());
		if(galleryList!=null && galleryList.size()>0){
			photoBtn.setVisibility(View.VISIBLE);	
			paths = new ArrayList<String>();
			for(GalleryDto dto : galleryList){
				paths.add(dto.getUrl());
			}
			photoBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(ContentMainActivity.this,AndroidGalleryMainActivity.class);
					intent.putExtra("ActionBarTitle", titles.get(i));
					intent.putExtra("blog_id", blogDto.getId());
					intent.putStringArrayListExtra("attList", paths);
					startActivity(intent);
				}
			});
		}else{
			photoBtn.setVisibility(View.INVISIBLE);
		}
			
		
//		documentBtn = (ImageView) findViewById(R.id.documentBtn);
//		final List<AttachDto> attList = JSONParserForGetList.getInstance().getFilesForBlog(blogDto.getId());
//		if(attList!=null && attList.size()>0){
//			documentBtn.setVisibility(View.VISIBLE);
//			documentBtn.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					Intent intent = new Intent(ContentMainActivity.this,PublishDocumentPageActivity.class);
//					intent.putExtra("ActionBarText", titles.get(i));
//					intent.putExtra("blog_id", blogDto.getId());
//					intent.putExtra("blogDto",(Serializable) blogDto);
//					startActivity(intent);
//					finish();
//				}
//			});
//		}else{
//		documentBtn.setVisibility(View.INVISIBLE);
//		}
		
		/*
		 * This button must not be set view to INVISIBLE
		 * Please set to INVISIBLE
		 */
		
		referenceBtn = (ImageView) findViewById(R.id.referenceBtn);
//		referenceBtn.setVisibility(View.INVISIBLE);
		if(blogDto.getRefList()!=null && blogDto.getRefList().size()>0){
			referenceBtn.setVisibility(View.VISIBLE);
			referenceBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(ContentMainActivity.this,ReferenceMainActivity.class);
					intent.putExtra("ActionBarTitle", titles.get(i));
					intent.putExtra("blogDto", blogDto);
					startActivity(intent);
				}
			});	
		}else{
			referenceBtn.setVisibility(View.INVISIBLE);
		}
		
		
		
		mindMapBtn = (ImageView) findViewById(R.id.mindMapBtn);
		if(blogDto.getMindmap()!=null && !"".equals(blogDto.getMindmap())){
		mindMapBtn.setVisibility(View.VISIBLE);
		}else{
		mindMapBtn.setVisibility(View.INVISIBLE);	
		}
		mindMapBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ContentMainActivity.this,MindMapMainActivity.class);
				intent.putExtra("ActionBarTitle", titles.get(i));
				intent.putExtra("blog_id", blogDto.getId());
				intent.putExtra("json", blogDto.getMindmap());
				startActivity(intent);
			}
		});
		
		commentIcon = (ImageView) findViewById(R.id.commentIcon);
		commentIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ContentMainActivity.this,ComentMainActivity.class);
				intent.putExtra("ActionBarTitle", titles.get(i));
				intent.putExtra("blog_id", blogDto.getId());
				startActivity(intent);
			}
		});
		shareIcon = (ImageView) findViewById(R.id.imageView1);
		shareIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				postStatusMessage();
			}
		}); 
		likeIcon = (ImageView) findViewById(R.id.imageView3);
		likeIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				postStatusMessage();
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

	class MyPageAdapter extends FragmentPagerAdapter {
		

		public MyPageAdapter(FragmentManager fm) {
			super(fm);
			fragments = new ArrayList<Fragment>();
			titles = new ArrayList<String>();
		}

		public void addItem(String url, String title) {
			Fragment myFragment = new MyWebView();
			Bundle args = new Bundle();
			args.putString("url", url);
			myFragment.setArguments(args);
			fragments.add(myFragment);
			titles.add(title);
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		public CharSequence getPageTitle(int position) {
			return titles.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}
	}
	
	public boolean checkPermissions() {
        Session s = Session.getActiveSession();
        if (s != null) {
        	if(s.isOpened()){
        		return true;
        	}else{
        		return false;
        	}
//            return s.getPermissions().contains("publish_actions");
        	
        } else
            return false;
    }
	public void postStatusMessage() {
        if (checkPermissions()) {
        	byte[] data = null;

        	Bitmap bi = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        	bi.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        	data = baos.toByteArray();
        	Bundle postParams = new Bundle();
            postParams.putString("name", blogDto.getTitle());
           // postParams.putString("caption", "Build great social apps and get more installs.");
            //postParams.putString("description", blogDto.getContent());
            postParams.putString("link", JSONParserForGetList.getInstance().getLinkHref(blogDto.getContent()));
            postParams.putString("picture", JSONParserForGetList.getInstance().getImageSrc(blogDto.getContent()));
        	
            
            WebDialog feedDialog = (
                    new WebDialog.FeedDialogBuilder(ContentMainActivity.this,
                        Session.getActiveSession(),
                        postParams))
                    .setOnCompleteListener(new OnCompleteListener() {

                        @Override
                        public void onComplete(Bundle values,
                            FacebookException error) {
                            if (error == null) {
                                // When the story is posted, echo the success
                                // and the post Id.
                                final String postId = values.getString("post_id");
                                if (postId != null) {
                                    Toast.makeText(ContentMainActivity.this,
                                        "Posted story, id: "+postId,
                                        Toast.LENGTH_SHORT).show();
                                } else {
                                    // User clicked the Cancel button
//                                    Toast.makeText(ContentMainActivity.this.getApplicationContext(), 
//                                        "Publish cancelled", 
//                                        Toast.LENGTH_SHORT).show();
                                }
                            } else if (error instanceof FacebookOperationCanceledException) {
                                // User clicked the "x" button
//                                Toast.makeText(ContentMainActivity.this.getApplicationContext(), 
//                                    "Publish cancelled", 
//                                    Toast.LENGTH_SHORT).show();
                            } else {
                                // Generic, ex: network error
                                Toast.makeText(ContentMainActivity.this.getApplicationContext(), 
                                    "Error posting story", 
                                    Toast.LENGTH_SHORT).show();
                            }
                        }

                    })
                    .build();
                feedDialog.show();
        } else {
            requestPermissions();
        }
    }
	public void requestPermissions() {
//        Session s = Session.getActiveSession();
//        if (s != null)
//            s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
//                    this, PERMISSIONS));
        Intent i = new Intent(ContentMainActivity.this,LoginPageActivity.class);
        ContentMainActivity.this.startActivity(i);
    }

	 @Override
	    public void onResume() {
	        super.onResume();
	        uiHelper.onResume();
	        //buttonsEnabled(Session.getActiveSession().isOpened());
	    }

	    @Override
	    public void onPause() {
	        super.onPause();
	        uiHelper.onPause();
	    }

	    @Override
	    public void onDestroy() {
	        super.onDestroy();
	        uiHelper.onDestroy();
	    }

	    @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        uiHelper.onActivityResult(requestCode, resultCode, data);
	    }

	    @Override
	    public void onSaveInstanceState(Bundle savedState) {
	        super.onSaveInstanceState(savedState);
	        uiHelper.onSaveInstanceState(savedState);
	    }
}
