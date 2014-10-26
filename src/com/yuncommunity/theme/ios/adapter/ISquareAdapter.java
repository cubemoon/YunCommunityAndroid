package com.yuncommunity.theme.ios.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oldfeel.base.BaseBaseAdapter;
import com.yuncommunity.R;
import com.yuncommunity.item.SquareItem;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class ISquareAdapter extends BaseBaseAdapter<SquareItem> {

	public ISquareAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View view) {
		SquareItem item = getItem(position);
		view = inflater.inflate(R.layout.i_square_item, null);
		ImageView ivImage = getImageView(view, R.id.i_square_item_image);
		TextView tvTitle = getTextView(view, R.id.i_square_item_title);
		TextView tvDesc = getTextView(view, R.id.i_square_item_desc);
		TextView tvEvaluation = getTextView(view, R.id.i_square_item_evaluation);
		TextView tvTime = getTextView(view, R.id.i_square_item_time);
		RatingBar rbScore = super.getRatingBar(view, R.id.i_square_item_score);
		imageLoader.displayImage(item.getImage(), ivImage, options);
		tvTitle.setText(item.getTitle());
		tvDesc.setText(item.getDescription());
		tvEvaluation.setText(String.valueOf(item.getEvaluation()));
		tvTime.setText(item.getTime());
		rbScore.setRating(item.getScore());
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
