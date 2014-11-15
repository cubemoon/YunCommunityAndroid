package com.yuncommunity.list;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.StringUtil;
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
				R.layout.square_comment_header, null);
		ImageView ivAvatar = getImageView(view,
				R.id.square_comment_header_avatar);
		TextView tvName = getTextView(view, R.id.square_comment_header_name);
		TextView tvTime = getTextView(view, R.id.square_comment_header_time);
		TextView tvDesc = getTextView(view, R.id.square_comment_header_desc);
		GridView gvImages = (GridView) view
				.findViewById(R.id.square_comment_header_images);
		imageLoader.displayImage(item.getUserInfo().getAvatar(), ivAvatar,
				options);
		tvName.setText(item.getUserInfo().getName());
		tvTime.setText(item.getTime());
		tvDesc.setText(item.getDescription());

		if (StringUtil.isEmpty(item.getImage())) {
			gvImages.setVisibility(View.GONE);
		} else {
			gvImages.setVisibility(View.VISIBLE);
			SquareImagesAdapter adapter = new SquareImagesAdapter(
					getActivity(), item.getImage());
			gvImages.setAdapter(adapter);
		}
		return view;
	}

	@Override
	public void initAdapter() {
		adapter = new SquareCommentAdapter(getActivity());
	}

}
