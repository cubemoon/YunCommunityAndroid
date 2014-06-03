package android.oldfeel.yanzhuang.fragment;

import android.oldfeel.yanzhuang.base.BaseDialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 查看大图
 * 
 * @author oldfeel
 * 
 */
public class LookBigImage extends BaseDialogFragment {
	private String image;

	public static LookBigImage newInstance(String image) {
		LookBigImage fragment = new LookBigImage();
		fragment.image = image;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
