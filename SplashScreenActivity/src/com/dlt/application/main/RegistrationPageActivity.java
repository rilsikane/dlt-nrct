package com.dlt.application.main;

import java.io.Serializable;

import com.dlt.application.dto.CommonDto;
import com.dlt.application.dto.MemberDto;
import com.dlt.application.json.JSONParserForGetList;
import com.dlt.application.model.User;
import com.jakewharton.salvage.ScreenSizeUntil;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

public class RegistrationPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_page);
		final int color = Color.parseColor("#1B89CA");

		new ScreenSizeUntil(getApplicationContext(), this).changeColor(color,getIntent().getStringExtra("ActionBarTitle"));
		
		final EditText username = (EditText) findViewById(R.id.editText1);
		final EditText email  = (EditText) findViewById(R.id.editText2);
		final EditText password  = (EditText) findViewById(R.id.editText3);
		final EditText repassword  = (EditText) findViewById(R.id.editText4);
		final EditText firstname  = (EditText) findViewById(R.id.editText5);
		final EditText lastname  = (EditText) findViewById(R.id.editText6);
		Button registerBt = (Button) findViewById(R.id.button1);
		
		registerBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommonDto common = JSONParserForGetList.getInstance().registerProfile(username.getText().toString(), email.getText().toString()
						, password.getText().toString(), repassword.getText().toString(), firstname.getText().toString(), lastname.getText().toString());
				
				if(common.isSuccess()){
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RegistrationPageActivity.this);
	                builder.setTitle(RegistrationPageActivity.this.getResources().getString(R.string.register_success));
	                builder.setInverseBackgroundForced(true);
	                builder.setPositiveButton("OK",
	                        new DialogInterface.OnClickListener() {
	                            @Override
	                            public void onClick(DialogInterface dialog,
	                                    int which) {
	                            	CommonDto dto = JSONParserForGetList.getInstance().getAuthorize(email.getText().toString(), password.getText().toString());
	                            	//Intent i= new Intent(RegistrationPageActivity.this,MamberPageActivity.class);
	                            	MemberDto memberDto =  JSONParserForGetList.getInstance().getProfile(dto.getToken());
	                				//i.putExtra("memberDto",(Serializable) memberDto);
	                				
	                				User user = new User();
	                				user.user_id = memberDto.getId();
	                				user.token = dto.getToken();
	                				user.username = memberDto.getUsername();
	                				user.firstname = memberDto.getFirstname();
	                				user.lastname = memberDto.getLastname();
	                				user.save();
	                				Intent intent = new Intent(RegistrationPageActivity.this,RightMenuActivity.class);
	            					RegistrationPageActivity.this.startActivity(intent);
	            					RegistrationPageActivity.this.finish();
	        						overridePendingTransition (R.anim.open_next, R.anim.close_main);
	                            }
	                        });
	               
	                AlertDialog alert = builder.create();
	                alert.show();
					
				}else{
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RegistrationPageActivity.this);
	                builder.setTitle(common.getDescription());
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
		});
	}

}
