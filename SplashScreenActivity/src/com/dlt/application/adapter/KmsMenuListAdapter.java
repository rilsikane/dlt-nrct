package com.dlt.application.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlt.application.dto.MenuDto;
import com.dlt.application.main.R;
import com.dlt.application.main.KMSMenuActivity.MockKMSMenu;
import com.dlt.application.main.RightMenuActivity.MockMenuList;
import com.dlt.application.model.Menu;

public class KmsMenuListAdapter extends BaseAdapter{
	private List<MenuDto> _listData;
	private Activity _activity;
	private ViewHolder viewHolder;
	private MenuDto temp;
	public KmsMenuListAdapter(Activity activity, List<MenuDto> listData){
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView== null){
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) _activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			convertView = inflater.inflate(R.layout.kms_menu_list_layout, null);
			viewHolder.accecpted = (ImageView) convertView.findViewById(R.id.accepted);
			
			viewHolder.title = (TextView) convertView.findViewById(R.id.text_item);
			viewHolder.listLayout = (RelativeLayout) convertView.findViewById(R.id.click_relative);
			
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		temp = _listData.get(position);
		viewHolder.title.setText(temp.getName());
		Menu menu = new Menu();
		if(menu.isMenuSelected(temp.getId())){
			viewHolder.accecpted.setVisibility(View.VISIBLE);
		}else{
			viewHolder.accecpted.setVisibility(View.INVISIBLE);
		}
		viewHolder.listLayout.setOnClickListener(new OnMenuSelectedListener(position,convertView,temp));

		return convertView;
	}
	public class OnMenuSelectedListener implements OnClickListener{
		private int _position;
		private View view;
		private MenuDto menuDto;
		public OnMenuSelectedListener(int position,View btnView,MenuDto menuDto){
			this._position = position;
			this.view = btnView;
			this.menuDto = menuDto;
		}
		
		@Override
		public void onClick(View v) {
			ImageView selectedImg = (ImageView) view.findViewById(R.id.accepted);
			Menu menu = new Menu();
			if(menu.isMenuSelected(menuDto.getId())){
			selectedImg.setVisibility(View.INVISIBLE);
			menu.deleteMenuSelected(menuDto.getId());
			}else{
			selectedImg.setVisibility(View.VISIBLE);	
			menuDto.setSelected("T");
			menu = menu.converMenuDtoToMenu(menuDto);
			menu.save();
			}
			
		}
		
	}
	public class ViewHolder{
		public TextView title;
		public ImageView accecpted;
		public RelativeLayout listLayout;
	}
}
