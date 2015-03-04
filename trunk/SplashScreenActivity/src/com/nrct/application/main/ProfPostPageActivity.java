package com.nrct.application.main;

import com.jakewharton.salvage.ScreenSizeUntil;
import com.nrct.application.dto.CommonDto;
import com.nrct.application.json.JSONParserForGetList;

import android.os.Bundle;
import android.os.StrictMode;
import android.preference.EditTextPreference;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
@SuppressLint("NewApi")
public class ProfPostPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		setContentView(R.layout.activity_prof_post_page);
		
		int color = Color.parseColor("#1B89CA");
		new ScreenSizeUntil(getApplicationContext(), this).changeColor(color,getIntent().getStringExtra("ActioBarName"));
		
		TextView profName = (TextView) findViewById(R.id.professorname);
		profName.setText(getIntent().getStringExtra("ProfessorName"));
		ImageView profImage = (ImageView) findViewById(R.id.imageView1);
		profImage.setImageDrawable(getResources().getDrawable(getIntent().getIntExtra("ProfessorImage", 0)));
		TextView profPosition = (TextView) findViewById(R.id.professorPosition);
		profPosition.setText(getIntent().getStringExtra("ProfessorPosition"));
		final String menu_id = getIntent().getStringExtra("menu_id");
		final EditText title = (EditText) findViewById(R.id.editText1);
		final EditText content = (EditText) findViewById(R.id.editText2);
		
		Button createBt = (Button) findViewById(R.id.imageView2);
		
		createBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommonDto common = JSONParserForGetList.getInstance().writeBlog(menu_id, title.getText().toString(), content.getText().toString());
				
				if(common.isSuccess()){
					Intent i = new Intent(getApplicationContext(),ProfessorContentActivity.class);
					i.putExtra("ProfessorName",getIntent().getStringExtra("ProfessorName"));
					i.putExtra("ProfessorImage", getIntent().getIntExtra("ProfessorImage", 0));
					i.putExtra("ProfessorPosition", getIntent().getStringExtra("ProfessorPosition"));
					i.putExtra("menuId", menu_id);
					ProfPostPageActivity.this.startActivity(i);
				}else{
					AlertDialog.Builder builder = new AlertDialog.Builder(
							ProfPostPageActivity.this);
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
