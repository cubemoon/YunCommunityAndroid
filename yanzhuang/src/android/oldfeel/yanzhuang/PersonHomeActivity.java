package android.oldfeel.yanzhuang;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.oldfeel.yanzhuang.app.Constant;
import android.oldfeel.yanzhuang.app.Api;
import android.oldfeel.yanzhuang.app.PersonInfo;
import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.fragment.HeaderFragment;
import android.oldfeel.yanzhuang.fragment.list.InformationListFragment;
import android.oldfeel.yanzhuang.item.UserItem;
import android.oldfeel.yanzhuang.util.DialogUtil;
import android.oldfeel.yanzhuang.util.FileUtil;
import android.oldfeel.yanzhuang.util.ImageUtil;
import android.oldfeel.yanzhuang.util.JSONUtil;
import android.oldfeel.yanzhuang.util.LogUtil;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.oldfeel.yanzhuang.util.NetUtil.RequestStringListener;
import android.oldfeel.yanzhuang.util.StringUtil;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.gson.Gson;

/**
 * 个人首页
 * 
 * @author oldfeel
 * 
 */
public class PersonHomeActivity extends BaseActivity implements OnClickListener {
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
		setContentView(R.layout.person_home);
		targetid = getIntent().getLongExtra("targetid", -1);
		if (targetid == -1) {
			userNotExists();
		}
		initView();
		initListener();
		getPersonInfo();
		initInformation();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (targetid == getUserid()) {
			getMenuInflater().inflate(R.menu.person_home, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_edit:
			Intent intent = new Intent(PersonHomeActivity.this,
					EditPersonInfo.class);
			startActivityForResult(intent, EDIT_PERSON_INFO);
			break;
		case R.id.action_change_password:
			openActivity(ChangePassword.class);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 显示提醒
	 */
	private void initInformation() {
		NetUtil netUtil = new NetUtil(PersonHomeActivity.this,
				Api.USER_INFORMATION_LIST);
		netUtil.setParams("targetid", targetid);
		InformationListFragment fragment = InformationListFragment
				.newInstance(netUtil);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.person_home_information, fragment).commit();
	}

	/**
	 * 获取个人信息
	 */
	private void getPersonInfo() {
		NetUtil netUtil = new NetUtil(PersonHomeActivity.this,
				Api.USER_INFO);
		netUtil.setParams("userid", getUserid());
		netUtil.setParams("targetid", targetid);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JSONUtil.isSuccess(result)) {
					parseInfo(result);
				} else {
					showToast(JSONUtil.getMessage(result));
				}
			}
		});
	}

	protected void parseInfo(String result) {
		UserItem item = new Gson().fromJson(
				JSONUtil.getData(result).toString(), UserItem.class);
		if (!StringUtil.isEmpty(item.getBackground())) {
			imageLoader.displayImage(item.getBackground(), ivBg, options);
		}
		getSupportFragmentManager()
				.beginTransaction()
				.replace(
						R.id.person_home_header,
						HeaderFragment.newInstance(item.getAvatar(),
								getUserid() == targetid)).commit();
		tvName.setText(item.getName() + "\n" + item.getIntroduction());
		tvBirthday.setText(item.getHousenumber() + "\n" + item.getBirthday());
		btnFollowing.setText(item.isFollowing() ? "关注" : "取消关注");
		btnFollowings.setText("关注(" + item.getFollowingCount() + ")");
		btnFans.setText("粉丝(" + item.getFansCount() + ")");
		btnServer.setText("发布(" + item.getServerCount() + ")");
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
		if (super.getUserid() == targetid) {
			btnFollowing.setVisibility(View.GONE);
			btnMsg.setVisibility(View.GONE);
		} else {
			ibBgEdit.setVisibility(View.GONE);
		}
	}

	private void userNotExists() {
		DialogUtil.getInstance().showSimpleDialog(PersonHomeActivity.this,
				"用户不存在", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						PersonHomeActivity.this.finish();
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
		new AlertDialog.Builder(PersonHomeActivity.this)
				.setTitle("上传背景(建议分辨率800 * 400)")
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
		startActivityForResult(Intent.createChooser(intent, "选择图片"),
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
			showToast("无法保存上传的头像，请检查SD卡是否挂载");
			return null;
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss",
				Locale.getDefault()).format(new Date());
		String thePath = ImageUtil.getAbsolutePathFromNoStandardUri(uri);

		// 如果是标准Uri
		if (StringUtil.isEmpty(thePath)) {
			thePath = ImageUtil.getAbsoluteImagePath(PersonHomeActivity.this,
					uri);
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
	 * 关注/取消关注
	 */
	private void following() {
		boolean isFollowing = false;
		if (btnFollowing.getText().equals("关注")) {
			isFollowing = true;
		}
		NetUtil netUtil = new NetUtil(PersonHomeActivity.this,
				Api.USER_FOLLOWING);
		netUtil.setParams("userid", getUserid());
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
		Intent intent = new Intent(PersonHomeActivity.this, UserList.class);
		intent.putExtra("targetid", targetid);
		intent.putExtra("api", Api.USER_FOLLOWINGS);
		startActivity(intent);
	}

	/**
	 * 发送私信/聊天
	 */
	private void showMessage() {
		Intent intent = new Intent();
		intent.setClass(PersonHomeActivity.this, ChatActivity.class);
		intent.putExtra("targetid", targetid);
		startActivity(intent);
	}

	/**
	 * 显示关注该用户的用户列表
	 */
	private void showFans() {
		Intent intent = new Intent(PersonHomeActivity.this, UserList.class);
		intent.putExtra("targetid", targetid);
		intent.putExtra("api", Api.USER_FANS);
		startActivity(intent);
	}

	/**
	 * 显示该用户发出的服务
	 */
	private void showServer() {
		Intent intent = new Intent(PersonHomeActivity.this,
				UserReleaseList.class);
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
			showSimpleDialog("图像不存在，上传失败·");
		}
		File headerFile = new File(protraitFile.getParent() + "/bg-"
				+ System.currentTimeMillis() + "-" + getUserid() + ".jpg");
		protraitFile.renameTo(headerFile);
		protraitFile = headerFile;
		NetUtil netUtil = new NetUtil(PersonHomeActivity.this, Api.UPTOKEN);
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
		NetUtil netUtil = new NetUtil(PersonHomeActivity.this, "");
		netUtil.postFile("正在上传背景...", protraitFile.getName(), protraitFile,
				uptoken, new RequestStringListener() {

					@Override
					public void onComplete(String result) {
						showToast("上传成功");
						ivBg.setImageBitmap(protraitBitmap);
						PersonInfo.getInstance(PersonHomeActivity.this)
								.setBackGround(protraitFile.getName());
						PersonInfo.update(PersonHomeActivity.this);
					}
				});
	}

	private void updateData(Intent data) {
		PersonInfo personInfo = PersonInfo.getInstance(getApplicationContext());
		tvName.setText(personInfo.getName() + "\n"
				+ personInfo.getIntroduction());
		tvBirthday.setText(personInfo.getHouseNumber() + "\n"
				+ personInfo.getBirthday());
	}
}
