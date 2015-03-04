package com.nrct.application.main;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jakewharton.salvage.ProgressHUD;
import com.jakewharton.salvage.ScreenSizeUntil;
import com.nrct.application.adapter.InformationDetailListAdapter;
import com.nrct.application.animator.ActivityAnimator;
import com.nrct.application.dto.BlogDto;
import com.nrct.application.json.JSONParserForGetList;

@SuppressLint("NewApi") public class InformDetailActivity extends Activity {
	private PullToRefreshListView mPullRefreshListView;
	private ListView actualListView;
	private final Handler handler = new Handler();
	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inform_detail);		
		int color = Color.parseColor("#1B89CA");
		
		String getintent = getIntent().getStringExtra("ActionBarText");
		final String menu_id = getIntent().getStringExtra("menu_id");
		changeColor(color,getintent);
		
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
				new GetAllInformationDetail(menu_id).execute();
			}
		});
		new GetAllInformationDetail(menu_id).execute();
		
		
	}
	private void changeColor(int newColor,String getintent) {

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(
					R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] {
					colorDrawable, bottomDrawable });

			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
				ld.setCallback(drawableCallback);
			} else {
				getActionBar().setBackgroundDrawable(ld);
				getActionBar().setDisplayShowHomeEnabled(false);
				getActionBar().setDisplayShowCustomEnabled(true);
				getActionBar().setDisplayShowTitleEnabled(false);
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LayoutParams layout = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				View view = inflater.inflate(R.layout.actionbar_layout_news_page_2, null);
				getActionBar().setCustomView(view, layout);
				TextView title = (TextView) view.findViewById(R.id.textView1);
				title.setText(getintent);
				
				ImageView backIcon = (ImageView) view.findViewById(R.id.imageView1);
				backIcon.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(InformDetailActivity.this,NrctMainActivity.class);
						startActivity(i);
						finish();
					}
				});
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
	@Override
	public void onBackPressed() {
		back(null);
	}

	public void back(View v) {
		Intent i = new Intent(this,NrctMainActivity.class);
		startActivity(i);
		finish();
		try {
			ActivityAnimator anim = new ActivityAnimator();
			anim.getClass().getMethod(this.getIntent().getExtras().getString("backAnimation")+ "Animation", Activity.class).invoke(anim, this);
		} catch (Exception e) {
			Toast.makeText(this, "An error occured " + e.toString(),Toast.LENGTH_LONG).show();
		}
	}
	
	public class GetAllInformationDetail extends AsyncTask<String, Void, List<BlogDto>> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		String menu_id;
		
		public GetAllInformationDetail(String menu_id){
			this.menu_id = menu_id;
		}
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(InformDetailActivity.this,"Loading Content...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected List<BlogDto> doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<BlogDto> newList = JSONParserForGetList.getInstance().getBlog(menu_id);
			return newList;
		}
		
		@Override
		protected void onPostExecute(List<BlogDto> result) {
			super.onPostExecute(result);
			if (result != null) {
				InformationDetailListAdapter adp = new InformationDetailListAdapter(InformDetailActivity.this,result);
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

