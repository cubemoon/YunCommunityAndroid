package com.yuncommunity.theme.ios;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.yuncommunity.R;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.theme.ios.base.I_BaseActivity;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class I_PersonInfo extends I_BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_person_info);
		setTitle(LoginInfo.getInstance(this).getUserInfo().getName());
		btnRight.setText("注销");
		btnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LoginInfo.getInstance(I_PersonInfo.this).cancelLogin();
				Intent intent = new Intent(I_PersonInfo.this,
						I_LoginActivity.class);
				intent.putExtra("cancelLogin", true);
				startActivity(intent);
				finish();
			}
		});
	}
}
