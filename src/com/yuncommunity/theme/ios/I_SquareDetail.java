package com.yuncommunity.theme.ios;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.R;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.item.SquareItem;
import com.yuncommunity.list.SquareCommentList;
import com.yuncommunity.theme.ios.base.I_BaseActivity;

/**
 * 广场内容详情
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class I_SquareDetail extends I_BaseActivity implements OnClickListener {
	public static final int REQUEST_SEND_COMMENT = 1; // 发评论
	private SquareItem squareItem;
	private SquareCommentList commentList;
	private Button btnAttention, btnCollection, btnComment;
	private boolean isfollowing = false;
	private boolean iscollection = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_square_detail);
		setTitle("详情");
		hideRight();
		squareItem = (SquareItem) getIntent()
				.getSerializableExtra("squareitem");
		commentList = SquareCommentList.newInstance(getCommentListNetUtil(),
				squareItem);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.i_square_detail_frame, commentList).commit();
		btnAttention = getButton(R.id.i_square_detail_attention);
		btnCollection = getButton(R.id.i_square_detail_collection);
		btnComment = getButton(R.id.i_square_detail_comment);
		btnAttention.setOnClickListener(this);
		btnCollection.setOnClickListener(this);
		btnComment.setOnClickListener(this);
		getDetail();
	}

	private void getDetail() {
		NetUtil netUtil = new NetUtil(I_SquareDetail.this,
				JsonApi.INFORMATION_DETAIL);
		netUtil.setParams("userid", getUserId());
		netUtil.setParams("informationid", squareItem.getInformationid());
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					parseDetail(result);
				}
			}
		});
	}

	protected void parseDetail(String result) {
		try {
			JSONObject data = new JSONObject(JsonUtil.getData(result));
			isfollowing = data.getBoolean("IsFollowing");
			iscollection = data.getBoolean("IsCollection");
			btnAttention.setText(isfollowing ? "取消关注" : "关注");
			btnCollection.setText(iscollection ? "取消收藏" : "收藏");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private NetUtil getCommentListNetUtil() {
		NetUtil netUtil = new NetUtil(this, JsonApi.INFORMATION_COMMENTLIST);
		netUtil.setParams("userid", getUserId());
		netUtil.setParams("informationid", squareItem.getInformationid());
		return netUtil;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.i_square_detail_attention:
			attention();
			break;
		case R.id.i_square_detail_collection:
			collection();
			break;
		case R.id.i_square_detail_comment:
			comment();
			break;
		default:
			break;
		}
	}

	private void collection() {
		NetUtil netUtil = new NetUtil(I_SquareDetail.this,
				JsonApi.INFORMATION_COLLECTION);
		netUtil.setParams("userid", getUserId());
		netUtil.setParams("informationid", squareItem.getInformationid());
		netUtil.setParams("iscollection", !iscollection);
		netUtil.postRequest(iscollection ? "正在取消收藏" : "正在添加到收藏夹...",
				new RequestStringListener() {

					@Override
					public void onComplete(String result) {
						iscollection = !iscollection;
						btnCollection.setText(iscollection ? "取消收藏" : "收藏");
					}
				});
	}

	private void comment() {
		Intent intent = new Intent(I_SquareDetail.this,
				I_SquareCommentActivity.class);
		intent.putExtra("squareitem", squareItem);
		startActivityForResult(intent, REQUEST_SEND_COMMENT);
	}

	private void attention() {
		NetUtil netUtil = new NetUtil(I_SquareDetail.this,
				JsonApi.INFORMATION_FOLLOWING);
		netUtil.setParams("userid", getUserId());
		netUtil.setParams("informationid", squareItem.getInformationid());
		netUtil.setParams("isfollowing", !isfollowing);
		netUtil.postRequest(isfollowing ? "正在取消关注" : "正在关注...",
				new RequestStringListener() {

					@Override
					public void onComplete(String result) {
						isfollowing = !isfollowing;
						btnAttention.setText(isfollowing ? "取消关注" : "关注");
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
