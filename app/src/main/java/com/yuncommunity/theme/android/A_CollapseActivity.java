package com.yuncommunity.theme.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.oldfeel.utils.LogUtil;
import com.yuncommunity.R;
import com.yuncommunity.theme.android.base.A_BaseActivity;

/**
 * 应用崩溃提示
 * 
 * @author oldfeel
 * 
 *         Created on: 2014-1-14
 */
public class A_CollapseActivity extends A_BaseActivity {
	private Button btnRestart, btnExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collapse_activity);
		LogUtil.showLog("collapse oncreate");
		setTitle(getText(R.string.system_crash));
		btnRestart = (Button) findViewById(R.id.collapse_restart);
		btnExit = (Button) findViewById(R.id.collapse_exit);
		btnRestart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						A_MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
			}
		});
		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
