package com.nrct.application.main;

import java.io.Serializable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

		new ScreenSizeUntil(getApplicationContext(), this, RightMenuActivity.class).changeColor(color,getResources().getString(R.string.edit_text_2));
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
				            //clear your preferences if saved
//				        }
				    }
				
			}
		});
	}


}
