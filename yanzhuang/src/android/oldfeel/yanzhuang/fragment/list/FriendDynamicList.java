package android.oldfeel.yanzhuang.fragment.list;

import android.oldfeel.yanzhuang.adapter.FriendDynamicListAdapter;
import android.oldfeel.yanzhuang.base.BaseListFragment;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.support.v4.app.Fragment;

/**
 * 朋友的动态信息
 * 
 * @author oldfeel
 * 
 */
public class FriendDynamicList extends BaseListFragment {

	public static Fragment newInstance(NetUtil netUtil) {
		FriendDynamicList fragment = new FriendDynamicList();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
	}

	@Override
	public void initAdapter() {
		adapter = new FriendDynamicListAdapter(getActivity());
	}

}
