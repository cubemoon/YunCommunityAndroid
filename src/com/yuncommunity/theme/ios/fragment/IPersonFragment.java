package com.yuncommunity.theme.ios.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.yuncommunity.item.FuncItem;
import com.yuncommunity.theme.ios.adapter.IPersonAdapter;

/**
 * 个人中心
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月25日
 */
public class IPersonFragment extends ListFragment {
	private IPersonAdapter personAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		personAdapter = new IPersonAdapter(getActivity());
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
}