package com.yuncommunity.theme.ios;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.oldfeel.interfaces.MyLocationListener;
import com.oldfeel.utils.NetUtil;
import com.yuncommunity.R;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.list.NearCommunityList;
import com.yuncommunity.theme.ios.base.I_BaseActivity;
import com.yuncommunity.utils.Utils;

/**
 * 切换小区
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月10日
 */
public class I_ChangeCommunity extends I_BaseActivity {
	private EditText etKey;
	private ImageButton ibSearch;
	private Button btnSubmit;
	private double lat, lon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_change_community);
		setTitle("选择小区");
		hideRight();
		initView();
		initListener();
		startLocation();
	}

	private void startLocation() {
		Utils.startLocation(this, new MyLocationListener() {

			@Override
			public void location(double lat, double lon) {
				I_ChangeCommunity.this.lat = lat;
				I_ChangeCommunity.this.lon = lon;
				getNearCommunityList("");
			}
		});

	}

	protected void getNearCommunityList(String key) {
		NetUtil netUtil = new NetUtil(I_ChangeCommunity.this,
				JsonApi.NEAR_COMMUNITY_LIST);
		netUtil.setParams("lat", lat);
		netUtil.setParams("lon", lon);
		netUtil.setParams("key", key);
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.i_change_community_list,
						NearCommunityList.newInstance(netUtil)).commit();
	}

	private void initListener() {
		etKey.setOnEditorActionListener(editorActionListener);
		ibSearch.setOnClickListener(clickListener);
		btnSubmit.setOnClickListener(clickListener);
	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.i_change_community_search:
				search();
				break;
			case R.id.i_change_community_submit:
				submit();
				break;
			default:
				break;
			}
		}
	};

	private OnEditorActionListener editorActionListener = new OnEditorActionListener() {

		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_SEARCH) {
				getNearCommunityList(getString(etKey));
			}
			return false;
		}
	};

	private void initView() {
		etKey = getEditText(R.id.i_change_community_key);
		ibSearch = getImageButton(R.id.i_change_community_search);
		btnSubmit = getButton(R.id.i_change_community_submit);
	}

	/**
	 * 搜索小区
	 */
	protected void search() {
		getNearCommunityList(getString(etKey));
	}

	/**
	 * 确认切换小区
	 */
	protected void submit() {
	}
}
