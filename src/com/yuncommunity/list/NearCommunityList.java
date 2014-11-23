package com.yuncommunity.list;

import android.content.Intent;

import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.adapter.NearCommunityAdapter;
import com.yuncommunity.base.CustomBaseListFragment;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.item.CommunityItem;
import com.yuncommunity.theme.android.A_MainActivity;
import com.yuncommunity.utils.Utils;

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
		if (Utils.isLogin(getActivity(), false)) {
			changeUserCommunity(communityItem);
		} else {
			changeNowCommunity(communityItem);
		}
	}

	/**
	 * 切换应用当前显示的小区
	 * 
	 * @param communityItem
	 */
	private void changeNowCommunity(CommunityItem communityItem) {
		LoginInfo.getInstance(getActivity()).saveCommunityInfo(communityItem);
		Intent intent = getThemeIntent(A_MainActivity.class);
		intent.putExtra("communityitem", communityItem);
		startActivity(intent);
		getActivity().onBackPressed();
	}

	/**
	 * 切换用户小区
	 * 
	 * @param communityItem
	 */
	private void changeUserCommunity(final CommunityItem communityItem) {
		NetUtil netUtil = new NetUtil(getActivity(), JsonApi.CHANGE_COMMUNITY);
		netUtil.setParams("userid", LoginInfo.getInstance(getActivity())
				.getUserId());
		netUtil.setParams("communityid", communityItem.getCommunityid());
		netUtil.postRequest("正在切换小区", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					changeNowCommunity(communityItem);
				}
			}
		});
	}

	@Override
	public void initAdapter() {
		adapter = new NearCommunityAdapter(getActivity());
	}

	@Override
	public void initHeaderView() {
	}

}
