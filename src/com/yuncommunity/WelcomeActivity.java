package com.yuncommunity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.oldfeel.conf.BaseConstant;
import com.yuncommunity.conf.Constant;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.theme.ios.base.IBaseActivity;

/**
 * 欢迎界面
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年5月15日
 */
public class WelcomeActivity extends IBaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BaseConstant.getInstance().setRootUrl(Constant.ROOT_URL);
		BaseConstant.getInstance().setPageSize(Constant.PAGE_SIZE);
		ImageView imageView = new ImageView(this);
		imageView.setBackgroundColor(Color.GREEN);
		setContentView(imageView);
		openActivity(LoginInfo.getInstance(getApplicationContext())
				.getThemeClass());
		finish();
	}
}
