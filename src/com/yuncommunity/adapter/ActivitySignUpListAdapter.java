package com.yuncommunity.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oldfeel.base.BaseBaseAdapter;
import com.oldfeel.utils.LogUtil;
import com.oldfeel.utils.StringUtil;
import com.yuncommunity.R;
import com.yuncommunity.item.ActivitySignUpItem;
import com.yuncommunity.utils.Utils;

/**
 * 活动报名者适配器
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年7月27日
 */
public class ActivitySignUpListAdapter extends
		BaseBaseAdapter<ActivitySignUpItem> {

	public ActivitySignUpListAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View view) {
		view = inflater.inflate(R.layout.activity_sign_up_list_item, null);
		final ActivitySignUpItem item = getItem(position);
		ImageView ivAvatar = getImageView(view,
				R.id.activity_sign_up_list_item_avatar);
		TextView tvName = getTextView(view,
				R.id.activity_sign_up_list_item_name);
		TextView tvPhone = getTextView(view,
				R.id.activity_sign_up_list_item_phone);
		TextView tvTime = getTextView(view,
				R.id.activity_sign_up_list_item_time);
		TextView tvEmail = getTextView(view,
				R.id.activity_sign_up_list_item_email);
		TextView tvCount = getTextView(view,
				R.id.activity_sign_up_list_item_count);
		TextView tvRemark = getTextView(view,
				R.id.activity_sign_up_list_item_remark);
		imageLoader.displayImage(item.getAvatar(), ivAvatar, options);
		tvName.setText(item.getName());
		tvPhone.setText(item.getPhone());
		tvTime.setText(item.getTime());
		tvEmail.setText("(" + item.getEmail() + ")");
		tvCount.setText(item.getAdultcount()
				+ context.getString(R.string.adult_count)
				+ item.getChildcount()
				+ context.getString(R.string.child_count));
		tvRemark.setText(item.getRemark());
		tvPhone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (StringUtil.isMobileNO(item.getPhone())) {
					Utils.call(context, item.getPhone());
				} else {
					LogUtil.showLog("have no phone");
				}
			}
		});
		return view;
	}

	@Override
	public void addResult(int page, String result) {
		super.addResult(page, result);
		List<ActivitySignUpItem> list = new Gson().fromJson(array,
				new TypeToken<List<ActivitySignUpItem>>() {
				}.getType());
		addAll(list);
	}
}
