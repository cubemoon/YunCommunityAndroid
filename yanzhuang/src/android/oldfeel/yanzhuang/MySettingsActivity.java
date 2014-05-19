package android.oldfeel.yanzhuang;

import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.fragment.MySettingsFragment;
import android.os.Bundle;

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
		setTitle("设置");
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, MySettingsFragment.newInstance())
				.commit();
	}
}
