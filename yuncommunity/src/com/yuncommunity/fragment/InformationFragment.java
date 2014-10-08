package com.yuncommunity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oldfeel.base.BaseFragment;
import com.oldfeel.utils.NetUtil;
import com.yuncommunity.R;
import com.yuncommunity.app.JsonApi;
import com.yuncommunity.list.InformationListFragment;

/**
 * 活动/商家服务/个人服务
 * 
 * @author oldfeel
 * 
 */
public class InformationFragment extends BaseFragment {
	private int infotype;

	public static InformationFragment newInstance(int infotype) {
		InformationFragment fragment = new InformationFragment();
		fragment.infotype = infotype;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.single_frame, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		updateList();
	}

	private NetUtil getNetUtil() {
		NetUtil netUtil = new NetUtil(getActivity(), JsonApi.INFORMATION_LIST);
		netUtil.setParams("infotype", infotype);
		return netUtil;
	}

	/**
	 * 更新列表
	 */
	public void updateList() {
		getActivity()
				.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame,
						InformationListFragment.newInstance(getNetUtil()))
				.commit();
	}
}
