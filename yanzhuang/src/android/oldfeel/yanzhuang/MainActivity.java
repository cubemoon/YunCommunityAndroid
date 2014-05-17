package android.oldfeel.yanzhuang;

import android.content.Intent;
import android.oldfeel.yanzhuang.app.JsonApi;
import android.oldfeel.yanzhuang.app.PersonInfo;
import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.util.JSONUtil;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.oldfeel.yanzhuang.util.NetUtil.RequestStringListener;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
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
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		String[] menus = getResources().getStringArray(R.array.drawer_menus);
		adapter = new ArrayAdapter<>(getApplicationContext(),
				R.layout.single_text_light, R.id.text, menus);
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
			selectItem(1);
		}
		if (PersonInfo.getInstance(getApplicationContext()).isAutoLogin()) {
			autoLogin();
		}
	}

	/**
	 * 自动登录
	 */
	private void autoLogin() {
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
							result);
				} else {
					showToast("自动登录失败," + JSONUtil.getMessage(result));
					cancelLogin();
				}
			}
		});
	}

	/**
	 * 登出
	 */
	protected void cancelLogin() {
		PersonInfo.getInstance(MainActivity.this).setIsAutoLogin(false);
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
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						PersonHomeActivity.class);
				intent.putExtra("id",
						PersonInfo.getInstance(getApplicationContext()).getId());
				startActivity(intent);
			}
		});
		return view;
	}

	private void selectItem(int position) {
		mDrawerList.setSelection(position);
		mDrawerLayout.closeDrawers();
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
			openActivity(ReleaseActivity.class);
			break;
		case R.id.action_cancel:
			cancelLogin();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
}
