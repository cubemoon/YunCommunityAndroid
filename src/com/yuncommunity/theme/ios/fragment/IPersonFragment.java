package com.yuncommunity.theme.ios.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.yuncommunity.R;
import com.yuncommunity.adapter.PersonAdapter;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.item.FuncItem;
import com.yuncommunity.theme.ios.I_LoginActivity;
import com.yuncommunity.utils.UpdateUtils;

/**
 * 个人中心
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月25日
 */
public class IPersonFragment extends ListFragment {
	private PersonAdapter personAdapter;

	public static IPersonFragment newInstance() {
		IPersonFragment fragment = new IPersonFragment();
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		personAdapter = new PersonAdapter(getActivity());
		setListAdapter(personAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (position == 0
				&& LoginInfo.getInstance(getActivity()).getUserId() == 0) {
			startActivity(new Intent(getActivity(), I_LoginActivity.class));
			return;
		}
		if (!UpdateUtils.isLogin(getActivity())) {
			return;
		}
		FuncItem item = personAdapter.getItem(position);
		if (item.getTheClass() != null) {
			startActivity(new Intent(getActivity(), item.getTheClass()));
		}
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		getActivity().overridePendingTransition(R.anim.slide_in_right,
				R.anim.slide_out_left);
	}

	public void updateName() {
		personAdapter.getItem(0).setName(
				LoginInfo.getInstance(getActivity()).getName());
		personAdapter.notifyDataSetChanged();
	}
}
