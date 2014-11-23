package com.yuncommunity.theme.android.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.support.v4.app.Fragment;
import android.support.v4.preference.PreferenceFragment;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.oldfeel.base.BaseActivity;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.VersionUtil;
import com.winsontan520.wversionmanager.library.WVersionManager;
import com.yuncommunity.R;
import com.yuncommunity.conf.Constant;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.theme.android.A_ChangeCommunity;
import com.yuncommunity.theme.android.A_SwitchTheme;

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
		initTheme();
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
								.openActivity(A_ChangeCommunity.class);
						return false;
					}
				});
	}

	/**
	 * 主题
	 */
	private void initTheme() {
		Preference preference = getPreferenceManager().findPreference(
				getString(R.string.theme));
		preference.setSummary(LoginInfo.getInstance(getActivity()).getTheme());
		preference
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {
						((BaseActivity) getActivity())
								.openActivity(A_SwitchTheme.class);
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
		WVersionManager versionManager = new WVersionManager(getActivity());

		versionManager.setVersionContentUrl(Constant.CHECK_VERSION);
		versionManager.setUpdateNowLabel("现在更新");
		versionManager.setRemindMeLaterLabel("稍后更新");
		versionManager.setIgnoreThisVersionLabel("忽略当前版本");
		versionManager.setReminderTimer(1);

		versionManager.checkVersion();

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
			changeSettingInfo();
		}
	}

	private void changeSettingInfo() {
		NetUtil netUtil = new NetUtil(getActivity(),
				JsonApi.CHANGE_SETTING_INFO);
		netUtil.setParams("userinfo", new Gson().toJson(LoginInfo.getInstance(
				getActivity()).getUserInfo()));
		netUtil.postRequest();
	}

}
