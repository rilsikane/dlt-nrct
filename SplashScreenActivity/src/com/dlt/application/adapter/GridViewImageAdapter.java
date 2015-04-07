package com.dlt.application.adapter;

import java.security.PublicKey;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
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
import android.widget.Toast;

import com.dlt.application.main.CalendarMainActivity;
import com.dlt.application.main.InformDetailActivity;
import com.dlt.application.main.InformDetailSubMainActivity;
import com.dlt.application.main.KMSMenuActivity;
import com.dlt.application.main.ProfessorMainActivity;
import com.dlt.application.main.PublishDocumentPageActivity;
import com.dlt.application.main.R;
import com.dlt.application.model.Menu;
import com.squareup.picasso.Picasso;

@SuppressLint("NewApi") public class GridViewImageAdapter extends BaseAdapter {

	private Activity _activity;
	private List<Menu> menus;
	private int imageWidth;
	private ViewHolder viewHolder;

	public GridViewImageAdapter(Activity activity, List<Menu> menus,int imageWidth) {
		this._activity = activity;
		this.menus = menus;
		this.imageWidth = imageWidth;
	}

	@Override
	public int getCount() {
		return this.menus.size();
	}

	@Override
	public Object getItem(int position) {
		return this.menus.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Menu menu = menus.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			convertView = inflater.inflate(R.layout.each_box_layout, null);
			viewHolder.showCase = (ImageView) convertView.findViewById(R.id.showCase);
			viewHolder.relative = (RelativeLayout) convertView.findViewById(R.id.clickLayout);
			viewHolder.showCase.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,imageWidth-18));
			viewHolder.titleImage = (TextView) convertView.findViewById(R.id.titleImage);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(!"888".equals(menu.menu_id) && !"17".equals(menu.menu_id)){
			if("2".equals(menu.menu_id)){
			Picasso.with(_activity).load(R.drawable.menu2)
				.placeholder(R.drawable.loading).error(R.drawable.loading)
				.into(viewHolder.showCase);
			}else if("3".equals(menu.menu_id)){
				Picasso.with(_activity).load(R.drawable.menu3)
				.placeholder(R.drawable.loading).error(R.drawable.loading)
				.into(viewHolder.showCase);
			}else if("4".equals(menu.menu_id)){
				Picasso.with(_activity).load(R.drawable.menu4)
				.placeholder(R.drawable.loading).error(R.drawable.loading)
				.into(viewHolder.showCase);
			}else if("5".equals(menu.menu_id)){
				Picasso.with(_activity).load(R.drawable.menu5)
				.placeholder(R.drawable.loading).error(R.drawable.loading)
				.into(viewHolder.showCase);
			}else if("6".equals(menu.menu_id)){
				Picasso.with(_activity).load(R.drawable.menu6)
				.placeholder(R.drawable.loading).error(R.drawable.loading)
				.into(viewHolder.showCase);
			}else if("8".equals(menu.menu_id)){
				Picasso.with(_activity).load(R.drawable.menu8)
				.placeholder(R.drawable.loading).error(R.drawable.loading)
				.into(viewHolder.showCase);
			}else if("9".equals(menu.menu_id)){
				Picasso.with(_activity).load(R.drawable.menu9)
				.placeholder(R.drawable.loading).error(R.drawable.loading)
				.into(viewHolder.showCase);
			}else if("11".equals(menu.menu_id)){
				Picasso.with(_activity).load(R.drawable.menu11)
				.placeholder(R.drawable.loading).error(R.drawable.loading)
				.into(viewHolder.showCase);
			}else if("12".equals(menu.menu_id)){
				Picasso.with(_activity).load(R.drawable.menu12)
				.placeholder(R.drawable.loading).error(R.drawable.loading)
				.into(viewHolder.showCase);
			}else if("13".equals(menu.menu_id)){
				Picasso.with(_activity).load(R.drawable.menu13)
				.placeholder(R.drawable.loading).error(R.drawable.loading)
				.into(viewHolder.showCase);
			}else{
				Picasso.with(_activity).load(menu.imgpath)
				.placeholder(R.drawable.loading).error(R.drawable.loading)
				.into(viewHolder.showCase);
			}
		}else if("888".equals(menu.menu_id)){
			Picasso.with(_activity).load(R.drawable.menukms)
			.placeholder(R.drawable.loading).error(R.drawable.loading)
			.into(viewHolder.showCase);
		}else if("17".equals(menu.menu_id)){
			Picasso.with(_activity).load(R.drawable.promenu)
			.placeholder(R.drawable.loading).error(R.drawable.loading)
			.into(viewHolder.showCase);
		}
		viewHolder.titleImage.setText(menu.name);
		viewHolder.relative.setOnClickListener(new OnImageClickListener(menu.menu_id,menu.name,"T".equals(menu.childFlag)));
		return convertView;
	}

	public class ViewHolder {
		private ImageView showCase;
		private TextView titleImage;
		private RelativeLayout relative;
	}

	class OnImageClickListener implements OnClickListener {
		Intent i ;
		String id;
		String _str;
		boolean hasChild;
		// constructor
		public OnImageClickListener(String id,String str,boolean hasChild) {
			this.id = id;
			this._str = str;
			this.hasChild = hasChild;
		}

		@Override
		public void onClick(View v) {
				if(!"888".equals(id) && !"17".equals(id)&&!"14".equals(id)&&!"72".equals(id)){
					if(hasChild){
						i = new Intent(_activity, InformDetailSubMainActivity.class);
						i.putExtra("backAnimation", "fade");
						i.putExtra("ActionBarText", _str);
						i.putExtra("menu_id", id);
						_activity.startActivity(i);
						_activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
						_activity.finish();	
					}else{
						i = new Intent(_activity, InformDetailActivity.class);
						i.putExtra("backAnimation", "fade");
						i.putExtra("ActionBarText", _str);
						i.putExtra("menu_id", id);
						_activity.startActivity(i);
						_activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
						_activity.finish();
					}
				}else if("888".equals(id)){
					i = new Intent(_activity,KMSMenuActivity.class);
					i.putExtra("backAnimation", "fade");
					i.putExtra("ActionBarTitle", _activity.getResources().getString(R.string.add_kms_menu_text));
					i.putExtra("StateFrom", 1);
					_activity.startActivity(i);
					_activity.overridePendingTransition (R.anim.open_next, R.anim.close_main);
					_activity.finish();
				}else if("17".equals(id)){
					i = new Intent(_activity, ProfessorMainActivity.class);
					i.putExtra("backAnimation", "fade");
					i.putExtra("ActionBarText", _str);
					_activity.startActivity(i);
					_activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					_activity.finish();
				}else if("14".equals(id)){
					i = new Intent(_activity, PublishDocumentPageActivity.class);
					i.putExtra("backAnimation", "fade");
					i.putExtra("ActionBarText", _str);
					_activity.startActivity(i);
					_activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					_activity.finish();
				}else if("72".equals(id)){
					i = new Intent(_activity, CalendarMainActivity.class);
					i.putExtra("backAnimation", "fade");
					i.putExtra("ActionBarText", _str);
					_activity.startActivity(i);
					_activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					_activity.finish();
				}
			
//				i = new Intent(_activity,ProfessorMainActivity.class);
//				i.putExtra("ActionBarText", "คุยกับผู้เชี่ยวชาญ");
			
		}

	}
}
