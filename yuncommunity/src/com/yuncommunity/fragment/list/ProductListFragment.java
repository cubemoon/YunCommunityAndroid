package com.yuncommunity.fragment.list;

import com.yuncommunity.adapter.ProductListAdapter;
import com.yuncommunity.base.BaseListFragment;
import com.yuncommunity.item.ProductItem;
import com.yuncommunity.util.NetUtil;

/**
 * 产品列表
 * 
 * @author oldfeel
 * 
 */
public class ProductListFragment extends BaseListFragment {

	public static ProductListFragment newInstance(NetUtil netUtil) {
		ProductListFragment fragment = new ProductListFragment();
		fragment.netUtil = netUtil;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
	}

	@Override
	public void initAdapter() {
		adapter = new ProductListAdapter(getActivity());
	}

	public void add(ProductItem item) {
		((ProductListAdapter) adapter).add(0, item);
	}

}
