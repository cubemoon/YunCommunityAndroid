package com.yuncommunity.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oldfeel.base.BaseBaseAdapter;
import com.yuncommunity.R;
import com.yuncommunity.item.SquareCommentItem;
import com.yuncommunity.theme.ios.I_SquareCommentActivity;
import com.yuncommunity.theme.ios.I_SquareDetail;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月13日
 */
public class SquareCommentAdapter extends BaseBaseAdapter<SquareCommentItem> {

	public SquareCommentAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View view) {
		final SquareCommentItem item = getItem(position);
		view = inflater.inflate(R.layout.square_comment_item, null);
		ImageView ivAvatar = getImageView(view, R.id.square_comment_item_avatar);
		TextView tvName = getTextView(view, R.id.square_comment_item_name);
		TextView tvContent = getTextView(view, R.id.square_comment_item_content);
		TextView tvTime = getTextView(view, R.id.square_comment_item_time);
		Button btnReply = getButton(view, R.id.square_comment_item_reply);
		imageLoader.displayImage(item.getAvatar(), ivAvatar, options);
		tvName.setText(item.getName());
		tvContent.setText(item.getContent());
		tvTime.setText(item.getTime());
		btnReply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						I_SquareCommentActivity.class);
				intent.putExtra("squarecommentitem", item);
				((Activity) context).startActivityForResult(intent,
						I_SquareDetail.REQUEST_SEND_COMMENT);
			}
		});
		return view;
	}

	@Override
	public void addResult(int page, String result) {
		super.addResult(page, result);
		List<SquareCommentItem> list = new Gson().fromJson(array,
				new TypeToken<List<SquareCommentItem>>() {
				}.getType());
		addAll(list);
	}

}
