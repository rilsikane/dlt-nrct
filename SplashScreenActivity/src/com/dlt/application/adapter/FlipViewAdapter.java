package com.dlt.application.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.dlt.application.dto.SlideDto;
import com.dlt.application.main.R;
import com.dlt.application.main.Utils;
import com.dlt.application.model.Menu;

public class FlipViewAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	//Flip Page Size
	private Activity appContext;
	private ViewHolder viewHolder;
	private GridView gridView;
	private int columnWidth;
	private Utils utils;
	private GridViewImageAdapter adapter;
	private AutoScrollViewPager viewPager;
	private List<Menu> flipPageSize;
	private List<SlideDto> slideList;
	public FlipViewAdapter(Activity con,List<Menu> list,List<SlideDto> slideList){
		this.appContext = con;
		this.flipPageSize = list;
		this.slideList = slideList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return flipPageSize !=null && flipPageSize.size()>0 ? (int) ((Math.ceil((double)(flipPageSize.size()-4)/6))+1) :1;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return flipPageSize.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(position==0){
			List<Menu> tempList = new ArrayList<Menu>();
			for(int i=0;i<4;i++){
				if(flipPageSize!=null && flipPageSize.size()>i){
				tempList.add(flipPageSize.get(i));
				}
			}
			
			if(convertView== null){
				viewHolder = new ViewHolder();
				LayoutInflater inflater = (LayoutInflater) appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				utils = new Utils(appContext);
				convertView = inflater.inflate(R.layout.flip_grid_view_0, null);
				gridView = (GridView) convertView.findViewById(R.id.grid_view);
				InitilizeGridLayout();
				viewPager = (AutoScrollViewPager) convertView.findViewById(R.id.view_pager);
				viewPager.setLayoutParams(new RelativeLayout.LayoutParams(utils.getScreenHeight(),columnWidth));
				viewPager.setAdapter(new ImagePagerAdapter(appContext.getApplicationContext(), slideList,utils.getScreenWidth()).setInfiniteLoop(true));
		        viewPager.setInterval(3000);
		        viewPager.startAutoScroll();
		        if(tempList !=null && tempList.size()>0){
		        	viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % tempList.size());
		        }
		        int newColumnHight = ((utils.getScreenHeight()-490)*50)/100;
				adapter = new GridViewImageAdapter(appContext, tempList,newColumnHight);
				gridView.setAdapter(adapter);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			return convertView;
		}else{
			List<Menu> tempList = new ArrayList<Menu>();
			for(int i=0;i<6;i++){
				if(flipPageSize!=null && flipPageSize.size()>(i+((position-1)*6)+4)){
					tempList.add(flipPageSize.get(i+((position-1)*6)+4));		
				}
			}
			if(convertView== null){
				viewHolder = new ViewHolder();
				LayoutInflater inflater = (LayoutInflater) appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				utils = new Utils(appContext);
				convertView = inflater.inflate(R.layout.flip_grid_view, null);
				gridView = (GridView) convertView.findViewById(R.id.grid_view);
				InitilizeGridLayout();
				int newColumnHight = ((utils.getScreenHeight())*30)/100;
				adapter = new GridViewImageAdapter(appContext, tempList,newColumnHight);
				gridView.setAdapter(adapter);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			return convertView;
		}
		
	}
	public class ViewHolder {
		private ImageView newsImage;
		private TextView newsName;
		private TextView pubDate;
		private TextView countryName;
	}
	private void InitilizeGridLayout() {
		Resources r = appContext.getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,AppConstant.GRID_PADDING, r.getDisplayMetrics());

		columnWidth = (int) ((utils.getScreenWidth() - ((AppConstant.NUM_OF_COLUMNS + 1) * padding)) / AppConstant.NUM_OF_COLUMNS);

		gridView.setNumColumns(AppConstant.NUM_OF_COLUMNS);
		gridView.setColumnWidth(columnWidth);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setPadding((int) padding, (int) padding, (int) padding,(int) padding);
		gridView.setHorizontalSpacing((int) padding);
		gridView.setVerticalSpacing((int) padding);
	}
	public class AppConstant {

		// Number of columns of Grid View
		public static final int NUM_OF_COLUMNS = 2;

		// Gridview image padding
		public static final int GRID_PADDING = 5; // in dp

		// SD card image directory
		public static final String PHOTO_ALBUM = "NAT";

		// supported file formats
		public final List<String> FILE_EXTN = Arrays.asList("jpg", "jpeg","png");
	}
}
