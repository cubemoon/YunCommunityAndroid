package android.oldfeel.yanzhuang.fragment;

import android.oldfeel.yanzhuang.R;
import android.oldfeel.yanzhuang.app.JsonApi;
import android.oldfeel.yanzhuang.base.BaseFragment;
import android.oldfeel.yanzhuang.fragment.list.InformationListFragment;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
		getActivity()
				.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame,
						InformationListFragment.newInstance(getNetUtil()))
				.commit();
	}

	private NetUtil getNetUtil() {
		NetUtil netUtil = new NetUtil(getActivity(), JsonApi.INFORMATION_LIST);
		netUtil.setParams("infotype", infotype);
		return netUtil;
	}
}
