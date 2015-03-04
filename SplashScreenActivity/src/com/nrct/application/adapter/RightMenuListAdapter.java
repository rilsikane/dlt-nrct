package com.nrct.application.adapter;

import java.io.Serializable;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nrct.application.main.AboutPageActivity;
import com.nrct.application.main.ContactUsActivity;
import com.nrct.application.main.KMSMenuActivity;
import com.nrct.application.main.LoginPageActivity;
import com.nrct.application.main.MamberPageActivity;
import com.nrct.application.main.R;
import com.nrct.application.main.RightMenuActivity.MockMenuList;
import com.nrct.application.main.SearchPageActivity;
import com.nrct.application.model.User;

public class RightMenuListAdapter extends BaseAdapter{
	private List<MockMenuList> _listData;
	private Activity _activity;
	private ViewHolder viewHolder;
	private int _eachListSize;
	private MockMenuList temp;
	public RightMenuListAdapter(Activity activity, List<MockMenuList> listData,int eachListSize){
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
			viewHolder.showHide = (ImageView) convertView.findViewById(R.id.imageView1);
			if(position!=0){
				viewHolder.showHide.setVisibility(View.INVISIBLE);
			}
			viewHolder.listLayout = (RelativeLayout) convertView.findViewById(R.id.click_relative);
			LinearLayout.LayoutParams layout_description = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,_eachListSize);
			viewHolder.listLayout.setLayoutParams(layout_description);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
			temp = _listData.get(position);
			viewHolder.title.setText(temp.getMenuName());
			viewHolder.iconImg.setImageDrawable(_activity.getResources().getDrawable(temp.getImgDrawable()));
			viewHolder.listLayout.setOnClickListener(new OnClickListener() {
				Intent i ;
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					switch (position) {
					case 0:
						User user = new User();
						if(user.getUserLogin()!=null){
							i = new Intent(_activity,MamberPageActivity.class);	
							i.putExtra("memberDto",(Serializable) user.convertToMember(user.getUserLogin()));
						}else{
							i = new Intent(_activity,LoginPageActivity.class);
						}
						i.putExtra("ActionBarTitle", _activity.getResources().getString(R.string.login_text));
						break;
					case 1:
						i = new Intent(_activity,SearchPageActivity.class);
						i.putExtra("ActionBarTitle", _activity.getResources().getString(R.string.search_menu_text));
						break;
					case 2:
						i = new Intent(_activity,KMSMenuActivity.class);
						i.putExtra("ActionBarTitle", _activity.getResources().getString(R.string.add_kms_menu_text));
						i.putExtra("StateFrom", 0);
						_activity.finish();
						break;
					case 3:
						i = new Intent(_activity,ContactUsActivity.class);
						i.putExtra("ActionBarTitle", _activity.getResources().getString(R.string.contact_menu_text));
						break;
					case 4:
						i = new Intent(_activity,AboutPageActivity.class);
						i.putExtra("ActionBarTitle", _activity.getResources().getString(R.string.about_menu_text));
						break;
					default:
						i = new Intent(_activity,KMSMenuActivity.class);
						break;
					}
					_activity.startActivity(i);
				}
			});
			
		return convertView;
	}
	public class ViewHolder{
		public TextView title;
		public ImageView iconImg;
		public RelativeLayout listLayout;
		public ImageView showHide;
	}
}
