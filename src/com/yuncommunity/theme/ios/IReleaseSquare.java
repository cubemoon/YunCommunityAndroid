package com.yuncommunity.theme.ios;

import android.os.Bundle;
import android.widget.EditText;

import com.oldfeel.view.HorizontalListView;
import com.yuncommunity.R;
import com.yuncommunity.theme.ios.base.IBaseActivity;

/**
 * 发布广场信息
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月2日
 */
public class IReleaseSquare extends IBaseActivity {
	private EditText etSpeak;
	private HorizontalListView hlvImages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_release_square);
		etSpeak = (EditText) findViewById(R.id.i_release_square_speak);
		hlvImages = (HorizontalListView) findViewById(R.id.i_release_square_image);
	}
}
