package com.yuncommunity.list;

import android.support.v4.app.Fragment;

import com.oldfeel.base.BaseListFragment;
import com.oldfeel.utils.NetUtil;

/**
 * 聊天界面
 * 
 * @author oldfeel
 * 
 */
public class ChatListFragment extends BaseListFragment {

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

}
