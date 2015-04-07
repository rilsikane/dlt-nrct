package com.dlt.application.main;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlt.application.animator.ActivityAnimator;
import com.dlt.application.dto.AttachDto;
import com.dlt.application.json.JSONParserForGetList;
import com.dlt.application.model.User;
import com.dlt.application.utils.DateUtil;
import com.jakewharton.salvage.ProgressHUD;
import com.roscopeco.ormdroid.Entity;

public class PublishDocumentPageActivity extends Activity {
	private FrameLayout frame;
	private RelativeLayout layoutAll,layoutOwn;
	private TextView textViewAll,textViewOwn;
	private ListView listForAll,listForOwn;
	private final Handler handler = new Handler();
	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	private String blogId;
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_document_page);
		final int color = Color.parseColor("#1B89CA");
		changeColor(color,getIntent().getStringExtra("ActionBarText"));
		frame = (FrameLayout) findViewById(R.id.frame);
		textViewAll = (TextView) findViewById(R.id.textView2);
		textViewOwn = (TextView) findViewById(R.id.textView3);
		layoutAll = (RelativeLayout) findViewById(R.id.layoutAll);
		layoutAll.setOnClickListener(new LayoutAllListener());
		layoutOwn = (RelativeLayout) findViewById(R.id.layoutOwn);
		layoutOwn.setOnClickListener(new LayoutOwnListener());
		//Initial Button and firstAll list
		layoutAll.setBackground(getResources().getDrawable(R.drawable.boarder));
		textViewAll.setTextColor(Color.parseColor("#ffffff"));
		layoutOwn.setBackground(getResources().getDrawable(R.drawable.boarder_empty));
		textViewOwn.setTextColor(Color.parseColor("#8dc375"));
		LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
		View view = inflater.inflate(R.layout.layout_for_all,frame, true);
		listForAll = (ListView) view.findViewById(R.id.listForAll);
		blogId = getIntent().getStringExtra("blog_id");
		new LoadForAllList().execute();
	}
	
	@SuppressLint("NewApi") public void changeColor(int newColor,String str) {

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getApplicationContext().getResources().getDrawable(R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] {colorDrawable, bottomDrawable });

			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				ld.setCallback(drawableCallback);
			} else {
				getActionBar().setBackgroundDrawable(ld);
				getActionBar().setDisplayShowHomeEnabled(false);
				getActionBar().setDisplayShowCustomEnabled(true);
				getActionBar().setDisplayShowTitleEnabled(false);
				LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LayoutParams layout = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				View view = inflater.inflate(R.layout.actionbar_inform_detail, null);
				getActionBar().setCustomView(view,layout);
				TextView txtView = (TextView) view.findViewById(R.id.title);
				Typeface type = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/EDPenSook.ttf");
				txtView.setTypeface(type);
				txtView.setText(str);
				ImageView leftMenu = (ImageView) view.findViewById(R.id.leftMenu);
				leftMenu.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub						
						
						Intent i = new Intent(PublishDocumentPageActivity.this,ContentMainActivity.class);
						i.putExtra("ActionBarText", getIntent().getStringExtra("ActionBarText"));
						i.putExtra("blog_id", getIntent().getStringExtra("blog_id"));
						i.putExtra("blogDto", getIntent().getSerializableExtra("blogDto"));						
						startActivity(i);
						finish();
					}
				});
				
			}

			oldBackground = ld;

		}

		currentColor = newColor;

	}
	@SuppressLint("NewApi") private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@SuppressLint("NewApi") @Override
		public void invalidateDrawable(Drawable who) {
			PublishDocumentPageActivity.this.getActionBar().setBackgroundDrawable(who);
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
		if(blogId!=null && !"".equals(blogId)){
			this.finish();
		}else{
		Intent i = new Intent(this,NrctMainActivity.class);
		startActivity(i);
		finish();
		}
		try {
			ActivityAnimator anim = new ActivityAnimator();
			anim.getClass().getMethod("fadeAnimation", Activity.class).invoke(anim, this);
		} catch (Exception e) {
			Toast.makeText(this, "An error occured " + e.toString(),Toast.LENGTH_LONG).show();
		}
	}
	@SuppressLint("NewApi") public class LayoutAllListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			frame.removeAllViews();
			layoutAll.setBackground(getResources().getDrawable(R.drawable.boarder));
			textViewAll.setTextColor(Color.parseColor("#ffffff"));
			layoutOwn.setBackground(getResources().getDrawable(R.drawable.boarder_empty));
			textViewOwn.setTextColor(Color.parseColor("#8dc375"));
			
			LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			View view = inflater.inflate(R.layout.layout_for_all,frame, true);
			listForAll = (ListView) view.findViewById(R.id.listForAll);
			new LoadForAllList().execute();
		}
		
	}
	@SuppressLint("NewApi") public class LayoutOwnListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			layoutOwn.setBackground(getResources().getDrawable(R.drawable.boarder));
			textViewOwn.setTextColor(Color.parseColor("#ffffff"));
			layoutAll.setBackground(getResources().getDrawable(R.drawable.boarder_empty));
			textViewAll.setTextColor(Color.parseColor("#8dc375"));
			frame.removeAllViews();
			LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			View view = inflater.inflate(R.layout.layout_for_own,frame, true);
			listForOwn = (ListView) view.findViewById(R.id.listForAll);
			new LoadForOwnList().execute();
		}
		
	}
	public class LoadForOwnList extends AsyncTask<String, Void, List<AttachDto>> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		String menu_id;
		
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(PublishDocumentPageActivity.this,"Loading Content...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected List<AttachDto> doInBackground(String... params) {
			// TODO Auto-generated method stub
//			List<BlogDto> newList = JSONParserForGetList.getInstance().getBlog(menu_id);
			List<AttachDto> attList = new ArrayList<AttachDto>();
			User user = Entity.query(User.class).where("id").eq(1).execute();
			if(user!=null){
				attList = JSONParserForGetList.getInstance().getMyFiles(user.user_id);
			}else{
				attList = JSONParserForGetList.getInstance().getMyFiles("");	
			}
			return attList;
		}
		
		@Override
		protected void onPostExecute(List<AttachDto> result) {
			super.onPostExecute(result);
			if (result != null) {
				ListForAllAdapter lfa = new ListForAllAdapter(result);
				listForOwn.setAdapter(lfa);
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
	public class LoadForAllList extends AsyncTask<String, Void, List<AttachDto>> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		String menu_id;
		
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(PublishDocumentPageActivity.this,"Loading Content...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected List<AttachDto> doInBackground(String... params) {
			// TODO Auto-generated method stub
//			List<BlogDto> newList = JSONParserForGetList.getInstance().getBlog(menu_id);
			List<AttachDto> attList = new ArrayList<AttachDto>();
			if(blogId!=null && !"".equals(blogId)){
				attList = JSONParserForGetList.getInstance().getFilesForBlog(blogId);
			}else{
				attList = JSONParserForGetList.getInstance().getFiles();
			}
			
			return attList;
		}
		
		@Override
		protected void onPostExecute(List<AttachDto> result) {
			super.onPostExecute(result);
			if (result != null) {
				ListForAllAdapter lfa = new ListForAllAdapter(result);
				listForAll.setAdapter(lfa);
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
	public class ListForAllAdapter extends BaseAdapter{
		private List<AttachDto> list;
		private ViewHolderForFirst viewHolder;
		private AttachDto temp;
		public ListForAllAdapter(List<AttachDto> _list){
			this.list = _list;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			try{
				if(convertView== null){
					viewHolder = new ViewHolderForFirst();
					LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
					convertView = inflater.inflate(R.layout.each_attached_layout, null);
					viewHolder.title  = (TextView) convertView.findViewById(R.id.text_item);
					viewHolder.createdBy = (TextView) convertView.findViewById(R.id.createdBy);
					viewHolder.date = (TextView) convertView.findViewById(R.id.createdDate);
					viewHolder.fileSize = (TextView) convertView.findViewById(R.id.textView1);
					viewHolder.image_item = (ImageView) convertView.findViewById(R.id.image_item);
					viewHolder.click_relative = (RelativeLayout) convertView.findViewById(R.id.click_relative);
					
					convertView.setTag(viewHolder);
				}else{
					viewHolder = (ViewHolderForFirst) convertView.getTag();
				}
				temp = list.get(position);
				
				String[] tempName = temp.getFilename().split("\\.");
				
				viewHolder.image_item.setImageDrawable(getResources().getDrawable(getDrawableFileType(tempName[1])));
				viewHolder.title.setText(temp.getFilename());
				viewHolder.createdBy.setText(temp.getOwner_name());
				viewHolder.date.setText(DateUtil.toStringThaiDateFullFormat(temp.getCreate_date()));
				viewHolder.fileSize.setText(temp.getSize()+"");
				viewHolder.click_relative.setOnClickListener(new OnAttachClickListener(temp));
					
				}catch (Exception ex) {
					ex.printStackTrace();
				}
				return convertView;
		}
		
	}
	public class ViewHolderForFirst{
		public TextView title;
		public TextView createdBy;
		public TextView date;
		public RelativeLayout click_relative;
		public TextView fileSize;
		public ImageView image_item;
	}
	public int getDrawableFileType(String fileType){
		int drawable = 0;
		if(fileType.contains("xlsx")){
			drawable = R.drawable.excel_icon;
		}else if(fileType.contains("doc")){
			drawable = R.drawable.word_icon;
		}else if(fileType.contains("pdf")){
			drawable = R.drawable.pdf_icon;
		}else if(fileType.contains("txt")){
			drawable = R.drawable.text_icon;
		}else if(fileType.contains("pptx")){
			drawable = R.drawable.power_point_icon;
		}
		
		return drawable;
	}
	public class AppWebViewClients extends WebViewClient {



	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        // TODO Auto-generated method stub
	        view.loadUrl(url);
	        return true;
	    }

	    @Override
	    public void onPageFinished(WebView view, String url) {
	        // TODO Auto-generated method stub
	        super.onPageFinished(view, url);

	    }
	}
	class OnAttachClickListener implements OnClickListener {
		Intent i ;
		AttachDto atDto;
		// constructor
		public OnAttachClickListener(AttachDto atDto) {
			this.atDto = atDto;
		}

		@Override
		public void onClick(View v) {
			Intent i = new Intent(PublishDocumentPageActivity.this,PubDocContentActivity.class);
			i.putExtra("WEB_URL", atDto.getUrl());
			i.putExtra("ActionBarText", atDto.getFilename());
			startActivity(i);
//			WebView webView=new WebView(PublishDocumentPageActivity.this);	
//			webView.setWebViewClient(new AppWebViewClients());
//			webView.getSettings().setJavaScriptEnabled(true);
//			webView.getSettings().setUseWideViewPort(true);
//			webView.loadUrl(url); 
		}

	}
}
