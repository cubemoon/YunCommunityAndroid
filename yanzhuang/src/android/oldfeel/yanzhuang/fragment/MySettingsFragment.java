package android.oldfeel.yanzhuang.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.oldfeel.yanzhuang.R;
import android.oldfeel.yanzhuang.app.JsonApi;
import android.oldfeel.yanzhuang.app.PersonInfo;
import android.oldfeel.yanzhuang.util.DialogUtil;
import android.oldfeel.yanzhuang.util.JSONUtil;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.oldfeel.yanzhuang.util.NetUtil.RequestStringListener;
import android.oldfeel.yanzhuang.util.VersionUtil;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.support.v4.app.Fragment;
import android.support.v4.preference.PreferenceFragment;

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
		initFriendMsg();
		initActivityMsg();
		initBusinessMsg();
		initSearchHistory();
		initWifiUpdate();
		initCheckVersion();
		initAbout();
	}

	private void initAbout() {
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
		businessmsg.setChecked(PersonInfo.getInstance(getActivity())
				.getBusinesssmg());
		businessmsg
				.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

					@Override
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						isUpdate = true;
						PersonInfo.getInstance(getActivity()).setBusinessmsg(
								Boolean.valueOf(newValue.toString()));
						return true;
					}
				});
	}

	private void initActivityMsg() {
		CheckBoxPreference activitymsg = (CheckBoxPreference) getPreferenceManager()
				.findPreference(getString(R.string.activity_msg));
		activitymsg.setChecked(PersonInfo.getInstance(getActivity())
				.getActivitymsg());
		activitymsg
				.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

					@Override
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						isUpdate = true;
						PersonInfo.getInstance(getActivity()).setActivitymsg(
								Boolean.valueOf(newValue.toString()));
						return true;
					}
				});
	}

	private void initFriendMsg() {
		CheckBoxPreference friendMsg = (CheckBoxPreference) getPreferenceManager()
				.findPreference(getString(R.string.friend_msg));
		friendMsg.setChecked(PersonInfo.getInstance(getActivity())
				.getFriendmsg());
		friendMsg
				.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

					@Override
					public boolean onPreferenceChange(Preference preference,
							Object newValue) {
						isUpdate = true;
						PersonInfo.getInstance(getActivity()).setFriendmsg(
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
			PersonInfo.update(getActivity());
		}
	}

}
