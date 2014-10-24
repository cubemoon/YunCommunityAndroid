package com.yuncommunity.adapter;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oldfeel.base.BaseBaseAdapter;
import com.oldfeel.utils.DialogUtil;
import com.oldfeel.utils.JSONUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.R;
import com.yuncommunity.app.JsonApi;
import com.yuncommunity.app.LoginInfo;
import com.yuncommunity.item.ProductItem;

/**
 * 产品列表适配器
 * 
 * @author oldfeel
 * 
 */
public class ProductListAdapter extends BaseBaseAdapter<ProductItem> {

	public ProductListAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View view) {
		final ProductItem item = getItem(position);
		view = inflater.inflate(R.layout.product_list_item, null);
		ImageView ivImage = getImageView(view, R.id.product_add_image);
		TextView tvName = getTextView(view, R.id.product_list_item_name);
		TextView tvDesc = getTextView(view, R.id.product_list_item_desc);
		ImageButton ibDelete = getImageButton(view,
				R.id.product_list_item_delete);
		TextView tvTime = getTextView(view, R.id.product_list_item_time);
		imageLoader.displayImage(item.getImage(), ivImage, options);
		tvName.setText(item.getName());
		tvDesc.setText(item.getDescription());
		tvTime.setText(item.getTime());
		ibDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isDelete(item);
			}
		});
		return view;
	}

	protected void isDelete(final ProductItem item) {
		new AlertDialog.Builder((Activity) context)
				.setTitle("确定要删除产品" + item.getName() + "吗?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						delete(item);
					}
				}).setNegativeButton("取消", null).show();
	}

	protected void delete(final ProductItem item) {
		NetUtil netUtil = new NetUtil((Activity) context,
				JsonApi.PRODUCT_DELETE);
		netUtil.setParams("userid", LoginInfo.getInstance(context).getUserId());
		netUtil.setParams("productid", item.getProductid());
		netUtil.postRequest("正在删除", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JSONUtil.isSuccess(result)) {
					DialogUtil.getInstance().showToast(context, "删除成功");
					remove(item);
				} else {
					DialogUtil.getInstance().showToast(context,
							"删除失败," + JSONUtil.getMessage(result));
				}
			}
		});
	}

	@Override
	public void addResult(int page, String result) {
		super.addResult(page, result);
		List<ProductItem> list = new Gson().fromJson(array,
				new TypeToken<List<ProductItem>>() {
				}.getType());
		addAll(list);
	}
}
