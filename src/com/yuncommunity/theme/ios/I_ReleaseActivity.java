package com.yuncommunity.theme.ios;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.google.gson.Gson;
import com.oldfeel.utils.ETUtil;
import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.R;
import com.yuncommunity.base.UploadImagesFragment;
import com.yuncommunity.conf.Constant;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.interfaces.UploadImagesListener;
import com.yuncommunity.item.InformationItem;
import com.yuncommunity.theme.ios.base.I_BaseActivity;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class I_ReleaseActivity extends I_BaseActivity {
	private EditText etName, etPhone, etTags, etDesc;
	private UploadImagesFragment uploadImagesFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_release_activity);
		initTop();
		initView();
		initUploadImage();
	}

	private void initUploadImage() {
		uploadImagesFragment = new UploadImagesFragment();
		uploadImagesFragment
				.setOnUploadImageListener(new UploadImagesListener() {

					@Override
					public void onComplete() {
						submitRelease();
					}
				});
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.i_release_activity_upload_image,
						uploadImagesFragment).commit();
	}

	private void initView() {
		etName = getEditText(R.id.i_release_activity_name);
		etPhone = getEditText(R.id.i_release_activity_phone);
		etTags = getEditText(R.id.i_release_activity_tags);
		etDesc = getEditText(R.id.i_release_activity_desc);
	}

	private void initTop() {
		setTitle("发布商品");
		btnRight.setText("发布");
		btnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				verRelease();
			}
		});
	}

	/**
	 * 验证发布信息
	 */
	protected void verRelease() {
		if (ETUtil.isHaveNull(etName, etPhone)) {
			return;
		}
		if (!uploadImagesFragment.isHaveImage()) {
			submitRelease();
		}
	}

	private void submitRelease() {
		NetUtil netUtil = initNetUtil(JsonApi.INFORMATION_RELEASE);
		netUtil.setParams("infotype", Constant.TYPE_ACTIVITY);
		netUtil.setParams("description", getString(etDesc));
		netUtil.setParams("title", getString(etName));
		netUtil.setParams("phone", getString(etPhone));
		netUtil.setParams("image", uploadImagesFragment.getUploadImages());
		netUtil.setParams("tags", getString(etTags));
		netUtil.postRequest("正在发布...", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					showToast("发布成功");
					InformationItem item = new Gson().fromJson(
							JsonUtil.getData(result), InformationItem.class);
					item.setUserInfo(LoginInfo.getInstance(
							I_ReleaseActivity.this).getUserInfo());
					Intent intent = new Intent(I_ReleaseActivity.this,
							I_ActivityDetail.class);
					intent.putExtra("item", item);
					startActivity(intent);
					onBackPressed();
				} else {
					showToast(JsonUtil.getData(result));
				}
			}
		});
	}

}
