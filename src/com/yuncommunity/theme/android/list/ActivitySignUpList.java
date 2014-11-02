package com.yuncommunity.theme.android.list;

import android.support.v4.app.Fragment;

import com.oldfeel.base.BaseListFragment;
import com.oldfeel.utils.NetUtil;
import com.yuncommunity.theme.android.adapter.ActivitySignUpListAdapter;

/**
 * 活动报名者列表
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年7月27日
 */
public class ActivitySignUpList extends BaseListFragment {

	public static Fragment newInstance(NetUtil netUtil) {
		ActivitySignUpList fragment = new ActivitySignUpList();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
	}

	@Override
	public void initAdapter() {
		adapter = new ActivitySignUpListAdapter(getActivity());
	}

	@Override
	public void initHeaderView() {
	}

}
