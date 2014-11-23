package com.yuncommunity.theme.android;

import android.os.Bundle;

import com.yuncommunity.R;
import com.yuncommunity.theme.android.base.A_BaseActivity;
import com.yuncommunity.theme.android.fragment.MySettingsFragment;

/**
 * 设置
 * 
 * @author oldfeel
 * 
 */
public class A_MySettingsActivity extends A_BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_frame);
		setTitle(getText(R.string.setting));
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, MySettingsFragment.newInstance())
				.commit();
	}
}
