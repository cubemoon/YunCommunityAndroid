package com.yuncommunity.theme.ios;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.oldfeel.base.BasePagerAdapter;
import com.oldfeel.utils.NetUtil;
import com.yuncommunity.R;
import com.yuncommunity.theme.ios.base.IBaseActivity;
import com.yuncommunity.theme.ios.fragment.IActivityFragment;
import com.yuncommunity.theme.ios.fragment.IPersonFragment;
import com.yuncommunity.theme.ios.fragment.IServerFragment;
import com.yuncommunity.theme.ios.fragment.ISquareFragment;

/**
 * ios风格
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月24日
 */
public class IMainActivity extends IBaseActivity {
	private ViewPager pager;
	private RadioGroup rgNav;
	private BasePagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_main_activity);
		pager = (ViewPager) findViewById(R.id.i_main_pager);
		rgNav = (RadioGroup) findViewById(R.id.i_main_nav);
		adapter = new BasePagerAdapter(getSupportFragmentManager());
		adapter.add(ISquareFragment.newInstance(getSquareNetUtil()));
		adapter.add(new IServerFragment());
		adapter.add(new IActivityFragment());
		adapter.add(new IPersonFragment());
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				int id = R.id.i_main_square;
				switch (position) {
				case 0:
					id = R.id.i_main_square;
					break;
				case 1:
					id = R.id.i_main_server;
					break;
				case 2:
					id = R.id.i_main_activity;
					break;
				case 3:
					id = R.id.i_main_person;
					break;
				default:
					break;
				}
				rgNav.check(id);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		rgNav.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int index = 0;
				switch (checkedId) {
				case R.id.i_main_activity:
					index = 2;
					break;
				case R.id.i_main_person:
					index = 3;
					break;
				case R.id.i_main_server:
					index = 1;
					break;
				case R.id.i_main_square:
					index = 0;
					break;
				default:
					break;
				}
				pager.setCurrentItem(index);
			}
		});
	}

	private NetUtil getSquareNetUtil() {
		return null;
	}
}
