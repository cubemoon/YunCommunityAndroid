package android.oldfeel.yanzhuang;

import android.oldfeel.yanzhuang.app.JsonApi;
import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.fragment.list.InformationListFragment;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.os.Bundle;

/**
 * 用户发布的信息列表
 * 
 * @author oldfeel
 * 
 */
public class UserReleaseList extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_frame);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame,
						InformationListFragment.newInstance(getNetUtil()))
				.commit();
	}

	private NetUtil getNetUtil() {
		long userid = getIntent().getLongExtra("userid", -1);
		NetUtil netUtil = new NetUtil(UserReleaseList.this,
				JsonApi.USER_RELEASE_LIST);
		netUtil.setParams("userid", userid);
		return netUtil;
	}
}
