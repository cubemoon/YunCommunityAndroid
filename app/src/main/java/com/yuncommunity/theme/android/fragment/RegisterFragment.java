package com.yuncommunity.theme.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.oldfeel.base.BaseFragment;
import com.oldfeel.utils.ETUtil;
import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.R;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.theme.android.A_MainActivity;

/**
 * 注册
 * 
 * @author oldfeel
 * 
 */
public class RegisterFragment extends BaseFragment {
	private EditText etPhone, etPassword, etVcode;
	private Button btnGetVcode, btnSubmit;
	private CheckBox cbIsAgree;
	private TextView tvUserAgree;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.a_register, container, false);
		etPhone = getEditText(view, R.id.a_register_phone);
		etPassword = getEditText(view, R.id.a_register_password);
		etVcode = getEditText(view, R.id.a_register_vcode);
		btnGetVcode = getButton(view, R.id.a_register_getvcode);
		btnSubmit = getButton(view, R.id.a_register_submit);
		cbIsAgree = (CheckBox) view.findViewById(R.id.a_register_isagree);
		tvUserAgree = getTextView(view, R.id.a_register_useragree);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		btnGetVcode.setOnClickListener(clickListener);
		btnSubmit.setOnClickListener(clickListener);
	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.a_register_getvcode:
				getVcode();
				break;
			case R.id.a_register_submit:
				submit();
				break;
			default:
				break;
			}
		}
	};

	protected void getVcode() {
		if (!ETUtil.isMobileNO(etPhone)) {
			etPhone.requestFocus();
			etPhone.setError("格式错误");
			return;
		}
		NetUtil netUtil = new NetUtil(getActivity(), JsonApi.VCODE_REGISTER);
		netUtil.setParams("phone", getString(etPhone));
		netUtil.postRequest("正在发送验证码...", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					showToast("获取验证码成功");
					etVcode.setText(JsonUtil.getData(result));
				}
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
		if (getString(etPassword).length() < 6) {
			etPassword.setError("太短了");
			etPassword.requestFocus();
			return;
		}
		if (getString(etPassword).length() > 16) {
			etPassword.setError("太长了");
			etPassword.requestFocus();
			return;
		}
		NetUtil netUtil = new NetUtil(getActivity(), JsonApi.REGISTER);
		netUtil.setParams("account", getString(etPhone));
		netUtil.setParams("vcode", getString(etVcode));
		netUtil.setParams("password", getString(etPassword));
		netUtil.postRequest("正在提交注册信息...", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					showToast("注册成功");
					LoginInfo.getInstance(getActivity()).saveInfo(
							JsonUtil.getData(result));
					Intent intent = new Intent(getActivity(),
							A_MainActivity.class);
					intent.putExtra("islogin", true);
					startActivity(intent);
					getActivity().onBackPressed();
				}
			}
		});
	}
}
