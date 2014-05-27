package android.oldfeel.yanzhuang.fragment.list;

import android.oldfeel.yanzhuang.adapter.ProductListAdapter;
import android.oldfeel.yanzhuang.base.BaseListFragment;
import android.oldfeel.yanzhuang.item.ProductItem;
import android.oldfeel.yanzhuang.util.NetUtil;

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
