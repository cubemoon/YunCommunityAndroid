package android.oldfeel.yanzhuang.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.oldfeel.yanzhuang.R;
import android.oldfeel.yanzhuang.app.JsonApi;
import android.oldfeel.yanzhuang.app.PersonInfo;
import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.base.BaseBaseAdapter;
import android.oldfeel.yanzhuang.item.UserItem;
import android.oldfeel.yanzhuang.util.LogUtil;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.oldfeel.yanzhuang.util.NetUtil.RequestStringListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 用户列表适配器
 * 
 * @author oldfeel
 * 
 */
public class UserListAdapter extends BaseBaseAdapter<UserItem> {

	public UserListAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View view) {
		final UserItem item = getItem(position);
		view = inflater.inflate(R.layout.user_list_item, null);
		ImageView ivAvatar = getImageView(view, R.id.user_list_item_avatar);
		TextView tvName = getTextView(view, R.id.user_list_item_name);
		TextView tvLastActivity = getTextView(view,
				R.id.user_list_item_lastactivity);
		final Button btnFollowing = getButton(view,
				R.id.user_list_item_following);
		imageLoader.displayImage(item.getAvatar(), ivAvatar, options);
		tvName.setText(item.getName());
		tvLastActivity.setText(item.getLastActivity());
		showButton(btnFollowing, item);
		btnFollowing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isFollowing(item, btnFollowing);
			}
		});
		return view;
	}

	private void showButton(Button btnFollowing, UserItem item) {
		if (!item.isFollowing()) { // 未关注
			btnFollowing.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.ic_following, 0, 0, 0);
			btnFollowing.setText("关注");
		}
		if (item.isFollowing() && item.isFans()) { // 互相关注
			btnFollowing.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.ic_following_fans, 0, 0, 0);
			btnFollowing.setText("已关注");
		}
		if (item.isFollowing() && !item.isFans()) { // 单向关注
			btnFollowing.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.ic_followed, 0, 0, 0);
			btnFollowing.setText("已关注");
		}
	}

	/**
	 * 关注或询问是否取消关注
	 * 
	 * @param item
	 * @param btnFollowing
	 */
	protected void isFollowing(final UserItem item, final Button btnFollowing) {
		if (btnFollowing.getText().equals("已关注")) {
			new AlertDialog.Builder(context)
					.setTitle("确定要取消关注" + item.getName())
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									following(item, btnFollowing, false);
								}
							}).setNegativeButton("取消", null).show();
		} else {
			following(item, btnFollowing, true);
		}
	}

	/**
	 * 
	 * @param item
	 * @param btnFollowing
	 * @param isFollowing
	 *            true 为关注,false为取消关注
	 */
	private void following(UserItem item, Button btnFollowing,
			boolean isFollowing) {
		NetUtil netUtil = new NetUtil((BaseActivity) context,
				JsonApi.USER_FOLLOWING);
		netUtil.setParams("userid", PersonInfo.getInstance(context).getUserid());
		netUtil.setParams("targetid", item.getUserid());
		netUtil.setParams("isfollowing", isFollowing);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				LogUtil.showLog(result);
			}
		});
		item.setFollowing(isFollowing);
		showButton(btnFollowing, item);
	}

	@Override
	public void addResult(int page, String result) {
		super.addResult(page, result);
		List<UserItem> list = new Gson().fromJson(array,
				new TypeToken<List<UserItem>>() {
				}.getType());
		addAll(list);
	}
}
