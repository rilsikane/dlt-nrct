package com.nrct.application.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.jakewharton.salvage.ProgressHUD;
import com.jakewharton.salvage.ScreenSizeUntil;
import com.nrct.application.adapter.SearchListAdapter;
import com.nrct.application.dto.BlogDto;
import com.nrct.application.json.JSONParserForGetList;
import com.nrct.application.model.Menu;

public class SearchPageActivity extends Activity {
	private PullToRefreshListView mPullRefreshListView;
	private ListView actualListView;
	private List<BlogDto> blogList;
	private EditText cri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_page);
		final int color = Color.parseColor("#1B89CA");
		new ScreenSizeUntil(getApplicationContext(), this).changeColor(color,getIntent().getStringExtra("ActionBarTitle"));
		
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
				new GetSearchInformation().execute();
			}
		});
		mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				Toast.makeText(SearchPageActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
			}
		});
		cri = (EditText) findViewById(R.id.editText1);
		Button searchBtn = (Button) findViewById(R.id.button1);
		searchBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new SearchByCriteria(cri.getText().toString()).execute();
			}
		});
		new GetSearchInformation().execute();
		
	}
	public class GetSearchInformation extends AsyncTask<String, Void, List<BlogDto>> implements OnCancelListener{
		ProgressHUD mProgressHUD;

    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(SearchPageActivity.this,getResources().getString(R.string.search_menu_text), true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected List<BlogDto> doInBackground(String... params) {
			// TODO Auto-generated method stub
			Menu menu = new Menu();
			List<Menu> menuList = menu.getMenuSelected();
			List<BlogDto> blogList = new ArrayList<BlogDto>();
			if(menuList!=null && menuList.size()>0){
				for(Menu m : menuList){
					blogList.addAll(JSONParserForGetList.getInstance().getBlog(m.menu_id));
				}
			}
			
			
			return blogList;
		}
		
		@Override
		protected void onPostExecute(List<BlogDto> result) {
			super.onPostExecute(result);
			if (result != null) {
				
				mProgressHUD.dismiss();
				blogList = result;
			}
			
		}
		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mProgressHUD.dismiss();
		}

	}
	public class SearchByCriteria extends AsyncTask<String, Void, List<BlogDto>> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		String criteria;
		
		public SearchByCriteria(String criteria){
			this.criteria = criteria;
		}
		
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(SearchPageActivity.this,getResources().getString(R.string.search_menu_text), true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected List<BlogDto> doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<BlogDto> newList = new ArrayList<BlogDto>();
			if(blogList!=null && blogList.size()>0){
				for(BlogDto blog : blogList){
					if(blog.getTitle().contains(criteria)){
						newList.add(blog);
					}
				}
			}
			
			return newList;
		}
		
		@Override
		protected void onPostExecute(List<BlogDto> result) {
			super.onPostExecute(result);
			if (result != null) {
				SearchListAdapter adp = new SearchListAdapter(SearchPageActivity.this,result);
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
