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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlt.application.main.ProfessorContentActivity;
import com.dlt.application.main.R;
import com.dlt.application.main.ProfessorMainActivity.Professor;
import com.squareup.picasso.Picasso;

public class GridViewProfessorAdapter extends BaseAdapter {

	private Activity _activity;
	private List<Professor> professorName;
	private int imageWidth;
	private ViewHolder viewHolder;

	public GridViewProfessorAdapter(Activity activity, List<Professor> professorName,int imageWidth) {
		this._activity = activity;
		this.professorName = professorName;
		this.imageWidth = imageWidth;
	}

	@Override
	public int getCount() {
		return this.professorName.size();
	}

	@Override
	public Object getItem(int position) {
		return this.professorName.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Professor pro = professorName.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			convertView = inflater.inflate(R.layout.each_box_layout, null);
			
			viewHolder.showCase = (ImageView) convertView.findViewById(R.id.showCase);
			viewHolder.relative = (RelativeLayout) convertView.findViewById(R.id.clickLayout);
			viewHolder.showCase.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth,imageWidth));
			viewHolder.titleImage = (TextView) convertView.findViewById(R.id.titleImage);
			viewHolder.titleImage.setLines(2);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Picasso.with(_activity).load(pro.getProfessorImage())
				.placeholder(R.drawable.loading).error(R.drawable.loading)
				.into(viewHolder.showCase);
		viewHolder.titleImage.setText(pro.getProfessorName());
		viewHolder.relative.setOnClickListener(new OnImageClickListener(pro));
		return convertView;
	}

	public class ViewHolder {
		private ImageView showCase;
		private TextView titleImage;
		private RelativeLayout relative;
	}
	public class OnImageClickListener implements OnClickListener{
		private Professor _pro;
		public OnImageClickListener(Professor pro){
			this._pro = pro;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(_activity,ProfessorContentActivity.class);
			i.putExtra("ProfessorName",_pro.getProfessorName());
			i.putExtra("ProfessorImage", _pro.getProfessorImage());
			i.putExtra("ProfessorPosition", _pro.getProfessorPosition());
			i.putExtra("menuId", _pro.getId());
			_activity.startActivity(i);
		}
		
	}
}
