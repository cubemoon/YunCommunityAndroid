package android.oldfeel.yanzhuang.fragment;

import android.oldfeel.yanzhuang.R;
import android.oldfeel.yanzhuang.app.Constant;
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

/**
 * 发布商家服务
 * 
 * @author oldfeel
 * 
 */
public class ReleaseBusinessFragment extends BaseFragment implements
		OnClickListener {
	private EditText etTitle, etDesc, etAddress, etPhone, etTag;
	private Button btnMap, btnAddFile;
	private double lon, lat;
	private String image, voice, video;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.release_business, null);
		etTitle = getEditText(view, R.id.release_title);
		etDesc = getEditText(view, R.id.release_desc);
		etAddress = getEditText(view, R.id.release_address);
		etPhone = getEditText(view, R.id.release_phone);
		etTag = getEditText(view, R.id.release_tag);
		btnMap = getButton(view, R.id.release_map);
		btnAddFile = getButton(view, R.id.release_add_file);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		btnMap.setOnClickListener(this);
		btnAddFile.setOnClickListener(this);
	}

	public void submit() {
		if (ETUtil.isEmpty(etTitle)) {
			etTitle.setError("必填");
			return;
		}
		NetUtil netUtil = new NetUtil(getActivity(),
				JsonApi.RELEASE_INFORMATION);
		netUtil.setParams("infotype", Constant.TYPE_BUSINESS);
		netUtil.setParams("userid", PersonInfo.getInstance(getActivity())
				.getId());
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
		case R.id.release_add_file:
			addFile();
			break;
		default:
			break;
		}

	}

	/**
	 * 添加文件,可以是图片/语音/视频
	 */
	private void addFile() {
	}

	/**
	 * 打开地图
	 */
	private void openMap() {
	}
}
