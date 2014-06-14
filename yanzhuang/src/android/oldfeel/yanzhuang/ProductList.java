package android.oldfeel.yanzhuang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.oldfeel.yanzhuang.app.Api;
import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.fragment.list.ProductListFragment;
import android.oldfeel.yanzhuang.item.InformationItem;
import android.oldfeel.yanzhuang.item.ProductItem;
import android.oldfeel.yanzhuang.util.JSONUtil;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.oldfeel.yanzhuang.util.NetUtil.RequestStringListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

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
		NetUtil netUtil = new NetUtil(ProductList.this, Api.PRODUCT_LIST);
		netUtil.setParams("userid", getUserid());
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
		new AlertDialog.Builder(ProductList.this).setView(view)
				.setTitle("添加产品")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						submitAdd(etName, etDesc);
					}
				}).setNegativeButton("取消", null).show();
	}

	protected void submitAdd(EditText etName, EditText etDesc) {
		NetUtil netUtil = new NetUtil(ProductList.this, Api.PRODUCT_ADD);
		netUtil.setParams("informationid", item.getInformationid());
		netUtil.setParams("name", getString(etName));
		netUtil.setParams("description", getString(etDesc));
		netUtil.setParams("image", "");
		netUtil.postRequest("正在添加产品", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JSONUtil.isSuccess(result)) {
					showToast("添加成功");
					fragment.add(new Gson().fromJson(JSONUtil.getData(result)
							.toString(), ProductItem.class));
				} else {
					showToast("添加失败");
				}
			}
		});
	}
}
