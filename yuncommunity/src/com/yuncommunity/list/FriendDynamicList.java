package com.yuncommunity.list;

import com.yuncommunity.adapter.FriendDynamicListAdapter;
import com.yuncommunity.base.BaseListFragment;
import com.yuncommunity.util.NetUtil;

import android.support.v4.app.Fragment;

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

}
