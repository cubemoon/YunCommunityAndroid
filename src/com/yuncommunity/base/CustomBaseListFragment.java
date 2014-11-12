package com.yuncommunity.base;

import android.content.Intent;

import com.oldfeel.base.BaseListFragment;
import com.yuncommunity.R;
import com.yuncommunity.conf.LoginInfo;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月12日
 */
public abstract class CustomBaseListFragment extends BaseListFragment {
	/**
	 * 根据className获取主题对应的intent
	 * 
	 * @param activity
	 * @return
	 */
	public Intent getThemeIntent(Class<?> activity) {
		Intent intent = new Intent();
		String name = "";
		String simpleName = activity.getSimpleName();

		LoginInfo info = LoginInfo.getInstance(getActivity());
		String theme = info.getTheme();
		if (theme.equals(getActivity().getString(R.string.theme_android))) {
			name = getThemeName(simpleName, "com.yuncommunity.theme.android",
					"A_");
		} else if (theme.equals(getActivity().getString(R.string.theme_ios))) {
			name = getThemeName(simpleName, "com.yuncommunity.theme.ios.", "I_");
		}
		intent.setClassName(getActivity(), name);
		return intent;
	}

	/**
	 * 
	 * @param packageString
	 *            包名
	 * @param simpleName
	 *            不带包名的类名
	 * @param prefix
	 *            前缀
	 * @return
	 */
	private String getThemeName(String simpleName, String packageString,
			String prefix) {
		if (!simpleName.startsWith(prefix)) {
			int index = simpleName.indexOf("_");
			if (index > -1 && index < 3) {
				simpleName = simpleName.substring(index + 1,
						simpleName.length());
			}
			simpleName = prefix + simpleName;
		}
		return packageString + simpleName;
	}
}
