package android.oldfeel.yanzhuang.fragment.list;

import android.oldfeel.yanzhuang.base.BaseListFragment;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.support.v4.app.Fragment;

/**
 * 聊天界面
 * 
 * @author oldfeel
 * 
 */
public class ChatListFragment extends BaseListFragment {

	public static Fragment newInstance(NetUtil netUtil) {
		ChatListFragment fragment = new ChatListFragment();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
	}

	@Override
	public void initAdapter() {
	}

}
