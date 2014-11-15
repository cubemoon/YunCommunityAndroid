package com.yuncommunity.theme.ios;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.google.gson.Gson;
import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.R;
import com.yuncommunity.base.UploadImagesFragment;
import com.yuncommunity.conf.Constant;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.interfaces.UploadImagesListener;
import com.yuncommunity.item.SquareItem;
import com.yuncommunity.theme.ios.base.I_BaseActivity;

/**
 * 发布广场信息
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月2日
 */
public class I_ReleaseSquare extends I_BaseActivity {
	private EditText etSpeak;
	private UploadImagesFragment uploadImagesFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_release_square);
		initTop();
		etSpeak = (EditText) findViewById(R.id.i_release_square_speak);
		uploadImagesFragment = new UploadImagesFragment();
		uploadImagesFragment
				.setOnUploadImageListener(new UploadImagesListener() {

					@Override
					public void onComplete() {
						submitSpeak();
					}
				});
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.i_release_square_upload_image,
						uploadImagesFragment).commit();
	}

	private void initTop() {
		showRight();
		btnRight.setText("完成");
		btnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!uploadImagesFragment.isHaveImage()) {
					submitSpeak();
				}
			}
		});
	}

	/**
	 * 提交说说
	 */
	protected void submitSpeak() {
		NetUtil netUtil = initNetUtil(JsonApi.INFORMATION_RELEASE);
		netUtil.setParams("infotype", Constant.TYPE_SQUARE);
		netUtil.setParams("description", getString(etSpeak));
		netUtil.setParams("image", uploadImagesFragment.getUploadImages());
		netUtil.postRequest("正在说说...", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					showToast("说说成功");
					SquareItem item = new Gson().fromJson(
							JsonUtil.getData(result), SquareItem.class);
					item.setUserInfo(LoginInfo
							.getInstance(I_ReleaseSquare.this).getUserInfo());
					Intent intent = new Intent(I_ReleaseSquare.this,
							I_MainActivity.class);
					intent.putExtra("squareitem", item);
					startActivity(intent);
					onBackPressed();
				} else {
					showToast(JsonUtil.getData(result));
				}
			}
		});
	}
}
