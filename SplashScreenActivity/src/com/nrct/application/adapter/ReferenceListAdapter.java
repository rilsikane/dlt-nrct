package com.nrct.application.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nrct.application.adapter.KmsMenuListAdapter.OnMenuSelectedListener;
import com.nrct.application.dto.BlogDto;
import com.nrct.application.dto.MenuDto;
import com.nrct.application.dto.ReferenceDto;
import com.nrct.application.main.ContentMainActivity;
import com.nrct.application.main.R;
import com.nrct.application.main.ReferenceDetailMainActivity;
import com.nrct.application.main.ReferenceMainActivity.MockReference;
import com.nrct.application.model.Menu;
import com.nrct.application.utils.DateUtil;

public class ReferenceListAdapter extends BaseAdapter{
	private List<ReferenceDto> _listData;
	private Activity _activity;
	private ViewHolder viewHolder;
	private ReferenceDto temp;
	public ReferenceListAdapter(Activity activity, List<ReferenceDto> listData){
		this._activity = activity;
		this._listData = listData;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _listData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return _listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		temp = _listData.get(position);
		try{
		if(convertView== null){
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) _activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			convertView = inflater.inflate(R.layout.each_reference_layout, null);
			viewHolder.title  = (TextView) convertView.findViewById(R.id.text_item);
			viewHolder.date = (TextView) convertView.findViewById(R.id.createdDate);
			viewHolder.click_relative = (RelativeLayout) convertView.findViewById(R.id.click_relative);			
			viewHolder.title.setText(temp.getTitle());
			viewHolder.date.setText(temp.getUrl());
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
		viewHolder.click_relative.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(_activity,ReferenceDetailMainActivity.class);
				intent.putExtra("ActionBarTitle", temp.getTitle());
				intent.putExtra("RefDetail", temp.getUrl());
				_activity.startActivity(intent);
			}
		});
		return convertView;
	}
	public class ViewHolder{
		public TextView title;
		public TextView createdBy;
		public TextView date;
		public RelativeLayout click_relative;
	}
}
