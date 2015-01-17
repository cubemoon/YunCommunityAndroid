package com.yuncommunity.list;

import android.app.Fragment;

import com.oldfeel.utils.NetUtil;
import com.yuncommunity.adapter.FriendDynamicListAdapter;
import com.yuncommunity.base.CustomBaseListFragment;

/**
 * 朋友的动态信息
 * 
 * @author oldfeel
 * 
 */
public class FriendDynamicList extends CustomBaseListFragment {

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
