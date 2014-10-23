package com.yuncommunity.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oldfeel.base.BaseFragment;
import com.oldfeel.utils.NetUtil;
import com.viewpagerindicator.TabPageIndicator;
import com.yuncommunity.R;
import com.yuncommunity.app.Constant;
import com.yuncommunity.app.JsonApi;
import com.yuncommunity.app.LoginInfo;
import com.yuncommunity.list.FriendDynamicList;
import com.yuncommunity.list.InformationListFragment;

/**
 * 关注
 * 
 * @author oldfeel
 * 
 */
public class AttentionFragment extends BaseFragment {
	private ViewPager pager;
	private TabPageIndicator indicator;

	public static AttentionFragment newInstance() {
		AttentionFragment fragment = new AttentionFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.attention_fragment, null);
		pager = (ViewPager) view.findViewById(R.id.pager);
		indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		MyFragmentPageAdapter adapter = new MyFragmentPageAdapter(getActivity()
				.getSupportFragmentManager());
		adapter.add(String.valueOf(getText(R.string.friend)),
				FriendDynamicList.newInstance(getFriendNetUtil()));
		adapter.add(String.valueOf(getText(R.string.activity)),
				getInformationList(Constant.TYPE_ACTIVITY));
		adapter.add(String.valueOf(getText(R.string.business_service)),
				getInformationList(Constant.TYPE_BUSINESS));
		adapter.add(String.valueOf(getText(R.string.personal_service)),
				getInformationList(Constant.TYPE_TASK));
		pager.setAdapter(adapter);
		indicator.setViewPager(pager);
	}

	private Fragment getInformationList(int infotype) {
		NetUtil netUtil = new NetUtil(getActivity(),
				JsonApi.USER_INFORMATION_LIST);
		netUtil.setParams("userid", LoginInfo.getInstance(getActivity())
				.getUserInfo().getUserid());
		netUtil.setParams("infotype", infotype);
		return InformationListFragment.newInstance(netUtil);
	}

	private NetUtil getFriendNetUtil() {
		NetUtil netUtil = new NetUtil(getActivity(),
				JsonApi.USER_FRIEND_DYNAMIC);
		netUtil.setParams("userid", LoginInfo.getInstance(getActivity())
				.getUserInfo().getUserid());
		return netUtil;
	}

	class MyFragmentPageAdapter extends FragmentPagerAdapter {
		ArrayList<PagerItem> list = new ArrayList<PagerItem>();

		public MyFragmentPageAdapter(FragmentManager fm) {
			super(fm);
		}

		public void add(String title, Fragment fragment) {
			list.add(new PagerItem(title, fragment));
		}

		@Override
		public Fragment getItem(int position) {
			return list.get(position).getFragment();
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return list.get(position).getTitle();
		}

	}

	class PagerItem {
		private String title;
		private Fragment fragment;

		public PagerItem(String title, Fragment fragment) {
			super();
			this.title = title;
			this.fragment = fragment;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Fragment getFragment() {
			return fragment;
		}

		public void setFragment(Fragment fragment) {
			this.fragment = fragment;
		}

	}
}
