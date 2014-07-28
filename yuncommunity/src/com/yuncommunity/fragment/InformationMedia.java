package com.yuncommunity.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yuncommunity.R;
import com.yuncommunity.base.BaseFragment;
import com.yuncommunity.dialog.LookBigImage;
import com.yuncommunity.item.InformationItem;
import com.yuncommunity.util.LogUtil;
import com.yuncommunity.util.StringUtil;

/**
 * 信息详情中,多媒体的展示
 * 
 * @author oldfeel
 * 
 */
public class InformationMedia extends BaseFragment implements OnClickListener {
	private ImageView ivImage, ivVoice, ivVideo;
	private InformationItem item;

	public static InformationMedia newInstance(InformationItem item) {
		InformationMedia fragment = new InformationMedia();
		fragment.item = item;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.information_media, null);
		ivImage = getImageView(view, R.id.information_media_image);
		ivVoice = getImageView(view, R.id.information_media_voice);
		ivVideo = getImageView(view, R.id.information_media_video);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ivImage.setOnClickListener(this);
		ivVideo.setOnClickListener(this);
		ivVoice.setOnClickListener(this);
		ImageLoader imageLoader = ImageLoader.getInstance();
		if (!StringUtil.isEmpty(item.getImage())) {
			imageLoader.displayImage(item.getImage(), ivImage);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.information_media_image:
			lookBigImage();
			break;
		case R.id.information_media_video:
			playVideo();
			break;
		case R.id.information_media_voice:
			playVoice();
			break;
		default:
			break;
		}
	}

	private void lookBigImage() {
		if (StringUtil.isEmpty(item.getImage())) {
			showToast("没有图片");
			return;
		}
		LookBigImage dialogFragment = LookBigImage.newInstance(item.getImage());
		dialogFragment.show(getChildFragmentManager(), "image");
	}

	private void playVideo() {
		if (StringUtil.isEmpty(item.getVideo())) {
			showToast("没有视频");
			return;
		}
		playNetMedia(item.getVideo());
	}

	private void playVoice() {
		if (StringUtil.isEmpty(item.getVoice())) {
			showToast("没有语音");
			return;
		}
		playNetMedia(item.getVoice());
	}

	/**
	 * 播放网络多媒体文件
	 * 
	 * @param url
	 */
	private void playNetMedia(String url) {
		LogUtil.showLog("url is " + url);
		String extension = MimeTypeMap.getFileExtensionFromUrl(url);
		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
				extension);
		Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
		mediaIntent.setDataAndType(Uri.parse(url), mimeType);
		startActivity(mediaIntent);
	}
}
