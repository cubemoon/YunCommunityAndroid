package com.yuncommunity.theme.ios.list;

import com.oldfeel.base.BaseListFragment;
import com.oldfeel.utils.NetUtil;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class ISquareCommentList extends BaseListFragment {
	public static ISquareCommentList newInstance(NetUtil netUtil) {
		ISquareCommentList fragment = new ISquareCommentList();
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
