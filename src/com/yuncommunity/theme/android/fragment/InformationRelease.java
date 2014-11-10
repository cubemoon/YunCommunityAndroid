package com.yuncommunity.theme.android.fragment;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.oldfeel.base.BaseFragment;
import com.oldfeel.utils.ETUtil;
import com.oldfeel.utils.FileUtil;
import com.oldfeel.utils.ImageUtil;
import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.oldfeel.utils.StringUtil;
import com.oldfeel.utils.Utils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.yuncommunity.R;
import com.yuncommunity.conf.Constant;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.theme.android.MainActivity;
import com.yuncommunity.theme.android.SelectAddressBaiduMap;
import com.yuncommunity.theme.android.SelectAddressGoogleMap;
import com.yuncommunity.theme.android.dialog.LookBigImage;

/**
 * 发布信息
 * 
 * @author oldfeel
 * 
 */
public class InformationRelease extends BaseFragment implements OnClickListener {
	private static final int REQUEST_CAPTURE_RECORDER_SOUND = 11;
	private static final int REQUEST_CODE_TAKE_VIDEO = 12;
	private static final int REQUEST_SELECT_ADDRESS = 13;
	private static final String JPG = ".jpg";
	private static final String GPP = ".3gpp";
	private static final String MP4 = ".mp4";
	private EditText etTitle, etDesc, etAddress, etPhone, etTag;
	private ImageView ivImage, ivVoice, ivVideo;
	private Button btnMap;
	private double lon, lat;
	private String image, voice, video;
	private int infotype;
	private String protraitPath;
	private File protraitFile;
	private Uri cropUri;

