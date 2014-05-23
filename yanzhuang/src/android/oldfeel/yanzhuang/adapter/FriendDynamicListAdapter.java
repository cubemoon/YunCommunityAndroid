package android.oldfeel.yanzhuang.adapter;

import java.util.List;

import android.content.Context;
import android.oldfeel.yanzhuang.R;
import android.oldfeel.yanzhuang.base.BaseBaseAdapter;
import android.oldfeel.yanzhuang.item.FriendDynamicListItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 朋友的动态信息适配器
 * 
 * @author oldfeel
 * 
 */
public class FriendDynamicListAdapter extends
		BaseBaseAdapter<FriendDynamicListItem> {

	public FriendDynamicListAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View view) {
		FriendDynamicListItem item = getItem(position);
		view = inflater.inflate(R.layout.friend_dynamic_list_item, null);
		ImageView ivAvatar = getImageView(view,
				R.id.friend_dynamic_list_item_avatar);
		TextView tvName = getTextView(view, R.id.friend_dynamic_list_item_name);
		TextView tvExplanation = getTextView(view,
				R.id.friend_dynamic_list_item_explanation);
		TextView tvDesc = getTextView(view, R.id.friend_dynamic_list_item_desc);
		TextView tvChild = getTextView(view,
				R.id.friend_dynamic_list_item_child);
		TextView tvTime = getTextView(view, R.id.friend_dynamic_list_item_time);
		imageLoader.displayImage(item.getAvatar(), ivAvatar, options);
		tvName.setText(item.getName());
		tvExplanation.setText(item.getExplanation());
		tvDesc.setText(item.getDesc());
		tvChild.setText(item.getChild());
		tvTime.setText(item.getTime());
		return view;
	}

	@Override
	public void addResult(int page, String result) {
		super.addResult(page, result);
		List<FriendDynamicListItem> list = new Gson().fromJson(array,
				new TypeToken<List<FriendDynamicListItem>>() {
				}.getType());
		addAll(list);
	}
}
