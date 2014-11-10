package com.yuncommunity.theme.ios.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.oldfeel.base.BaseListFragment;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.view.HorizontalListView;
import com.yuncommunity.R;
import com.yuncommunity.adapter.SquareAdapter;
import com.yuncommunity.adapter.SquareHeaderAdapter;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.item.SquareItem;
import com.yuncommunity.theme.ios.IMainActivity;
import com.yuncommunity.theme.ios.IProductList;
import com.yuncommunity.theme.ios.IReleaseSquare;
import com.yuncommunity.theme.ios.ISquareDetail;

/**
 * 广场
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月25日
 */
public class ISquareFragment extends BaseListFragment {
	private SquareHeaderAdapter headerAdapter;

	public static ISquareFragment newInstance(NetUtil netUtil) {
		ISquareFragment fragment = new ISquareFragment();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getSpeakButton().setText(getString(R.string.speak));
		getSpeakButton().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (LoginInfo.getInstance(getActivity()).isLogin(getActivity())) {
					openActivity(IReleaseSquare.class);
				}
			}
		});
	}

	private Button getSpeakButton() {
		return ((IMainActivity) getActivity()).btnRight;
	}

	private View getHeaderView() {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.i_square_func, new LinearLayout(getActivity()), true);
		HorizontalListView listView = (HorizontalListView) view
				.findViewById(R.id.i_square_func);
		listView.getParent().requestDisallowInterceptTouchEvent(true);
		headerAdapter = new SquareHeaderAdapter(getActivity());
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
		adapter = new SquareAdapter(getActivity());
	}

	@Override
	public void initHeaderView() {
		getListView().addHeaderView(getHeaderView());
	}

	@Override
	public void onResume() {
		super.onResume();
		getData(0);
	}
}
