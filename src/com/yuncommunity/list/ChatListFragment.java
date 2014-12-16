package com.yuncommunity.list;

import android.app.Fragment;

import com.oldfeel.utils.NetUtil;
import com.yuncommunity.base.CustomBaseListFragment;

/**
 * 聊天界面
 * 
 * @author oldfeel
 * 
 */
public class ChatListFragment extends CustomBaseListFragment {

	public static Fragment newInstance(NetUtil netUtil) {
		ChatListFragment fragment = new ChatListFragment();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
	}

	@Override
	public void initAdapter() {
	}

	@Override
	public void initHeaderView() {
	}

}
