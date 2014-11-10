package com.yuncommunity.theme.ios;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.yuncommunity.R;
import com.yuncommunity.theme.ios.base.IBaseActivity;

/**
 * 切换小区
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月10日
 */
public class IChangeCommunity extends IBaseActivity {
	private EditText etKey;
	private ImageButton ibSearch;
	private Button btnSubmit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_change_community);
		initView();
		initListener();
	}

	private void initListener() {
	}

	private void initView() {
		etKey = getEditText(R.id.i_change_community_key);
		ibSearch = getImageButton(R.id.i_change_community_search);
		btnSubmit = getButton(R.id.i_change_community_submit);
	}
}
