package com.yuncommunity.theme.ios.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.yuncommunity.adapter.ServerTagAdapter;
import com.yuncommunity.item.TagItem;
import com.yuncommunity.theme.ios.I_ProductList;

/**
 * 服务
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月25日
 */
public class IServerFragment extends ListFragment {
	private ServerTagAdapter tagAdapter;

	public static IServerFragment newInstance() {
		IServerFragment fragment = new IServerFragment();
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tagAdapter = new ServerTagAdapter(getActivity());
		setListAdapter(tagAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		TagItem item = tagAdapter.getItem(position);
		getActivity().startActivity(
				new Intent(getActivity(), I_ProductList.class).putExtra("item",
						item));
	}
}
