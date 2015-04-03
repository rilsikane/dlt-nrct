package com.dlt.application.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlt.application.adapter.ProfKatooListAdapter;
import com.dlt.application.dto.BlogDto;
import com.dlt.application.json.JSONParserForGetList;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jakewharton.salvage.ProgressHUD;
import com.jakewharton.salvage.ScreenSizeUntil;

public class ProfessorContentActivity extends FragmentActivity {
	private PullToRefreshListView mPullRefreshListView;
	private ListView actualListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_professor_content);
		int color = Color.parseColor("#1B89CA");
		new ScreenSizeUntil(getApplicationContext(), this).changeColor(color,getResources().getString(R.string.professor_menu));
		TextView profName = (TextView) findViewById(R.id.professorname);
		profName.setText(getIntent().getStringExtra("ProfessorName"));
		ImageView profImage = (ImageView) findViewById(R.id.imageView1);
		profImage.setImageDrawable(getResources().getDrawable(getIntent().getIntExtra("ProfessorImage", 0)));
		TextView profPosition = (TextView) findViewById(R.id.professorPosition);
		profPosition.setText(getIntent().getStringExtra("ProfessorPosition"));
		final String menu_id = getIntent().getStringExtra("menuId");
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		actualListView = mPullRefreshListView.getRefreshableView();
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				new GetAllComment(menu_id).execute();
			}
		});
		new GetAllComment(menu_id).execute();
		
		Button post = (Button) findViewById(R.id.button1);
		post.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ProfessorContentActivity.this,ProfPostPageActivity.class);
				intent.putExtra("ProfessorName", getIntent().getStringExtra("ProfessorName"));
				intent.putExtra("ProfessorPosition", getIntent().getStringExtra("ProfessorPosition"));
				intent.putExtra("ProfessorImage", getIntent().getIntExtra("ProfessorImage", 0));
				intent.putExtra("ActioBarName", getResources().getString(R.string.question_katoo));
				intent.putExtra("menu_id", menu_id);
				startActivity(intent);
			}
		});
	}

	public class GetAllComment extends AsyncTask<String, Void, List<BlogDto>> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		String menu_id;
		public GetAllComment(String menu_id){
			this.menu_id = menu_id;
		}
		
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(ProfessorContentActivity.this,"Loading Content...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected List<BlogDto> doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<BlogDto> newList = new ArrayList<BlogDto>();
			newList = JSONParserForGetList.getInstance().getBlog(menu_id);
			return newList;
		}
		
		@Override
		protected void onPostExecute(List<BlogDto> result) {
			super.onPostExecute(result);
			if (result != null) {
				ProfKatooListAdapter adp = new ProfKatooListAdapter(ProfessorContentActivity.this,result,getIntent().getStringExtra("ProfessorName"),getIntent().getIntExtra("ProfessorImage", 0),getIntent().getStringExtra("ProfessorPosition"));
				adp.notifyDataSetChanged();
				actualListView.setAdapter(adp);				
				mPullRefreshListView.onRefreshComplete();
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
	
}
