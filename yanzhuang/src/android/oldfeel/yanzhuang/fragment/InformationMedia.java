package android.oldfeel.yanzhuang.fragment;

import android.oldfeel.yanzhuang.R;
import android.oldfeel.yanzhuang.base.BaseFragment;
import android.oldfeel.yanzhuang.item.InformationItem;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 信息详情中,多媒体的展示
 * 
 * @author oldfeel
 * 
 */
public class InformationMedia extends BaseFragment {
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
		return view;
	}

}
