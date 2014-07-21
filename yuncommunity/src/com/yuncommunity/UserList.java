package com.yuncommunity;

import android.os.Bundle;

import com.yuncommunity.app.Api;
import com.yuncommunity.base.BaseActivity;
import com.yuncommunity.fragment.list.UserListFragment;
import com.yuncommunity.util.NetUtil;

/**
 * 用户列表
 * 
 * @author oldfeel
 * 
 */
public class UserList extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_frame);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame,
						UserListFragment.newInstance(getNetUtil())).commit();
	}

	private NetUtil getNetUtil() {
		String api = getIntent().getStringExtra("api");
		NetUtil netUtil = new NetUtil(UserList.this, api);
		netUtil.setParams("userid", getUserid());
		if (api.equals(Api.INFORMATION_FOLLOWERS)) {
			long informationid = getIntent().getLongExtra("informationid", -1);
			netUtil.setParams("informationid", informationid);
		} else if (api.equals(Api.USER_FOLLOWINGS) || api.equals(Api.USER_FANS)) {
			long targetid = getIntent().getLongExtra("targetid", -1);
			netUtil.setParams("targetid", targetid);
		}
		return netUtil;
	}
}
