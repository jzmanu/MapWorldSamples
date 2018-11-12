package com.manu.mapworldsamples.samples;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.manu.mapworldsamples.R;
import com.manu.mapworldsamples.core.DefaultGeoPoint;
import com.manu.mapworldsamples.samples.bean.MarkerBean;
import com.manu.mapworldsamples.samples.overlay.GeometricOverlay;
import com.manu.mapworldsamples.samples.overlay.MItemOverlay;
import com.manu.mapworldsamples.samples.overlay.MItemOverlay2;
import com.manu.mapworldsamples.samples.overlay.MOverlay;
import com.manu.mapworldsamples.samples.overlay.TextOverlay;
import com.manu.mapworldsamples.samples.util.DataUtil;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultiMarkersActivity extends AppCompatActivity {

    @BindView(R.id.btnShowMarker)
    Button btnShowMarker;
    @BindView(R.id.btnWrapMarker)
    Button btnWrapMarker;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.btnSingleMarker)
    Button btnSingleMarker;
    @BindView(R.id.btnGeometricOverlay)
    Button btnGeometricOverlay;
    @BindView(R.id.btnTextOverlay)
    Button btnTextOverlay;

    private ArrayList<GeoPoint> points;
    private ArrayList<DefaultGeoPoint<MarkerBean>> markers;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_markers);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        view = LayoutInflater.from(this).inflate(R.layout.popupwindow_layout, null);
        mapView.addView(view, new MapView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                null,
                MapView.LayoutParams.TOP_LEFT));
        view.setVisibility(View.GONE);

    }

    @OnClick({R.id.btnShowMarker, R.id.btnWrapMarker, R.id.btnSingleMarker,R.id.btnGeometricOverlay, R.id.btnTextOverlay})
    public void onViewClicked(View v) {
        mapView.removeAllOverlay();
        mapView.invalidate();
        view.setVisibility(View.GONE);
        switch (v.getId()) {
            case R.id.btnShowMarker:
                points = DataUtil.getData();
                Drawable drawable = getResources().getDrawable(R.drawable.selected_marker);
                MItemOverlay mItemOverlay = new MItemOverlay(drawable, points, mapView, view);
                mapView.getOverlays().add(mItemOverlay);
                break;
            case R.id.btnWrapMarker:
                markers = DataUtil.getRealData();
                Drawable drawable1 = getResources().getDrawable(R.drawable.selected_marker);
                MItemOverlay2 mItemOverlay2 = new MItemOverlay2(this,drawable1, markers, mapView, view);
                mapView.getOverlays().add(mItemOverlay2);
                break;
            case R.id.btnSingleMarker:
                mapView.addOverlay(new MOverlay(this));
                break;
            case R.id.btnGeometricOverlay:
                mapView.addOverlay(new GeometricOverlay());
                break;
            case R.id.btnTextOverlay:
                mapView.addOverlay(new TextOverlay(this));
                break;

        }
    }
}
