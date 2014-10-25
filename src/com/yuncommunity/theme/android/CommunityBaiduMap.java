package com.yuncommunity.theme.android;

import android.os.Bundle;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.oldfeel.base.BaiduMapActivity;
import com.yuncommunity.R;
import com.yuncommunity.item.CommunityItem;

/**
 * 小区在地图上的位置
 * 
 * @author oldfeel
 * 
 */
public class CommunityBaiduMap extends BaiduMapActivity {
	CommunityItem item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.community_map);
		item = (CommunityItem) getIntent().getSerializableExtra("item");
		setTitle(item.getName());
		mBaiduMap.clear();
		LatLng latLng = new LatLng(item.getLat(), item.getLon());
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_gcoding);
		OverlayOptions options = new MarkerOptions().position(latLng).icon(
				bitmap);
		mBaiduMap.addOverlay(options);
	}
}
