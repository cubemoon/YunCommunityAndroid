package com.yuncommunity.theme.ios.list;

import android.content.Intent;

import com.oldfeel.base.BaseListFragment;
import com.oldfeel.utils.NetUtil;
import com.yuncommunity.item.ProductItem;
import com.yuncommunity.theme.ios.IProductDetail;
import com.yuncommunity.theme.ios.adapter.IProductListAdapter;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class IProductListFragment extends BaseListFragment {

	public static IProductListFragment newInstance(NetUtil netUtil) {
		IProductListFragment fragment = new IProductListFragment();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
		ProductItem item = (ProductItem) adapter.getItem(position);
		startActivity(new Intent(getActivity(), IProductDetail.class).putExtra(
				"item", item));
	}

	@Override
	public void initAdapter() {
		adapter = new IProductListAdapter(getActivity());
	}

	@Override
	public void initHeaderView() {
	}

}
