package com.dlt.application.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.dlt.application.adapter.ReferenceListAdapter;
import com.dlt.application.dto.BlogDto;
import com.dlt.application.dto.ReferenceDto;
import com.jakewharton.salvage.ProgressHUD;
import com.jakewharton.salvage.ScreenSizeUntil;

public class ReferenceMainActivity extends Activity {

	private ListView referenceList;
	private BlogDto blogDto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reference_main);
		final int color = Color.parseColor("#1B89CA");
		new ScreenSizeUntil(getApplicationContext(), this).changeColor(color,getIntent().getStringExtra("ActionBarTitle"));
		referenceList = (ListView) findViewById(R.id.referenceList);
		Intent intent = getIntent();
		blogDto = (BlogDto) intent.getSerializableExtra("blogDto");
		new GetAllInformationDetail("0").execute();
	}
	@Override
	public void onBackPressed() {
		back(null);
	}

	public void back(View v) {
		this.finish();
	}
	public class GetAllInformationDetail extends AsyncTask<String, Void, List<ReferenceDto>> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		String menu_id;
		
		public GetAllInformationDetail(String menu_id){
			this.menu_id = menu_id;
		}
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(ReferenceMainActivity.this,"Loading Content...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected List<ReferenceDto> doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			return blogDto.getRefList();
		}
		
		@Override
		protected void onPostExecute(List<ReferenceDto> result) {
			super.onPostExecute(result);
			if (result != null) {
				ReferenceListAdapter adp = new ReferenceListAdapter(ReferenceMainActivity.this,result);
				adp.notifyDataSetChanged();
				referenceList.setAdapter(adp);
				mProgressHUD.dismiss();
			} else {
				mProgressHUD.dismiss();
				(Toast.makeText(getApplicationContext(), "An error occured.", Toast.LENGTH_LONG)).show();
			}
			
		}
		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mProgressHUD.dismiss();
		}

	}
	
	public class MockReference {
		private String refName;
		private String refPath;
		
		public String getRefName() {
			return refName;
		}
		public void setRefName(String refName) {
			this.refName = refName;
		}
		public String getRefPath() {
			return refPath;
		}
		public void setRefPath(String refPath) {
			this.refPath = refPath;
		}
		
		public List<MockReference> getMock(){
			List<MockReference> result = new ArrayList<MockReference>();
			MockReference mock;
			for(int i=0;i<=1;i++){
				 mock = new MockReference();
				 mock.setRefName("Google");
				 mock.setRefPath("http://www.google.com");
				 result.add(mock);
			}			
			return result;
		}
		
	}
}
