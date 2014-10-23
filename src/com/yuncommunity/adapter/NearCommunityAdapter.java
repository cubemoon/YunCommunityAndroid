package com.yuncommunity.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oldfeel.base.BaseBaseAdapter;
import com.yuncommunity.R;
import com.yuncommunity.item.ActivitySignUpItem;
import com.yuncommunity.item.CommunityItem;

/**
 * 周边小区列表的适配器
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月22日
 */
public class NearCommunityAdapter extends BaseBaseAdapter<CommunityItem> {

	public NearCommunityAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View view) {
		CommunityItem item = getItem(position);
		view = inflater.inflate(R.layout.near_community_item, null);
		TextView tvName = getTextView(view, R.id.near_community_name);
		TextView tvNumber = getTextView(view, R.id.near_community_number);
		TextView tvDistance = getTextView(view, R.id.near_community_distance);
		tvName.setText(item.getName());
		tvNumber.setText(item.getPeopleCount() + "人");
		tvDistance.setText(item.getDistance());
		return view;
	}

	@Override
	public void addResult(int page, String result) {
		super.addResult(page, result);
		List<CommunityItem> list = new Gson().fromJson(array,
				new TypeToken<List<CommunityItem>>() {
				}.getType());
		addAll(list);
	}
}
