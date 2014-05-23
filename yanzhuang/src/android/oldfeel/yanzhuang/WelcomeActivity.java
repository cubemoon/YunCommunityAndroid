package android.oldfeel.yanzhuang;

import android.graphics.Color;
import android.oldfeel.yanzhuang.app.PersonInfo;
import android.oldfeel.yanzhuang.base.BaseActivity;
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
		if (PersonInfo.getInstance(getApplicationContext()).isLogin()) {
			openActivity(MainActivity.class);
		} else {
			openActivity(LoginRegisterActivity.class);
		}
		finish();
	}
}
