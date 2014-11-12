package com.yuncommunity.theme.ios.fragment;

import android.content.Intent;

import com.oldfeel.base.BaseListFragment;
import com.oldfeel.utils.NetUtil;
import com.yuncommunity.adapter.ActivityAdapter;
import com.yuncommunity.item.InformationItem;
import com.yuncommunity.theme.ios.I_ActivityDetail;

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
		startActivity(new Intent(getActivity(), I_ActivityDetail.class)
				.putExtra("item", item));
	}

	@Override
	public void initAdapter() {
		adapter = new ActivityAdapter(getActivity());
	}

	@Override
	public void initHeaderView() {
	}
}
