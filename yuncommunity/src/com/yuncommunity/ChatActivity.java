package com.yuncommunity;

import android.os.Bundle;

import com.oldfeel.base.BaseActivity;
import com.oldfeel.utils.NetUtil;
import com.yuncommunity.app.JsonApi;
import com.yuncommunity.list.ChatListFragment;

/**
 * 发送私信/聊天界面
 * 
 * @author oldfeel
 * 
 */
public class ChatActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_frame);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame,
						ChatListFragment.newInstance(getNetUtil())).commit();
	}

	private NetUtil getNetUtil() {
		long targetid = getIntent().getLongExtra("targetid", -1);
		NetUtil netUtil = new NetUtil(ChatActivity.this, JsonApi.CHAT_HISTORY);
		netUtil.setParams("userid", getUserid());
		netUtil.setParams("targetid", targetid);
		return netUtil;
	}
}
