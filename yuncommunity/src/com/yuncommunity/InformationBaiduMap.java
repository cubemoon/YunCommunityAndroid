package com.yuncommunity;

import android.os.Bundle;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.yuncommunity.base.BaiduMapActivity;
import com.yuncommunity.item.InformationItem;

/**
 * 信息地图
 * 
 * @author oldfeel
 * 
 */
public class InformationBaiduMap extends BaiduMapActivity {
	InformationItem item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.information_map);
		item = (InformationItem) getIntent().getSerializableExtra("item");
		setTitle(item.getTitle());
		mBaiduMap.clear();
		LatLng latLng = new LatLng(item.getLat(), item.getLon());
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_gcoding);
		OverlayOptions options = new MarkerOptions().position(latLng).icon(
				bitmap);
		mBaiduMap.addOverlay(options);
	}
}
