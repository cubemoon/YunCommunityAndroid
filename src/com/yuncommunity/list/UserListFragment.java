package com.yuncommunity.list;

import android.content.Intent;

import com.oldfeel.base.BaseListFragment;
import com.oldfeel.utils.NetUtil;
import com.yuncommunity.PersonHomeActivity;
import com.yuncommunity.adapter.UserListAdapter;
import com.yuncommunity.item.UserItem;

/**
 * 用户列表
 * 
 * @author oldfeel
 * 
 */
public class UserListFragment extends BaseListFragment {

	public static UserListFragment newInstance(NetUtil netUtil) {
		UserListFragment fragment = new UserListFragment();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
		UserItem item = (UserItem) adapter.getItem(position);
		Intent intent = new Intent(getActivity(), PersonHomeActivity.class);
		intent.putExtra("userid", item.getUserid());
		startActivity(intent);
	}

	@Override
	public void initAdapter() {
		adapter = new UserListAdapter(getActivity());
	}

}