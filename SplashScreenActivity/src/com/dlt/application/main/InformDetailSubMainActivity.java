package com.dlt.application.main;

import java.io.Serializable;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aphidmobile.flip.FlipViewController;
import com.dlt.application.adapter.InformationDetailListAdapter;
import com.dlt.application.dto.BlogDto;
import com.dlt.application.dto.MenuDto;
import com.dlt.application.json.JSONParserForGetList;
import com.dlt.application.main.InformDetailActivity.GetAllInformationDetail;
import com.jakewharton.salvage.ProgressHUD;

@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
public class InformDetailSubMainActivity extends Activity {
	private WebView webView;
	private final Handler handler = new Handler();
	private FlipViewController flipView;
	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	private MenuDto menuDto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_page);
		System.gc();
		
		webView = (WebView) findViewById(R.id.webView1);
		String getintent = getIntent().getStringExtra("ActionBarText");
		final String menu_id = getIntent().getStringExtra("menu_id");
		
		int color = Color.parseColor("#1B89CA");
		changeColor(color,getintent);
		
		new GetAllInformationDetail(menu_id).execute();
	}

	private void changeColor(int newColor,String header) {

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(
					R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] {
					colorDrawable, bottomDrawable });

			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				ld.setCallback(drawableCallback);
			} else {
				getActionBar().setBackgroundDrawable(ld);
				getActionBar().setDisplayShowHomeEnabled(false);
				getActionBar().setDisplayShowCustomEnabled(true);
				getActionBar().setDisplayShowTitleEnabled(false);
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LayoutParams layout = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				View view = inflater.inflate(R.layout.actionbar_layout_news_page, null);
				getActionBar().setCustomView(view, layout);
				ImageView shareIcon = (ImageView) view.findViewById(R.id.rightMenu);
				TextView title = (TextView) view.findViewById(R.id.textView1);
				title.setText(header);
				shareIcon.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(InformDetailSubMainActivity.this,TaxonomyActivity.class);
						i.putExtra("menuDto",(Serializable) menuDto);
						startActivity(i);
						finish();
					}
				});
				
				
				
				ImageView backIcon = (ImageView) view.findViewById(R.id.imageView1);
				backIcon.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(InformDetailSubMainActivity.this,NrctMainActivity.class);
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
	public class GetAllInformationDetail extends AsyncTask<String, Void, MenuDto> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		String menu_id;
		
		public GetAllInformationDetail(String menu_id){
			this.menu_id = menu_id;
		}
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(InformDetailSubMainActivity.this,"Loading Content...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected MenuDto doInBackground(String... params) {
			// TODO Auto-generated method stub
			MenuDto menuDto = JSONParserForGetList.getInstance().getMenuById(menu_id);
			return menuDto;
		}
		
		@Override
		protected void onPostExecute(MenuDto result) {
			super.onPostExecute(result);
			if (result != null) {
				 webView.getSettings().setJavaScriptEnabled(true);
				 webView.loadDataWithBaseURL("", result.getDescription(), "text/html", "UTF-8", "");
				menuDto = result;
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
