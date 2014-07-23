package com.yuncommunity.list;

import com.yuncommunity.adapter.CommentListAdapter;
import com.yuncommunity.base.BaseListFragment;
import com.yuncommunity.util.NetUtil;

/**
 * 评论列表
 * 
 * @author oldfeel
 * 
 */
public class CommentListFragment extends BaseListFragment {

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

}
