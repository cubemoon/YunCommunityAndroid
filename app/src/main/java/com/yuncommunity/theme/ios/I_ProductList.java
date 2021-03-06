package com.yuncommunity.theme.ios;

import android.os.Bundle;

import com.oldfeel.utils.NetUtil;
import com.yuncommunity.R;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.item.TagItem;
import com.yuncommunity.list.ProductListFragment;
import com.yuncommunity.theme.ios.base.I_BaseActivity;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class I_ProductList extends I_BaseActivity {
	private TagItem tagItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.single_frame);
		tagItem = (TagItem) getIntent().getSerializableExtra("item");
		setTitle(tagItem.getName());
		hideRight();
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame,
						ProductListFragment.newInstance(getListNetUtil()))
				.commit();
	}

	private NetUtil getListNetUtil() {
		NetUtil netUtil = initNetUtil(JsonApi.INFORMATION_LIST_BY_TAG);
		netUtil.setParams("tagid", tagItem.getTagid());
		return netUtil;
	}
}
