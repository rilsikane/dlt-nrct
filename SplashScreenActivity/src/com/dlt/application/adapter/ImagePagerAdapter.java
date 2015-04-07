package com.dlt.application.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dlt.application.dto.SlideDto;
import com.dlt.application.main.R;
import com.jakewharton.salvage.RecyclingPagerAdapter;
import com.squareup.picasso.Picasso;

public class ImagePagerAdapter extends RecyclingPagerAdapter {
	private Context context;
	private List<SlideDto> imageIdList;
	private int size;
	private boolean isInfiniteLoop;
	private int _imageWidth;

	public ImagePagerAdapter(Context context, List<SlideDto> imageIdList,int width) {
		this.context = context;
		this.imageIdList = imageIdList;
		this.size = imageIdList.size();
		isInfiniteLoop = false;
		this._imageWidth = width;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup container) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.viewpager_image_layout, null);			
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageView1);			
//			convertView = holder.imageView = new ImageView(context);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Picasso.with(context)
				.load(imageIdList.get(getPosition(position)).getImg_url())
				.placeholder(R.drawable.loading)
				.error(R.drawable.loading).into(holder.imageView);
		// Toast.makeText(context, _imageWidth+"", Toast.LENGTH_SHORT).show();
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return isInfiniteLoop ? Integer.MAX_VALUE : imageIdList.size();
	}

	private int getPosition(int position) {
		return isInfiniteLoop ? position % size : position;
	}

	private static class ViewHolder {

		ImageView imageView;
	}

	/**
	 * @return the isInfiniteLoop
	 */
	public boolean isInfiniteLoop() {
		return isInfiniteLoop;
	}

	/**
	 * @param isInfiniteLoop
	 *            the isInfiniteLoop to set
	 */
	public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
		this.isInfiniteLoop = isInfiniteLoop;
		return this;
	}
}
