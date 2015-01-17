package com.yuncommunity.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oldfeel.base.BaseBaseAdapter;
import com.yuncommunity.R;
import com.yuncommunity.item.TagItem;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月15日
 */
public class TagHistoryAdapter extends BaseBaseAdapter<TagItem> {

	public TagHistoryAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View view) {
		TagItem item = getItem(position);
		view = inflater.inflate(R.layout.single_text_light, null);
		TextView tvName = getTextView(view, R.id.text);
		tvName.setText(item.getName());
		return view;
	}

	@Override
	public void addResult(int page, String result) {
		super.addResult(page, result);
		List<TagItem> list = new Gson().fromJson(array,
				new TypeToken<List<TagItem>>() {
				}.getType());
		addAll(list);
	}
}
