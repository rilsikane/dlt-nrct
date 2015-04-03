package com.dlt.application.main;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
public class KatooContentActivity extends FragmentActivity {
	private UiLifecycleHelper uiHelper;
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
		setContentView(R.layout.activity_katoo_content);
		final int color = Color.parseColor("#1B89CA");
		new ScreenSizeUntil(getApplicationContext(), this).changeColor(color,getIntent().getStringExtra("KatooName"));
		
		ImageView profImage = (ImageView) findViewById(R.id.imageView1);
		profImage.setImageDrawable(getResources().getDrawable(getIntent().getIntExtra("ProfessorImage", 0)));
		TextView profName = (TextView) findViewById(R.id.professorname);
		profName.setText(getIntent().getStringExtra("ProfessorName"));
		TextView profPosition = (TextView) findViewById(R.id.professorPosition);
		profPosition.setText(getIntent().getStringExtra("ProfessorPosition"));
		TextView katooDetail = (TextView) findViewById(R.id.katooDetail);
		katooDetail.setText(Html.fromHtml(getIntent().getStringExtra("KatooDetail")).toString());
		
		ImageView commentClick = (ImageView) findViewById(R.id.imageView3);
		commentClick.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(KatooContentActivity.this,ComentMainActivity.class);
				i.putExtra("ActionBarTitle", getIntent().getStringExtra("KatooName"));
				i.putExtra("blog_id", getIntent().getStringExtra("KatooId"));
				startActivity(i);
			}
		});
		
		ImageView shareIcon = (ImageView) findViewById(R.id.imageView4);
		shareIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				postStatusMessage();
			}
		});
	}
	public boolean checkPermissions() {
        Session s = Session.getActiveSession();
        if (s != null) {
            return s.getPermissions().contains("publish_actions");
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
            postParams.putString("name",  getIntent().getStringExtra("KatooName"));
           postParams.putString("caption", getIntent().getStringExtra("ProfessorName"));
            postParams.putString("description", getIntent().getStringExtra("ProfessorPosition"));
            //postParams.putString("link", "http://www.nrct.go.th/th");
            postParams.putByteArray("picture", data);
        	
            
            WebDialog feedDialog = (
                    new WebDialog.FeedDialogBuilder(KatooContentActivity.this,
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
                                    Toast.makeText(KatooContentActivity.this,
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
                                Toast.makeText(KatooContentActivity.this.getApplicationContext(), 
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
        Session s = Session.getActiveSession();
        if (s != null)
            s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
                    this, PERMISSIONS));
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
