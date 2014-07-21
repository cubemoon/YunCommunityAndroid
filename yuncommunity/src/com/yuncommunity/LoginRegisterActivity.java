package com.yuncommunity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.yuncommunity.base.BaseActivity;
import com.yuncommunity.base.BaseTabsAdapter;
import com.yuncommunity.fragment.LoginFragment;
import com.yuncommunity.fragment.RegisterFragment;

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
		tabsAdapter.addTab("登录", new LoginFragment());
		tabsAdapter.addTab("注册", new RegisterFragment());
	}
}
