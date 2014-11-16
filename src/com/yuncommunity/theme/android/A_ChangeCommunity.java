package com.yuncommunity.theme.android;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.oldfeel.base.BaseActivity;
import com.oldfeel.interfaces.MyLocationListener;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.Utils;
import com.yuncommunity.R;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.list.NearCommunityList;

/**
 * 改变小区
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月22日
 */
public class A_ChangeCommunity extends BaseActivity {
	private EditText etKey;
	private ImageButton ibSearch;
	private double lat, lon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_community);
		etKey = getEditText(R.id.change_community_key);
		ibSearch = getImageButton(R.id.change_community_search);
		etKey.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					getNearCommunityList(getString(etKey));
				}
				return false;
			}
		});
		ibSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getNearCommunityList(getString(etKey));
			}
		});
		startLocation();
	}

	/**
	 * 开始定位
	 */
	private void startLocation() {
		Utils.startLocation(this, new MyLocationListener() {

			@Override
			public void location(double lat, double lon) {
				A_ChangeCommunity.this.lat = lat;
				A_ChangeCommunity.this.lon = lon;
				getNearCommunityList("");
			}
		});
	}

	/**
	 * 获取附近小区列表
	 */
	protected void getNearCommunityList(String key) {
		NetUtil netUtil = new NetUtil(A_ChangeCommunity.this,
				JsonApi.NEAR_COMMUNITY_LIST);
		netUtil.setParams("lat", lat);
		netUtil.setParams("lon", lon);
		netUtil.setParams("key", key);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.change_community_list,
						NearCommunityList.newInstance(netUtil)).commit();
	}
}
