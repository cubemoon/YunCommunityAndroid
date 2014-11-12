package com.yuncommunity.list;

import android.support.v4.app.Fragment;

import com.oldfeel.utils.NetUtil;
import com.yuncommunity.adapter.ActivitySignUpListAdapter;
import com.yuncommunity.base.CustomBaseListFragment;

/**
 * 活动报名者列表
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年7月27日
 */
public class ActivitySignUpList extends CustomBaseListFragment {

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
