package com.manu.map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MyLocationOverlay;
import com.tianditu.android.maps.TErrorCode;
import com.tianditu.android.maps.TGeoAddress;
import com.tianditu.android.maps.TGeoDecode;

public abstract class BaseMapFragment extends Fragment implements
        View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {

    protected MapView mapView;
    protected ViewGroup coverContainer;
    private Button btnZoomDown;
    private SeekBar sbMapSeekBar;
    private Button btnZoomUp;
    private Button btnLocation;

    protected MapController mMapController;
    protected GeoPoint mCurrentGeoPoint;

    public abstract Context getAppContext();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_map, container, false);
        initView(view);
        initListener();
        initMapStatus();
        return view;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnZoomDown) {
            sbMapSeekBar.setProgress(mapView.getZoomLevel() - 1);
            mMapController.setZoom(mapView.getZoomLevel() - 1);
        } else if (i == R.id.btnZoomUp) {
            sbMapSeekBar.setProgress(mapView.getZoomLevel() + 1);
            mMapController.setZoom(mapView.getZoomLevel() + 1);
        } else if (i == R.id.btnLocation) {
            animateCurrentLocation();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            sbMapSeekBar.setProgress(progress);
            mMapController.setZoom(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private void initView(View v) {
        mapView = v.findViewById(R.id.mapView);
        btnZoomUp = v.findViewById(R.id.btnZoomUp);
        btnZoomDown = v.findViewById(R.id.btnZoomDown);
        btnLocation = v.findViewById(R.id.btnLocation);
        sbMapSeekBar = v.findViewById(R.id.sbMapSeekBar);
        coverContainer = v.findViewById(R.id.cover_container);
        sbMapSeekBar.setMax(mapView.getMaxZoomLevel());
    }

    private void initListener() {
        btnZoomUp.setOnClickListener(this);
        btnZoomDown.setOnClickListener(this);
        btnLocation.setOnClickListener(this);
        sbMapSeekBar.setOnSeekBarChangeListener(this);
    }

    /**
     * 初始化地图显示
     */
    private void initMapStatus() {
        //定位信息
        MyLocationOverlay myLocationOverlay = new MyLocationOverlay(getAppContext(), mapView);
        myLocationOverlay.enableCompass();
        myLocationOverlay.enableMyLocation();
        mapView.addOverlay(myLocationOverlay);
        mCurrentGeoPoint = myLocationOverlay.getMyLocation();
        //地图设置
        mapView.setBuiltInZoomControls(false);
        mMapController = mapView.getController();
        mMapController.setZoom(12);
        sbMapSeekBar.setProgress(12);
        mMapController.setCenter(mCurrentGeoPoint);
        mMapController.animateTo(mCurrentGeoPoint);
    }

    /**
     * 移动到当前位置
     */
    private void animateCurrentLocation() {
        MyLocationOverlay myLocationOverlay = new MyLocationOverlay(getAppContext(), mapView);
        myLocationOverlay.enableCompass();
        myLocationOverlay.enableMyLocation();
        mapView.addOverlay(myLocationOverlay);
        mMapController.animateTo(myLocationOverlay.getMyLocation());
    }

    public MapView getMapView(){
        return mapView;
    }

    /**
     * 获取地址信息
     */
    protected void getLocationInfo(final OnLocationInfoListener listener){
        TGeoDecode geoDecode = new TGeoDecode(new TGeoDecode.OnGeoResultListener() {
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
                    listener.onLocationSuccess(tGeoAddress.getFullName());
                } else {
                    listener.onLocationError("查询出错:"+errorCode);
                }
            }
        });
        geoDecode.search(mCurrentGeoPoint);
    }


    public interface OnLocationInfoListener{
        void onLocationSuccess(String address);
        void onLocationError(String error);
    }
}
