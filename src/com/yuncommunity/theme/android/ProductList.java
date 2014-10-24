package com.yuncommunity.theme.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.oldfeel.base.BaseActivity;
import com.oldfeel.utils.JSONUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.R;
import com.yuncommunity.theme.android.conf.JsonApi;
import com.yuncommunity.theme.android.conf.LoginInfo;
import com.yuncommunity.theme.android.item.InformationItem;
import com.yuncommunity.theme.android.item.ProductItem;
import com.yuncommunity.theme.android.list.ProductListFragment;

/**
 * 产品列表
 * 
 * @author oldfeel
 * 
 */
public class ProductList extends BaseActivity {
	private InformationItem item;
	private ProductListFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_frame);
		item = (InformationItem) getIntent().getSerializableExtra("item");
		fragment = ProductListFragment.newInstance(getNetUtil());
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
	}

	private NetUtil getNetUtil() {
		NetUtil netUtil = new NetUtil(ProductList.this, JsonApi.PRODUCT_LIST);
		netUtil.setParams("userid",
				LoginInfo.getInstance(getApplicationContext()).getUserId());
		netUtil.setParams("informationid", item.getInformationid());
		return netUtil;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.product_list, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add:
			addProduct();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 添加产品
	 */
	private void addProduct() {
		View view = LayoutInflater.from(ProductList.this).inflate(
				R.layout.product_add, null);
		final EditText etName = (EditText) view
				.findViewById(R.id.product_add_name);
		final EditText etDesc = (EditText) view
				.findViewById(R.id.product_add_desc);
		ImageView ivImage = (ImageView) view
				.findViewById(R.id.product_add_image);
		new AlertDialog.Builder(ProductList.this)
				.setView(view)
				.setTitle(getText(R.string.add_product))
				.setPositiveButton(getText(R.string.confirm),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								submitAdd(etName, etDesc);
							}
						}).setNegativeButton(getText(R.string.cancel), null)
				.show();
	}

	protected void submitAdd(EditText etName, EditText etDesc) {
		NetUtil netUtil = new NetUtil(ProductList.this, JsonApi.PRODUCT_ADD);
		netUtil.setParams("informationid", item.getInformationid());
		netUtil.setParams("name", getString(etName));
		netUtil.setParams("description", getString(etDesc));
		netUtil.setParams("image", "");
		netUtil.postRequest(String.valueOf(getText(R.string.adding_product)),
				new RequestStringListener() {

					@Override
					public void onComplete(String result) {
						if (JSONUtil.isSuccess(result)) {
							showToast(String
									.valueOf(getText(R.string.added_successfully)));
							fragment.add(new Gson().fromJson(
									JSONUtil.getData(result).toString(),
									ProductItem.class));
						} else {
							showToast(String
									.valueOf(getText(R.string.added_failed)));
						}
					}
				});
	}
}
