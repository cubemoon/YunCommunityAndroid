package com.yuncommunity.list;

import com.oldfeel.utils.NetUtil;
import com.yuncommunity.adapter.CommentListAdapter;
import com.yuncommunity.base.CustomBaseListFragment;

/**
 * 评论列表
 * 
 * @author oldfeel
 * 
 */
public class CommentListFragment extends CustomBaseListFragment {

	public static CommentListFragment newInstance(NetUtil netUtil) {
		CommentListFragment fragment = new CommentListFragment();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
	}

	@Override
	public void initAdapter() {
		adapter = new CommentListAdapter(getActivity());
	}

	@Override
	public void initHeaderView() {
	}

}
