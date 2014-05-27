package android.oldfeel.yanzhuang;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.oldfeel.yanzhuang.app.JsonApi;
import android.oldfeel.yanzhuang.app.PersonInfo;
import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.fragment.list.InformationListFragment;
import android.oldfeel.yanzhuang.item.UserItem;
import android.oldfeel.yanzhuang.util.DialogUtil;
import android.oldfeel.yanzhuang.util.JSONUtil;
import android.oldfeel.yanzhuang.util.LogUtil;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.oldfeel.yanzhuang.util.NetUtil.RequestStringListener;
import android.oldfeel.yanzhuang.util.StringUtils;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.gson.Gson;

/**
 * 个人首页
 * 
 * @author oldfeel
 * 
 */
public class PersonHomeActivity extends BaseActivity implements OnClickListener {
	public static final int EDIT_PERSON_INFO = 1;
	private ImageView ivBg, ivAvtar;
	private ImageButton ibBgEdit;
	private TextView tvName, tvBirthday;
	private Button btnFollowing, btnMsg, btnFollowings, btnFans, btnServer;
	private long targetid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_home);
		targetid = getIntent().getLongExtra("targetid", -1);
		if (targetid == -1) {
			userNotExists();
		}
		initView();
		initListener();
		getPersonInfo();
		initInformation();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (targetid == getUserid()) {
			getMenuInflater().inflate(R.menu.person_home, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_edit:
			Intent intent = new Intent(PersonHomeActivity.this,
					EditPersonInfo.class);
			startActivityForResult(intent, EDIT_PERSON_INFO);
			break;
		case R.id.action_change_password:
			openActivity(ChangePassword.class);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 显示提醒
	 */
	private void initInformation() {
		NetUtil netUtil = new NetUtil(PersonHomeActivity.this,
				JsonApi.USER_INFORMATION_LIST);
		netUtil.setParams("targetid", targetid);
		InformationListFragment fragment = InformationListFragment
				.newInstance(netUtil);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.person_home_information, fragment).commit();
	}

	/**
	 * 获取个人信息
	 */
	private void getPersonInfo() {
		NetUtil netUtil = new NetUtil(PersonHomeActivity.this,
				JsonApi.USER_INFO);
		netUtil.setParams("userid", getUserid());
		netUtil.setParams("targetid", targetid);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JSONUtil.isSuccess(result)) {
					parseInfo(result);
				} else {
					showToast(JSONUtil.getMessage(result));
				}
			}
		});
	}

	protected void parseInfo(String result) {
		UserItem item = new Gson().fromJson(
				JSONUtil.getData(result).toString(), UserItem.class);
		if (!StringUtils.isEmpty(item.getBackground())) {
			imageLoader.displayImage(item.getBackground(), ivBg, options);
		}
		imageLoader.displayImage(item.getAvatar(), ivAvtar, options);
		tvName.setText(item.getName() + "\n" + item.getIntroduction());
		tvBirthday.setText(item.getHousenumber() + "\n" + item.getBirthday());
		btnFollowing.setText(item.isFollowing() ? "关注" : "取消关注");
		btnFollowings.setText("关注(" + item.getFollowingCount() + ")");
		btnFans.setText("粉丝(" + item.getFansCount() + ")");
		btnServer.setText("发布(" + item.getServerCount() + ")");
	}

	private void initListener() {
		ibBgEdit.setOnClickListener(this);
		btnFans.setOnClickListener(this);
		btnFollowing.setOnClickListener(this);
		btnFollowings.setOnClickListener(this);
		btnMsg.setOnClickListener(this);
		btnServer.setOnClickListener(this);
	}

	private void initView() {
		ivBg = getImageView(R.id.person_home_bg);
		ivAvtar = getImageView(R.id.person_home_avtar);
		ibBgEdit = getImageButton(R.id.person_home_bg_edit);
		tvName = getTextView(R.id.person_home_name);
		tvBirthday = getTextView(R.id.person_home_birthday);
		btnFollowing = getButton(R.id.person_home_following);
		btnMsg = getButton(R.id.person_home_message);
		btnFollowings = getButton(R.id.person_home_followings);
		btnFans = getButton(R.id.person_home_fans);
		btnServer = getButton(R.id.person_home_server);
		int width = getResources().getDisplayMetrics().widthPixels;
		ivBg.setLayoutParams(new LayoutParams(width, width / 2));
		if (super.getUserid() == targetid) {
			btnFollowing.setVisibility(View.GONE);
			btnMsg.setVisibility(View.GONE);
		} else {
			ibBgEdit.setVisibility(View.GONE);
		}
	}

	private void userNotExists() {
		DialogUtil.getInstance().showSimpleDialog(PersonHomeActivity.this,
				"用户不存在", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						PersonHomeActivity.this.finish();
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.person_home_bg_edit:
			bgEdit();
			break;
		case R.id.person_home_following:
			following();
			break;
		case R.id.person_home_followings:
			showFollowings();
			break;
		case R.id.person_home_message:
			showMessage();
			break;
		case R.id.person_home_fans:
			showFans();
			break;
		case R.id.person_home_server:
			showServer();
			break;
		default:
			break;
		}
	}

	/**
	 * 编辑背景
	 */
	private void bgEdit() {
	}

	/**
	 * 关注/取消关注
	 */
	private void following() {
		boolean isFollowing = false;
		if (btnFollowing.getText().equals("关注")) {
			isFollowing = true;
		}
		NetUtil netUtil = new NetUtil(PersonHomeActivity.this,
				JsonApi.USER_FOLLOWING);
		netUtil.setParams("userid", getUserid());
		netUtil.setParams("targetid", targetid);
		netUtil.setParams("isfollowingid", isFollowing);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				LogUtil.showLog(result);
			}
		});
	}

	/**
	 * 显示该用户关注的用户列表
	 */
	private void showFollowings() {
		Intent intent = new Intent(PersonHomeActivity.this, UserList.class);
		intent.putExtra("targetid", targetid);
		intent.putExtra("api", JsonApi.USER_FOLLOWINGS);
		startActivity(intent);
	}

	/**
	 * 发送私信/聊天
	 */
	private void showMessage() {
		Intent intent = new Intent();
		intent.setClass(PersonHomeActivity.this, ChatActivity.class);
		intent.putExtra("targetid", targetid);
		startActivity(intent);
	}

	/**
	 * 显示关注该用户的用户列表
	 */
	private void showFans() {
		Intent intent = new Intent(PersonHomeActivity.this, UserList.class);
		intent.putExtra("targetid", targetid);
		intent.putExtra("api", JsonApi.USER_FANS);
		startActivity(intent);
	}

	/**
	 * 显示该用户发出的服务
	 */
	private void showServer() {
		Intent intent = new Intent(PersonHomeActivity.this,
				UserReleaseList.class);
		intent.putExtra("targetid", targetid);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == EDIT_PERSON_INFO && resultCode == Activity.RESULT_OK) {
			updateData(data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void updateData(Intent data) {
		PersonInfo personInfo = PersonInfo.getInstance(getApplicationContext());
		tvName.setText(personInfo.getName() + "\n"
				+ personInfo.getIntroduction());
		tvBirthday.setText(personInfo.getHouseNumber() + "\n"
				+ personInfo.getBirthday());
	}
}
