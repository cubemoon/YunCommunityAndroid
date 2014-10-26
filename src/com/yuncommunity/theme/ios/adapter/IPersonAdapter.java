package com.yuncommunity.theme.ios.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

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
		add(new FuncItem(context.getString(R.string.release_product),
				R.drawable.ic_launcher, IReleaseProduct.class));
		add(new FuncItem(context.getString(R.string.release_activity),
				R.drawable.ic_launcher, IReleaseActivity.class));
		add(new FuncItem(context.getString(R.string.my_collection),
				R.drawable.ic_launcher, IMyCollection.class));
		add(new FuncItem(context.getString(R.string.my_collection),
				R.drawable.ic_launcher, IMyAttention.class));
		add(new FuncItem(context.getString(R.string.setting),
				R.drawable.ic_launcher, ISetting.class));
	}

	@Override
	public View getView(int position, View view) {
		FuncItem item = getItem(position);
		view = inflater.inflate(R.layout.single_text_light, null);
		TextView tvName = (TextView) view.findViewById(R.id.text);
		tvName.setText(item.getName());
		tvName.setCompoundDrawablesWithIntrinsicBounds(item.getImageResId(), 0,
				0, 0);
		return view;
	}

}
