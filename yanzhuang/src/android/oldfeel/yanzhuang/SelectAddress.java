package android.oldfeel.yanzhuang;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Point;
import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.util.ETUtil;
import android.oldfeel.yanzhuang.util.LogUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfigeration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

/**
 * 选择地址
 * 
 * @author oldfeel
 * 
 */
public class SelectAddress extends BaseActivity implements OnClickListener {
	private EditText etContent;
	private Button btnUp, btnDown, btnRight, btnLeft;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	public boolean isFirstLoc = true;
	protected Point clickedPoint;
	private GeoCoder mSearch;
	private LatLng clickedLatLng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_address);
		initView();
		initMap();
		initListener();
	}

	private void initMap() {
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		mBaiduMap.setMaxAndMinZoomLevel(19, 17);
		mLocationClient = new LocationClient(getApplicationContext());

		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		mLocationClient.setLocOption(option);

		// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
		BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_geo);
		MyLocationConfigeration config = new MyLocationConfigeration(
				com.baidu.mapapi.map.MyLocationConfigeration.LocationMode.NORMAL,
				true, mCurrentMarker);
		mBaiduMap.setMyLocationConfigeration(config);
	}

	private void initListener() {
		btnDown.setOnClickListener(this);
		btnLeft.setOnClickListener(this);
		btnRight.setOnClickListener(this);
		btnUp.setOnClickListener(this);
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				return false;
			}

			@Override
			public void onMapClick(LatLng ll) {
				clickedPoint = mBaiduMap.getProjection().toScreenLocation(ll);
				drawClicked();
			}
		});
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

			@Override
			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
				if (result == null
						|| result.error != SearchResult.ERRORNO.NO_ERROR) {
					showToast("抱歉,未能找到结果");
				} else {
					showToast(result.getAddress());
					etContent.setText(result.getAddress());
					LogUtil.showLog("result is " + result);
				}
			}

			@Override
			public void onGetGeoCodeResult(GeoCodeResult arg0) {
			}
		});
	}

	/**
	 * 绘制被点击的点
	 */
	protected void drawClicked() {
		mBaiduMap.clear();
		clickedLatLng = mBaiduMap.getProjection().fromScreenLocation(
				clickedPoint);
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_gcoding);
		OverlayOptions options = new MarkerOptions().position(clickedLatLng)
				.icon(bitmap);
		mBaiduMap.addOverlay(options);
		mSearch.reverseGeoCode(new ReverseGeoCodeOption()
				.location(clickedLatLng));
	}

	private void initView() {
		mMapView = (MapView) findViewById(R.id.bmapsView);
		etContent = getEditText(R.id.select_address_content);
		btnUp = (Button) findViewById(R.id.select_address_up);
		btnDown = getButton(R.id.select_address_down);
		btnRight = getButton(R.id.select_address_right);
		btnLeft = getButton(R.id.select_address_left);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.select_address, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_help:
			showHelp();
			break;
		case R.id.action_complete:
			complete();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void complete() {
		if (clickedLatLng == null) {
			showToast("请先在地图上选择地址");
			return;
		}
		onBackPressed();
	}

	/**
	 * 显示帮助
	 */
	private void showHelp() {
		new AlertDialog.Builder(SelectAddress.this).setTitle("帮助")
				.setMessage("点击地图选择位置,点击上,下,左,右做个按钮进行微调.")
				.setPositiveButton("确定", null).show();
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (mBaiduMap == null || location == null) {
				return;
			}
			// 构造定位数据
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			// 设置定位数据
			mBaiduMap.setMyLocationData(locData);
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

	@Override
	protected void onDestroy() {
		mMapView.onDestroy();
		super.onDestroy();
	}

	public void logMsg(String string) {
		LogUtil.showLog(string);
	}

	@Override
	protected void onResume() {
		mLocationClient.registerLocationListener(myListener);
		mLocationClient.start();
		mLocationClient.requestLocation();
		mMapView.onResume();
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		super.onResume();
	}

	@Override
	protected void onPause() {
		if (mLocationClient != null) {
			mLocationClient.stop();
			mLocationClient.unRegisterLocationListener(myListener);
		}
		if (mMapView != null) {
			mMapView.onPause();
		}
		if (mBaiduMap != null) {
			// 当不需要定位图层时关闭定位图层
			mBaiduMap.setMyLocationEnabled(false);
			mBaiduMap = null;
		}
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		if (clickedPoint == null) {
			showToast("请先点击地图选择位置");
			return;
		}
		switch (v.getId()) {
		case R.id.select_address_down:
			clickedPoint.y++;
			break;
		case R.id.select_address_left:
			clickedPoint.x--;
			break;
		case R.id.select_address_right:
			clickedPoint.x++;
			break;
		case R.id.select_address_up:
			clickedPoint.y--;
			break;
		default:
			break;
		}
		drawClicked();
	}

	@Override
	public void onBackPressed() {
		if (clickedLatLng != null && !ETUtil.isEmpty(etContent)) {
			Intent intent = new Intent();
			intent.putExtra("lat", clickedLatLng.latitude);
			intent.putExtra("lon", clickedLatLng.longitude);
			intent.putExtra("address", getString(etContent));
			setResult(RESULT_OK, intent);
			finish();
		}
		super.onBackPressed();
	}
}
