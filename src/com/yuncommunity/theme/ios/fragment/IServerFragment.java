package com.yuncommunity.theme.ios.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.yuncommunity.item.TagItem;
import com.yuncommunity.theme.ios.IProductList;
import com.yuncommunity.theme.ios.adapter.IServerTagAdapter;

/**
 * 服务
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月25日
 */
public class IServerFragment extends ListFragment {
	private IServerTagAdapter tagAdapter;

	public static IServerFragment newInstance() {
		IServerFragment fragment = new IServerFragment();
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tagAdapter = new IServerTagAdapter(getActivity());
		setListAdapter(tagAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		TagItem item = tagAdapter.getItem(position);
		startActivity(new Intent(getActivity(), IProductList.class).putExtra(
				"item", item));
	}
}
