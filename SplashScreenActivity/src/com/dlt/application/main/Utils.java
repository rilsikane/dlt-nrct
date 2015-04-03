package com.dlt.application.main;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class Utils {

	private Context _context;

	// constructor
	public Utils(Context context) {
		this._context = context;
	}

	/*
	 * Reading file paths from SDCard
	 */
	public ArrayList<String> getFilePaths() {
		ArrayList<String> filePaths = new ArrayList<String>();

		for(int i =0;i<5;i++){
			filePaths.add("http://media.digitalcameraworld.com/files/2013/01/Photo_ideas_photography_tips_DCM134.tenthings.mikko_lagerstedt_otherside.jpg");
		}

		return filePaths;
	}

	/*
	 * getting screen width
	 */
	@SuppressLint("NewApi")
	public int getScreenWidth() {
		int columnWidth;
		WindowManager wm = (WindowManager) _context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		final Point point = new Point();
		try {
			display.getSize(point);
		} catch (java.lang.NoSuchMethodError ignore) { // Older device
			point.x = display.getWidth();
			point.y = display.getHeight();
		}
		columnWidth = point.x;
		return columnWidth;
	}
	@SuppressLint("NewApi")
	public int getScreenHeight() {
		int columnHeight;
		WindowManager wm = (WindowManager) _context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		final Point point = new Point();
		try {
			display.getSize(point);
		} catch (java.lang.NoSuchMethodError ignore) { // Older device
			point.x = display.getWidth();
			point.y = display.getHeight();
		}
		columnHeight = point.y;
		return columnHeight;
	}
}
