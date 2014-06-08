package android.oldfeel.yanzhuang;

import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.util.LogUtil;
import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

/**
 * 信息地图
 * 
 * @author oldfeel
 * 
 */
public class InformationMap extends BaseActivity {
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	public BitmapDescriptor mCurrentMarker;
	public boolean isFirstLoc = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information_map);
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			LogUtil.showLog("on receive location");
			if (mBaiduMap == null || location == null) {
				return;
			}
			// // 构造定位数据
			// MyLocationData locData = new MyLocationData.Builder()
			// .accuracy(location.getRadius())
			// // 此处设置开发者获取到的方向信息，顺时针0-360
			// .direction(100).latitude(location.getLatitude())
			// .longitude(location.getLongitude()).build();
			// // 设置定位数据
			// mBaiduMap.setMyLocationData(locData);
			// // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
			// mCurrentMarker = BitmapDescriptorFactory
			// .fromResource(R.drawable.icon_geo);
			// MyLocationConfigeration config = new MyLocationConfigeration(
			// com.baidu.mapapi.map.MyLocationConfigeration.LocationMode.NORMAL,
			// true, mCurrentMarker);
			// mBaiduMap.setMyLocationConfigeration(config);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
}
