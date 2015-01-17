package com.yuncommunity.theme.ios;

import android.os.Bundle;

import com.yuncommunity.R;
import com.yuncommunity.theme.android.fragment.MySettingsFragment;
import com.yuncommunity.theme.ios.base.I_BaseActivity;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class I_Setting extends I_BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.single_frame);
		setTitle(getText(R.string.setting));
		getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, MySettingsFragment.newInstance())
				.commit();
	}
}
