package com.dlt.application.main;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dlt.application.adapter.CommentListAdapter;
import com.dlt.application.dto.CommentDto;
import com.dlt.application.dto.CommonDto;
import com.dlt.application.json.JSONParserForGetList;
import com.dlt.application.model.User;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jakewharton.salvage.ActionItem;
import com.jakewharton.salvage.ProgressHUD;
import com.jakewharton.salvage.QuickAction;
import com.jakewharton.salvage.ScreenSizeUntil;

@SuppressLint("NewApi") 
	public class ComentMainActivity extends Activity {
	private int ID_EDIT =0;
	private int ID_DELETE =1;
	private PullToRefreshListView mPullRefreshListView;
	private ListView actualListView;
	private TextView sendButton;
	private EditText editText;
	private int position;
	private CommentListAdapter adp;
	private CommentDto comment;
	private String comment_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		      StrictMode.setThreadPolicy(policy);
		 }
		setContentView(R.layout.activity_coment_main);
		final int color = Color.parseColor("#1B89CA");

		new ScreenSizeUntil(getApplicationContext(), this).changeColor(color,getIntent().getStringExtra("ActionBarTitle"));
		final String blog_id = getIntent().getStringExtra("blog_id");
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		sendButton = (TextView) findViewById(R.id.sendButton);
		editText = (EditText) findViewById(R.id.messageInput);
		actualListView = mPullRefreshListView.getRefreshableView();
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				new GetAllComment(blog_id).execute();
			}
		});
		new GetAllComment(blog_id).execute();
		
		ActionItem addItem 		= new ActionItem(ID_EDIT, getResources().getString(R.string.edit_text), getResources().getDrawable(R.drawable.edit26));
		ActionItem acceptItem 	= new ActionItem(ID_DELETE, getResources().getString(R.string.delete_text), getResources().getDrawable(R.drawable.delete23));
		final QuickAction mQuickAction 	= new QuickAction(this);
		
		mQuickAction.addActionItem(addItem);
		mQuickAction.addActionItem(acceptItem);
		
		mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction quickAction, int pos, int actionId) {
				ActionItem actionItem = quickAction.getActionItem(pos);
				
				if (actionId == ID_EDIT) { //Add item selected
					comment =  (CommentDto) adp.getItem(position-1);
					comment_id = comment.getId();
					editText.setText(comment.getComments());
					sendButton.setText(getResources().getString(R.string.edit_text));
				}
				if (actionId == ID_DELETE) { //Add item selected
					comment =  (CommentDto) adp.getItem(position-1);
					AlertDialog.Builder builder = new AlertDialog.Builder(ComentMainActivity.this);
					builder.setMessage(getResources().getString(R.string.confirm_del))
					       .setCancelable(false)
					       
					       .setPositiveButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					                dialog.cancel();
					           }
					       })
					       .setNegativeButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   User user = new User();
					        	   String token = "";
					        	   if(user.getUserLogin()!=null){
					        		   token = user.getUserLogin().token;
					        	   }
					        	   CommonDto common = JSONParserForGetList.getInstance().delComment(comment.getId(),token);
					        	   if(common.isSuccess()){
										new GetAllComment(blog_id).execute();
									}else{
										AlertDialog.Builder builder = new AlertDialog.Builder(
												ComentMainActivity.this);
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
					AlertDialog alert = builder.create();
					alert.show();
				}
			}
		});
		
		//setup on dismiss listener, set the icon back to normal
		mQuickAction.setOnDismissListener(new PopupWindow.OnDismissListener() {			
			@Override
			public void onDismiss() {
				
			}
		});
		
		actualListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				position = arg2;
				mQuickAction.show(arg1);
				return false;
			}
		});
		
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String token = "";
				JSONParserForGetList json = JSONParserForGetList.getInstance();
				User user = new User();
				if(user.getUserLogin()!=null){
					
					token = user.getUserLogin().token;
				}
				CommonDto common = null;
				if(comment_id==null || "".equals(comment_id)){
					common = json.writeComment(blog_id, editText.getText().toString(),token);
				}else{
					common = json.editComment(comment_id,token,editText.getText().toString());	
					sendButton.setText(getResources().getString(R.string.add_text));
					comment_id="";
				}
				if(common.isSuccess()){
					new GetAllComment(blog_id).execute();
				}else{
					AlertDialog.Builder builder = new AlertDialog.Builder(
							ComentMainActivity.this);
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
				editText.setText("");
			}
		});
	}
	@Override
	public void onBackPressed() {
		back(null);
	}

	public void back(View v) {
		this.finish();
	}

	public class GetAllComment extends AsyncTask<String, Void, List<CommentDto>> implements OnCancelListener{
		ProgressHUD mProgressHUD;
		String blog_id;
		public GetAllComment(String blog_id){
			this.blog_id = blog_id;
		}
		
    	@Override
    	protected void onPreExecute() {
        	mProgressHUD = ProgressHUD.show(ComentMainActivity.this,"Loading Content...", true,true,this);
    		super.onPreExecute();
    	}
		@Override
		protected List<CommentDto> doInBackground(String... params) {
			// TODO Auto-generated method stub
			return JSONParserForGetList.getInstance().getComments(blog_id);
		}
		
		@Override
		protected void onPostExecute(List<CommentDto> result) {
			super.onPostExecute(result);
			if (result != null) {
				adp = new CommentListAdapter(ComentMainActivity.this,result);
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
