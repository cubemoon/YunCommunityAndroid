package com.yuncommunity.theme.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.oldfeel.base.BaseFragment;
import com.oldfeel.utils.ETUtil;
import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.yuncommunity.R;
import com.yuncommunity.conf.AccessTokenKeeper;
import com.yuncommunity.conf.Constant;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.theme.android.A_MainActivity;

/**
 * 登录
 * 
 * @author oldfeel
 * 
 */
public class LoginFragment extends BaseFragment {
	private WeiboAuth mWeiboAuth;
	private SsoHandler mSsoHandler;
	private EditText etAccount, etPassword;
	private Button btnSubmit;
	private Button btnWeibo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.a_login, container, false);
		etAccount = getEditText(view, R.id.a_login_account);
		etPassword = getEditText(view, R.id.a_login_password);
		btnSubmit = getButton(view, R.id.a_login_submit);
		btnWeibo = getButton(view, R.id.a_login_weibo);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		btnSubmit.setOnClickListener(clickListener);
		btnWeibo.setOnClickListener(clickListener);
	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.a_login_submit:
				startLogin();
				break;
			case R.id.a_login_weibo:
				weiboLogin();
				break;
			default:
				break;
			}
		}
	};

	protected void weiboLogin() {
		mWeiboAuth = new WeiboAuth(getActivity(), Constant.APP_KEY,
				Constant.REDIRECT_URL, Constant.SCOPE);
		mSsoHandler = new SsoHandler(getActivity(), mWeiboAuth);
		mSsoHandler.authorize(new WeiboAuthListener() {

			@Override
			public void onWeiboException(WeiboException arg0) {
			}

			@Override
			public void onComplete(Bundle values) {
				// 从Bundle中解析Token
				Oauth2AccessToken mAccessToken = Oauth2AccessToken
						.parseAccessToken(values);
				if (mAccessToken.isSessionValid()) {
					// 保存Token
					AccessTokenKeeper.writeAccessToken(getActivity(),
							mAccessToken);
				} else {
					// 当您注册的应用程序签名不正确时,就会收到错误Code,请确保签名正确
					String code = values.getString("code");
				}
			}

			@Override
			public void onCancel() {
			}
		});

	}

	protected void startLogin() {
		if (ETUtil.isHaveNull(etAccount, etPassword)) {
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
		NetUtil netUtil = new NetUtil(getActivity(), JsonApi.LOGIN);
		netUtil.setParams("account", getString(etAccount));
		netUtil.setParams("password", getString(etPassword));
		netUtil.postRequest("正在验证登录信息...", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					LoginInfo.getInstance(getActivity()).saveInfo(
							JsonUtil.getData(result));
					Intent intent = new Intent(getActivity(),
							A_MainActivity.class);
					intent.putExtra("islogin", true);
					startActivity(intent);
					getActivity().onBackPressed();
				} else {
					showToast(JsonUtil.getData(result));
				}
			}
		});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}
}
