package com.yuncommunity.base;

import android.content.Intent;

import com.oldfeel.base.BaseListFragment;
import com.yuncommunity.utils.Utils;

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
		return Utils.getThemeIntent(getActivity(), activity);
	}
}
