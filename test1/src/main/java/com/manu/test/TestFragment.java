package com.manu.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manu.map.BaseMapFragment;
import com.manu.map.overlay.LineOverlay;
import com.manu.test.app.MApplication;
import com.manu.test.bean.MarkerBean;
import com.manu.test.overlay.MultiOverlay;
import com.manu.test.util.DataUtil;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.overlay.MarkerOverlay;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends BaseMapFragment implements BaseMapFragment.OnLocationInfoListener, MultiOverlay.OnMarkerItemClickListener{

    private Activity activity;

    public TestFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public Context getAppContext() {
        return activity == null ? new MApplication().getInstance() : activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater,container,savedInstanceState);
        inflater.inflate(R.layout.fragment_test, coverContainer, true);
        getLocationInfo(this);
//        drawLine();
        initData();
        return rootView;
    }

    private void initData() {
        Drawable drawable = getResources().getDrawable(R.drawable.drawable_ic_map_red);
        MultiOverlay markerOverlay = new MultiOverlay(getAppContext(),drawable,DataUtil.getRealData());
        markerOverlay.setOnMarkerItemClickListener(this);
        getMapView().getOverlays().add(markerOverlay);
        getMapView().invalidate();
        GeoPoint point = new GeoPoint((int)(39.90923 * 1E6),(int) (116.397428 * 1E6));
        mMapController.animateTo(point);
    }

    @Override
    public void onLocationSuccess(String address) {
        System.out.println(address);
    }

    @Override
    public void onLocationError(String error) {

    }

    private void drawLine(){
        LineOverlay overlay = new LineOverlay(DataUtil.getData());
        getMapView().addOverlay(overlay);
    }

    @Override
    public void onMarkerItemClick(MarkerBean bean) {

    }
}
