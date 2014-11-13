package com.yuncommunity.theme.ios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.oldfeel.base.BasePagerAdapter;
import com.oldfeel.utils.DialogUtil;
import com.oldfeel.utils.NetUtil;
import com.yuncommunity.R;
import com.yuncommunity.conf.Constant;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.item.CommunityItem;
import com.yuncommunity.item.SquareItem;
import com.yuncommunity.theme.ios.base.I_BaseActivity;
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
public class I_MainActivity extends I_BaseActivity {
	private ViewPager pager;
	private RadioGroup rgNav;
	private BasePagerAdapter adapter;
	private ISquareFragment squareFragment;
	private IServerFragment serverFragment;
	private IActivityFragment activityFragment;
	private IPersonFragment personFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_main_activity);
		setSwipeBackEnable(false);
		hideLeft();
		initView();
		initPager();
		initListener();
		if (LoginInfo.getInstance(this).getCommunityInfo().getCommunityid() == 0) {
			openActivity(I_ChangeCommunity.class);
			finish();
		} else {
			setTitle(LoginInfo.getInstance(this).getCommunityInfo().getName());
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		changeCommunity(intent);
		squareRelease(intent);
		islogin(intent);
	}

	private void islogin(Intent intent) {
		boolean islogin = intent.getBooleanExtra("islogin", false);
		if (islogin) {
			personFragment.updateName();
		}
	}

	private void squareRelease(Intent intent) {
		SquareItem squareItem = (SquareItem) intent
				.getSerializableExtra("squareitem");
		if (squareItem != null) {
			squareFragment.add(squareItem);
		}
	}

	private void changeCommunity(Intent intent) {
		CommunityItem communityItem = (CommunityItem) intent
				.getSerializableExtra("communityitem");
		if (communityItem != null) {
			setTitle(communityItem.getName());
			squareFragment.setNetUtil(getSquareNetUtil());
			activityFragment.setNetUtil(getActivityNetUtil());
		}
	}

	private void initListener() {
		rgNav.setOnCheckedChangeListener(checkedChangeListener);
		pager.setOnPageChangeListener(pageChangeListener);
		tvTitle.setOnClickListener(clickListener);
	}

	private OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {

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
	};
	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			hideRight();
			int id = R.id.i_main_square;
			switch (position) {
			case 0:
				id = R.id.i_main_square;
				showRight();
				break;
			case 1:
				id = R.id.i_main_server;
				hideRight();
				break;
			case 2:
				id = R.id.i_main_activity;
				hideRight();
				break;
			case 3:
				id = R.id.i_main_person;
				hideRight();
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
	};

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.i_base_view_title:
				openActivity(I_ChangeCommunity.class);
				break;

			default:
				break;
			}
		}
	};

	private void initView() {
		pager = (ViewPager) findViewById(R.id.i_main_pager);
		rgNav = (RadioGroup) findViewById(R.id.i_main_nav);
	}

	private void initPager() {
		squareFragment = ISquareFragment.newInstance(getSquareNetUtil());
		serverFragment = IServerFragment.newInstance();
		activityFragment = IActivityFragment.newInstance(getActivityNetUtil());
		personFragment = IPersonFragment.newInstance();
		adapter = new BasePagerAdapter(getSupportFragmentManager());
		adapter.add(squareFragment);
		adapter.add(serverFragment);
		adapter.add(activityFragment);
		adapter.add(personFragment);
		pager.setAdapter(adapter);
		pager.setOffscreenPageLimit(3);
	}

	private NetUtil getActivityNetUtil() {
		NetUtil netUtil = super.initNetUtil(JsonApi.INFORMATION_LIST);
		netUtil.setParams("infotype", Constant.TYPE_ACTIVITY);
		return netUtil;
	}

	private NetUtil getSquareNetUtil() {
		NetUtil netUtil = super.initNetUtil(JsonApi.INFORMATION_LIST);
		netUtil.setParams("infotype", Constant.TYPE_SQUARE);
		return netUtil;
	}

	long clickTime;

	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - clickTime < 2 * 1000) {
			DialogUtil.getInstance().cancelToast();
			super.onBackPressed();
		} else {
			clickTime = System.currentTimeMillis();
			showToast("再点一次退出应用");
		}
	}
}
