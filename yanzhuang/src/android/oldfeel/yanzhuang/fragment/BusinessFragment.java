package android.oldfeel.yanzhuang.fragment;

import android.oldfeel.yanzhuang.R;
import android.oldfeel.yanzhuang.app.Constant;
import android.oldfeel.yanzhuang.app.JsonApi;
import android.oldfeel.yanzhuang.base.BaseFragment;
import android.oldfeel.yanzhuang.fragment.list.BusinessListFragment;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 商家服务
 * 
 * @author oldfeel
 * 
 */
public class BusinessFragment extends BaseFragment {

	public static BusinessFragment newInstance() {
		BusinessFragment fragment = new BusinessFragment();
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
						BusinessListFragment.newInstance(getNetUtil()))
				.commit();
	}

	private NetUtil getNetUtil() {
		NetUtil netUtil = new NetUtil(getActivity(), JsonApi.INFORMATION_LIST);
		netUtil.setParams("infotype", Constant.TYPE_BUSINESS);
		return netUtil;
	}
}
