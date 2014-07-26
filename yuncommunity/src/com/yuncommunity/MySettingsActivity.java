package com.yuncommunity;

import android.os.Bundle;

import com.yuncommunity.base.BaseActivity;
import com.yuncommunity.fragment.MySettingsFragment;

/**
 * 设置
 * 
 * @author oldfeel
 * 
 */
public class MySettingsActivity extends BaseActivity {
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
