package com.nrct.application.main;

import java.io.Serializable;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;
import com.jakewharton.salvage.ProgressHUD;
import com.jakewharton.salvage.ScreenSizeUntil;
import com.nrct.application.dto.CommonDto;
import com.nrct.application.dto.MemberDto;
import com.nrct.application.json.JSONParserForGetList;
import com.nrct.application.model.User;
import com.roscopeco.ormdroid.Entity;

@SuppressLint("NewApi")
public class LoginPageActivity extends FragmentActivity {
	private EditText email, password;
	private Button loginButton;
	private TextView newRegistration;
	String emailTxt,passTxt;
	private LoginButton loginFbButton;
	private UiLifecycleHelper uiHelper;



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
		setContentView(R.layout.activity_login_page);
		final int color = Color.parseColor("#1B89CA");

		new ScreenSizeUntil(getApplicationContext(), this).changeColor(color,
				getIntent().getStringExtra("ActionBarTitle"));
		email = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);
		newRegistration = (TextView) findViewById(R.id.textView3);
		loginFbButton = (LoginButton) findViewById(R.id.imageView1);
		
		
		newRegistration.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(LoginPageActivity.this,RegistrationPageActivity.class);
				i.putExtra("ActionBarTitle", getIntent().getStringExtra("ActionBarTitle"));
				startActivity(i);
			}
		});
		loginButton = (Button) findViewById(R.id.button1);
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				emailTxt = email.getText().toString();
				passTxt = password.getText().toString();

				if (emailTxt != null && passTxt != null) {
					CommonDto dto = new CommonDto(); 
					new CheckLogin(dto).execute();
				}

			}
		});
		loginFbButton.setUserInfoChangedCallback(new UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser userGraph) {
                if (userGraph != null) {
                	User user = new User();
    				user.user_id = userGraph.getId();
    				//user.token = userGraph.getId();
    				user.username = userGraph.getName();
    				user.firstname = userGraph.getName();
    				user.lastname = userGraph.getLastName();
    				user.save();
    				
    				MemberDto memberDto =  new MemberDto();
    				memberDto.setEmail(userGraph.getName());
    				memberDto.setFirstname(userGraph.getFirstName());
    				memberDto.setLastname(userGraph.getLastName());
    				
    				Intent i = new Intent(LoginPageActivity.this,
    						MamberPageActivity.class);
    				i.putExtra("memberDto",(Serializable) memberDto);
    				startActivity(i);
    				
                } else {
                    
                }
            }
        });

	}

	public class CheckLogin extends AsyncTask<String, Void, CommonDto>
			implements OnCancelListener {
		ProgressHUD mProgressHUD;
		private CommonDto dto;

		public CheckLogin(CommonDto dto) {
			this.dto = dto;
		}

		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(LoginPageActivity.this,"Authenticating...", true, true, this);
			super.onPreExecute();
		}

		@Override
		protected CommonDto doInBackground(String... params) {
			// TODO Auto-generated method stub
			// JSONParserForGetList json = JSONParserForGetList.getInstance();
			dto = JSONParserForGetList.getInstance().getAuthorize(emailTxt, passTxt);
			return dto;
		}

		@Override
		protected void onPostExecute(CommonDto result) {
			super.onPostExecute(result);
			if (result.isSuccess()) {
				MemberDto memberDto =  JSONParserForGetList.getInstance().getProfile(dto.getToken());
				Intent i = new Intent(LoginPageActivity.this,
						MamberPageActivity.class);
				i.putExtra("memberDto",(Serializable) memberDto);
				
				User user = Entity.query(User.class).where("id").eq(1).execute();
				
				if(user==null){
					user = new User();
					user.id = 1;
				}
				
				user.user_id = memberDto.getId();
				user.token = dto.getToken();
				user.username = memberDto.getUsername();
				user.firstname = memberDto.getFirstname();
				user.lastname = memberDto.getLastname();
				user.save();
				startActivity(i);
				mProgressHUD.dismiss();
			} else {
				mProgressHUD.dismiss();
				AlertDialog.Builder builder = new AlertDialog.Builder(
						LoginPageActivity.this);
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
			}

		}

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mProgressHUD.dismiss();
		}

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
