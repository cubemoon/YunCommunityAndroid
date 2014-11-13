package com.yuncommunity.theme.android;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.oldfeel.base.BaseActivity;
import com.yuncommunity.R;
import com.yuncommunity.conf.Constant;
import com.yuncommunity.theme.android.fragment.InformationRelease;

/**
 * 发布
 * 
 * @author oldfeel
 * 
 */
public class InformationReleaseActivity extends BaseActivity {
	public String[] navList;
	private int currentPosition;
	private InformationRelease fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_frame);
		navList = new String[] { String.valueOf(getText(R.string.activity)),
				String.valueOf(getText(R.string.business_service)),
				String.valueOf(getText(R.string.personal_service)) };
		setTitle(getText(R.string.publish));
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
		getSupportActionBar().setSelectedNavigationItem(
				getIntent().getIntExtra("infotype", 1) - 1);
	}

	protected void selectItem(int position) {
		currentPosition = position;
		switch (currentPosition) {
		case 0:
			fragment = InformationRelease.newInstance(Constant.TYPE_ACTIVITY);
			break;
		case 1:
			fragment = InformationRelease.newInstance(Constant.TYPE_BUSINESS);
			break;
		case 2:
			fragment = InformationRelease.newInstance(Constant.TYPE_PERSONAL);
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
			fragment.submit();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
