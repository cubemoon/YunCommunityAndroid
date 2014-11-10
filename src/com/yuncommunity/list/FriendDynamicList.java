package com.yuncommunity.list;

import android.support.v4.app.Fragment;

import com.oldfeel.base.BaseListFragment;
import com.oldfeel.utils.NetUtil;
import com.yuncommunity.adapter.FriendDynamicListAdapter;

/**
 * 朋友的动态信息
 * 
 * @author oldfeel
 * 
 */
public class FriendDynamicList extends BaseListFragment {

	public static Fragment newInstance(NetUtil netUtil) {
		FriendDynamicList fragment = new FriendDynamicList();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
	}

	@Override
	public void initAdapter() {
		adapter = new FriendDynamicListAdapter(getActivity());
	}

	@Override
	public void initHeaderView() {
	}

}
