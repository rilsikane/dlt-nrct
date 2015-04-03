package com.dlt.application.adapter;

import java.io.Serializable;
import java.util.List;

import com.dlt.application.adapter.InformationDetailListAdapter.OnBlogSelectedListener;
import com.dlt.application.adapter.InformationDetailListAdapter.ViewHolder;
import com.dlt.application.dto.BlogDto;
import com.dlt.application.main.ContentMainActivity;
import com.dlt.application.main.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SearchListAdapter extends BaseAdapter{
	private List<BlogDto> _list;
	private Activity _activity;
	private ViewHolder viewHolder;
	private BlogDto temp;
	public SearchListAdapter(Activity act, List<BlogDto> list) {
		// TODO Auto-generated constructor stub
		this._list = list;
		this._activity = act;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return _list.get(arg0);
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
			convertView = inflater.inflate(R.layout.each_search_layout, null);
			viewHolder.title  = (TextView) convertView.findViewById(R.id.text_item);
			viewHolder.click_relative = (RelativeLayout) convertView.findViewById(R.id.click_relative);
			temp = _list.get(position);
			convertView.setTag(viewHolder);
			
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.title.setText(temp.getTitle());
		viewHolder.click_relative.setOnClickListener(new OnBlogSelectedListener(position,convertView,temp));
		return convertView;
	}
	public class OnBlogSelectedListener implements OnClickListener{
		private int _position;
		private View view;
		private BlogDto blogDto;
		public OnBlogSelectedListener(int position,View btnView,BlogDto blogDto){
			this._position = position;
			this.view = btnView;
			this.blogDto =  blogDto;
		}
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(_activity,ContentMainActivity.class);
			i.putExtra("blogDto",(Serializable) _list.get(_position));
			_activity.startActivity(i);		
		}
		
	}
	public class ViewHolder{
		public TextView title;
		public RelativeLayout click_relative;
	}

}
