package android.oldfeel.yanzhuang.fragment;

import android.oldfeel.yanzhuang.R;
import android.oldfeel.yanzhuang.app.Constant;
import android.oldfeel.yanzhuang.app.Api;
import android.oldfeel.yanzhuang.app.PersonInfo;
import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.base.BaseFragment;
import android.oldfeel.yanzhuang.base.BaseTabsAdapter;
import android.oldfeel.yanzhuang.fragment.list.FriendDynamicList;
import android.oldfeel.yanzhuang.fragment.list.InformationListFragment;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 关注
 * 
 * @author oldfeel
 * 
 */
public class AttentionFragment extends BaseFragment {
	private ViewPager pager;
	private BaseTabsAdapter adapter;

	public static AttentionFragment newInstance() {
		AttentionFragment fragment = new AttentionFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.view_pager, null);
		pager = (ViewPager) view.findViewById(R.id.pager);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new BaseTabsAdapter((BaseActivity) getActivity(), pager);
		adapter.addTab("朋友", FriendDynamicList.newInstance(getFriendNetUtil()));
		adapter.addTab("活动", getInformationList(Constant.TYPE_ACTIVITY));
		adapter.addTab("商家服务", getInformationList(Constant.TYPE_BUSINESS));
		adapter.addTab("个人服务", getInformationList(Constant.TYPE_PERSONAL));
	}

	private Fragment getInformationList(int infotype) {
		NetUtil netUtil = new NetUtil(getActivity(),
				Api.USER_INFORMATION_LIST);
		netUtil.setParams("userid", PersonInfo.getInstance(getActivity())
				.getUserid());
		netUtil.setParams("infotype", infotype);
		return InformationListFragment.newInstance(netUtil);
	}

	private NetUtil getFriendNetUtil() {
		NetUtil netUtil = new NetUtil(getActivity(),
				Api.USER_FRIEND_DYNAMIC);
		netUtil.setParams("userid", PersonInfo.getInstance(getActivity())
				.getUserid());
		return netUtil;
	}

	@Override
	public void onStop() {
		ActionBar actionBar = ((BaseActivity) getActivity())
				.getSupportActionBar();
		actionBar.removeAllTabs();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		super.onStop();
	}
}
