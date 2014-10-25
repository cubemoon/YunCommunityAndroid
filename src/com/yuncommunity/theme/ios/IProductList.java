package com.yuncommunity.theme.ios;

import android.os.Bundle;

import com.oldfeel.utils.NetUtil;
import com.yuncommunity.R;
import com.yuncommunity.item.TagItem;
import com.yuncommunity.theme.ios.base.IBaseActivity;
import com.yuncommunity.theme.ios.list.IProductListFragment;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class IProductList extends IBaseActivity {
	private TagItem tagItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.single_frame);
		tagItem = (TagItem) getIntent().getSerializableExtra("item");
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame,
						IProductListFragment.newInstance(getListNetUtil()))
				.commit();
	}

	private NetUtil getListNetUtil() {
		return null;
	}
}
