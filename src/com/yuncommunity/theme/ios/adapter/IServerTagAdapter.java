package com.yuncommunity.theme.ios.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.oldfeel.base.BaseBaseAdapter;
import com.yuncommunity.R;
import com.yuncommunity.item.TagItem;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class IServerTagAdapter extends BaseBaseAdapter<TagItem> {

	public IServerTagAdapter(Context context) {
		super(context);
		add(R.string.takeout, R.drawable.ic_launcher, TagItem.TAGID_TAKEOUT);
		add(R.string.life_distribution, R.drawable.ic_launcher,
				TagItem.TAGID_LIFE_DISTRIBUTION);
		add(R.string.fruits, R.drawable.ic_launcher, TagItem.TAGID_FRUITS);
		add(R.string.housekeeping_service, R.drawable.ic_launcher,
				TagItem.TAGID_HOUSEKEEPING_SERVICE);
	}

	private void add(int nameResId, int iconResId, long tagId) {
		add(new TagItem(context.getString(nameResId), iconResId, tagId));
	}

	@Override
	public View getView(int position, View view) {
		TagItem item = getItem(position);
		view = inflater.inflate(R.layout.single_text_light, null);
		TextView tvName = (TextView) view.findViewById(R.id.text);
		tvName.setText(item.getName());
		tvName.setCompoundDrawablesWithIntrinsicBounds(item.getIconResId(), 0,
				0, 0);
		return view;
	}

}
