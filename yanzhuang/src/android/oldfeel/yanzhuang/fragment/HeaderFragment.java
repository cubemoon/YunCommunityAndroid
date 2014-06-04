package android.oldfeel.yanzhuang.fragment;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.oldfeel.yanzhuang.R;
import android.oldfeel.yanzhuang.app.Constant;
import android.oldfeel.yanzhuang.app.JsonApi;
import android.oldfeel.yanzhuang.app.PersonInfo;
import android.oldfeel.yanzhuang.base.BaseFragment;
import android.oldfeel.yanzhuang.util.FileUtil;
import android.oldfeel.yanzhuang.util.ImageUtil;
import android.oldfeel.yanzhuang.util.JSONUtil;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.oldfeel.yanzhuang.util.NetUtil.RequestStringListener;
import android.oldfeel.yanzhuang.util.StringUtil;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 我的头像,提供更新图片的功能
 * 
 * @author oldfeel
 * 
 *         Created on: 2014年3月1日
 */
public class HeaderFragment extends BaseFragment {
	protected DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageView ivHeader;

	private final static int CROP = 200;
	private Uri origUri;
	private Uri cropUri;
	private File protraitFile;
	private Bitmap protraitBitmap;
	private String protraitPath;
	private String imageUrl;// 头像网站
	private boolean isSelf;

	public static Fragment newInstance(String imageUrl, boolean isSelf) {
		HeaderFragment fragment = new HeaderFragment();
		fragment.imageUrl = imageUrl;
		fragment.isSelf = isSelf;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.header_view, null);
		ivHeader = getImageView(view, R.id.header_image);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		int id = R.drawable.ic_launcher;
		options = new DisplayImageOptions.Builder().showImageForEmptyUri(id)
				.showImageOnFail(id).cacheInMemory(true).cacheOnDisc(true)
				.build();
		imageLoader.displayImage(imageUrl, ivHeader, options);
		ivHeader.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isSelf) {
					uploadHeader();
				}
			}
		});
	}

	/**
	 * 上传头像
	 */
	protected void uploadHeader() {
		new AlertDialog.Builder(getActivity())
				.setTitle("上传头像")
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
		intent.putExtra(MediaStore.EXTRA_OUTPUT, this.getCameraTempFile());
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
		String cropFileName = "yanzhuang_" + timeStamp + ".jpg";
		// 裁剪头像的绝对路径
		protraitPath = Constant.FILE_SAVEPATH + "/" + cropFileName;
		protraitFile = new File(protraitPath);
		cropUri = Uri.fromFile(protraitFile);
		this.origUri = this.cropUri;
		return this.cropUri;
	}

	/**
	 * 选择图片裁剪
	 * 
	 * @param output
	 */
	protected void startImagePick() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		startActivityForResult(Intent.createChooser(intent, "选择图片"),
				ImageUtil.REQUEST_CODE_GETIMAGE_BYCROP);
	}

	/**
	 * 拍照后裁剪
	 * 
	 * @param data
	 *            原始图片
	 * @param output
	 *            裁剪后图片
	 */
	private void startActionCrop(Uri data) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(data, "image/*");
		intent.putExtra("output", this.getUploadTempFile(data));
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", CROP);// 输出图片大小
		intent.putExtra("outputY", CROP);
		intent.putExtra("scale", true);// 去黑边
		intent.putExtra("scaleUpIfNeeded", true);// 去黑边
		startActivityForResult(intent, ImageUtil.REQUEST_CODE_GETIMAGE_BYSDCARD);
	}

	// 裁剪头像的绝对路径
	private Uri getUploadTempFile(Uri uri) {
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
		String thePath = ImageUtil.getAbsolutePathFromNoStandardUri(uri);

		// 如果是标准Uri
		if (StringUtil.isEmpty(thePath)) {
			thePath = ImageUtil.getAbsoluteImagePath(getActivity(), uri);
		}
		String ext = FileUtil.getFileFormat(thePath);
		ext = StringUtil.isEmpty(ext) ? "jpg" : ext;
		// 照片命名
		String cropFileName = "yanzhuang_" + timeStamp + "." + ext;
		// 裁剪头像的绝对路径
		protraitPath = Constant.FILE_SAVEPATH + "/" + cropFileName;
		protraitFile = new File(protraitPath);

		cropUri = Uri.fromFile(protraitFile);
		return this.cropUri;
	}

	/**
	 * 上传新照片
	 */
	private void uploadNewPhoto() {
		// 获取头像缩略图
		if (!StringUtil.isEmpty(protraitPath) && protraitFile.exists()) {
			protraitBitmap = ImageUtil.loadImgThumbnail(protraitPath, 200, 200);
		} else {
			showSimpleDialog("图像不存在，上传失败·");
		}
		File headerFile = new File(protraitFile.getParent() + "/avatar-"
				+ System.currentTimeMillis() + "-"
				+ PersonInfo.getInstance(getActivity()).getUserid() + ".jpg");
		protraitFile.renameTo(headerFile);
		protraitFile = headerFile;
		NetUtil netUtil = new NetUtil(getActivity(), JsonApi.UPTOKEN);
		netUtil.postRequest("正在上传...", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JSONUtil.isSuccess(result)) {
					startUpload(JSONUtil.getMessage(result));
				} else {
					showToast("获取uptoken失败");
				}
			}
		});
	}

	/**
	 * 开始上传
	 * 
	 * @param uptoken
	 */
	protected void startUpload(String uptoken) {
		NetUtil netUtil = new NetUtil(getActivity(), "");
		netUtil.postFile("正在上传头像...", protraitFile.getName(), protraitFile,
				uptoken, new RequestStringListener() {

					@Override
					public void onComplete(String result) {
						showToast("上传成功");
						ivHeader.setImageBitmap(protraitBitmap);
						PersonInfo.getInstance(getActivity()).setAvatar(
								protraitFile.getName());
						PersonInfo.update(getActivity());
					}
				});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case ImageUtil.REQUEST_CODE_GETIMAGE_BYCAMERA:
			startActionCrop(origUri);// 拍照后裁剪
			break;
		case ImageUtil.REQUEST_CODE_GETIMAGE_BYCROP:
			startActionCrop(data.getData());// 选图后裁剪
			break;
		case ImageUtil.REQUEST_CODE_GETIMAGE_BYSDCARD:
			uploadNewPhoto();// 上传新照片
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