	public static InformationRelease newInstance(int infotype) {
		InformationRelease fragment = new InformationRelease();
		fragment.infotype = infotype;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.information_release, null);
		etTitle = getEditText(view, R.id.release_title);
		etDesc = getEditText(view, R.id.release_desc);
		etAddress = getEditText(view, R.id.release_address);
		etPhone = getEditText(view, R.id.release_phone);
		etTag = getEditText(view, R.id.release_tag);
		btnMap = getButton(view, R.id.release_map);
		ivImage = getImageView(view, R.id.release_image);
		ivVoice = getImageView(view, R.id.release_voice);
		ivVideo = getImageView(view, R.id.release_video);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		btnMap.setOnClickListener(this);
		ivImage.setOnClickListener(this);
		ivVideo.setOnClickListener(this);
		ivVoice.setOnClickListener(this);
		etPhone.setText(LoginInfo.getInstance(getActivity()).getUserInfo()
				.getPhone());
	}

	public void submit() {
		if (ETUtil.isEmpty(etTitle)) {
			etTitle.setError("必填");
			return;
		}
		if (image != null || voice != null || video != null) {
			getUptoken();
		}
		NetUtil netUtil = new NetUtil(getActivity(),
				JsonApi.INFORMATION_RELEASE);
		netUtil.setParams("infotype", infotype);
		netUtil.setParams("userid", LoginInfo.getInstance(getActivity())
				.getUserInfo().getUserid());
		netUtil.setParams("title", getString(etTitle));
		netUtil.setParams("description", getString(etDesc));
		netUtil.setParams("address", getString(etAddress));
		netUtil.setParams("lon", lon);
		netUtil.setParams("lat", lat);
		netUtil.setParams("phone", getString(etPhone));
		netUtil.setParams("tags", getString(etTag));
		netUtil.setParams("image", FileUtil.getName(image));
		netUtil.setParams("voice", FileUtil.getName(voice));
		netUtil.setParams("video", FileUtil.getName(video));
		netUtil.postRequest("正在发布", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					showToast("发布成功");
					Intent intent = new Intent(getActivity(),
							MainActivity.class);
					intent.putExtra("infotype", infotype);
					intent.putExtra("result", true);
					startActivity(intent);
					getActivity().finish();
				} else {
					showToast("发布失败," + JsonUtil.getData(result));
				}
			}
		});
	}

	private void getUptoken() {
		NetUtil netUtil = new NetUtil(getActivity(), JsonApi.UPTOKEN);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					startUploadFile(JsonUtil.getData(result));
				} else {
					showToast("获取uptoken失败");
				}
			}
		});
	}

	protected void startUploadFile(String uptoken) {
		upload(image, uptoken);
		upload(voice, uptoken);
		upload(video, uptoken);
	}

	private void upload(String filePath, String uptoken) {
		if (StringUtil.isEmpty(filePath)) {
			return;
		}
		File file = new File(filePath);
		UploadManager uploadManager = new UploadManager();
		uploadManager.put(file, file.getName(), uptoken,
				new UpCompletionHandler() {

					@Override
					public void complete(String key, ResponseInfo info,
							JSONObject response) {
					}
				}, null);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.release_map:
			selectAddress();
			break;
		case R.id.release_image:
			image();
			break;
		case R.id.release_voice:
			voice();
			break;
		case R.id.release_video:
			video();
			break;
		default:
			break;
		}
	}

	private void image() {
		int itemsId = R.array.add_image_type;
		if (image != null) {
			itemsId = R.array.change_image_type;
		}
		new AlertDialog.Builder(getActivity()).setItems(itemsId,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (image == null) {
							which++;
						}
						switch (which) {
						case 0:
							lookBigImage();
							break;
						case 1:
							startActionCamera();
							break;
						case 2:
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
	 * 从相册选择
	 */
	protected void startImagePick() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		startActivityForResult(Intent.createChooser(intent, "选择图片"),
				ImageUtil.REQUEST_CODE_GETIMAGE_BYCROP);
	}

	/**
	 * 手机拍照
	 */
	protected void startActionCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, this.getCameraTempFile());
		startActivityForResult(intent, ImageUtil.REQUEST_CODE_GETIMAGE_BYCAMERA);
	}

	// 拍照保存的绝对路径
	private Uri getCameraTempFile() {
		protraitPath = getTempFile(JPG);
		protraitFile = new File(protraitPath);
		cropUri = Uri.fromFile(protraitFile);
		return this.cropUri;
	}

	/**
	 * 查看大图
	 */
	protected void lookBigImage() {
		LookBigImage dialogFragment = LookBigImage.newInstance(image);
		dialogFragment.show(getChildFragmentManager(), "image");
	}

	/**
	 * 录音
	 */
	private void voice() {
		int itemsId = R.array.add_voice_type;
		if (voice != null) {
			itemsId = R.array.change_voice_type;
		}
		new AlertDialog.Builder(getActivity()).setItems(itemsId,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (voice == null) {
							which++;
						}
						switch (which) {
						case 0:
							playVoice();
							break;
						case 1:
							startActionVoice();
						default:
							break;
						}
						dialog.cancel();
					}
				}).show();
	}

	/**
	 * 视频
	 */
	private void video() {
		int itemsId = R.array.add_video_type;
		if (video != null) {
			itemsId = R.array.change_video_type;
		}
		new AlertDialog.Builder(getActivity()).setItems(itemsId,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (video == null) {
							which++;
						}
						switch (which) {
						case 0:
							playVideo();
							break;
						case 1:
							startActionVideo();
							break;
						default:
							break;
						}
						dialog.cancel();
					}
				}).show();
	}

	protected void startActionVoice() {
		Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
		startActivityForResult(intent, REQUEST_CAPTURE_RECORDER_SOUND);
	}

	protected void startActionVideo() {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
		startActivityForResult(intent, REQUEST_CODE_TAKE_VIDEO);
	}

	protected void playVoice() {
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(voice)), "audio/3gpp");
		startActivity(intent);
	}

	protected void playVideo() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(video)), "video/mp4");
		startActivity(intent);
	}

	/**
	 * 打开地图,选择地址
	 */
	private void selectAddress() {
		Intent intent = new Intent();
		if (Utils.isChinese()) {
			intent.setClass(getActivity(), SelectAddressBaiduMap.class);
		} else {
			intent.setClass(getActivity(), SelectAddressGoogleMap.class);
		}
		startActivityForResult(intent, REQUEST_SELECT_ADDRESS);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case ImageUtil.REQUEST_CODE_GETIMAGE_BYCAMERA:
			image = getPathByUri(cropUri);
			ivImage.setImageBitmap(ImageUtil.getBitmapByPath(image));
			break;
		case ImageUtil.REQUEST_CODE_GETIMAGE_BYCROP:
			image = getTempFile(JPG);
			FileUtil.reNamePath(getPathByUri(data.getData()), image);
			ivImage.setImageBitmap(ImageUtil.getBitmapByPath(image));
			break;
		case REQUEST_CODE_TAKE_VIDEO:
			video = getTempFile(MP4);
			FileUtil.reNamePath(getPathByUri(data.getData()), video);
			break;
		case REQUEST_CAPTURE_RECORDER_SOUND:
			voice = getTempFile(GPP);
			FileUtil.reNamePath(getPathByUri(data.getData()), voice);
			break;
		case REQUEST_SELECT_ADDRESS:
			lon = data.getDoubleExtra("lon", 0);
			lat = data.getDoubleExtra("lat", 0);
			etAddress.setText(data.getStringExtra("address"));
			etAddress.setEnabled(true);
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private String getTempFile(String type) {
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(Constant.FILE_SAVEPATH);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
		} else {
			showToast("无法保存上传的图片，请检查SD卡是否挂载");
			return null;
		}
		String timeStamp = StringUtil.getTimeStamp();
		String fileName = LoginInfo.getInstance(getActivity()).getUserInfo()
				.getUserid()
				+ "_" + timeStamp + type;
		return Constant.FILE_SAVEPATH + "/" + fileName;
	}

	private String getPathByUri(Uri uri) {
		String thePath = ImageUtil.getAbsolutePathFromNoStandardUri(uri);
		// 如果是标准Uri
		if (StringUtil.isEmpty(thePath)) {
			thePath = ImageUtil.getAbsoluteImagePath(getActivity(), uri);
		}
		return thePath;
	}
}
