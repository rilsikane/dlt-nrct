package com.dlt.application.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlt.application.adapter.KmsMenuListAdapter.OnMenuSelectedListener;
import com.dlt.application.dto.BlogDto;
import com.dlt.application.dto.MenuDto;
import com.dlt.application.json.JSONParserForGetList;
import com.dlt.application.main.ContentMainActivity;
import com.dlt.application.main.R;
import com.dlt.application.model.Menu;
import com.dlt.application.utils.DateUtil;
import com.dlt.application.utils.RoundedTransformation;
import com.squareup.picasso.Picasso;

public class InformationDetailListAdapter extends BaseAdapter{
	private List<BlogDto> _listData;
	private Activity _activity;
	private ViewHolder viewHolder;
	private BlogDto temp;
	public InformationDetailListAdapter(Activity activity, List<BlogDto> listData){
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
		try{
		if(convertView== null){
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) _activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			convertView = inflater.inflate(R.layout.each_inform_detail_layout, null);
			viewHolder.title  = (TextView) convertView.findViewById(R.id.text_item);
			viewHolder.createdBy = (TextView) convertView.findViewById(R.id.createdBy);
			viewHolder.date = (TextView) convertView.findViewById(R.id.createdDate);
			viewHolder.click_relative = (RelativeLayout) convertView.findViewById(R.id.click_relative);
			viewHolder.tumbnail = (ImageView) convertView.findViewById(R.id.image_item);
			temp = _listData.get(position);
			viewHolder.title.setText(temp.getTitle());
			viewHolder.createdBy.setText(_activity.getResources().getString(R.string.text_by)+" "+temp.getAuthor_name());
			viewHolder.date.setText(DateUtil.toStringThaiDateFullFormat(temp.getCreate_date()));
			Picasso.with(_activity).load(temp.getImgUrl())
			.transform(new RoundedTransformation(50, 4))
			.placeholder(R.drawable.rounded_corners).error(R.drawable.rounded_corners)
			.into(viewHolder.tumbnail);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
			
			
			//viewHolder.date.setText(temp.getDateTime());
			viewHolder.click_relative.setOnClickListener(new OnBlogSelectedListener(position,convertView,temp));
		}catch (Exception ex) {
			ex.printStackTrace();
		}
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
		
		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			if (android.os.Build.VERSION.SDK_INT > 9) {
			      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			      StrictMode.setThreadPolicy(policy);
			 }
			ArrayList<String> list = new ArrayList<String>();
			for(BlogDto dto : _listData){
				list.add(dto.getContent());
			}
			BlogDto blogDto = JSONParserForGetList.getInstance().getBlogById(_listData.get(_position).getId());
			Intent i = new Intent(_activity,ContentMainActivity.class);
			i.putExtra("blogDto",(Serializable) blogDto);
			i.putStringArrayListExtra("list", list);
			_activity.startActivity(i);		
		}
		
	}
	public class ViewHolder{
		public TextView title;
		public TextView createdBy;
		public TextView date;
		public RelativeLayout click_relative;
		public ImageView tumbnail;
	}
}
