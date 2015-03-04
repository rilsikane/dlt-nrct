package com.nrct.application.adapter;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nrct.application.adapter.InformationDetailListAdapter.OnBlogSelectedListener;
import com.nrct.application.dto.BlogDto;
import com.nrct.application.dto.MenuDto;
import com.nrct.application.json.JSONParserForGetList;
import com.nrct.application.main.AboutPageActivity;
import com.nrct.application.main.ContactUsActivity;
import com.nrct.application.main.ContentMainActivity;
import com.nrct.application.main.InformDetailActivity;
import com.nrct.application.main.KMSMenuActivity;
import com.nrct.application.main.LoginPageActivity;
import com.nrct.application.main.MamberPageActivity;
import com.nrct.application.main.R;
import com.nrct.application.main.TaxonomyActivity.MockMenuList;
import com.nrct.application.main.SearchPageActivity;
import com.nrct.application.model.User;
import com.squareup.picasso.Picasso;

public class TaxonomyListAdapter extends BaseAdapter{
	private List<MenuDto> _listData;
	private Activity _activity;
	private ViewHolder viewHolder;
	private int _eachListSize;
	private MenuDto temp;
	public TaxonomyListAdapter(Activity activity, List<MenuDto> listData,int eachListSize){
		this._activity = activity;
		this._listData = listData;
		this._eachListSize = eachListSize;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView== null){
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) _activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			convertView = inflater.inflate(R.layout.right_menu_list_layout, null);
			viewHolder.iconImg = (ImageView) convertView.findViewById(R.id.image_item);
			viewHolder.title = (TextView) convertView.findViewById(R.id.text_item);
			viewHolder.listLayout = (RelativeLayout) convertView.findViewById(R.id.click_relative);
			LinearLayout.LayoutParams layout_description = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,_eachListSize);
			viewHolder.listLayout.setLayoutParams(layout_description);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
			temp = _listData.get(position);
			viewHolder.title.setText(temp.getName());
			//Picasso.with(_activity).load(_activity.getResources().getDrawable(R.drawable.ic_launcher));
			
			viewHolder.iconImg.setImageDrawable(_activity.getResources().getDrawable(R.drawable.ic_launcher));
			viewHolder.listLayout.setOnClickListener(new OnTaxanomySelectedListener(position,convertView,temp));
			
		return convertView;
	}
	public class OnTaxanomySelectedListener implements OnClickListener{
		private int _position;
		private View view;
		private MenuDto menuDto;
		public OnTaxanomySelectedListener(int position,View btnView,MenuDto menuDto){
			this._position = position;
			this.view = btnView;
			this.menuDto =  menuDto;
		}
		
		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			Intent i = new Intent(_activity, InformDetailActivity.class);
			i.putExtra("backAnimation", "fade");
			i.putExtra("ActionBarText", menuDto.getName());
			i.putExtra("menu_id", menuDto.getId());
			_activity.startActivity(i);
			_activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			_activity.finish();
		}
		
	}
	public class ViewHolder{
		public TextView title;
		public ImageView iconImg;
		public RelativeLayout listLayout;
		public ImageView showHide;
	}
}
