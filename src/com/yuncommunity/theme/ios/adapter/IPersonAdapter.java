package com.yuncommunity.theme.ios.adapter;

import android.content.Context;
import android.view.View;

import com.oldfeel.base.BaseBaseAdapter;
import com.yuncommunity.R;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.item.FuncItem;
import com.yuncommunity.theme.ios.IMyAttention;
import com.yuncommunity.theme.ios.IMyCollection;
import com.yuncommunity.theme.ios.IPersonInfo;
import com.yuncommunity.theme.ios.IReleaseActivity;
import com.yuncommunity.theme.ios.IReleaseProduct;
import com.yuncommunity.theme.ios.ISetting;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class IPersonAdapter extends BaseBaseAdapter<FuncItem> {

	public IPersonAdapter(Context context) {
		super(context);
		add(new FuncItem(LoginInfo.getInstance(context).getName(),
				R.drawable.avatar_default, IPersonInfo.class));
		add(new FuncItem(R.string.release_product, 0, IReleaseProduct.class));
		add(new FuncItem(R.string.release_activity, 0, IReleaseActivity.class));
		add(new FuncItem(R.string.my_collection, 0, IMyCollection.class));
		add(new FuncItem(R.string.my_collection, 0, IMyAttention.class));
		add(new FuncItem(R.string.setting, 0, ISetting.class));
	}

	@Override
	public View getView(int position, View view) {
		return null;
	}

}
