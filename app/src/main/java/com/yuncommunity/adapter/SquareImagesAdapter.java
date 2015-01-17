package com.yuncommunity.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.oldfeel.base.BaseBaseAdapter;
import com.yuncommunity.R;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月15日
 */
public class SquareImagesAdapter extends BaseBaseAdapter<String> {
	public SquareImagesAdapter(Context context, String images) {
		super(context);
		String[] strings = images.split(",");
		for (String string : strings) {
			add(string);
		}
	}

	@Override
	public View getView(int position, View view) {
		view = inflater.inflate(R.layout.i_square_item_image, null);
		ImageView ivImage = getImageView(view, R.id.i_square_item_image);
		imageLoader.displayImage(getItem(position), ivImage, options);
		return view;
	}

}