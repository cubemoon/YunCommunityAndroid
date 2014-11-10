package com.yuncommunity.theme.android.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oldfeel.base.BaseActivity;
import com.oldfeel.base.BaseBaseAdapter;
import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.LogUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.R;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.item.CommentItem;

/**
 * 评论列表
 * 
 * @author oldfeel
 * 
 */
public class CommentListAdapter extends BaseBaseAdapter<CommentItem> {

	public CommentListAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View view) {
		final CommentItem item = getItem(position);
		view = inflater.inflate(R.layout.comment_list_item, null);
		ImageView ivAvatar = getImageView(view, R.id.comment_list_item_avatar);
		TextView tvName = getTextView(view, R.id.comment_list_item_name);
		TextView tvContent = getTextView(view, R.id.comment_list_item_content);
		TextView tvTime = getTextView(view, R.id.comment_list_item_time);
		final Button btnApproval = getButton(view,
				R.id.comment_list_item_approval);
		final Button btnOpposition = getButton(view,
				R.id.comment_list_item_opposition);
		RatingBar rbScore = (RatingBar) view
				.findViewById(R.id.comment_list_item_score);
		imageLoader.displayImage(item.getAvatar(), ivAvatar, options);
		tvName.setText(item.getName());
		tvContent.setText(item.getContent());
		tvTime.setText(item.getTime());
		btnApproval.setText(item.getApprovalCount() + "");
		btnOpposition.setText(item.getOppositionCount() + "");
		rbScore.setRating(item.getScore());
		btnApproval.setSelected(item.isApproval());
		btnOpposition.setSelected(item.isOpposition());
		btnApproval.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (item.isApproval()) {
					item.setApproval(false);
					item.setApprovalCount(item.getApprovalCount() - 1);
					btnApproval.setSelected(false);
					approval(item, false);
				} else {
					item.setApproval(true);
					btnApproval.setSelected(true);
					item.setApprovalCount(item.getApprovalCount() + 1);
					approval(item, true);
				}
				btnApproval.setText(item.getApprovalCount() + "");
			}
		});
		btnOpposition.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (item.isOpposition()) {
					item.setOpposition(false);
					btnOpposition.setSelected(false);
					item.setOppositionCount(item.getOppositionCount() - 1);
					opposition(item, false);
				} else {
					item.setOpposition(true);
					btnOpposition.setSelected(true);
					item.setOppositionCount(item.getOppositionCount() + 1);
					opposition(item, true);
				}
				btnOpposition.setText(item.getOppositionCount() + "");
			}
		});
		return view;
	}

	/**
	 * 反对
	 * 
	 * @param item
	 * @param isOpposition
	 */
	protected void opposition(CommentItem item, boolean isOpposition) {
		NetUtil netUtil = new NetUtil((BaseActivity) context,
				JsonApi.COMMENT_OPPOSITION);
		netUtil.setParams("userid", LoginInfo.getInstance(context)
				.getUserInfo().getUserid());
		netUtil.setParams("commentid", item.getCommentid());
		netUtil.setParams("isopposition", isOpposition);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				LogUtil.showLog(JsonUtil.getData(result));
			}
		});
	}

	/**
	 * 赞
	 * 
	 * @param item
	 * @param isapproval
	 */
	protected void approval(CommentItem item, boolean isapproval) {
		NetUtil netUtil = new NetUtil((BaseActivity) context,
				JsonApi.COMMENT_APPROVAL);
		netUtil.setParams("userid", LoginInfo.getInstance(context)
				.getUserInfo().getUserid());
		netUtil.setParams("commentid", item.getCommentid());
		netUtil.setParams("isapproval", isapproval);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				LogUtil.showLog(JsonUtil.getData(result));
			}
		});
	}

	@Override
	public void addResult(int page, String result) {
		super.addResult(page, result);
		List<CommentItem> list = new Gson().fromJson(array,
				new TypeToken<List<CommentItem>>() {
				}.getType());
		addAll(list);
	}
}
