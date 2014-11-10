package com.yuncommunity.theme.ios;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oldfeel.utils.DialogUtil;
import com.oldfeel.utils.ImageUtil;
import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.oldfeel.utils.StringUtil;
import com.oldfeel.view.HorizontalListView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.yuncommunity.R;
import com.yuncommunity.conf.Constant;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.theme.ios.adapter.IUploadImageAdapter;
import com.yuncommunity.theme.ios.base.IBaseActivity;

/**
 * 发布广场信息
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月2日
 */
@SuppressLint("ViewHolder")
public class IReleaseSquare extends IBaseActivity {
	private EditText etSpeak;
	private HorizontalListView hlvImages;
	private IUploadImageAdapter adapter;

	private Uri origUri;
	private File protraitFile;
	private String protraitPath;
	private UploadManager uploadManager = new UploadManager();
	private String uploadImages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_release_square);
		etSpeak = (EditText) findViewById(R.id.i_release_square_speak);
		hlvImages = (HorizontalListView) findViewById(R.id.i_release_square_image_list);
		adapter = new IUploadImageAdapter(this);
		hlvImages.setAdapter(adapter);
		showRight();
		btnRight.setText("完成");
		btnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adapter.getCount() > 1) {
					getToken();
				} else {
					submitSpeak();
				}
			}
		});
	}

	/**
	 * 提交说说
	 */
	protected void submitSpeak() {
		NetUtil netUtil = new NetUtil(IReleaseSquare.this,
				JsonApi.INFORMATION_RELEASE);
		netUtil.setParams("infotype", Constant.TYPE_SQUARE);
		netUtil.setParams("description", getString(etSpeak));
		netUtil.setParams("image", uploadImages);
		netUtil.postRequest("正在说说...", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					showToast("说说成功");
					finish();
				} else {
					showToast(JsonUtil.getData(result));
				}
			}
		});
	}

	/**
	 * 开始上传图片
	 */
	private void getToken() {
		DialogUtil.getInstance().showPd(this, "正在上传图片...");
		uploadImages = adapter.getUploadImages();
		NetUtil netUtil = new NetUtil(IReleaseSquare.this, JsonApi.UPTOKEN);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					uploadImage(JsonUtil.getData(result));
				} else {
					DialogUtil.getInstance().cancelPd();
					showToast(JsonUtil.getData(result));
				}
			}
		});
	}

	protected void uploadImage(final String uptoken) {
		if (adapter.getCount() > 1) {
			File file = adapter.getImageFile(0);
			uploadManager.put(file, file.getName(), uptoken,
					new UpCompletionHandler() {

						@Override
						public void complete(String key, ResponseInfo info,
								JSONObject response) {
							adapter.remove(0);
							uploadImage(uptoken);
						}
					}, null);
		} else {
			DialogUtil.getInstance().cancelPd();
			submitSpeak();
		}
	}

	class ImageAdapter extends BaseAdapter {
		ImageLoader imageLoader = ImageLoader.getInstance();
		private Context context;
		private List<Uri> list = new ArrayList<Uri>();

		public ImageAdapter(Context context) {
			this.context = context;
		}

		public String getUploadImages() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < getCount() - 1; i++) {
				File file = getImageFile(i);
				if (i == 0) {
					sb.append(file.getName());
				} else {
					sb.append("," + file.getName());
				}
			}
			return sb.toString();
		}

		public void remove(int i) {
			list.remove(0);
			notifyDataSetChanged();
		}

		public File getImageFile(int position) {
			Uri uri = getItem(position);
			String path = ImageUtil.getAbsolutePathFromNoStandardUri(uri);
			if (StringUtil.isEmpty(path)) {
				path = ImageUtil.getAbsoluteImagePath(context, uri);
			}
			return new File(path);
		}

		@Override
		public int getCount() {
			return list.size() + 1;
		}

		@Override
		public Uri getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			view = LayoutInflater.from(context).inflate(
					R.layout.i_release_square_image_item, parent, false);
			ImageView ivImage = (ImageView) view
					.findViewById(R.id.i_release_square_image);

			if (position == getCount() - 1) {
				ivImage.setImageResource(R.drawable.ic_launcher);
				ivImage.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						((IReleaseSquare) context).getImage();
					}
				});
			} else {
				imageLoader.displayImage(getItem(position).toString(), ivImage);
			}
			return view;
		}

		public void add(Uri uri) {
			list.add(uri);
			notifyDataSetChanged();
		}

	}

	/**
	 * 添加图片
	 */
	public void getImage() {
		new AlertDialog.Builder(IReleaseSquare.this)
				.setTitle("添加图片")
				.setItems(R.array.add_image_type,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {
								case 0: // 手机拍照
									startActionCamera();
									break;
								case 1: // 从相册选择
									startImagePick();
									break;
								default:
									break;
								}
								dialog.cancel();
							}
						}).show();
	}

	/**
	 * 相机拍照
	 */
	protected void startActionCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getCameraTempFile());
		startActivityForResult(intent, ImageUtil.REQUEST_CODE_GETIMAGE_BYCAMERA);
	}

	// 拍照保存的绝对路径
	private Uri getCameraTempFile() {
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(Constant.FILE_SAVEPATH);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
		} else {
			showToast("无法保存上传的头像，请检查SD卡是否挂载");
			return null;
		}
		String timeStamp = StringUtil.getTimeStamp();
		// 照片命名
		String cropFileName = getUserId() + "_" + timeStamp + ".jpg";
		// 裁剪头像的绝对路径
		protraitPath = Constant.FILE_SAVEPATH + "/" + cropFileName;
		protraitFile = new File(protraitPath);
		origUri = Uri.fromFile(protraitFile);
		return origUri;
	}

	/**
	 * 选择图片
	 * 
	 * @param output
	 */
	protected void startImagePick() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		startActivityForResult(Intent.createChooser(intent, "选择图片"),
				ImageUtil.REQUEST_CODE_GETIMAGE_BYSDCARD);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case ImageUtil.REQUEST_CODE_GETIMAGE_BYCAMERA:
			addImage(origUri);
			break;
		case ImageUtil.REQUEST_CODE_GETIMAGE_BYSDCARD:
			addImage(data.getData());
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 添加图片到图片列表中
	 * 
	 * @param uri
	 */
	private void addImage(Uri uri) {
		adapter.add(uri);
	}
}
