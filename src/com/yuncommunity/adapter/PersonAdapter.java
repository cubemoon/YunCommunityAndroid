package com.yuncommunity.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.oldfeel.base.BaseBaseAdapter;
import com.yuncommunity.R;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.item.FuncItem;
import com.yuncommunity.theme.ios.I_MyAttention;
import com.yuncommunity.theme.ios.I_MyCollection;
import com.yuncommunity.theme.ios.I_PersonInfo;
import com.yuncommunity.theme.ios.I_ReleaseActivity;
import com.yuncommunity.theme.ios.I_ReleaseProduct;
import com.yuncommunity.theme.ios.I_Setting;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class PersonAdapter extends BaseBaseAdapter<FuncItem> {

	public PersonAdapter(Context context) {
		super(context);
		add(new FuncItem(LoginInfo.getInstance(context).getName(),
				R.drawable.avatar_default, I_PersonInfo.class));
		add(new FuncItem(context.getString(R.string.release_product),
				R.drawable.ic_launcher, I_ReleaseProduct.class));
		add(new FuncItem(context.getString(R.string.release_activity),
				R.drawable.ic_launcher, I_ReleaseActivity.class));
		add(new FuncItem(context.getString(R.string.my_collection),
				R.drawable.ic_launcher, I_MyCollection.class));
		add(new FuncItem(context.getString(R.string.my_attention),
				R.drawable.ic_launcher, I_MyAttention.class));
		add(new FuncItem(context.getString(R.string.setting),
				R.drawable.ic_launcher, I_Setting.class));
		add(new FuncItem("注销", R.drawable.ic_launcher, null));
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
