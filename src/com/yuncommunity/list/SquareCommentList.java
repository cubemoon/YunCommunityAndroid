package com.yuncommunity.list;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.StringUtil;
import com.oldfeel.view.HorizontalListView;
import com.yuncommunity.R;
import com.yuncommunity.adapter.SquareCommentAdapter;
import com.yuncommunity.adapter.SquareImagesAdapter;
import com.yuncommunity.base.CustomBaseListFragment;
import com.yuncommunity.item.SquareItem;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月10日
 */
public class SquareCommentList extends CustomBaseListFragment {
	private SquareItem squareItem;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;

	public static SquareCommentList newInstance(NetUtil netUtil,
			SquareItem squareItem) {
		SquareCommentList fragment = new SquareCommentList();
		fragment.netUtil = netUtil;
		fragment.squareItem = squareItem;
		return fragment;
	}

	@Override
	public void onItemClick(int position) {
	}

	@Override
	public void initHeaderView() {
		getListView().addHeaderView(getHeaderView());
	}

	private View getHeaderView() {
		int id = R.drawable.ic_launcher;
		options = new DisplayImageOptions.Builder().showImageForEmptyUri(id)
				.showImageOnFail(id).cacheInMemory(true).cacheOnDisc(true)
				.build();
		SquareItem item = squareItem;
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.i_square_item, null);
		ImageView ivAvatar = getImageView(view, R.id.i_square_item_avatar);
		TextView tvName = getTextView(view, R.id.i_square_item_name);
		TextView tvTime = getTextView(view, R.id.i_square_item_time);
		TextView tvDesc = getTextView(view, R.id.i_square_item_desc);
		TextView tvComment = getTextView(view, R.id.i_square_item_comment);
		HorizontalListView hlvImages = (HorizontalListView) view
				.findViewById(R.id.i_square_item_images);
		imageLoader.displayImage(item.getUserInfo().getAvatar(), ivAvatar,
				options);
		tvName.setText(item.getUserInfo().getName());
		tvTime.setText(item.getTime());
		tvDesc.setText(item.getDescription());
		tvComment.setText(item.getCommentCount() + "");

		if (StringUtil.isEmpty(item.getImage())) {
			hlvImages.setVisibility(View.GONE);
		} else {
			hlvImages.setVisibility(View.VISIBLE);
			SquareImagesAdapter adapter = new SquareImagesAdapter(
					getActivity(), item.getImage());
			hlvImages.setAdapter(adapter);
		}
		return view;
	}

	@Override
	public void initAdapter() {
		adapter = new SquareCommentAdapter(getActivity());
	}

}
