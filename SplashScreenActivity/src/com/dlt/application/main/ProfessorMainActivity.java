package com.dlt.application.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlt.application.adapter.GridViewProfessorAdapter;
import com.dlt.application.adapter.FlipViewAdapter.AppConstant;
import com.jakewharton.salvage.ScreenSizeUntil;

@SuppressLint("NewApi") public class ProfessorMainActivity extends Activity {
	private GridViewProfessorAdapter adapter;
	private int columnWidth;
	private Utils utils;
	private GridView gridView;
	private List<Professor> tempList;
	private final Handler handler = new Handler();
	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
//		overridePendingTransition(R.anim.open_main, R.anim.close_next);
		back(null);
	}
	public void back(View v) {
		Intent i = new Intent(ProfessorMainActivity.this,NrctMainActivity.class);
		startActivity(i);
		finish();
	}
	public void changeColor(int newColor,String str) {

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getApplicationContext().getResources().getDrawable(R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] {colorDrawable, bottomDrawable });

			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				ld.setCallback(drawableCallback);
			} else {
				ProfessorMainActivity.this.getActionBar().setBackgroundDrawable(ld);
				ProfessorMainActivity.this.getActionBar().setDisplayShowHomeEnabled(false);
				ProfessorMainActivity.this.getActionBar().setDisplayShowCustomEnabled(true);
				ProfessorMainActivity.this.getActionBar().setDisplayShowTitleEnabled(false);
				LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LayoutParams layout = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				View view = inflater.inflate(R.layout.actionbar_inform_detail, null);
				ProfessorMainActivity.this.getActionBar().setCustomView(view,layout);
				TextView txtView = (TextView) view.findViewById(R.id.title);
				Typeface type = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/EDPenSook.ttf");
				txtView.setTypeface(type);
				txtView.setText(str);
				ImageView leftMenu = (ImageView) view.findViewById(R.id.leftMenu);
				leftMenu.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(ProfessorMainActivity.this,NrctMainActivity.class);
						startActivity(i);
						finish();
					}
				});
				
			}

			oldBackground = ld;

		}

		currentColor = newColor;

	}
	@SuppressLint("NewApi") private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@SuppressLint("NewApi") @Override
		public void invalidateDrawable(Drawable who) {
			ProfessorMainActivity.this.getActionBar().setBackgroundDrawable(who);
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_professor_main);
		System.gc();
		utils = new Utils(this);
		gridView = (GridView) findViewById(R.id.grid_view);
		int color = Color.parseColor("#1B89CA");
		String getintent = getIntent().getStringExtra("ActionBarText");
		changeColor(color,getintent);
		
		tempList = new ArrayList<Professor>();
		Professor pro;
		pro = new Professor();
		pro.setId("20");
		pro.setProfessorImage(R.drawable.sutiporn);
		pro.setProfessorName(getResources().getString(R.string.professor_suttiporn));
		pro.setProfessorPosition(getResources().getString(R.string.professor_suttiporn_position));
		tempList.add(pro);
		
		pro = new Professor();
		pro.setId("21");
		pro.setProfessorImage(R.drawable.kritawat);
		pro.setProfessorName(getResources().getString(R.string.professor_kritawat));
		pro.setProfessorPosition(getResources().getString(R.string.professor_kritawat_position));
		tempList.add(pro);
		
		pro = new Professor();
		pro.setId("22");
		pro.setProfessorImage(R.drawable.sukanya);
		pro.setProfessorName(getResources().getString(R.string.professor_sukanya));
		pro.setProfessorPosition(getResources().getString(R.string.professor_sukanya_position));
		tempList.add(pro);
		
		pro = new Professor();
		pro.setId("23");
		pro.setProfessorImage(R.drawable.jintanapa);
		pro.setProfessorName(getResources().getString(R.string.professor_jintana));
		pro.setProfessorPosition(getResources().getString(R.string.professor_jintana_position));
		tempList.add(pro);
		
		pro = new Professor();
		pro.setId("24");
		pro.setProfessorImage(R.drawable.cunchit);
		pro.setProfessorName(getResources().getString(R.string.professor_kunchit));
		pro.setProfessorPosition(getResources().getString(R.string.professor_kunchit_position));
		tempList.add(pro);
		
		pro = new Professor();
		pro.setId("25");
		pro.setProfessorImage(R.drawable.yodwadee);
		pro.setProfessorName(getResources().getString(R.string.professor_yodwadee));
		pro.setProfessorPosition(getResources().getString(R.string.professor_yodwadee_position));
		tempList.add(pro);
		
		pro = new Professor();
		pro.setId("26");
		pro.setProfessorImage(R.drawable.patamarat);
		pro.setProfessorName(getResources().getString(R.string.professor_patama));
		pro.setProfessorPosition(getResources().getString(R.string.professor_patama_position));
		tempList.add(pro);

		
		InitilizeGridLayout();
		adapter = new GridViewProfessorAdapter(this, tempList,columnWidth);
		gridView.setAdapter(adapter);
		
	}
	private void InitilizeGridLayout() {
		Resources r = ProfessorMainActivity.this.getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,AppConstant.GRID_PADDING, r.getDisplayMetrics());

		columnWidth = (int) ((utils.getScreenWidth() - ((AppConstant2.NUM_OF_COLUMNS + 1) * padding)) / AppConstant2.NUM_OF_COLUMNS);

		gridView.setNumColumns(AppConstant.NUM_OF_COLUMNS);
		gridView.setColumnWidth(columnWidth);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setPadding((int) padding, (int) padding, (int) padding,(int) padding);
		gridView.setHorizontalSpacing((int) padding);
		gridView.setVerticalSpacing((int) padding);
	}
public class Professor{
	private int professorImage;
	private String professorName;
	private String professorPosition;
	private String id;
	
	public String getProfessorPosition() {
		return professorPosition;
	}
	public void setProfessorPosition(String professorPosition) {
		this.professorPosition = professorPosition;
	}
	public int getProfessorImage() {
		return professorImage;
	}
	public void setProfessorImage(int professorImage) {
		this.professorImage = professorImage;
	}
	public String getProfessorName() {
		return professorName;
	}
	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
public class AppConstant2 {

	// Number of columns of Grid View
	public static final int NUM_OF_COLUMNS = 2;

	// Gridview image padding
	public static final int GRID_PADDING = 1; // in dp

	// SD card image directory
	public static final String PHOTO_ALBUM = "NAT";

	// supported file formats
	public final List<String> FILE_EXTN = Arrays.asList("jpg", "jpeg","png");
}
}
