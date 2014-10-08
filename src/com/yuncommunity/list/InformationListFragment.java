package com.yuncommunity.list;

import android.content.Intent;

import com.oldfeel.base.BaseListFragment;
import com.oldfeel.utils.NetUtil;
import com.yuncommunity.ActivityDetail;
import com.yuncommunity.InformationDetail;
import com.yuncommunity.adapter.InformationListAdapter;
import com.yuncommunity.app.Constant;
import com.yuncommunity.item.InformationItem;

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
		Intent intent = new Intent();
		if (item.getInfotype() == Constant.TYPE_ACTIVITY) {
			intent.setClass(getActivity(), ActivityDetail.class);
		} else {
			intent.setClass(getActivity(), InformationDetail.class);
		}
		intent.putExtra("item", item);
		startActivity(intent);
	}

	@Override
	public void initAdapter() {
		adapter = new InformationListAdapter(getActivity());
	}

}
