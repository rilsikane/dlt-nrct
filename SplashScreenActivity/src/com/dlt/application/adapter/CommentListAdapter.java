package com.dlt.application.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dlt.application.dto.CommentDto;
import com.dlt.application.main.R;
import com.dlt.application.utils.DateUtil;

public class CommentListAdapter extends BaseAdapter{
	private List<CommentDto> _commentList;
	private Activity _activity;
	private ViewHolder viewHolder;
	private CommentDto temp;
	public CommentListAdapter(Activity act,List<CommentDto> listData) {
		// TODO Auto-generated constructor stub
		this._commentList = listData;
		this._activity = act;
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
		try{
		if(convertView== null){
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) _activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			convertView = inflater.inflate(R.layout.each_comment_layout, null);
			viewHolder.postName = (TextView) convertView.findViewById(R.id.text_item);
			viewHolder.postComment = (TextView) convertView.findViewById(R.id.commentPost);
			viewHolder.timePost = (TextView) convertView.findViewById(R.id.commentTime);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
			
			temp = _commentList.get(position);
			viewHolder.postName.setText(temp.getCommentName());
			viewHolder.postComment.setText(temp.getComments());
			viewHolder.timePost.setText(DateUtil.toStringThaiDateFullFormat( temp.getCreate_date()));
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return convertView;
	}
	private class ViewHolder{
		public TextView postName;
		public TextView timePost;
		public TextView postComment;
	}

}
