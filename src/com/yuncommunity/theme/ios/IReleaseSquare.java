package com.yuncommunity.theme.ios;

import java.io.File;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

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

	/**
	 * 上传照片
	 * 
	 * @param uptoken
	 */
	protected void uploadImage(final String uptoken) {
		if (adapter.getCount() > 1) {
			File file = adapter.getImageFile(0);
			adapter.remove(0);
			if (file == null) {
				uploadImage(uptoken);
				return;
			}
			uploadManager.put(file, file.getName(), uptoken,
					new UpCompletionHandler() {

						@Override
						public void complete(String key, ResponseInfo info,
								JSONObject response) {
							uploadImage(uptoken);
						}
					}, null);
		} else {
			DialogUtil.getInstance().cancelPd();
			submitSpeak();
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
		intent.putExtra(MediaStore.EXTRA_OUTPUT, initImageTemp());
		startActivityForResult(intent, ImageUtil.REQUEST_CODE_GETIMAGE_BYCAMERA);
	}

	/**
	 * 获取上传照片的绝对路径
	 * 
	 * @return
	 */
	private Uri initImageTemp() {
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
			Uri uri = data.getData();
			String path = ImageUtil.getAbsolutePathFromNoStandardUri(uri);
			if (StringUtil.isEmpty(path)) {
				path = ImageUtil.getAbsoluteImagePath(this, uri);
			}
			initImageTemp();
			File file = new File(path);
			file.renameTo(protraitFile);
			addImage(origUri);
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
