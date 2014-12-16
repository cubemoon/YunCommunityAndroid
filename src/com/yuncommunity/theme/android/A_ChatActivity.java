package com.yuncommunity.theme.android;

import android.os.Bundle;

import com.oldfeel.utils.NetUtil;
import com.yuncommunity.R;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.list.ChatListFragment;
import com.yuncommunity.theme.android.base.A_BaseActivity;

/**
 * 发送私信/聊天界面
 * 
 * @author oldfeel
 * 
 */
public class A_ChatActivity extends A_BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_frame);
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame,
						ChatListFragment.newInstance(getNetUtil())).commit();
	}

	private NetUtil getNetUtil() {
		long targetid = getIntent().getLongExtra("targetid", -1);
		NetUtil netUtil = new NetUtil(A_ChatActivity.this, JsonApi.CHAT_HISTORY);
		netUtil.setParams("userid",
				LoginInfo.getInstance(getApplicationContext()).getUserId());
		netUtil.setParams("targetid", targetid);
		return netUtil;
	}
}
