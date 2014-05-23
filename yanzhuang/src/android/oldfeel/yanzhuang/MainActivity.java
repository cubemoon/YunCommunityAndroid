package android.oldfeel.yanzhuang;

import android.content.Intent;
import android.oldfeel.yanzhuang.adapter.DrawerListAdapter;
import android.oldfeel.yanzhuang.app.Constant;
import android.oldfeel.yanzhuang.app.JsonApi;
import android.oldfeel.yanzhuang.app.PersonInfo;
import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.fragment.AttentionFragment;
import android.oldfeel.yanzhuang.fragment.InformationFragment;
import android.oldfeel.yanzhuang.util.JSONUtil;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.oldfeel.yanzhuang.util.NetUtil.OnNetFailListener;
import android.oldfeel.yanzhuang.util.NetUtil.RequestStringListener;
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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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

	/**
	 * 自动登录
	 */
	private void updatePersonInfo() {
		String email = PersonInfo.getInstance(getApplicationContext())
				.getEmail();
		String password = PersonInfo.getInstance(getApplicationContext())
				.getPassword();
		NetUtil netUtil = new NetUtil(MainActivity.this, JsonApi.LOGIN);
		netUtil.setParams("email", email);
		netUtil.setParams("password", password);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JSONUtil.isSuccess(result)) {
					PersonInfo.getInstance(getApplicationContext()).saveInfo(
							JSONUtil.getData(result).toString());
				} else {
					showToast("自动登录失败," + JSONUtil.getMessage(result));
					cancelLogin();
				}
			}
		});
		netUtil.setOnNetFailListener(new OnNetFailListener() {

			@Override
			public void onTimeOut() {
				showToast("自动登录失败,网络超时");
				cancelLogin();
			}

			@Override
			public void onError() {
				showToast("自动登录失败,网络错误");
				cancelLogin();
			}

			@Override
			public void cancel() {
				showToast("自动登录失败,取消登录");
				cancelLogin();
			}
		});
	}

	/**
	 * 登出
	 */
	protected void cancelLogin() {
		PersonInfo.getInstance(MainActivity.this).saveInfo("");
		openActivity(LoginRegisterActivity.class);
		finish();
	}

	private View getHeaderView() {
		View view = getLayoutInflater()
				.inflate(R.layout.menu_header_view, null);
		ImageView ivAvatar = (ImageView) view.findViewById(R.id.avatar);
		TextView tvName = (TextView) view.findViewById(R.id.text);
		int id = R.drawable.ic_launcher;
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(id).showImageOnFail(id)
				.cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(PersonInfo
				.getInstance(getApplicationContext()).getAvatar(), ivAvatar,
				options);
		tvName.setText(PersonInfo.getInstance(getApplicationContext())
				.getName());
		return view;
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
			openFeedback();
			break;
		case 6:
			openSettings();
			break;
		default:
			break;
		}
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
		setTitle("个人服务");
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
		setTitle("商家服务");
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
		setTitle("活动");
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
		setTitle("关注");
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
		Intent intent = new Intent(MainActivity.this, ReleaseActivity.class);
		intent.putExtra("infotype", infotype);
		startActivity(intent);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
}
