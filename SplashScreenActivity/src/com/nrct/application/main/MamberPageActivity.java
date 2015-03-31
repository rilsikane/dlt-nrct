package com.nrct.application.main;

import java.io.Serializable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aphidmobile.flip.FlipViewController;
import com.facebook.Session;
import com.jakewharton.salvage.ScreenSizeUntil;
import com.nrct.application.dto.CommonDto;
import com.nrct.application.dto.MemberDto;
import com.nrct.application.json.JSONParserForGetList;
import com.nrct.application.model.User;
@SuppressLint("NewApi")
public class MamberPageActivity extends Activity {
	private Button logoutBtn;
	private Button editBtn;
	private final Handler handler = new Handler();
	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		setContentView(R.layout.activity_mamber_page);
		final int color = Color.parseColor("#1B89CA");

//		new ScreenSizeUntil(getApplicationContext(), this, RightMenuActivity.class).changeColor(color,getResources().getString(R.string.edit_text_2));
		changeColor(color);
		MemberDto memberDto = (MemberDto) getIntent().getSerializableExtra("memberDto");

		
		TextView username = (TextView) findViewById(R.id.textView1);
		final EditText firstname  = (EditText) findViewById(R.id.editText1);
		final EditText lastName  = (EditText) findViewById(R.id.editText2);
		final EditText password  = (EditText) findViewById(R.id.editText3);
		final EditText confirm  = (EditText) findViewById(R.id.editText4);
		
		username.setText(memberDto.getUsername());
		firstname.setText(memberDto.getFirstname());
		lastName.setText(memberDto.getLastname());
		
		logoutBtn = (Button) findViewById(R.id.button2);
		editBtn = (Button) findViewById(R.id.button1);
		
		editBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommonDto dto = JSONParserForGetList.getInstance().getUpdateProfile(password.getText().toString(), confirm.getText().toString()
						, firstname.getText().toString(), lastName.getText().toString());
				if(!dto.isSuccess()){
					AlertDialog.Builder builder = new AlertDialog.Builder(
							MamberPageActivity.this);
	                builder.setTitle(dto.getDescription());
	                builder.setInverseBackgroundForced(true);
	                builder.setPositiveButton("OK",
	                        new DialogInterface.OnClickListener() {
	                            @Override
	                            public void onClick(DialogInterface dialog,
	                                    int which) {
	                                dialog.dismiss();
	                            }
	                        });
	               
	                AlertDialog alert = builder.create();
	                alert.show();
				}else{
					User user = new User();
					user = user.getUserLogin();
					User newUser = new User();
					Intent i = new Intent(MamberPageActivity.this,
							MamberPageActivity.class);
					
					newUser.user_id = user.user_id;
					newUser.token = user.token;
					newUser.username = user.username;
					newUser.firstname = firstname.getText().toString();
					newUser.lastname = lastName.getText().toString();
					user.removeAll();
					newUser.save();
					i.putExtra("memberDto",(Serializable) newUser.convertToMember(newUser.getUserLogin()));
					startActivity(i);
				}
			}
		});
		
		logoutBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				User user = new User();
				user.removeAll();
				 Session session = Session.getActiveSession();
				    if (session != null) {
//				        if (!session.isClosed()) {
				            session.closeAndClearTokenInformation();
				            MamberPageActivity.this.finish();
							Intent intent = new Intent(getApplicationContext(),RightMenuActivity.class);
							startActivity(intent);
							finish();
				            //clear your preferences if saved
//				        }
				    }
				
			}
		});
	}
	@Override
	public void onBackPressed(){		            
		Intent intent = new Intent(getApplicationContext(),RightMenuActivity.class);
		startActivity(intent);
		finish();		         		   
	}
	private void changeColor(int newColor) {

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] {colorDrawable, bottomDrawable });

			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
				ld.setCallback(drawableCallback);
			} else {
				getActionBar().setBackgroundDrawable(ld);
				getActionBar().setDisplayShowHomeEnabled(false);
				getActionBar().setDisplayShowCustomEnabled(true);
				getActionBar().setDisplayShowTitleEnabled(false);
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LayoutParams layout = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				View view = inflater.inflate(R.layout.actionbar_layout, null);
				getActionBar().setCustomView(view,layout);
				ImageView leftArrow = (ImageView) view.findViewById(R.id.imageView1);
				leftArrow.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(MamberPageActivity.this,RightMenuActivity.class);
						startActivity(intent);
						MamberPageActivity.this.finish();
						overridePendingTransition (R.anim.open_next, R.anim.close_main);
					}
				});
				TextView title = (TextView) view.findViewById(R.id.textView321);
				title.setText(getResources().getString(R.string.edit_text_2));
				ImageView rightMenu = (ImageView) view.findViewById(R.id.rightMenu);
				rightMenu.setVisibility(View.GONE);
			}

			oldBackground = ld;

		}

		currentColor = newColor;

	}
	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@Override
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

}
