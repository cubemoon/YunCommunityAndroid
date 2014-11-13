package com.yuncommunity.adapter;

import java.io.File;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.oldfeel.base.BaseBaseAdapter;
import com.yuncommunity.R;
import com.yuncommunity.item.UploadImageItem;
import com.yuncommunity.theme.ios.I_ReleaseSquare;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月13日
 */
public class UploadImageAdapter extends BaseBaseAdapter<UploadImageItem> {

	public UploadImageAdapter(Context context) {
		super(context);
	}

	@Override
	public int getCount() {
		return super.getCount() + 1;
	}

	@Override
	public View getView(int position, View view) {
		view = LayoutInflater.from(context).inflate(
				R.layout.i_release_square_image_item, null);
		ImageView ivImage = (ImageView) view
				.findViewById(R.id.i_release_square_image);

		if (position == getCount() - 1) {
			ivImage.setImageResource(R.drawable.ic_launcher);
			ivImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					((I_ReleaseSquare) context).getImage();
				}
			});
		} else {
			imageLoader.displayImage(Uri.fromFile(getItem(position).getFile())
					.toString(), ivImage);
		}
		return view;
	}

	public String getUploadImages() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getCount() - 1; i++) {
			String key = getItem(i).getKey();
			if (i == 0) {
				sb.append(key);
			} else {
				sb.append("," + key);
			}
		}
		return sb.toString();
	}

	public void add(File file, String name) {
		UploadImageItem item = new UploadImageItem();
		item.setFile(file);
		item.setKey(name);
		add(item);
	}

}
