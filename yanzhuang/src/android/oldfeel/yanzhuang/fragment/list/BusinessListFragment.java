package android.oldfeel.yanzhuang.fragment.list;

import android.oldfeel.yanzhuang.adapter.BusinessListAdapter;
import android.oldfeel.yanzhuang.base.BaseListFragment;
import android.oldfeel.yanzhuang.util.NetUtil;

/**
 * 商家列表
 * 
 * @author oldfeel
 * 
 */
public class BusinessListFragment extends BaseListFragment {
	public static BusinessListFragment newInstance(NetUtil netUtil) {
		BusinessListFragment fragment = new BusinessListFragment();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
	}

	@Override
	public void initAdapter() {
		adapter = new BusinessListAdapter(getActivity());
	}

}
