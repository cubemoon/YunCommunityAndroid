package com.yuncommunity.theme.android.base;

import com.oldfeel.base.BaseActivity;
import com.yuncommunity.conf.LoginInfo;

/**
 * android 风格的activity基类
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月23日
 */
public abstract class A_BaseActivity extends BaseActivity {

	public long getUserId() {
		return LoginInfo.getInstance(getApplicationContext()).getUserId();
	}
}
