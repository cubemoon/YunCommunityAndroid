package com.yuncommunity.theme.android;

import android.os.Bundle;

import com.oldfeel.base.BaseActivity;
import com.oldfeel.utils.NetUtil;
import com.yuncommunity.R;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.theme.android.list.InformationListFragment;

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
		long userid = getIntent().getLongExtra("targetid", -1);
		NetUtil netUtil = new NetUtil(UserReleaseList.this,
				JsonApi.USER_RELEASE_LIST);
		netUtil.setParams("userid", userid);
		return netUtil;
	}
}
