package com.yuncommunity.theme.ios.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.oldfeel.base.BaseBaseAdapter;
import com.yuncommunity.R;
import com.yuncommunity.item.TagItem;

/**
 * 广场头部的功能菜单适配器
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class ISquareHeaderAdapter extends BaseBaseAdapter<TagItem> {

	public ISquareHeaderAdapter(Context context) {
		super(context);
		add(new TagItem(context.getString(R.string.property),
				R.drawable.ic_launcher, TagItem.TAGID_PROPERTY));
		add(new TagItem(context.getString(R.string.repair),
				R.drawable.ic_launcher, TagItem.TAGID_REPAIR));
		add(new TagItem(context.getString(R.string.express),
				R.drawable.ic_launcher, TagItem.TAGID_EXPRESS));
		add(new TagItem(context.getString(R.string.washing),
				R.drawable.ic_launcher, TagItem.TAGID_WASHING));
	}

	@Override
	public View getView(int position, View view) {
		TagItem item = getItem(position);
		view = inflater.inflate(R.layout.i_square_header_item, null);
		TextView tvName = (TextView) view
				.findViewById(R.id.i_square_header_item_name);
		tvName.setText(item.getName());
		tvName.setCompoundDrawablesWithIntrinsicBounds(0, item.getIconResId(),
				0, 0);
		return view;
	}
}
