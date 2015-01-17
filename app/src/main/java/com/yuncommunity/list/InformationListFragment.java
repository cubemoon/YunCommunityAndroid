package com.yuncommunity.list;

import android.content.Intent;

import com.oldfeel.utils.NetUtil;
import com.yuncommunity.adapter.InformationListAdapter;
import com.yuncommunity.base.CustomBaseListFragment;
import com.yuncommunity.conf.Constant;
import com.yuncommunity.item.InformationItem;
import com.yuncommunity.theme.android.A_ActivityDetail;
import com.yuncommunity.theme.android.A_InformationDetail;

/**
 * 活动/商家服务/个人服务列表
 * 
 * @author oldfeel
 * 
 */
public class InformationListFragment extends CustomBaseListFragment {
	public static InformationListFragment newInstance(NetUtil netUtil) {
		InformationListFragment fragment = new InformationListFragment();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
		InformationItem item = (InformationItem) adapter.getItem(position);
		Intent intent = new Intent();
		if (item.getInfotype() == Constant.TYPE_ACTIVITY) {
			intent.setClass(getActivity(), A_ActivityDetail.class);
		} else {
			intent.setClass(getActivity(), A_InformationDetail.class);
		}
		intent.putExtra("item", item);
		startActivity(intent);
	}

	@Override
	public void initAdapter() {
		adapter = new InformationListAdapter(getActivity());
	}

	@Override
	public void initHeaderView() {
	}

}
