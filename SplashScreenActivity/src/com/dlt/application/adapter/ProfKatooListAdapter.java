package com.dlt.application.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlt.application.dto.BlogDto;
import com.dlt.application.main.KatooContentActivity;
import com.dlt.application.main.R;
import com.dlt.application.utils.DateUtil;

public class ProfKatooListAdapter extends BaseAdapter{
	private List<BlogDto> _commentList;
	private Activity _activity;
	private ViewHolder viewHolder;
	private BlogDto temp;
	private String prof_name;
	private int prof_image;
	private String prof_position;
	public ProfKatooListAdapter(Activity act,List<BlogDto> listData,String prof_name,int prof_image,String prof_position) {
		// TODO Auto-generated constructor stub
		this._commentList = listData;
		this._activity = act;
		this.prof_image =prof_image;
		this.prof_name=prof_name;
		this.prof_position = prof_position;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _commentList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return _commentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView== null){
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) _activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			convertView = inflater.inflate(R.layout.each_katoo_layout, null);
			viewHolder.katooName = (TextView) convertView.findViewById(R.id.text_item);
			viewHolder.katooBy = (TextView) convertView.findViewById(R.id.commentPost);
			viewHolder.timePost = (TextView) convertView.findViewById(R.id.commentTime);
			viewHolder.click_relative = (RelativeLayout) convertView.findViewById(R.id.click_relative);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		try {
			temp = _commentList.get(position);
			viewHolder.katooName.setText(temp.getTitle());
			viewHolder.katooBy.setText(temp.getAuthor_name());
			viewHolder.timePost.setText(DateUtil.toStringThaiDateFullFormat(temp.getCreate_date()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			viewHolder.click_relative.setOnClickListener(new OnListClickListener(temp));
			
		return convertView;
	}
	public class OnListClickListener implements OnClickListener{
		private BlogDto _temp;
		
		public OnListClickListener(BlogDto temp){
			this._temp = temp;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(_activity,KatooContentActivity.class);
			intent.putExtra("ProfessorName",prof_name);
			intent.putExtra("ProfessorImage", prof_image);
			intent.putExtra("ProfessorPosition", prof_position);
			intent.putExtra("KatooName", _temp.getTitle());
			intent.putExtra("KatooDetail", _temp.getContent());
			intent.putExtra("KatooId", _temp.getId());
			_activity.startActivity(intent);
		}
		
	}
	private class ViewHolder{
		public TextView katooName;
		public TextView timePost;
		public TextView katooBy;
		public RelativeLayout click_relative;
	}

}
