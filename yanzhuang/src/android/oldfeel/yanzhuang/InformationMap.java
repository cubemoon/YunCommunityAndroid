package android.oldfeel.yanzhuang;

import android.oldfeel.yanzhuang.base.BaseMapActivity;
import android.oldfeel.yanzhuang.item.InformationItem;
import android.os.Bundle;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

/**
 * 信息地图
 * 
 * @author oldfeel
 * 
 */
public class InformationMap extends BaseMapActivity {
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
