package com.yuncommunity.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oldfeel.base.BaseBaseAdapter;
import com.oldfeel.utils.StringUtil;
import com.yuncommunity.R;
import com.yuncommunity.item.SquareItem;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class SquareAdapter extends BaseBaseAdapter<SquareItem> {

	public SquareAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View view) {
		SquareItem item = getItem(position);
		view = inflater.inflate(R.layout.i_square_item, null);
		ImageView ivAvatar = getImageView(view, R.id.i_square_item_avatar);
		TextView tvName = getTextView(view, R.id.i_square_item_name);
		TextView tvTime = getTextView(view, R.id.i_square_item_time);
		TextView tvDesc = getTextView(view, R.id.i_square_item_desc);
		TextView tvComment = getTextView(view, R.id.i_square_item_comment);
		GridView gvImages = (GridView) view
				.findViewById(R.id.i_square_item_images);
		imageLoader.displayImage(item.getUserInfo().getAvatar(), ivAvatar,
				options);
		tvName.setText(item.getUserInfo().getName());
		tvTime.setText(item.getTime());
		tvDesc.setText(item.getDescription());
		tvComment.setText(item.getCommentCount() + "");

		if (StringUtil.isEmpty(item.getImage())) {
			gvImages.setVisibility(View.GONE);
		} else {
			gvImages.setVisibility(View.VISIBLE);
			SquareImagesAdapter adapter = new SquareImagesAdapter(context,
					item.getImage());
			gvImages.setAdapter(adapter);
		}
		return view;
	}

	@Override
	public void addResult(int page, String result) {
		super.addResult(page, result);
		List<SquareItem> list = new Gson().fromJson(array,
				new TypeToken<List<SquareItem>>() {
				}.getType());
		addAll(list);
	}
}
