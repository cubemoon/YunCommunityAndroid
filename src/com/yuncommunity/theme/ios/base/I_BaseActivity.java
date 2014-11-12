package com.yuncommunity.theme.ios.base;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ZoomControls;

import com.baidu.mobstat.StatService;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oldfeel.conf.MyApplication;
import com.oldfeel.utils.DialogUtil;
import com.oldfeel.utils.ETUtil;
import com.oldfeel.utils.NetUtil;
import com.yuncommunity.R;
import com.yuncommunity.conf.LoginInfo;

/**
 * ios风格的activity基类
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月25日
 */
public class I_BaseActivity extends SwipeBackActivity {
	private MyApplication myApplication;
	public DisplayImageOptions options;
	public ImageLoader imageLoader = ImageLoader.getInstance();
	public Button btnLeft, btnRight;
	public TextView tvTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.i_base_view);
		getSupportActionBar().hide();
		myApplication = (MyApplication) getApplication();
		myApplication.addActivity(this);
		int id = R.drawable.ic_launcher;
		if (id > 0)
			options = new DisplayImageOptions.Builder()
					.showImageForEmptyUri(id).showImageOnFail(id)
					.cacheInMemory(true).cacheOnDisc(true).build();

		btnLeft = getButton(R.id.i_base_view_left);
		btnRight = getButton(R.id.i_base_view_right);
		tvTitle = getTextView(R.id.i_base_view_title);
		btnLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	protected void setContentLayout(View view) {
		((FrameLayout) findViewById(R.id.i_base_view_content)).addView(view);
	}

	protected void setContentLayout(int layoutResID) {
		View view = LayoutInflater.from(getApplicationContext()).inflate(
				layoutResID, new LinearLayout(this), false);
		setContentLayout(view);
	}

	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
		tvTitle.setText(title);
	}

	@Override
	protected void onDestroy() {
		myApplication.removeActivity(this);
		super.onDestroy();
	}

	/**
	 * 显示toast提示
	 * 
	 * @param text
	 */
	public void showToast(String text) {
		DialogUtil.getInstance().showToast(this, text);
	}

	/**
	 * 显示一个简易的dialog
	 * 
	 * @param text
	 */
	public void showSimpleDialog(String text) {
		DialogUtil.getInstance().showSimpleDialog(this, text);
	}

	/**
	 * 打开指定activity
	 * 
	 * @param targetClass
	 */
	public void openActivity(Class<?> targetClass) {
		Intent intent = new Intent(this, targetClass);
		startActivity(intent);
	}

	public EditText getEditText(int id) {
		return (EditText) findViewById(id);
	}

	public TextView getTextView(int id) {
		return (TextView) findViewById(id);
	}

	public Button getButton(int id) {
		return (Button) findViewById(id);
	}

	public ImageButton getImageButton(int id) {
		return (ImageButton) findViewById(id);
	}

	public ImageView getImageView(int id) {
		return (ImageView) findViewById(id);
	}

	public LinearLayout getLinearLayout(int id) {
		return (LinearLayout) findViewById(id);
	}

	public Switch getSwitch(int id) {
		return (Switch) findViewById(id);
	}

	public ZoomControls getZoomControls(int id) {
		return (ZoomControls) findViewById(id);
	}

	public RadioGroup getRadioGroup(int id) {
		return (RadioGroup) findViewById(id);
	}

	public RadioButton getRadioButton(int id) {
		return (RadioButton) findViewById(id);
	}

	public Spinner getSpinner(int id) {
		return (Spinner) findViewById(id);
	}

	public ListView getListView(int id) {
		return (ListView) findViewById(id);
	}

	public GridView getGridView(int id) {
		return (GridView) findViewById(id);
	}

	public VideoView getVideoView(int id) {
		return (VideoView) findViewById(id);
	}

	public CheckBox getCheckBox(int id) {
		return (CheckBox) findViewById(id);
	}

	public String getString(EditText et) {
		return ETUtil.getString(et);
	}

	public String getString(TextView tv) {
		return tv.getText().toString().trim();
	}

	public String getString(Spinner sp) {
		return sp.getSelectedItem().toString();
	}

	public int getInt(Spinner sp) {
		return Integer.valueOf(sp.getSelectedItem().toString());
	}

	public int getSelection(int resId, String string) {
		String[] strings = getResources().getStringArray(resId);
		for (int i = 0; i < strings.length; i++) {
			if (string.equals(strings[i])) {
				return i;
			}
		}
		return 0;
	}

	public boolean isEmpty(EditText... ets) {
		return ETUtil.isEmpty(ets);
	}

	public void cancelPd() {
		DialogUtil.getInstance().cancelPd();
	}

	@Override
	protected void onResume() {
		StatService.onResume(this);
		super.onResume();
	}

	@Override
	protected void onPause() {
		StatService.onPause(this);
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	public long getUserId() {
		return LoginInfo.getInstance(getApplicationContext()).getUserId();
	}

	public void hideRight() {
		btnRight.setVisibility(View.GONE);
	}

	public void hideLeft() {
		btnLeft.setVisibility(View.GONE);
	}

	public void showRight() {
		btnRight.setVisibility(View.VISIBLE);
	}

	public void showLeft() {
		btnLeft.setVisibility(View.VISIBLE);
	}

	public NetUtil initNetUtil(String api) {
		NetUtil netUtil = new NetUtil(this, api);
		netUtil.setParams("userid", getUserId());
		netUtil.setParams("communityid", getCommunityId());
		return netUtil;
	}

	private long getCommunityId() {
		return LoginInfo.getInstance(getApplicationContext())
				.getCommunityInfo().getCommunityid();
	}
}
