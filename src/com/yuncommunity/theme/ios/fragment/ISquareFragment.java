package com.yuncommunity.theme.ios.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.oldfeel.base.BaseListFragment;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.view.HorizontalListView;
import com.yuncommunity.R;
import com.yuncommunity.item.SquareItem;
import com.yuncommunity.theme.ios.IProductList;
import com.yuncommunity.theme.ios.ISquareDetail;
import com.yuncommunity.theme.ios.adapter.ISquareAdapter;
import com.yuncommunity.theme.ios.adapter.ISquareHeaderAdapter;

/**
 * 广场
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月25日
 */
public class ISquareFragment extends BaseListFragment {
	private ISquareHeaderAdapter headerAdapter;

	public static ISquareFragment newInstance(NetUtil netUtil) {
		ISquareFragment fragment = new ISquareFragment();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getListView().addHeaderView(getHeaderView());
	}

	private View getHeaderView() {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.i_square_func, null);
		HorizontalListView listView = (HorizontalListView) view
				.findViewById(R.id.i_square_func);
		headerAdapter = new ISquareHeaderAdapter(getActivity());
		listView.setAdapter(headerAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), IProductList.class);
				intent.putExtra("item", headerAdapter.getItemId(position));
				startActivity(intent);
			}
		});
		return view;
	}

	@Override
	public void onItemClick(int position) {
		SquareItem item = (SquareItem) adapter.getItem(position);
		Intent intent = new Intent(getActivity(), ISquareDetail.class);
		intent.putExtra("item", item);
		startActivity(intent);
	}

	@Override
	public void initAdapter() {
		adapter = new ISquareAdapter(getActivity());
	}

}
