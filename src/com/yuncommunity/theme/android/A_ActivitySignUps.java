package com.yuncommunity.theme.android;

import android.os.Bundle;

import com.oldfeel.utils.NetUtil;
import com.yuncommunity.R;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.item.InformationItem;
import com.yuncommunity.list.ActivitySignUpList;
import com.yuncommunity.theme.android.base.A_BaseActivity;

/**
 * 活动报名者列表
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年7月27日
 */
public class A_ActivitySignUps extends A_BaseActivity {
	InformationItem item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_frame);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame,
						ActivitySignUpList.newInstance(getNetUtil())).commit();
	}

	private NetUtil getNetUtil() {
		long informationid = getIntent().getLongExtra("informationid", 0);
		NetUtil netUtil = new NetUtil(this, JsonApi.ACTIVITY_SIGN_UP_LIST);
		netUtil.setParams("informationid", informationid);
		return netUtil;
	}

}
