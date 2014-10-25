package com.yuncommunity.theme.ios;

import android.os.Bundle;

import com.yuncommunity.R;
import com.yuncommunity.theme.ios.base.IBaseActivity;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class IReleaseActivity extends IBaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_release_activity);
	}
}
