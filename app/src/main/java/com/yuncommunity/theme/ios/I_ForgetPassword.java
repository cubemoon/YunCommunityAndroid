package com.yuncommunity.theme.ios;

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
import com.yuncommunity.theme.ios.base.I_BaseActivity;

/**
 * 忘记密码
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月9日
 */
public class I_ForgetPassword extends I_BaseActivity {
	private EditText etPhone, etVcode, etNewPassword;
	private Button btnGetVcode, btnSubmit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_forget_password);
		setTitle("忘记密码");
		hideRight();
		initView();
		initListener();
	}

	private void initListener() {
		btnGetVcode.setOnClickListener(clickListener);
		btnSubmit.setOnClickListener(clickListener);
	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.i_forget_password_getvcode:
				getVcode();
				break;
			case R.id.i_forget_password_submit:
				submit();
				break;
			default:
				break;
			}
		}
	};

	private void initView() {
		etPhone = getEditText(R.id.i_forget_password_phone);
		etVcode = getEditText(R.id.i_forget_password_vcode);
		etNewPassword = getEditText(R.id.i_forget_password_newpassword);
		btnGetVcode = getButton(R.id.i_forget_password_getvcode);
		btnSubmit = getButton(R.id.i_forget_password_submit);
	}

	protected void submit() {
		if (ETUtil.isHaveNull(etNewPassword, etPhone, etVcode)) {
			return;
		}
		NetUtil netUtil = new NetUtil(this, JsonApi.FORGET_PASSWORD);
		netUtil.setParams("phone", getString(etPhone));
		netUtil.setParams("vcode", getString(etVcode));
		netUtil.setParams("password", getString(etNewPassword));
		netUtil.postRequest("正在修改密码", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					showToast("修改成功,请登录");
					onBackPressed();
				}
			}
		});
	}

	protected void getVcode() {
		if (!ETUtil.isMobileNO(etPhone)) {
			etPhone.setError("手机号码格式错误");
			etPhone.requestFocus();
			return;
		}
		NetUtil netUtil = new NetUtil(this, JsonApi.VCODE_FORGET_PASSWORD);
		netUtil.setParams("phone", getString(etPhone));
		netUtil.postRequest("正在获取验证码", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					etVcode.setText(JsonUtil.getData(result));
				}
			}
		});
	}
}
