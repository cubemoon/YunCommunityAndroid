package com.yuncommunity.theme.ios;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.oldfeel.utils.ETUtil;
import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.R;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.theme.ios.base.IBaseActivity;

/**
 * 注册
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月8日
 */
public class IRegisterActivity extends IBaseActivity {
	private EditText etPhone, etPassword, etVcode;
	private Button btnGetVcode, btnSubmit;
	private CheckBox cbIsAgree;
	private TextView tvUserAgree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_register);
		setTitle("注册");
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
			case R.id.i_register_getvcode:
				getVcode();
				break;
			case R.id.i_register_submit:
				submit();
				break;
			default:
				break;
			}
		}
	};

	private void initView() {
		etPhone = getEditText(R.id.i_register_phone);
		etPassword = getEditText(R.id.i_register_password);
		etVcode = getEditText(R.id.i_register_vcode);
		btnGetVcode = getButton(R.id.i_register_getvcode);
		btnSubmit = getButton(R.id.i_register_submit);
		cbIsAgree = getCheckBox(R.id.i_register_isagree);
		tvUserAgree = getTextView(R.id.i_register_useragree);
	}

	protected void getVcode() {
		if (!ETUtil.isMobileNO(etPhone)) {
			etPhone.requestFocus();
			etPhone.setError("格式错误");
			return;
		}
		NetUtil netUtil = new NetUtil(IRegisterActivity.this,
				JsonApi.REGISTER_VCODE);
		netUtil.setParams("phone", getString(etPhone));
		netUtil.postRequest("正在发送验证码...", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				showToast("获取验证码成功");
				etVcode.setText(JsonUtil.getData(result));
			}
		});
	}

	protected void submit() {
		if (!cbIsAgree.isChecked()) {
			showSimpleDialog("只有同意用户协议才能注册");
			return;
		}
		if (ETUtil.isHaveNull(etPhone, etPassword)) {
			return;
		}
		NetUtil netUtil = new NetUtil(IRegisterActivity.this, JsonApi.REGISTER);
		netUtil.setParams("phone", getString(etPhone));
		netUtil.setParams("vcode", getString(etVcode));
		netUtil.setParams("password", getString(etPassword));
		netUtil.postRequest("正在提交注册信息...", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				showToast("注册成功");
				LoginInfo.getInstance(IRegisterActivity.this).saveInfo(
						JsonUtil.getData(result));
				openActivity(IMainActivity.class);
			}
		});
	}
}
