package android.oldfeel.yanzhuang;

import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.base.BaseTabsAdapter;
import android.oldfeel.yanzhuang.fragment.LoginFragment;
import android.oldfeel.yanzhuang.fragment.RegisterFragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

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
