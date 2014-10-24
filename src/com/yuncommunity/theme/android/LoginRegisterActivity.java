package com.yuncommunity.theme.android;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.oldfeel.base.BaseActivity;
import com.oldfeel.base.BaseTabsAdapter;
import com.yuncommunity.R;
import com.yuncommunity.theme.android.fragment.LoginFragment;
import com.yuncommunity.theme.android.fragment.RegisterFragment;

/**
 * 注册或者登录
 * 
 * @author oldfeel
 * 
 */
public class LoginRegisterActivity extends BaseActivity {
	private ViewPager pager;
	private BaseTabsAdapter tabsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager);
		pager = (ViewPager) findViewById(R.id.pager);
		tabsAdapter = new BaseTabsAdapter(LoginRegisterActivity.this, pager);
		tabsAdapter.addTab(String.valueOf(getText(R.string.login)),
				new LoginFragment());
		tabsAdapter.addTab(String.valueOf(getText(R.string.register)),
				new RegisterFragment());
	}
}
