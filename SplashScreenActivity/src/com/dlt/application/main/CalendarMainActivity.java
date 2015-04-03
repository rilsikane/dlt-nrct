package com.dlt.application.main;

import java.text.DateFormatSymbols;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hb.views.PinnedSectionListView;
import com.hb.views.PinnedSectionListView.PinnedSectionListAdapter;

public class CalendarMainActivity extends Activity{
	private final Handler handler = new Handler();
	private boolean addPadding;
	private PinnedSectionListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_page); 
		final int color = Color.parseColor("#1B89CA"); 
		changeColor(color, getResources().getString(R.string.calendar_view));
		
		listView = (PinnedSectionListView) findViewById(R.id.list);
		SimpleAdapter adapter = new SimpleAdapter(this,android.R.layout.simple_list_item_1, android.R.id.text1);
		listView.setAdapter(adapter);
	}
	public class SimpleAdapter extends ArrayAdapter<Item> implements PinnedSectionListAdapter {

		private final int[] COLORS = new int[] {android.R.color.white};

		public SimpleAdapter(Context context, int resource,int textViewResourceId) {
			super(context, resource, textViewResourceId);
			generateDataset();
		}

		public void generateDataset() {
 
//				Item section = new Item(Item.SECTION,"มกราคม");
//				section.sectionPosition = 0;
//				section.listPosition = 1;
//				onSectionAdded(section, 0);
//				add(section);
//
//				for (int j = 0; j < 3; j++) {
//					Item item = new Item(Item.ITEM,section.text.toUpperCase(Locale.ENGLISH) + " - "+ j);
//					item.sectionPosition = 0;
//					item.listPosition = 1;
//					add(item);
//				}
//				
//				Item section2 = new Item(Item.SECTION,"กุมภาพันธ์");
//				section2.sectionPosition = 1;
//				section2.listPosition = 2;
//				onSectionAdded(section2, 1);
//				add(section2);
//
//				for (int j = 0; j < 2; j++) {
//					Item item = new Item(Item.ITEM,section2.text.toUpperCase(Locale.ENGLISH) + " - "+ j);
//					item.sectionPosition = 1;
//					item.listPosition = 2;
//					add(item);
//				}
//				
//				Item section3 = new Item(Item.SECTION,"มีนาคม");
//				section3.sectionPosition = 2;
//				section3.listPosition = 3;
//				onSectionAdded(section3, 2);
//				add(section3);
//
//				for (int j = 0; j < 10; j++) {
//					Item item = new Item(Item.ITEM,section3.text.toUpperCase(Locale.ENGLISH) + " - "+ j);
//					item.sectionPosition = 2;
//					item.listPosition = 3;
//					add(item);
//				}
			
			for(int i=0;i<12;i++){
				Item section = new Item(Item.SECTION,getMonth(i));
				section.sectionPosition = i;
				section.listPosition = i+1;
				onSectionAdded(section, i);
				add(section);
				for (int j = 0; j < 1; j++) {
				Item item = new Item(Item.ITEM,section.text.toUpperCase(Locale.ENGLISH) + " - "+ j);
				item.sectionPosition = 0;
				item.listPosition = 1;
				add(item);
			}
			}

		}

		protected void prepareSections(int sectionsNumber) {
		}

		protected void onSectionAdded(Item section, int sectionPosition) {
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {			
//			TextView view = (TextView) super.getView(position, convertView,parent);
//			view.setTextColor(Color.DKGRAY);
//			view.setTag("" + position);
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.each_calendar_layout, null);
			Item item = getItem(position);
			if (item.type == Item.SECTION) {
				convertView = inflater.inflate(R.layout.each_calendar_layout_two, null);
				TextView titleSection = (TextView) convertView.findViewById(R.id.textView1);
				RelativeLayout rel = (RelativeLayout) convertView.findViewById(R.id.click_relative);
				rel.setBackgroundColor(parent.getResources().getColor(COLORS[item.sectionPosition % COLORS.length]));
				titleSection.setText(item.text);
//				view.setBackgroundColor(parent.getResources().getColor(COLORS[item.sectionPosition % COLORS.length]));
			}
			return convertView;
		} 

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public int getItemViewType(int position) {
			return getItem(position).type;
		}

		@Override
		public boolean isItemViewTypePinned(int viewType) {
			return viewType == Item.SECTION;
		}

	}
	static class Item {

		public static final int ITEM = 0;
		public static final int SECTION = 1;

		public final int type;
		public final String text;

		public int sectionPosition;
		public int listPosition;

		public Item(int type, String text) {
			this.type = type;
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}

	}
	@Override
	public void onBackPressed(){
		this.finish();
	}
	@SuppressLint("NewApi") public void changeColor(int newColor,String str) {

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] {colorDrawable, bottomDrawable });

			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
				ld.setCallback(drawableCallback);
			} else {
				getActionBar().setBackgroundDrawable(ld);
				getActionBar().setDisplayShowHomeEnabled(false);
				getActionBar().setDisplayShowCustomEnabled(true);
				getActionBar().setDisplayShowTitleEnabled(false);
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LayoutParams layout = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				View view = inflater.inflate(R.layout.actionbar_layout_news_page_2, null);
				getActionBar().setCustomView(view,layout);
				TextView txtView = (TextView) view.findViewById(R.id.textView1);
//				Typeface type = Typeface.createFromAsset(getAssets(), "fonts/EDPenSook.ttf");
//				txtView.setTypeface(type);
				txtView.setText(str);
				ImageView leftMenu = (ImageView) view.findViewById(R.id.imageView1);
				leftMenu.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(CalendarMainActivity.this,NrctMainActivity.class);
						startActivity(i);
						finish();
					}
				});
				
			}

		}

	}
	
	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@SuppressLint("NewApi") @Override
		public void invalidateDrawable(Drawable who) {
			getActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			handler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
			handler.removeCallbacks(what);
		}
	};
	public String getMonth(int month) {
	    return new DateFormatSymbols(new Locale("th", "TH")).getMonths()[month];
	}
}
