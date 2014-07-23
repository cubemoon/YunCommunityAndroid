package com.yuncommunity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.yuncommunity.app.JsonApi;
import com.yuncommunity.base.BaseActivity;
import com.yuncommunity.item.CommunityItem;
import com.yuncommunity.util.JSONUtil;
import com.yuncommunity.util.NetUtil;
import com.yuncommunity.util.NetUtil.RequestStringListener;
import com.yuncommunity.util.StringUtil;

/**
 * 编辑小区介绍
 * 
 * @author oldfeel
 * 
 */
public class CommunityEdit extends BaseActivity {
	private ImageView ivImage;
	private EditText etDesc;
	private CommunityItem item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.community_edit);
		item = (CommunityItem) getIntent().getSerializableExtra("item");
		ivImage = getImageView(R.id.community_edit_image);
		etDesc = getEditText(R.id.community_edit_desc);
		imageLoader.displayImage(item.getImage(), ivImage, options);
		if (!StringUtil.isEmpty(item.getDescription())) {
			etDesc.setText(item.getDescription());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.complete, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_complete:
			complete();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 完成编辑
	 */
	private void complete() {
		NetUtil netUtil = new NetUtil(CommunityEdit.this, JsonApi.COMMUNITY_EDIT);
		netUtil.setParams("communityid", item.getCommunityid());
		netUtil.setParams("description", getString(etDesc));
		netUtil.setParams("image", StringUtil.getFileName(item.getImage()));
		netUtil.setParams("lat", item.getLat());
		netUtil.setParams("lon", item.getLon());
		netUtil.postRequest("上传中...", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JSONUtil.isSuccess(result)) {
					showToast("编辑完成");
					finish();
				} else {
					showToast(JSONUtil.getMessage(result));
				}
			}
		});
	}
}
