package com.yuncommunity.theme.ios;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.google.gson.Gson;
import com.oldfeel.utils.ETUtil;
import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.R;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.item.SquareCommentItem;
import com.yuncommunity.item.SquareItem;
import com.yuncommunity.theme.ios.base.I_BaseActivity;

/**
 * 评论广场信息
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月13日
 */
public class I_SquareCommentActivity extends I_BaseActivity {
	private long informationid;
	private long replyid;
	private EditText etContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_square_comment);
		setTitle("发评论");
		etContent = getEditText(R.id.i_square_comment_content);
		SquareItem squareItem = (SquareItem) getIntent().getSerializableExtra(
				"squareitem");
		SquareCommentItem squarecommentitem = (SquareCommentItem) getIntent()
				.getSerializableExtra("squarecommentitem");
		if (squarecommentitem != null) {
			etContent.setHint("回复" + squarecommentitem.getName() + "的评论...");
			replyid = squarecommentitem.getUserid();
			informationid = squarecommentitem.getInformationid();
		} else {
			informationid = squareItem.getInformationid();
		}
		btnRight.setText("发送");
		btnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				submitSend();
			}
		});
	}

	protected void submitSend() {
		if (ETUtil.isHaveNull(etContent)) {
			return;
		}
		NetUtil netUtil = new NetUtil(this, JsonApi.SQUARE_COMMENT);
		netUtil.setParams("userid", getUserId());
		netUtil.setParams("informationid", informationid);
		netUtil.setParams("content", getString(etContent));
		netUtil.setParams("replyid", replyid);
		netUtil.postRequest("正在评论...", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					showToast("评论成功 ");
					SquareCommentItem item = new Gson().fromJson(
							JsonUtil.getData(result), SquareCommentItem.class);
					Intent intent = new Intent();
					intent.putExtra("item", item);
					setResult(RESULT_OK, intent);
					onBackPressed();
				}
			}
		});
	}
}
