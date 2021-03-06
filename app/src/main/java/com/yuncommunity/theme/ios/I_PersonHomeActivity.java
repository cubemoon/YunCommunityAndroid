package com.yuncommunity.theme.ios;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.gson.Gson;
import com.oldfeel.utils.DialogUtil;
import com.oldfeel.utils.FileUtil;
import com.oldfeel.utils.ImageUtil;
import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.LogUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.oldfeel.utils.StringUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.yuncommunity.R;
import com.yuncommunity.conf.Constant;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.item.UserItem;
import com.yuncommunity.list.InformationListFragment;
import com.yuncommunity.theme.android.A_ChatActivity;
import com.yuncommunity.theme.android.A_UserList;
import com.yuncommunity.theme.android.A_UserReleaseList;
import com.yuncommunity.theme.android.fragment.HeaderFragment;
import com.yuncommunity.theme.ios.base.I_BaseActivity;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class I_PersonHomeActivity extends I_BaseActivity implements OnClickListener {
	public static final int EDIT_PERSON_INFO = 11;
	private final static int CROP = 800;
	private ImageView ivBg;
	private ImageButton ibBgEdit;
	private TextView tvName, tvBirthday;
	private Button btnFollowing, btnMsg, btnFollowings, btnFans, btnServer;
	private long targetid;
	private String protraitPath;
	private File protraitFile;
	private Uri cropUri;
	private Uri origUri;
	private Bitmap protraitBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_person_info);
		targetid = getIntent().getLongExtra("targetid", 0);
		if (targetid == 0) {
			userNotExists();
		}
		initTop();
		initView();
		initListener();
		getPersonInfo();
		initInformation();
	}

	private void initTop() {
		if (targetid != getUserId()) {
			return;
		}
		btnRight.setText("修改资料");
		btnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(I_PersonHomeActivity.this,
						I_EditPersonInfo.class);
				startActivityForResult(intent, EDIT_PERSON_INFO);
			}
		});
	}

	/**
	 * 显示提醒
	 */
	private void initInformation() {
		NetUtil netUtil = new NetUtil(I_PersonHomeActivity.this,
				JsonApi.USER_INFORMATION_LIST);
		netUtil.setParams("targetid", targetid);
		InformationListFragment fragment = InformationListFragment
				.newInstance(netUtil);
		getFragmentManager().beginTransaction()
				.replace(R.id.person_home_information, fragment).commit();
	}

	/**
	 * 获取个人信息
	 */
	private void getPersonInfo() {
		NetUtil netUtil = new NetUtil(I_PersonHomeActivity.this, JsonApi.USER_INFO);
		netUtil.setParams("userid",
				LoginInfo.getInstance(getApplicationContext()).getUserId());
		netUtil.setParams("targetid", targetid);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					parseInfo(result);
				} else {
					showToast(JsonUtil.getData(result));
				}
			}
		});
	}

	protected void parseInfo(String result) {
		UserItem item = new Gson().fromJson(
				JsonUtil.getData(result).toString(), UserItem.class);
		if (!StringUtil.isEmpty(item.getBackground())) {
			imageLoader.displayImage(item.getBackground(), ivBg, options);
		}
		getFragmentManager()
				.beginTransaction()
				.replace(
						R.id.person_home_header,
						HeaderFragment.newInstance(item.getAvatar(), LoginInfo
								.getInstance(getApplicationContext())
								.getUserId() == targetid)).commit();
		tvName.setText(item.getName() + "\n" + item.getIntroduction());
		tvBirthday.setText(item.getHousenumber() + "\n" + item.getBirthday());
		btnFollowing.setText(item.isFollowing() ? getText(R.string.follow)
				: getText(R.string.follow_cancel));
		btnFollowings.setText(getText(R.string.follow) + "("
				+ item.getFollowingCount() + ")");
		btnFans.setText(getText(R.string.fan) + "(" + item.getFansCount() + ")");
		btnServer.setText(getText(R.string.publish) + "("
				+ item.getServerCount() + ")");
	}

	private void initListener() {
		ibBgEdit.setOnClickListener(this);
		btnFans.setOnClickListener(this);
		btnFollowing.setOnClickListener(this);
		btnFollowings.setOnClickListener(this);
		btnMsg.setOnClickListener(this);
		btnServer.setOnClickListener(this);
	}

	private void initView() {
		ivBg = getImageView(R.id.person_home_bg);
		ibBgEdit = getImageButton(R.id.person_home_bg_edit);
		tvName = getTextView(R.id.person_home_name);
		tvBirthday = getTextView(R.id.person_home_birthday);
		btnFollowing = getButton(R.id.person_home_following);
		btnMsg = getButton(R.id.person_home_message);
		btnFollowings = getButton(R.id.person_home_followings);
		btnFans = getButton(R.id.person_home_fans);
		btnServer = getButton(R.id.person_home_server);
		int width = getResources().getDisplayMetrics().widthPixels;
		ivBg.setLayoutParams(new LayoutParams(width, width / 2));
		if (LoginInfo.getInstance(getApplicationContext()).getUserId() == targetid) {
			btnFollowing.setVisibility(View.GONE);
			btnMsg.setVisibility(View.GONE);
		} else {
			ibBgEdit.setVisibility(View.GONE);
		}
	}

	private void userNotExists() {
		DialogUtil.getInstance().showSimpleDialog(I_PersonHomeActivity.this,
				String.valueOf(getText(R.string.user_not_exist)),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						I_PersonHomeActivity.this.finish();
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.person_home_bg_edit:
			bgEdit();
			break;
		case R.id.person_home_following:
			following();
			break;
		case R.id.person_home_followings:
			showFollowings();
			break;
		case R.id.person_home_message:
			showMessage();
			break;
		case R.id.person_home_fans:
			showFans();
			break;
		case R.id.person_home_server:
			showServer();
			break;
		default:
			break;
		}
	}

	/**
	 * 编辑背景
	 */
	private void bgEdit() {
		new AlertDialog.Builder(I_PersonHomeActivity.this)
				.setTitle(getText(R.string.upload_background))
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

	/**
	 * 选择图片裁剪
	 * 
	 * @param output
	 */
	protected void startImagePick() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		startActivityForResult(
				Intent.createChooser(intent, getText(R.string.choose_photo)),
				ImageUtil.REQUEST_CODE_GETIMAGE_BYCROP);
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
			showToast(String
					.valueOf(getText(R.string.failed_save_uploaded_photo)));
			return null;
		}
		String timeStamp = StringUtil.getTimeStamp();
		// 照片命名
		String cropFileName = Constant.APP_NAME + "_" + timeStamp + ".jpg";
		// 裁剪头像的绝对路径
		protraitPath = Constant.FILE_SAVEPATH + "/" + cropFileName;
		protraitFile = new File(protraitPath);
		cropUri = Uri.fromFile(protraitFile);
		this.origUri = this.cropUri;
		return this.cropUri;
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
		intent.putExtra("aspectX", 2);// 裁剪框比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", CROP);// 输出图片大小
		intent.putExtra("outputY", CROP / 2);
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
			showToast(String
					.valueOf(getText(R.string.failed_save_uploaded_photo)));
			return null;
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss",
				Locale.getDefault()).format(new Date());
		String thePath = ImageUtil.getAbsolutePathFromNoStandardUri(uri);

		// 如果是标准Uri
		if (StringUtil.isEmpty(thePath)) {
			thePath = ImageUtil.getAbsoluteImagePath(I_PersonHomeActivity.this, uri);
		}
		String ext = FileUtil.getFileFormat(thePath);
		ext = StringUtil.isEmpty(ext) ? "jpg" : ext;
		// 照片命名
		String cropFileName = Constant.APP_NAME + "_" + timeStamp + "." + ext;
		// 裁剪头像的绝对路径
		protraitPath = Constant.FILE_SAVEPATH + "/" + cropFileName;
		protraitFile = new File(protraitPath);

		cropUri = Uri.fromFile(protraitFile);
		return this.cropUri;
	}

	/**
	 * 关注/取消关注
	 */
	private void following() {
		boolean isFollowing = false;
		if (btnFollowing.getText().equals(getText(R.string.follow))) {
			isFollowing = true;
		}
		NetUtil netUtil = new NetUtil(I_PersonHomeActivity.this, JsonApi.USER_FOLLOWING);
		netUtil.setParams("userid",
				LoginInfo.getInstance(getApplicationContext()).getUserId());
		netUtil.setParams("targetid", targetid);
		netUtil.setParams("isfollowingid", isFollowing);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				LogUtil.showLog(result);
			}
		});
	}

	/**
	 * 显示该用户关注的用户列表
	 */
	private void showFollowings() {
		Intent intent = new Intent(I_PersonHomeActivity.this, A_UserList.class);
		intent.putExtra("targetid", targetid);
		intent.putExtra("api", JsonApi.USER_FOLLOWINGS);
		startActivity(intent);
	}

	/**
	 * 发送私信/聊天
	 */
	private void showMessage() {
		Intent intent = new Intent();
		intent.setClass(I_PersonHomeActivity.this, A_ChatActivity.class);
		intent.putExtra("targetid", targetid);
		startActivity(intent);
	}

	/**
	 * 显示关注该用户的用户列表
	 */
	private void showFans() {
		Intent intent = new Intent(I_PersonHomeActivity.this, A_UserList.class);
		intent.putExtra("targetid", targetid);
		intent.putExtra("api", JsonApi.USER_FANS);
		startActivity(intent);
	}

	/**
	 * 显示该用户发出的服务
	 */
	private void showServer() {
		Intent intent = new Intent(I_PersonHomeActivity.this, A_UserReleaseList.class);
		intent.putExtra("targetid", targetid);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
		case EDIT_PERSON_INFO:
			updateData(data);
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 上传新照片
	 */
	private void uploadNewPhoto() {
		// 获取背景缩略图
		if (!StringUtil.isEmpty(protraitPath) && protraitFile.exists()) {
			protraitBitmap = ImageUtil.loadImgThumbnail(protraitPath, 800, 400);
		} else {
			showSimpleDialog(String
					.valueOf(getText(R.string.failed_upload_not_exist_photo)));
		}
		File headerFile = new File(protraitFile.getParent() + "/bg-"
				+ System.currentTimeMillis() + "-"
				+ LoginInfo.getInstance(getApplicationContext()).getUserId()
				+ ".jpg");
		protraitFile.renameTo(headerFile);
		protraitFile = headerFile;
		NetUtil netUtil = new NetUtil(I_PersonHomeActivity.this, JsonApi.UPTOKEN);
		netUtil.postRequest(String.valueOf(getText(R.string.uploading)),
				new RequestStringListener() {

					@Override
					public void onComplete(String result) {
						if (JsonUtil.isSuccess(result)) {
							startUpload(JsonUtil.getData(result));
						} else {
							showToast(String
									.valueOf(getText(R.string.failed_to_get_uptoken)));
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
		UploadManager uploadManager = new UploadManager();
		uploadManager.put(protraitFile, protraitFile.getName(), uptoken,
				new UpCompletionHandler() {

					@Override
					public void complete(String key, ResponseInfo info,
							JSONObject response) {
						changePersonHomeBg();
					}
				}, null);
	}

	protected void changePersonHomeBg() {
		NetUtil netUtil = new NetUtil(I_PersonHomeActivity.this,
				JsonApi.CHANGE_PERSON_HOME_BG);
		netUtil.setParams("userid", getUserId());
		netUtil.setParams("background", protraitFile.getName());
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					showToast("上传成功");
					ivBg.setImageBitmap(protraitBitmap);
					LoginInfo.getInstance(I_PersonHomeActivity.this).getUserInfo()
							.setBackground(protraitFile.getName());
				}
			}
		});
	}

	private void updateData(Intent data) {
		LoginInfo personInfo = LoginInfo.getInstance(getApplicationContext());
		tvName.setText(personInfo.getUserInfo().getName() + "\n"
				+ personInfo.getUserInfo().getIntroduction());
		tvBirthday.setText(personInfo.getUserInfo().getHousenumber() + "\n"
				+ personInfo.getUserInfo().getBirthday());
	}
}
