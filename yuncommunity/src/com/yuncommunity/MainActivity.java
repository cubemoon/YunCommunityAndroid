package com.yuncommunity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
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

import com.yuncommunity.adapter.DrawerListAdapter;
import com.yuncommunity.app.Constant;
import com.yuncommunity.app.JsonApi;
import com.yuncommunity.app.LoginInfo;
import com.yuncommunity.base.BaseActivity;
import com.yuncommunity.fragment.AttentionFragment;
import com.yuncommunity.fragment.InformationFragment;
import com.yuncommunity.util.JSONUtil;
import com.yuncommunity.util.LogUtil;
import com.yuncommunity.util.NetUtil;
import com.yuncommunity.util.NetUtil.RequestStringListener;

/**
 * 主界面
 * 
 * @author oldfeel
 * 
 */
public class MainActivity extends BaseActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerListAdapter adapter;
	private AttentionFragment attentionFragment;
	private InformationFragment activityFragment;
	private InformationFragment businessFragment;
	private InformationFragment personalFragment;
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
		adapter = new DrawerListAdapter(getApplicationContext());
		mDrawerList.addHeaderView(getHeaderView());
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectItem(position);
			}
		});
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (savedInstanceState == null) {
			selectItem(2);
		}
		updatePersonInfo();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		boolean isLogin = intent.getBooleanExtra("login", false);
		if (isLogin) {
			updateHeaderView();
		}
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
			if (infotype == Constant.TYPE_PERSONAL
					&& personalFragment.isVisible()) {
				personalFragment.updateList();
			}
		}
		super.onNewIntent(intent);
	}

	/**
	 * 自动登录
	 */
	private void updatePersonInfo() {
		if (!LoginInfo.getInstance(getApplicationContext()).isLogin()) {
			return;
		}
		String email = LoginInfo.getInstance(getApplicationContext())
				.getEmail();
		String password = LoginInfo.getInstance(getApplicationContext())
				.getRealPassword();
		NetUtil netUtil = new NetUtil(MainActivity.this, JsonApi.LOGIN);
		netUtil.setParams("email", email);
		netUtil.setParams("password", password);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JSONUtil.isSuccess(result)) {
					LoginInfo.getInstance(getApplicationContext()).saveInfo(
							JSONUtil.getData(result).toString());
					updateHeaderView();
				} else {

					showToast(getText(R.string.auto_login_failed) + ","
							+ JSONUtil.getMessage(result));

					cancelLogin();
				}
			}
		});
	}

	/**
	 * 登出
	 */
	protected void cancelLogin() {
		LoginInfo.getInstance(MainActivity.this).saveInfo("");
		LoginInfo.getInstance(MainActivity.this).close();
		openActivity(LoginRegisterActivity.class);
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
				.getAvatar(), ivAvatar, options);
		if (!LoginInfo.getInstance(getApplicationContext()).isLogin()) {
			tvName.setText(R.string.login_or_register);
		} else {
			tvName.setText(LoginInfo.getInstance(getApplicationContext())
					.getName());
		}
	}

	private void selectItem(int position) {
		mDrawerLayout.closeDrawers();
		switch (position) {
		case 0:
			openPersonHome();
			break;
		case 1:
			openAttention();
			break;
		case 2:
			openActivity();
			break;
		case 3:
			openBusiness();
			break;
		case 4:
			openPersonal();
			break;
		case 5:
			openIntroduction();
			break;
		case 6:
			openFeedback();
			break;
		case 7:
			openSettings();
			break;
		default:
			break;
		}
	}

	/**
	 * 小区简介
	 */
	private void openIntroduction() {
		openActivity(CommunityIntroduction.class);
	}

	/**
	 * 设置
	 */
	private void openSettings() {
		openActivity(MySettingsActivity.class);
	}

	/**
	 * 建议反馈
	 */
	private void openFeedback() {
		openActivity(FeedbackActivity.class);
	}

	/**
	 * 个人服务
	 */
	private void openPersonal() {

		setTitle(R.string.person_service);

		adapter.setSelected(3);
		infotype = Constant.TYPE_PERSONAL;
		if (personalFragment == null) {
			personalFragment = InformationFragment
					.newInstance(Constant.TYPE_PERSONAL);
		}
		showFragment(personalFragment);
	}

	/**
	 * 商家服务
	 */
	private void openBusiness() {

		setTitle(R.string.business_service);

		adapter.setSelected(2);
		infotype = Constant.TYPE_BUSINESS;
		if (businessFragment == null) {
			businessFragment = InformationFragment
					.newInstance(Constant.TYPE_BUSINESS);
		}
		showFragment(businessFragment);
	}

	/**
	 * 活动
	 */
	private void openActivity() {

		setTitle(R.string.activity);

		adapter.setSelected(1);
		infotype = Constant.TYPE_ACTIVITY;
		if (activityFragment == null) {
			activityFragment = InformationFragment
					.newInstance(Constant.TYPE_ACTIVITY);
		}
		showFragment(activityFragment);
	}

	/**
	 * 关注
	 */
	private void openAttention() {

		setTitle(R.string.attention);

		adapter.setSelected(0);
		if (attentionFragment == null) {
			attentionFragment = AttentionFragment.newInstance();
		}
		showFragment(attentionFragment);
	}

	private void showFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
	}

	/**
	 * 打开个人首页
	 */
	private void openPersonHome() {
		if (!LoginInfo.getInstance(getApplicationContext()).isLogin()) {
			openActivity(LoginRegisterActivity.class);
			return;
		}
		Intent intent = new Intent(MainActivity.this, PersonHomeActivity.class);
		intent.putExtra("targetid", getUserid());
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
			openActivity(SearchActivity.class);
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
		if (getUserid() == 0) {
			new AlertDialog.Builder(MainActivity.this)

			.setTitle(R.string.you_must_login_before)
					.setPositiveButton(R.string.login_or_register,

					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							openActivity(LoginRegisterActivity.class);
						}

					}).setNegativeButton(android.R.string.cancel, null).show();

			return;
		}
		Intent intent = new Intent(MainActivity.this,
				InformationReleaseActivity.class);
		intent.putExtra("infotype", infotype);
		startActivity(intent);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
}
