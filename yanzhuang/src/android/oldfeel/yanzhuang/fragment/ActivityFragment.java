package android.oldfeel.yanzhuang.fragment;

import android.oldfeel.yanzhuang.R;
import android.oldfeel.yanzhuang.app.Constant;
import android.oldfeel.yanzhuang.app.JsonApi;
import android.oldfeel.yanzhuang.base.BaseFragment;
import android.oldfeel.yanzhuang.fragment.list.ActivityListFragment;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 活动
 * 
 * @author oldfeel
 * 
 */
public class ActivityFragment extends BaseFragment {

	public static ActivityFragment newInstance() {
		ActivityFragment fragment = new ActivityFragment();
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
						ActivityListFragment.newInstance(getNetUtil()))
				.commit();
	}

	private NetUtil getNetUtil() {
		NetUtil netUtil = new NetUtil(getActivity(), JsonApi.INFORMATION_LIST);
		netUtil.setParams("infotype", Constant.TYPE_ACTIVITY);
		return netUtil;
	}
}
