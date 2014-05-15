package android.oldfeel.yanzhuang.base;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;

/**
 * viewpager的adapter,包含控制tab切换
 * 
 * @author oldfeel
 * 
 *         Created on: 2014-1-16
 */
public class BaseTabsAdapter extends FragmentPagerAdapter implements
		ActionBar.TabListener, ViewPager.OnPageChangeListener {
	private final Context mContext;
	private final ActionBar mActionBar;
	private final ViewPager mViewPager;
	private ArrayList<Fragment> list = new ArrayList<Fragment>();

	public BaseTabsAdapter(ActionBarActivity activity, ViewPager pager) {
		super(activity.getSupportFragmentManager());
		mContext = activity;
		mActionBar = activity.getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mViewPager = pager;
		mViewPager.setAdapter(this);
		mViewPager.setOnPageChangeListener(this);
	}

	public void addTab(String tabName, Class<?> clss) {
		addTab(tabName, clss, null);
	}

	public void addTab(String tabName, Class<?> clss, Bundle args) {
		Fragment fragment = Fragment
				.instantiate(mContext, clss.getName(), args);
		ActionBar.Tab tab = mActionBar.newTab().setText(tabName);
		tab.setTag(fragment);
		tab.setTabListener(this);
		list.add(fragment);
		notifyDataSetChanged();
		mActionBar.addTab(tab);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Fragment getItem(int position) {
		return list.get(position);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
		mActionBar.setSelectedNavigationItem(position);
		if (selectedListener != null) {
			selectedListener.onPageSelected(position);
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Object tag = tab.getTag();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == tag) {
				mViewPager.setCurrentItem(i);
			}
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	public void clear() {
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.removeAllTabs();
		list.clear();
	}

	public interface OnSelectedListener {
		public void onPageSelected(int position);
	}

	private OnSelectedListener selectedListener;

	public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
		this.selectedListener = onSelectedListener;
	}
}