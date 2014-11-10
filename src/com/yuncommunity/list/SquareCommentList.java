package com.yuncommunity.list;

import com.oldfeel.base.BaseListFragment;
import com.oldfeel.utils.NetUtil;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月10日
 */
public class SquareCommentList extends BaseListFragment {

	public static SquareCommentList newInstance(NetUtil netUtil) {
		SquareCommentList fragment = new SquareCommentList();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
	}

	@Override
	public void initHeaderView() {
	}

	@Override
	public void initAdapter() {
	}

}
