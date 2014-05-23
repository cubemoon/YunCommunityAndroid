package android.oldfeel.yanzhuang;

import android.oldfeel.yanzhuang.app.JsonApi;
import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.fragment.list.ChatListFragment;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.os.Bundle;

/**
 * 发送私信/聊天界面
 * 
 * @author oldfeel
 * 
 */
public class ChatActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_frame);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame,
						ChatListFragment.newInstance(getNetUtil())).commit();
	}

	private NetUtil getNetUtil() {
		long targetid = getIntent().getLongExtra("targetid", -1);
		NetUtil netUtil = new NetUtil(ChatActivity.this, JsonApi.CHAT_HISTORY);
		netUtil.setParams("userid", getUserid());
		netUtil.setParams("targetid", targetid);
		return netUtil;
	}
}
