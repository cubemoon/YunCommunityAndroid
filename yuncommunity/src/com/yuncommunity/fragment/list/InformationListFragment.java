package com.yuncommunity.fragment.list;

import com.yuncommunity.InformationDetail;
import com.yuncommunity.adapter.InformationListAdapter;
import com.yuncommunity.base.BaseListFragment;
import com.yuncommunity.item.InformationItem;
import com.yuncommunity.util.NetUtil;

import android.content.Intent;

/**
 * 活动/商家服务/个人服务列表
 * 
 * @author oldfeel
 * 
 */
public class InformationListFragment extends BaseListFragment {
	public static InformationListFragment newInstance(NetUtil netUtil) {
		InformationListFragment fragment = new InformationListFragment();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
		InformationItem item = (InformationItem) adapter.getItem(position);
		Intent intent = new Intent(getActivity(), InformationDetail.class);
		intent.putExtra("item", item);
		startActivity(intent);
	}

	@Override
	public void initAdapter() {
		adapter = new InformationListAdapter(getActivity());
	}

}
