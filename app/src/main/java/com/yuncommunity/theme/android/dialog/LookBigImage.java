package com.yuncommunity.theme.android.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oldfeel.base.BaseDialogFragment;
import com.oldfeel.utils.ImageUtil;
import com.yuncommunity.R;

/**
 * 查看大图
 * 
 * @author oldfeel
 * 
 */
public class LookBigImage extends BaseDialogFragment {
	private String image;
	private ImageView ivImage;

	public static LookBigImage newInstance(String image) {
		LookBigImage fragment = new LookBigImage();
		fragment.image = image;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.look_big_image, null);
		ivImage = getImageView(view, R.id.image);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		ImageLoader imageLoader = ImageLoader.getInstance();
		if (image.startsWith("http")) {
			imageLoader.displayImage(image, ivImage);
		} else {
			ivImage.setImageBitmap(ImageUtil.getBitmapByPath(image));
		}
	}
}
