package com.yuncommunity.adapter;

import java.io.File;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.oldfeel.base.BaseBaseAdapter;
import com.oldfeel.utils.ImageUtil;
import com.oldfeel.utils.StringUtil;
import com.yuncommunity.R;
import com.yuncommunity.theme.ios.IReleaseSquare;

public class UploadImageAdapter extends BaseBaseAdapter<Uri> {

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
					((IReleaseSquare) context).getImage();
				}
			});
		} else {
			imageLoader.displayImage(getItem(position).toString(), ivImage);
		}
		return view;
	}

	public String getUploadImages() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getCount() - 1; i++) {
			File file = getImageFile(i);
			if (i == 0) {
				sb.append(file.getName());
			} else {
				sb.append("," + file.getName());
			}
		}
		return sb.toString();
	}

	public File getImageFile(int position) {
		Uri uri = getItem(position);
		String path = ImageUtil.getAbsolutePathFromNoStandardUri(uri);
		if (StringUtil.isEmpty(path)) {
			path = ImageUtil.getAbsoluteImagePath(context, uri);
		}
		return new File(path);
	}

}
