package android.oldfeel.yanzhuang.fragment.list;

import android.oldfeel.yanzhuang.adapter.ActivityListAdapter;
import android.oldfeel.yanzhuang.base.BaseListFragment;
import android.oldfeel.yanzhuang.util.NetUtil;

/**
 * 活动列表
 * 
 * @author oldfeel
 * 
 */
public class ActivityListFragment extends BaseListFragment {
	public static ActivityListFragment newInstance(NetUtil netUtil) {
		ActivityListFragment fragment = new ActivityListFragment();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
	}

	@Override
	public void initAdapter() {
		adapter = new ActivityListAdapter(getActivity());
	}

}
