package android.oldfeel.yanzhuang;

import android.oldfeel.yanzhuang.app.JsonApi;
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
		String api = getIntent().getStringExtra("api");
		NetUtil netUtil = new NetUtil(UserList.this, api);
		netUtil.setParams("userid", getUserid());
		if (api.equals(JsonApi.INFORMATION_FOLLOWERS)) {
			long informationid = getIntent().getLongExtra("informationid", -1);
			netUtil.setParams("informationid", informationid);
		} else if (api.equals(JsonApi.USER_FOLLOWINGS)
				|| api.equals(JsonApi.USER_FANS)) {
			long targetid = getIntent().getLongExtra("targetid", -1);
			netUtil.setParams("targetid", targetid);
		}
		return netUtil;
	}
}
