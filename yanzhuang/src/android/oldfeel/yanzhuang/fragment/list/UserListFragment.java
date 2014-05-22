package android.oldfeel.yanzhuang.fragment.list;

import android.content.Intent;
import android.oldfeel.yanzhuang.PersonHomeActivity;
import android.oldfeel.yanzhuang.adapter.UserListAdapter;
import android.oldfeel.yanzhuang.base.BaseListFragment;
import android.oldfeel.yanzhuang.item.UserItem;
import android.oldfeel.yanzhuang.util.NetUtil;

/**
 * 用户列表
 * 
 * @author oldfeel
 * 
 */
public class UserListFragment extends BaseListFragment {

	public static UserListFragment newInstance(NetUtil netUtil) {
		UserListFragment fragment = new UserListFragment();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
		UserItem item = (UserItem) adapter.getItem(position);
		Intent intent = new Intent(getActivity(), PersonHomeActivity.class);
		intent.putExtra("userid", item.getUserid());
		startActivity(intent);
	}

	@Override
	public void initAdapter() {
		adapter = new UserListAdapter(getActivity());
	}

}
