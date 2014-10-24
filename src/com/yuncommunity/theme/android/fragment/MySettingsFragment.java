package com.yuncommunity.theme.android.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.support.v4.app.Fragment;
import android.support.v4.preference.PreferenceFragment;
import android.webkit.WebView;

import com.oldfeel.base.BaseActivity;
import com.oldfeel.utils.DialogUtil;
import com.oldfeel.utils.JSONUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.oldfeel.utils.VersionUtil;
import com.yuncommunity.R;
import com.yuncommunity.theme.android.ChangeCommunity;
import com.yuncommunity.theme.android.conf.JsonApi;
import com.yuncommunity.theme.android.conf.LoginInfo;

/**
 * 设置
 * 
 * @author oldfeel
 * 
 */
public class MySettingsFragment extends PreferenceFragment {
	/** true为有修改设置内容,false为没有修改设置内容 */
	protected boolean isUpdate = false;

	public static Fragment newInstance() {
		MySettingsFragment fragment = new MySettingsFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		addPreferencesFromResource(R.xml.my_settings);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initChangeCommunity();
		initFriendMsg();
		initActivityMsg();
		initBusinessMsg();
		initSearchHistory();
		initWifiUpdate();
		initCheckVersion();
		initAbout();
	}

	/**
	 * 切换小区
	 */
	private void initChangeCommunity() {
		Preference preference = getPreferenceManager().findPreference(
				getString(R.string.change_community));
		preference.setSummary(LoginInfo.getInstance(getActivity())
				.getCommunityInfo().getName());
		preference
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {
						((BaseActivity) getActivity())
								.openActivity(ChangeCommunity.class);
						return false;
					}
				});
	}

	private void initAbout() {
		Preference preference = getPreferenceManager().findPreference(
				getString(R.string.about));
		preference
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {
						about();
						return false;
					}
				});
	}

	/**
	 * 关于我们
	 */
	protected void about() {
		WebView webView = new WebView(getActivity());
		webView.loadUrl("file:///android_asset/about.html");
		new AlertDialog.Builder(getActivity()).setView(webView).show();
	}

	private void initCheckVersion() {
		Preference preference = getPreferenceManager().findPreference(
				getString(R.string.check_version));
		preference.setSummary("版本" + VersionUtil.getVersionName(getActivity()));
		preference
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {
						checkVersion();
						return false;
					}
				});
	}

	/**
	 * 检查版本
	 */
	protected void checkVersion() {
		NetUtil netUtil = new NetUtil(getActivity(), JsonApi.CHECK_VERSION);
		netUtil.setParams("communityid", LoginInfo.getInstance(getActivity())
				.getCommunityInfo().getCommunityid());
		netUtil.postRequest("正在检查版本", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JSONUtil.isSuccess(result)) {
					if (VersionUtil.isNeedUpdate(getActivity(), result)) {
						DialogUtil.getInstance().showSimpleDialog(
								getActivity(),
								"检测到新版本" + VersionUtil.getNewVersion(result)
										+ ",是否更新?", new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										updateVersion();
									}
								});
					} else {
						DialogUtil.getInstance().showSimpleDialog(
								getActivity(), "已经是最新版本,不需要更新");
					}
				} else {
					DialogUtil.getInstance().showToast(getActivity(),
							"检查失败," + JSONUtil.getMessage(result));
				}
			}
		});
	}

	/**
	 * 更新版本
	 */
	public void updateVersion() {

	}

	private void initWifiUpdate() {
	}

	private void initSearchHistory() {
		Preference preference = getPreferenceManager().findPreference(
				getString(R.string.search_history));
		preference
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {
						clearSearchHistory();
						return false;
					}
				});
	}

	/**
	 * 清除搜索记录
	 */
	protected void clearSearchHistory() {
	}

	private void initBusinessMsg() {
		CheckBoxPreference businessmsg = (CheckBoxPreference) getPreferenceManager()
				.findPreference(getString(R.string.business_msg));
		businessmsg.setChecked(LoginInfo.getInstance(getActivity())
				.getUserInfo().getBusinessmsg());
		businessmsg
				.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

					@Override
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						isUpdate = true;
						LoginInfo
								.getInstance(getActivity())
								.getUserInfo()
								.setBusinessmsg(
										Boolean.valueOf(newValue.toString()));
						return true;
					}
				});
	}

	private void initActivityMsg() {
		CheckBoxPreference activitymsg = (CheckBoxPreference) getPreferenceManager()
				.findPreference(getString(R.string.activity_msg));
		activitymsg.setChecked(LoginInfo.getInstance(getActivity())
				.getUserInfo().getActivitymsg());
		activitymsg
				.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

					@Override
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						isUpdate = true;
						LoginInfo
								.getInstance(getActivity())
								.getUserInfo()
								.setActivitymsg(
										Boolean.valueOf(newValue.toString()));
						return true;
					}
				});
	}

	private void initFriendMsg() {
		CheckBoxPreference friendMsg = (CheckBoxPreference) getPreferenceManager()
				.findPreference(getString(R.string.friend_msg));
		friendMsg.setChecked(LoginInfo.getInstance(getActivity()).getUserInfo()
				.getFriendmsg());
		friendMsg
				.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

					@Override
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						isUpdate = true;
						LoginInfo
								.getInstance(getActivity())
								.getUserInfo()
								.setFriendmsg(
										Boolean.valueOf(newValue.toString()));
						return true;
					}
				});
	}

	@Override
	public void onPause() {
		updateMySettings();
		super.onPause();
	}

	private void updateMySettings() {
		if (isUpdate) {
			LoginInfo.update(getActivity());
		}
	}

}
