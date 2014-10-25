package com.yuncommunity.theme.android;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oldfeel.base.BaseActivity;
import com.oldfeel.base.BaseFragment;
import com.yuncommunity.R;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.theme.ios.IMainActivity;

/**
 * 切换主题
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月25日
 */
public class SwitchTheme extends BaseActivity {
	private ViewPager pager;
	private ThemeFragmentPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new ThemeFragmentPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
	}

	static class ThemeFragment extends BaseFragment {
		private int nameResId;
		private int imageResId;
		private TextView tvName;
		private ImageView ivThumbnail;
		private Button btnSelect;

		public static ThemeFragment newInstance(int nameResId, int imageResId) {
			ThemeFragment fragment = new ThemeFragment();
			fragment.nameResId = nameResId;
			fragment.imageResId = imageResId;
			return fragment;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.theme_thumbnail, null);
			tvName = (TextView) view.findViewById(R.id.theme_thumbnail_name);
			ivThumbnail = (ImageView) view
					.findViewById(R.id.theme_thumbnail_image);
			btnSelect = (Button) view.findViewById(R.id.theme_thumbnail_select);
			return view;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			tvName.setText(getActivity().getString(nameResId));
			ivThumbnail.setImageResource(imageResId);
			btnSelect.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					((SwitchTheme) getActivity()).select(getActivity()
							.getString(nameResId));
				}
			});
		}
	}

	class ThemeFragmentPagerAdapter extends FragmentPagerAdapter {
		ArrayList<ThemeItem> list = new ArrayList<SwitchTheme.ThemeItem>();

		public ThemeFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
			add(R.string.theme_android, R.drawable.thum_theme_android);
			add(R.string.theme_ios, R.drawable.thum_theme_ios);
		}

		private void add(int nameResId, int imageResId) {
			list.add(new ThemeItem(nameResId, imageResId));
		}

		@Override
		public Fragment getItem(int position) {
			return ThemeFragment.newInstance(list.get(position).getNameResId(),
					list.get(position).getImageResId());
		}

		@Override
		public int getCount() {
			return list.size();
		}

	}

	class ThemeItem {
		private int imageResId, nameResId;

		public ThemeItem(int imageResId, int nameResId) {
			super();
			this.imageResId = imageResId;
			this.nameResId = nameResId;
		}

		public int getImageResId() {
			return imageResId;
		}

		public void setImageResId(int imageResId) {
			this.imageResId = imageResId;
		}

		public int getNameResId() {
			return nameResId;
		}

		public void setNameResId(int nameResId) {
			this.nameResId = nameResId;
		}

	}

	/**
	 * 选择主题
	 * 
	 * @param name
	 *            主题名字
	 */
	protected void select(String name) {
		if (name.equals(LoginInfo.getInstance(getApplicationContext())
				.getTheme())) {
			finish();
		} else {
			LoginInfo.getInstance(getApplicationContext()).setTheme(name);
			if (name.equals(getString(R.string.theme_android))) {
				openActivity(MainActivity.class);
			} else if (name.equals(getString(R.string.theme_ios))) {
				openActivity(IMainActivity.class);
			}
		}
	}
}
