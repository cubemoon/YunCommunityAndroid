package android.oldfeel.yanzhuang;

import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.base.BaseFragment;
import android.oldfeel.yanzhuang.fragment.ReleaseActivityFragment;
import android.oldfeel.yanzhuang.fragment.ReleaseBusinessFragment;
import android.oldfeel.yanzhuang.fragment.ReleasePersonalFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

/**
 * 发布
 * 
 * @author oldfeel
 * 
 */
public class ReleaseActivity extends BaseActivity {
	public static final String[] navList = new String[] { "活动", "商家服务", "个人服务" };
	private int currentPosition;
	private BaseFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_frame);
		setTitle("发布");
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(
				new ArrayAdapter<String>(getApplicationContext(),
						R.layout.single_text_dark, R.id.text, navList),
				new OnNavigationListener() {

					@Override
					public boolean onNavigationItemSelected(int position,
							long arg1) {
						selectItem(position);
						return false;
					}
				});
	}

	protected void selectItem(int position) {
		currentPosition = position;
		switch (currentPosition) {
		case 0:
			fragment = new ReleaseActivityFragment();
			break;
		case 1:
			fragment = new ReleaseBusinessFragment();
			break;
		case 2:
			fragment = new ReleasePersonalFragment();
			break;
		default:
			break;
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.release, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_complete:
			submitRelease();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void submitRelease() {
		switch (currentPosition) {
		case 0:
			((ReleaseActivityFragment) fragment).submit();
			break;
		case 1:
			((ReleaseBusinessFragment) fragment).submit();
			break;
		case 2:
			((ReleasePersonalFragment) fragment).submit();
			break;
		default:
			break;
		}
	}
}
