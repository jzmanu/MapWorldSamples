package com.manu.mapworldsamples.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.manu.mapworldsamples.R;
import com.manu.mapworldsamples.widget.MLocationOverlay;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MyLocationOverlay;
import com.tianditu.android.maps.TErrorCode;
import com.tianditu.android.maps.TGeoAddress;
import com.tianditu.android.maps.TGeoDecode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapShowActivity extends AppCompatActivity {

    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.btnMyLocation)
    Button btnMyLocation;

    @BindView(R.id.btnConcrete)
    Button btnConcrete;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.btnAddLocationIcon)
    Button btnAddLocationIcon;

    private MyLocationOverlay myLocationOverlay;
    private MapController mMapController;
    private GeoPoint mPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_show);
        ButterKnife.bind(this);
        initMapView();
    }

    private void initMapView() {
        //启用内置的缩放组件
        mapView.setBuiltInZoomControls(false);
        //得到MapView的控制权,可以用它控制和驱动平移和缩放
        mMapController = mapView.getController();
        //用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
        GeoPoint point = new GeoPoint((int) (39.915 * 1E6), (int) (116.404 * 1E6));
        //设置地图中心点
        mMapController.setCenter(point);
        //设置地图zoom级别
        mMapController.setZoom(12);

        System.out.println("当前地图最大缩放级别"+mapView.getMaxZoomLevel());
        System.out.println("当前地图最小缩放级别"+mapView.getMinZoomLevel());
    }

    @OnClick({R.id.btnMyLocation, R.id.btnConcrete,R.id.btnAddLocationIcon})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btnMyLocation:
                myLocationOverlay = new MyLocationOverlay(this, mapView);
                //启用指南针位置更新
                myLocationOverlay.enableCompass();
                //启用我的位置
                myLocationOverlay.enableMyLocation();
                mapView.addOverlay(myLocationOverlay);
                //获得当前位置
                mPoint = myLocationOverlay.getMyLocation();
                //动画移动到当前位置
                mMapController.animateTo(mPoint);
                break;
            case R.id.btnConcrete:
                //逆地理编码类，根据输入的点坐标，返回相应的地理信息
                TGeoDecode tGeoDecode = new TGeoDecode(new OnGeoResultListener());
                tGeoDecode.search(mPoint);
                break;
            case R.id.btnAddLocationIcon:
                updateMyLocationIcon();
                break;

        }
    }


    /**
     * 逆地理编码回调结果监听
     */
    class OnGeoResultListener implements TGeoDecode.OnGeoResultListener {

        @Override
        public void onGeoDecodeResult(TGeoAddress tGeoAddress, int errorCode) {

            if (TErrorCode.OK == errorCode) {
                // 查询点相关信息
                String str = "最近的 poi 名称:" + tGeoAddress.getPoiName() + "\n";
                str += "查询点 Poi 点的方位:" + tGeoAddress.getPoiDirection() + "\n";
                str += "查询点 Poi 点的距离:" + tGeoAddress.getPoiDistance() + "\n";
                str += "查询点行政区名称:" + tGeoAddress.getCity() + "\n";
                str += "查询点地理描述全称:" + tGeoAddress.getFullName() + "\n";
                str += "查询点的地址:" + tGeoAddress.getAddress() + "\n";
                str += "查询点的方位:" + tGeoAddress.getAddrDirection() + "\n";
                str += "查询点的距离:" + tGeoAddress.getAddrDistance() + "\n";
                str += "查询点道路名称:" + tGeoAddress.getRoadName() + "\n";
                str += "查询点与最近道路的距离:" + tGeoAddress.getRoadDistance();
                tvAddress.setText(tGeoAddress.getCity().substring(0,tGeoAddress.getCity().length()-3));
                System.out.println(str);
            } else {
                System.out.println("查询出错：" + errorCode);
            }
        }
    }

    private void updateMyLocationIcon(){
        MLocationOverlay overlay = new MLocationOverlay(this,mapView);
        overlay.enableCompass();
        overlay.enableMyLocation();
        mapView.addOverlay(overlay);
        GeoPoint point = overlay.getMyLocation();
        mMapController.animateTo(point);
    }
}
