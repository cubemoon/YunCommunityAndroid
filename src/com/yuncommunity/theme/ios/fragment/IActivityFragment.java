package com.yuncommunity.theme.ios.fragment;

import android.content.Intent;

import com.oldfeel.base.BaseListFragment;
import com.oldfeel.utils.NetUtil;
import com.yuncommunity.item.InformationItem;
import com.yuncommunity.theme.ios.IActivityDetail;
import com.yuncommunity.theme.ios.adapter.IActivityAdapter;

/**
 * 活动
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月25日
 */
public class IActivityFragment extends BaseListFragment {
	public static IActivityFragment newInstance(NetUtil netUtil) {
		IActivityFragment fragment = new IActivityFragment();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
		InformationItem item = (InformationItem) adapter.getItem(position);
		startActivity(new Intent(getActivity(), IActivityDetail.class)
				.putExtra("item", item));
	}

	@Override
	public void initAdapter() {
		adapter = new IActivityAdapter(getActivity());
	}

	@Override
	public void initHeaderView() {
	}
}
