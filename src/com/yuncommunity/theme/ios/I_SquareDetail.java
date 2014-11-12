package com.yuncommunity.theme.ios;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.oldfeel.utils.NetUtil;
import com.yuncommunity.R;
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
	private SquareItem squareItem;
	private SquareCommentList commentList;
	private Button btnAttention, btnCollection, btnComment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_square_detail);
		squareItem = (SquareItem) getIntent().getSerializableExtra("item");
		commentList = SquareCommentList.newInstance(getCommentListNetUtil());
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.i_square_detail_frame, commentList).commit();
		btnAttention = getButton(R.id.i_square_detail_attention);
		btnCollection = getButton(R.id.i_square_detail_collection);
		btnComment = getButton(R.id.i_square_detail_comment);
		btnAttention.setOnClickListener(this);
		btnCollection.setOnClickListener(this);
		btnComment.setOnClickListener(this);
	}

	private NetUtil getCommentListNetUtil() {
		return null;
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
	}

	private void comment() {
	}

	private void attention() {
	}
}
