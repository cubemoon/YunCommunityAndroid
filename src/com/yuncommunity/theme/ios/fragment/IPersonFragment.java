package com.yuncommunity.theme.ios.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.yuncommunity.R;
import com.yuncommunity.adapter.PersonAdapter;
import com.yuncommunity.item.FuncItem;

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
}
