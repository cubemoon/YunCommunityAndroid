package com.yuncommunity.theme.android;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.oldfeel.utils.ETUtil;
import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.R;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.theme.android.base.A_BaseActivity;

/**
 * 修改密码
 * 
 * @author oldfeel
 * 
 */
public class A_ChangePassword extends A_BaseActivity implements OnClickListener {
	private EditText etPassword, etPassword1, etPassword2;
	private Button btnSubmit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_password);
		setTitle(getText(R.string.change_password));
		initView();
		initListener();
	}

	private void initView() {
		etPassword = getEditText(R.id.change_password_0);
		etPassword1 = getEditText(R.id.change_password_1);
		etPassword2 = getEditText(R.id.change_password_2);
		btnSubmit = getButton(R.id.change_password_submit);
	}

	private void initListener() {
		btnSubmit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_password_submit:
			submit();
			break;
		default:
			break;
		}
	}

	private void submit() {
		if (isEmpty(etPassword)) {
			etPassword.setError(getText(R.string.not_optional));
			return;
		}
		if (isEmpty(etPassword1)) {
			etPassword1.setError(getText(R.string.not_optional));
			return;
		}
		if (isEmpty(etPassword2)) {
			etPassword2.setError(getText(R.string.not_optional));
			return;
		}
		if (!ETUtil.isEquals(etPassword1, etPassword2)) {
			etPassword2.setError(getText(R.string.two_password_not_match));
			return;
		}
		if (!LoginInfo.getInstance(getApplicationContext()).getUserInfo()
				.getPassword().equals(getString(etPassword))) {
			etPassword.setError(getText(R.string.wrong_password));
			return;
		}
		LoginInfo.getInstance(getApplicationContext()).saveRealPassword(
				getString(etPassword1));
		NetUtil netUtil = new NetUtil(A_ChangePassword.this,
				JsonApi.CHANGE_PASSWORD);
		netUtil.setParams("userid", LoginInfo
				.getInstance(A_ChangePassword.this).getUserId());
		netUtil.setParams("oldpassword", getString(etPassword));
		netUtil.setParams("newpassword", getString(etPassword1));
		netUtil.postRequest(getString(R.string.changing_password),
				new RequestStringListener() {

					@Override
					public void onComplete(String result) {
						if (JsonUtil.isSuccess(result)) {
							showToast(String
									.valueOf(getText(R.string.changed_successfully)));
							finish();
						} else {
							showToast(getText(R.string.changed_failed) + ","
									+ JsonUtil.getData(result));
						}
					}
				});
	}
}
