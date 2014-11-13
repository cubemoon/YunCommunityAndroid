package com.yuncommunity.list;

import android.content.Intent;

import com.oldfeel.utils.NetUtil;
import com.yuncommunity.adapter.NearCommunityAdapter;
import com.yuncommunity.base.CustomBaseListFragment;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.item.CommunityItem;
import com.yuncommunity.theme.android.MainActivity;
import com.yuncommunity.utils.UpdateUtils;

/**
 * 附近小区列表
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月22日
 */
public class NearCommunityList extends CustomBaseListFragment {
	public static NearCommunityList newInstance(NetUtil netUtil) {
		NearCommunityList fragment = new NearCommunityList();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
		CommunityItem communityItem = (CommunityItem) adapter.getItem(position);
		LoginInfo.getInstance(getActivity()).saveCommunityInfo(communityItem);
		UpdateUtils.update(getActivity());
		Intent intent = getThemeIntent(MainActivity.class);
		intent.putExtra("communityitem", communityItem);
		startActivity(intent);
		getActivity().finish();
	}

	@Override
	public void initAdapter() {
		adapter = new NearCommunityAdapter(getActivity());
	}

	@Override
	public void initHeaderView() {
	}

}
