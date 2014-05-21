package android.oldfeel.yanzhuang.adapter;

import java.util.List;

import android.content.Context;
import android.oldfeel.yanzhuang.R;
import android.oldfeel.yanzhuang.base.BaseBaseAdapter;
import android.oldfeel.yanzhuang.item.CommentItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 评论列表
 * 
 * @author oldfeel
 * 
 */
public class CommentListAdapter extends BaseBaseAdapter<CommentItem> {

	public CommentListAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View view) {
		CommentItem item = getItem(position);
		view = inflater.inflate(R.layout.comment_list_item, null);
		ImageView ivAvatar = getImageView(view, R.id.comment_list_item_avatar);
		TextView tvName = getTextView(view, R.id.comment_list_item_name);
		TextView tvContent = getTextView(view, R.id.comment_list_item_content);
		TextView tvTime = getTextView(view, R.id.comment_list_item_time);
		Button btnApproval = getButton(view, R.id.comment_list_item_approval);
		Button btnOpposition = getButton(view,
				R.id.comment_list_item_opposition);
		RatingBar rbScore = (RatingBar) view
				.findViewById(R.id.comment_list_item_score);
		imageLoader.displayImage(item.getAvatar(), ivAvatar, options);
		tvName.setText(item.getName());
		tvContent.setText(item.getContent());
		tvTime.setText(item.getTime());
		btnApproval.setText(item.getApporval() + "");
		btnOpposition.setText(item.getOpposition() + "");
		rbScore.setRating(item.getScore());
		return view;
	}

	@Override
	public void addResult(int page, String result) {
		super.addResult(page, result);
		List<CommentItem> list = new Gson().fromJson(array,
				new TypeToken<List<CommentItem>>() {
				}.getType());
		addAll(list);
	}
}
