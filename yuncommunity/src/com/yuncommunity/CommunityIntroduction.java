package com.yuncommunity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yuncommunity.app.Constant;
import com.yuncommunity.app.JsonApi;
import com.yuncommunity.base.BaseActivity;
import com.yuncommunity.item.CommunityItem;
import com.yuncommunity.util.JSONUtil;
import com.yuncommunity.util.NetUtil;
import com.yuncommunity.util.NetUtil.RequestStringListener;
import com.yuncommunity.util.Utils;

/**
 * 小区简介
 * 
 * @author oldfeel
 * 
 */
public class CommunityIntroduction extends BaseActivity {
	private static final int REQUEST_EDIT = 0;
	private ImageView ivImage;
	private TextView tvDesc;
	private CommunityItem item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.community_introduction);
		ivImage = getImageView(R.id.community_image);
		tvDesc = getTextView(R.id.community_desc);
		getData();
	}

	private void getData() {
		NetUtil netUtil = new NetUtil(CommunityIntroduction.this,
				JsonApi.COMMUNITY_INTRODUCTION);
		netUtil.setParams("communityid", Constant.COMMUNITY_ID);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JSONUtil.isSuccess(result)) {
					parseCommunity(result);
				} else {
					showToast(JSONUtil.getMessage(result));
				}
			}
		});
	}

	/**
	 * 解析小区数据
	 * 
	 * @param result
	 */
	protected void parseCommunity(String result) {
		item = new Gson().fromJson(JSONUtil.getData(result).toString(),
				CommunityItem.class);
		imageLoader.displayImage(item.getImage(), ivImage, options);
		tvDesc.setText(item.getDescription());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.introduction, menu);
		if (Constant.COMMUNITY_ADMIN_ID == getUserid()) {
			menu.findItem(R.id.action_admin).setVisible(false);
		} else {
			menu.findItem(R.id.action_edit).setVisible(false);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_admin:
			lookAdmin();
			break;
		case R.id.action_map:
			lookMap();
			break;
		case R.id.action_edit:
			editCommunity();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 编辑小区信息
	 */
	private void editCommunity() {
		if (item == null) {
			showToast(String
					.valueOf(getText(R.string.community_info_loading_failed)));
			return;
		}
		Intent intent = new Intent(CommunityIntroduction.this,
				CommunityEdit.class);
		intent.putExtra("item", item);
		startActivityForResult(intent, REQUEST_EDIT);
	}

	/**
	 * 查看小区在地图上的位置
	 */
	private void lookMap() {
		if (item == null) {
			showToast(String
					.valueOf(getText(R.string.community_info_loading_failed)));
			return;
		}
		Intent intent = new Intent();
		if (Utils.isChinese()) {
			intent.setClass(CommunityIntroduction.this, CommunityBaiduMap.class);
		} else {
			intent.setClass(CommunityIntroduction.this,
					CommunityGoogleMap.class);
		}
		intent.putExtra("item", item);
		startActivity(intent);
	}

	/**
	 * 查看管理员
	 */
	private void lookAdmin() {
		if (item == null) {
			showToast(String
					.valueOf(getText(R.string.community_info_loading_failed)));
			return;
		}
		Intent intent = new Intent(CommunityIntroduction.this,
				PersonHomeActivity.class);
		intent.putExtra("targetid", this.item.getUserid());
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case REQUEST_EDIT:
			getData();
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
