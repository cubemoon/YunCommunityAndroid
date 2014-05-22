package android.oldfeel.yanzhuang;

import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.fragment.list.UserListFragment;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.os.Bundle;

/**
 * 用户列表
 * 
 * @author oldfeel
 * 
 */
public class UserList extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_frame);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame,
						UserListFragment.newInstance(getNetUtil())).commit();
	}

	private NetUtil getNetUtil() {
		long userid = getIntent().getLongExtra("userid", -1);
		String api = getIntent().getStringExtra("api");
		NetUtil netUtil = new NetUtil(UserList.this, api);
		netUtil.setParams("userid", getUserid());
		netUtil.setParams("targetid", userid);
		return netUtil;
	}
}
