package com.yuncommunity.theme.android.list;

import android.content.Intent;

import com.oldfeel.base.BaseListFragment;
import com.oldfeel.utils.NetUtil;
import com.yuncommunity.theme.android.MainActivity;
import com.yuncommunity.theme.android.adapter.NearCommunityAdapter;
import com.yuncommunity.theme.android.item.CommunityItem;

/**
 * 附近小区列表
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月22日
 */
public class NearCommunityList extends BaseListFragment {
	public static NearCommunityList newInstance(NetUtil netUtil) {
		NearCommunityList fragment = new NearCommunityList();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
		CommunityItem communityItem = (CommunityItem) adapter.getItem(position);
		Intent intent = new Intent(getActivity(), MainActivity.class);
		intent.putExtra("communityitem", communityItem);
		startActivity(intent);
	}

	@Override
	public void initAdapter() {
		adapter = new NearCommunityAdapter(getActivity());
	}

}
