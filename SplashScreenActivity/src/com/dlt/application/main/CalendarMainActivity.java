package com.dlt.application.main;

import java.text.DateFormatSymbols;
import java.util.Locale;
import java.util.Random;

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
import android.widget.Toast;

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

		public SimpleAdapter(Context context, int resource,int textViewResourceId) {
			super(context, resource, textViewResourceId);
			generateDataset();
		}

		public void generateDataset() {
 
			
			for(int i=0;i<12;i++){
				Item section = new Item(Item.SECTION,getMonth(i),false);
				section.sectionPosition = i;
				section.listPosition = i+1;
				onSectionAdded(section, i);
				add(section);
				if(i%2==0){
					for (int j = 0; j < 2; j++) {		
						Item item = new Item(Item.ITEM,section.text.toUpperCase(Locale.ENGLISH) + " - "+ j,false);
						item.sectionPosition = 0;
						item.listPosition = 1;
						add(item);				
					}
				}else{	
					Item item = new Item(Item.ITEM,"",true);
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
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			Item item = getItem(position);
			if(item.isEmpty==true){
				convertView = inflater.inflate(R.layout.each_calendar_layout_empty, null);
			}else{
				convertView = inflater.inflate(R.layout.each_calendar_layout, null);
			}					
			if (item.type == Item.SECTION) {
				convertView = inflater.inflate(R.layout.each_calendar_layout_two, null);
				TextView titleSection = (TextView) convertView.findViewById(R.id.textView1);
				RelativeLayout rel = (RelativeLayout) convertView.findViewById(R.id.click_relative);
				titleSection.setText(item.text);
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
		public final boolean isEmpty;

		public int sectionPosition;
		public int listPosition;

		public Item(int type, String text,boolean isEmpty) {
			this.type = type;
			this.text = text;
			this.isEmpty=isEmpty;
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
				View view = inflater.inflate(R.layout.actionbar_layout_calendar_page, null);
				getActionBar().setCustomView(view,layout);
				TextView txtView = (TextView) view.findViewById(R.id.textView1);
//				Typeface type = Typeface.createFromAsset(getAssets(), "fonts/EDPenSook.ttf");
//				txtView.setTypeface(type);
				txtView.setText(str);
				TextView todayTxt = (TextView) view.findViewById(R.id.textView2);
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
				todayTxt.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "Link To taday", Toast.LENGTH_SHORT).show();
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
