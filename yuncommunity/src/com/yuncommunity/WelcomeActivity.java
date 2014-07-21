package com.yuncommunity;

import com.yuncommunity.base.BaseActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * 欢迎界面
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年5月15日
 */
public class WelcomeActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ImageView imageView = new ImageView(this);
		imageView.setBackgroundColor(Color.GREEN);
		setContentView(imageView);
		openActivity(MainActivity.class);
		finish();
	}
}
