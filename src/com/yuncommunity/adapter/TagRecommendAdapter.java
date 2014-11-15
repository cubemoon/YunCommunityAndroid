package com.yuncommunity.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.oldfeel.base.BaseBaseAdapter;
import com.yuncommunity.R;
import com.yuncommunity.item.TagItem;

/**
 * 推荐标签适配器
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月15日
 */
public class TagRecommendAdapter extends BaseBaseAdapter<TagItem> {

	public TagRecommendAdapter(Context context) {
		super(context);
		String[] tags = context.getResources().getStringArray(
				R.array.recommend_tag);
		for (String string : tags) {
			add(new TagItem(string, 0, 0));
		}
	}

	@Override
	public View getView(int position, View view) {
		TagItem item = getItem(position);
		view = inflater.inflate(R.layout.single_text_light, null);
		TextView tvName = getTextView(view, R.id.text);
		tvName.setSingleLine(false);
		tvName.setText(item.getName());
		return view;
	}

}
