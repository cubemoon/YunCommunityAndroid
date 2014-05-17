package android.oldfeel.yanzhuang.adapter;

import android.content.Context;
import android.oldfeel.yanzhuang.R;
import android.oldfeel.yanzhuang.base.BaseBaseAdapter;
import android.oldfeel.yanzhuang.item.ActivityListItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 活动列表适配器
 * 
 * @author oldfeel
 * 
 */
public class ActivityListAdapter extends BaseBaseAdapter<ActivityListItem> {

	public ActivityListAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View view) {
		ActivityListItem item = getItem(position);
		view = inflater.inflate(R.layout.activity_list_item, null);
		ImageView ivImage = getImageView(view, R.id.activity_list_item_image);
		TextView tvTitle = getTextView(view, R.id.activity_list_item_title);
		TextView tvDesc = getTextView(view, R.id.activity_list_item_desc);
		TextView tvEvaluation = getTextView(view,
				R.id.activity_list_item_evaluation);
		TextView tvTime = getTextView(view, R.id.activity_list_item_time);
		imageLoader.displayImage(item.getImage(), ivImage, options);
		tvTitle.setText(item.getTitle());
		tvDesc.setText(item.getDescription());
		tvEvaluation.setText(String.valueOf(item.getEvaluation()));
		tvTime.setText(item.getTime());
		return view;
	}

	@Override
	public void addResult(int page, String result) {
		super.addResult(page, result);
		// List<ActivityListItem> list = new Gson().fromJson(array,
		// new TypeToken<List<ActivityListItem>>() {
		// }.getType());
		// addAll(list);
	}
}
