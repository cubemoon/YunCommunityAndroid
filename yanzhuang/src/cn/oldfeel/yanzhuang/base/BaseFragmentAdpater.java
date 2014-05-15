package cn.oldfeel.yanzhuang.base;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * viewpager中fragment适配器的基类
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年3月10日
 */
public class BaseFragmentAdpater extends FragmentPagerAdapter {
	ArrayList<Fragment> list = new ArrayList<Fragment>();

	public BaseFragmentAdpater(FragmentManager fm) {
		super(fm);
	}

	public void add(Fragment fragment) {
		list.add(fragment);
		notifyDataSetChanged();
	}

	@Override
	public Fragment getItem(int position) {
		return list.get(position);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return getItem(position).getArguments().getString("title");
	}
}
