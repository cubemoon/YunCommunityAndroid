package com.yuncommunity.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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

/**
 * 上传图片的fragment的基类
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月15日
 */
public class UploadImagesFragment extends BaseFragment {
	protected static final int COMP_OK = 1; // 压缩完成
	protected static final int COMP_FAIL = 2; // 压缩失败
	private HorizontalListView hlvImages;
	private UploadImageAdapter imageAdapter;
	private File protraitFile;
	private UploadManager uploadManager = new UploadManager();
	private boolean isDestory;
	private UploadImagesListener uploadImageListener;
	private String uploadImages;
	private Uri origUri;
	private int degree;

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
			File file = imageAdapter.getItem(0);
			imageAdapter.remove(0);
			if (file == null || file.length() == 0) {
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
			// 有的相机拍照后图片旋转,计算出旋转角度
			degree = ImageUtil
					.readPictureDegree(protraitFile.getAbsolutePath());
			addImageToAdapter(origUri);
			break;
		case ImageUtil.REQUEST_CODE_GETIMAGE_BYSDCARD:
			// 从相册选择的图片不旋转
			degree = 0;
			initImageTemp();
			addImageToAdapter(data.getData());
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void addImageToAdapter(final Uri uri) {
		DialogUtil.getInstance().showPd(getActivity(), "正在压缩图片...");
		new Thread() {
			public void run() {
				try {
					Bitmap bitmap = MediaStore.Images.Media.getBitmap(
							getActivity().getContentResolver(), uri);
					if (degree != 0) {
						bitmap = ImageUtil.rotaingImageView(degree, bitmap);
					}
					ImageUtil.compAndSaveImage(getActivity(),
							protraitFile.getAbsolutePath(), bitmap);
					handler.sendEmptyMessage(COMP_OK);
				} catch (FileNotFoundException e) {
					handler.sendEmptyMessage(COMP_FAIL);
					e.printStackTrace();
				} catch (IOException e) {
					handler.sendEmptyMessage(COMP_FAIL);
					e.printStackTrace();
				}
			};
		}.start();
	}

	/**
	 * 压缩图片
	 */
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			DialogUtil.getInstance().cancelPd();
			switch (msg.what) {
			case COMP_OK:
				imageAdapter.add(protraitFile);
				break;
			case COMP_FAIL:
				showToast("压缩失败");
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onDestroy() {
		DialogUtil.getInstance().cancelPd();
		isDestory = true;
		super.onDestroy();
	}
}
