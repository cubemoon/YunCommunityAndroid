package com.yuncommunity.theme.android;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.oldfeel.base.BaseTabsAdapter;
import com.yuncommunity.R;
import com.yuncommunity.theme.android.base.A_BaseActivity;
import com.yuncommunity.theme.android.fragment.LoginFragment;
import com.yuncommunity.theme.android.fragment.RegisterFragment;

/**
 * 注册或者登录
 * 
 * @author oldfeel
 * 
 */
public class A_LoginActivity extends A_BaseActivity {
	private ViewPager pager;
	private BaseTabsAdapter tabsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_view_pager);
		pager = (ViewPager) findViewById(R.id.pager);
		tabsAdapter = new BaseTabsAdapter(A_LoginActivity.this, pager);
		tabsAdapter.addTab(String.valueOf(getText(R.string.login)),
				new LoginFragment());
		tabsAdapter.addTab(String.valueOf(getText(R.string.register)),
				new RegisterFragment());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_forgot_password:
			forgetPassword();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void forgetPassword() {
		openActivity(A_ForgetPassword.class);
	}
}
