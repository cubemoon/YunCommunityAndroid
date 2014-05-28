package android.oldfeel.yanzhuang.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.oldfeel.yanzhuang.MainActivity;
import android.oldfeel.yanzhuang.R;
import android.oldfeel.yanzhuang.app.JsonApi;
import android.oldfeel.yanzhuang.app.PersonInfo;
import android.oldfeel.yanzhuang.base.BaseFragment;
import android.oldfeel.yanzhuang.util.ETUtil;
import android.oldfeel.yanzhuang.util.JSONUtil;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.oldfeel.yanzhuang.util.NetUtil.RequestStringListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * 发布信息
 * 
 * @author oldfeel
 * 
 */
public class InformationReleaseFragment extends BaseFragment implements
		OnClickListener {
	private EditText etTitle, etDesc, etAddress, etPhone, etTag;
	private ImageView ivImage, ivVoice, ivVideo;
	private Button btnMap;
	private double lon, lat;
	private String image, voice, video;
	private int infotype;

	public static InformationReleaseFragment newInstance(int infotype) {
		InformationReleaseFragment fragment = new InformationReleaseFragment();
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
		etPhone.setText(PersonInfo.getInstance(getActivity()).getPhone());
	}

	public void submit() {
		if (ETUtil.isEmpty(etTitle)) {
			etTitle.setError("必填");
			return;
		}
		NetUtil netUtil = new NetUtil(getActivity(),
				JsonApi.INFORMATION_RELEASE);
		netUtil.setParams("infotype", infotype);
		netUtil.setParams("userid", PersonInfo.getInstance(getActivity())
				.getUserid());
		netUtil.setParams("title", getString(etTitle));
		netUtil.setParams("description", getString(etDesc));
		netUtil.setParams("address", getString(etAddress));
		netUtil.setParams("lon", lon);
		netUtil.setParams("lat", lat);
		netUtil.setParams("phone", getString(etPhone));
		netUtil.setParams("tags", getString(etTag));
		netUtil.setParams("image", image);
		netUtil.setParams("voice", voice);
		netUtil.setParams("video", video);
		netUtil.postRequest("正在发布", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JSONUtil.isSuccess(result)) {
					showToast("发布成功");
					Intent intent = new Intent(getActivity(),
							MainActivity.class);
					intent.putExtra("infotype", infotype);
					intent.putExtra("result", true);
					startActivity(intent);
					getActivity().finish();
				} else {
					showToast("发布失败," + JSONUtil.getMessage(result));
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.release_map:
			openMap();
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
		String[] items = new String[] { "查看大图", "从手机中选择", "拍照" };
		if (image == null) {
			items = new String[] { "从手机中选择", "拍照" };
		}
		new AlertDialog.Builder(getActivity()).setItems(items,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	private void voice() {
	}

	private void video() {
	}

	/**
	 * 打开地图
	 */
	private void openMap() {
	}

}
