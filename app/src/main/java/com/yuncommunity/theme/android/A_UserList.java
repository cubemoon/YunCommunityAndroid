package com.yuncommunity.theme.android;

import android.os.Bundle;

import com.oldfeel.utils.NetUtil;
import com.yuncommunity.R;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.list.UserListFragment;
import com.yuncommunity.theme.android.base.A_BaseActivity;

/**
 * 用户列表
 * 
 * @author oldfeel
 * 
 */
public class A_UserList extends A_BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_frame);
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame,
						UserListFragment.newInstance(getNetUtil())).commit();
	}

	private NetUtil getNetUtil() {
		String api = getIntent().getStringExtra("api");
		NetUtil netUtil = new NetUtil(A_UserList.this, api);
		netUtil.setParams("userid",
				LoginInfo.getInstance(getApplicationContext()).getUserId());
		if (api.equals(JsonApi.INFORMATION_FOLLOWERS)) {
			long informationid = getIntent().getLongExtra("informationid", -1);
			netUtil.setParams("informationid", informationid);
		} else if (api.equals(JsonApi.USER_FOLLOWINGS)
				|| api.equals(JsonApi.USER_FANS)) {
			long targetid = getIntent().getLongExtra("targetid", -1);
			netUtil.setParams("targetid", targetid);
		}
		return netUtil;
	}
}
