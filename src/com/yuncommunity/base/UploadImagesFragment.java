package com.yuncommunity.base;

import java.io.File;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oldfeel.base.BaseFragment;
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
import com.yuncommunity.adapter.UploadImageAdapter;
import com.yuncommunity.conf.Constant;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.interfaces.UploadImagesListener;
import com.yuncommunity.item.UploadImageItem;

/**
 * 上传图片的fragment的基类
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月15日
 */
public class UploadImagesFragment extends BaseFragment {
	private HorizontalListView hlvImages;
	private UploadImageAdapter imageAdapter;
	private File protraitFile;
	private UploadManager uploadManager = new UploadManager();
	private boolean isDestory;
	private UploadImagesListener uploadImageListener;
	private String uploadImages;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.upload_images_fragment, null);
		hlvImages = (HorizontalListView) view
				.findViewById(R.id.upload_image_list);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		imageAdapter = new UploadImageAdapter(getActivity(), this);
		hlvImages.setAdapter(imageAdapter);
	}

	/**
	 * 添加图片
	 */
	public void getImage() {
		new AlertDialog.Builder(getActivity())
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
		String cropFileName = LoginInfo.getInstance(getActivity()).getUserId()
				+ "_" + timeStamp + ".jpg";
		// 裁剪头像的绝对路径
		String protraitPath = Constant.FILE_SAVEPATH + "/" + cropFileName;
		protraitFile = new File(protraitPath);
		Uri origUri = Uri.fromFile(protraitFile);
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

	public boolean isHaveImage() {
		if (imageAdapter.getCount() > 1) {
			getToken();
			return true;
		}
		return false;
	}

	/**
	 * 开始上传图片
	 */
	private void getToken() {
		DialogUtil.getInstance().showPd(getActivity(), "正在上传图片...");
		uploadImages = imageAdapter.getUploadImages();
		NetUtil netUtil = new NetUtil(getActivity(), JsonApi.UPTOKEN);
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
		if (isDestory) {
			return;
		}
		if (imageAdapter.getCount() > 1) {
			UploadImageItem item = imageAdapter.getItem(0);
			imageAdapter.remove(0);
			if (item.getFile() == null || item.getFile().length() == 0) {
				uploadImage(uptoken);
				return;
			}
			uploadManager.put(item.getFile(), item.getKey(), uptoken,
					new UpCompletionHandler() {

						@Override
						public void complete(String key, ResponseInfo info,
								JSONObject response) {
							uploadImage(uptoken);
						}
					}, null);
		} else {
			DialogUtil.getInstance().cancelPd();
			if (uploadImageListener != null) {
				uploadImageListener.onComplete();
			}
		}
	}

	public void setOnUploadImageListener(
			UploadImagesListener uploadImagesListener) {
		this.uploadImageListener = uploadImagesListener;
	}

	public String getUploadImages() {
		return uploadImages;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case ImageUtil.REQUEST_CODE_GETIMAGE_BYCAMERA:
			imageAdapter.add(protraitFile, protraitFile.getName());
			break;
		case ImageUtil.REQUEST_CODE_GETIMAGE_BYSDCARD:
			Uri uri = data.getData();
			String path = ImageUtil.getAbsolutePathFromNoStandardUri(uri);
			if (StringUtil.isEmpty(path)) {
				path = ImageUtil.getAbsoluteImagePath(getActivity(), uri);
			}
			if (StringUtil.isEmpty(path)) {
				path = ImageUtil.getImagePath(getActivity(), uri);
			}
			initImageTemp();
			if (!StringUtil.isEmpty(path)) {
				File file = new File(path);
				if (file.length() != 0) {
					imageAdapter.add(file, protraitFile.getName());
				} else {
					showToast("加载图片失败,可能是因为媒体库数据没有更新");
				}
			} else {
				showToast("加载图片失败");
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
		DialogUtil.getInstance().cancelPd();
		isDestory = true;
		super.onDestroy();
	}
}
