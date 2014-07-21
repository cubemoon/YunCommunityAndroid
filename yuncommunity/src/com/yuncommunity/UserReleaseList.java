package com.yuncommunity;

import android.os.Bundle;

import com.yuncommunity.app.Api;
import com.yuncommunity.base.BaseActivity;
import com.yuncommunity.fragment.list.InformationListFragment;
import com.yuncommunity.util.NetUtil;

/**
 * 用户发布的信息列表
 * 
 * @author oldfeel
 * 
 */
public class UserReleaseList extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_frame);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame,
						InformationListFragment.newInstance(getNetUtil()))
				.commit();
	}

	private NetUtil getNetUtil() {
		long userid = getIntent().getLongExtra("userid", -1);
		NetUtil netUtil = new NetUtil(UserReleaseList.this,
				Api.USER_RELEASE_LIST);
		netUtil.setParams("userid", userid);
		return netUtil;
	}
}
