package com.yuncommunity.theme.ios;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oldfeel.utils.ETUtil;
import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.LogUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.R;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.theme.ios.base.I_BaseActivity;

/**
 * 登陆
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月8日
 */
public class I_LoginActivity extends I_BaseActivity {
	private EditText etAccount, etPassword;
	private Button btnSubmit;
	private TextView tvForgetPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_login);
		setTitle("登录");
		initView();
		initListener();
		showRight();
		btnRight.setText("注册");
		btnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openActivity(I_RegisterActivity.class);
			}
		});
	}

	private void initListener() {
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startLogin();
			}
		});
		tvForgetPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openActivity(I_ForgetPassword.class);
			}
		});
	}

	protected void startLogin() {
		LogUtil.showLog("start login");
		if (ETUtil.isHaveNull(etAccount, etPassword)) {
			return;
		}
		NetUtil netUtil = new NetUtil(I_LoginActivity.this, JsonApi.LOGIN);
		netUtil.setParams("account", getString(etAccount));
		netUtil.setParams("password", getString(etPassword));
		netUtil.postRequest("正在验证登录信息...", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					LoginInfo.getInstance(I_LoginActivity.this).saveInfo(
							JsonUtil.getData(result));
					openActivity(I_MainActivity.class);
				} else {
					showToast(JsonUtil.getData(result));
				}
			}
		});
	}

	private void initView() {
		etAccount = getEditText(R.id.i_login_account);
		etPassword = getEditText(R.id.i_login_password);
		btnSubmit = getButton(R.id.i_login_submit);
		tvForgetPassword = getTextView(R.id.i_login_forgetpassword);
	}
}
