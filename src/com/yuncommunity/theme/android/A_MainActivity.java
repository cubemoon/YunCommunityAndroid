package com.yuncommunity.theme.android;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.winsontan520.wversionmanager.library.WVersionManager;
import com.yuncommunity.R;
import com.yuncommunity.adapter.DrawerListAdapter;
import com.yuncommunity.conf.Constant;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.item.CommunityItem;
import com.yuncommunity.theme.android.base.A_BaseActivity;
import com.yuncommunity.theme.android.fragment.AttentionFragment;
import com.yuncommunity.theme.android.fragment.InformationFragment;
import com.yuncommunity.utils.Utils;

/**
 * 主界面
 * 
 * @author oldfeel
 * 
 */
public class A_MainActivity extends A_BaseActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerListAdapter drawerListAdapter;
	private InformationFragment squareFragment;
	private AttentionFragment attentionFragment;
	private InformationFragment activityFragment;
	private InformationFragment businessFragment;
	private InformationFragment taskFragment;
	private int infotype;
	private TextView tvName;
	private ImageView ivAvatar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setSwipeBackEnable(false);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		drawerListAdapter = new DrawerListAdapter(getApplicationContext());
		mDrawerList.addHeaderView(getHeaderView());
		mDrawerList.setAdapter(drawerListAdapter);
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectItem(position);
			}
		});
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (savedInstanceState == null) {
			selectItem(2);
		}
		updatePersonInfo();

		initCommunity();
		checkVersion();
	}

	private void checkVersion() {
		WVersionManager versionManager = new WVersionManager(this);

		versionManager.setVersionContentUrl(Constant.CHECK_VERSION);
		versionManager.setUpdateNowLabel("现在更新");
		versionManager.setRemindMeLaterLabel("稍后更新");
		versionManager.setIgnoreThisVersionLabel("忽略当前版本");
		versionManager.setReminderTimer(1);

		versionManager.checkVersion();
	}

	private void initCommunity() {
		if (LoginInfo.getInstance(getApplicationContext()).getCommunityInfo()
				.getCommunityid() == 0) {
			openActivity(A_ChangeCommunity.class);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// 登陆
		boolean isLogin = intent.getBooleanExtra("islogin", false);
		if (isLogin) {
			updateHeaderView();
		}
		// 发布
		boolean result = intent.getBooleanExtra("result", false);
		if (result) { // 发布成功
			int infotype = intent.getIntExtra("infotype", 0);
			if (infotype == Constant.TYPE_ACTIVITY
					&& activityFragment.isVisible()) {
				activityFragment.updateList();
			}
			if (infotype == Constant.TYPE_BUSINESS
					&& businessFragment.isVisible()) {
				businessFragment.updateList();
			}
			if (infotype == Constant.TYPE_PERSONAL && taskFragment.isVisible()) {
				taskFragment.updateList();
			}
		}
		// 切换小区
		CommunityItem communityItem = (CommunityItem) intent
				.getSerializableExtra("communityitem");
		if (communityItem != null) {
			LoginInfo.getInstance(getApplicationContext()).getUserInfo()
					.setCommunityInfo(communityItem);
		}
		super.onNewIntent(intent);
	}

	/**
	 * 自动登录
	 */
	private void updatePersonInfo() {
		if (!Utils.isLogin(A_MainActivity.this, false)) {
			return;
		}
		String email = LoginInfo.getInstance(getApplicationContext())
				.getUserInfo().getEmail();
		String password = LoginInfo.getInstance(getApplicationContext())
				.getRealPassword();
		NetUtil netUtil = new NetUtil(A_MainActivity.this, JsonApi.LOGIN);
		netUtil.setParams("email", email);
		netUtil.setParams("password", password);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					LoginInfo.getInstance(getApplicationContext()).saveInfo(
							JsonUtil.getData(result).toString());
					updateHeaderView();
				} else {

					showToast(getText(R.string.auto_login_failed) + ","
							+ JsonUtil.getData(result));

					cancelLogin();
				}
			}
		});
	}

	/**
	 * 登出
	 */
	protected void cancelLogin() {
		LoginInfo.getInstance(A_MainActivity.this).cancelLogin();
		openActivity(A_LoginActivity.class);
		finish();
	}

	private View getHeaderView() {
		View view = getLayoutInflater()
				.inflate(R.layout.menu_header_view, null);
		ivAvatar = (ImageView) view.findViewById(R.id.avatar);
		tvName = (TextView) view.findViewById(R.id.text);
		updateHeaderView();
		return view;
	}

	private void updateHeaderView() {
		imageLoader.displayImage(LoginInfo.getInstance(getApplicationContext())
				.getUserInfo().getAvatar(), ivAvatar, options);
		if (!Utils.isLogin(A_MainActivity.this, false)) {
			tvName.setText(R.string.login_or_register);
		} else {
			tvName.setText(LoginInfo.getInstance(getApplicationContext())
					.getUserInfo().getName());
		}
	}

	private void selectItem(int position) {
		mDrawerLayout.closeDrawers();
		switch (position) {
		case 0:
			openPersonHome();
			break;
		case 1:
			openAttention(0);
			break;
		case 2:
			openSquare(1);
			break;
		case 3:
			openActivity(2);
			break;
		case 4:
			openBusiness(3);
			break;
		case 5:
			openTask(4);
			break;
		case 6:
			openCommunityManager();
			break;
		case 7:
			openFeedback();
			break;
		case 8:
			openSettings();
			break;
		default:
			break;
		}
	}

	/**
	 * 小区简介
	 */
	private void openCommunityManager() {
		openActivity(A_CommunityManager.class);
	}

	/**
	 * 设置
	 */
	private void openSettings() {
		openActivity(A_MySettingsActivity.class);
	}

	/**
	 * 建议反馈
	 */
	private void openFeedback() {
		openActivity(A_FeedbackActivity.class);
	}

	/**
	 * 任务
	 * 
	 * @param i
	 */
	private void openTask(int i) {
		setTitle(R.string.task);
		drawerListAdapter.setSelected(i);
		infotype = Constant.TYPE_PERSONAL;
		if (taskFragment == null) {
			taskFragment = InformationFragment
					.newInstance(Constant.TYPE_PERSONAL);
		}
		showFragment(taskFragment);
	}

	/**
	 * 商家服务
	 * 
	 * @param i
	 */
	private void openBusiness(int i) {
		setTitle(R.string.business_service);
		drawerListAdapter.setSelected(i);
		infotype = Constant.TYPE_BUSINESS;
		if (businessFragment == null) {
			businessFragment = InformationFragment
					.newInstance(Constant.TYPE_BUSINESS);
		}
		showFragment(businessFragment);
	}

	/**
	 * 活动
	 * 
	 * @param i
	 */
	private void openActivity(int i) {
		setTitle(R.string.activity);
		drawerListAdapter.setSelected(i);
		infotype = Constant.TYPE_ACTIVITY;
		if (activityFragment == null) {
			activityFragment = InformationFragment
					.newInstance(Constant.TYPE_ACTIVITY);
		}
		showFragment(activityFragment);
	}

	/**
	 * 关注
	 * 
	 * @param i
	 */
	private void openAttention(int i) {
		setTitle(R.string.attention);
		drawerListAdapter.setSelected(i);
		if (attentionFragment == null) {
			attentionFragment = AttentionFragment.newInstance();
		}
		showFragment(attentionFragment);
	}

	/**
	 * 打开广场
	 * 
	 * @param i
	 */
	private void openSquare(int i) {
		setTitle(R.string.square);
		drawerListAdapter.setSelected(i);
		if (squareFragment == null) {
			squareFragment = InformationFragment
					.newInstance(Constant.TYPE_SQUARE);
		}
		showFragment(squareFragment);
	}

	private void showFragment(Fragment fragment) {
		getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
	}

	/**
	 * 打开个人首页
	 */
	private void openPersonHome() {
		if (!Utils.isLogin(A_MainActivity.this, false)) {
			openActivity(A_LoginActivity.class);
			return;
		}
		Intent intent = new Intent(A_MainActivity.this,
				A_PersonHomeActivity.class);
		intent.putExtra("targetid",
				LoginInfo.getInstance(getApplicationContext()).getUserId());
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_search:
			openActivity(A_SearchActivity.class);
			break;
		case R.id.action_release:
			releaseInformation();
			break;
		case R.id.action_cancel:
			cancelLogin();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void releaseInformation() {
		if (LoginInfo.getInstance(getApplicationContext()).getUserId() == 0) {
			new AlertDialog.Builder(A_MainActivity.this)
					.setTitle(R.string.you_must_login_before)
					.setPositiveButton(R.string.login_or_register,

					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							openActivity(A_LoginActivity.class);
						}

					}).setNegativeButton(android.R.string.cancel, null).show();

			return;
		}
		Intent intent = new Intent(A_MainActivity.this,
				A_InformationReleaseActivity.class);
		intent.putExtra("infotype", infotype);
		startActivity(intent);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
}
