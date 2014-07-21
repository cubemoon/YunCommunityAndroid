package com.yuncommunity.fragment.list;

import com.yuncommunity.PersonHomeActivity;
import com.yuncommunity.adapter.UserListAdapter;
import com.yuncommunity.base.BaseListFragment;
import com.yuncommunity.item.UserItem;
import com.yuncommunity.util.NetUtil;

import android.content.Intent;

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
