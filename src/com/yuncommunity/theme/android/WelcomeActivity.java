package com.yuncommunity.theme.android;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.oldfeel.base.BaseActivity;
import com.oldfeel.conf.BaseConstant;
import com.yuncommunity.conf.Constant;

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
		BaseConstant.getInstance().setRootUrl(Constant.ROOT_URL);
		BaseConstant.getInstance().setPageSize(Constant.PAGE_SIZE);
		ImageView imageView = new ImageView(this);
		imageView.setBackgroundColor(Color.GREEN);
		setContentView(imageView);
		openActivity(MainActivity.class);
		finish();
	}
}