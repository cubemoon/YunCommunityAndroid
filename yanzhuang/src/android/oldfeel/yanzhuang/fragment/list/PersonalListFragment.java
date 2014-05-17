package android.oldfeel.yanzhuang.fragment.list;

import android.oldfeel.yanzhuang.adapter.PersonListAdapter;
import android.oldfeel.yanzhuang.base.BaseListFragment;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.support.v4.app.Fragment;

/**
 * 个人服务列表
 * 
 * @author oldfeel
 * 
 */
public class PersonalListFragment extends BaseListFragment {

	public static Fragment newInstance(NetUtil netUtil) {
		PersonalListFragment fragment = new PersonalListFragment();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
	}

	@Override
	public void initAdapter() {
		adapter = new PersonListAdapter(getActivity());
	}

}
