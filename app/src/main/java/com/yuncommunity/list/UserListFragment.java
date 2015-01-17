package com.yuncommunity.list;

import android.content.Intent;

import com.oldfeel.utils.NetUtil;
import com.yuncommunity.adapter.UserListAdapter;
import com.yuncommunity.base.CustomBaseListFragment;
import com.yuncommunity.item.UserItem;
import com.yuncommunity.theme.android.A_PersonHomeActivity;

/**
 * 用户列表
 * 
 * @author oldfeel
 * 
 */
public class UserListFragment extends CustomBaseListFragment {

	public static UserListFragment newInstance(NetUtil netUtil) {
		UserListFragment fragment = new UserListFragment();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
		UserItem item = (UserItem) adapter.getItem(position);
		Intent intent = new Intent(getActivity(), A_PersonHomeActivity.class);
		intent.putExtra("userid", item.getUserid());
		startActivity(intent);
	}

	@Override
	public void initAdapter() {
		adapter = new UserListAdapter(getActivity());
	}

	@Override
	public void initHeaderView() {
	}

}
