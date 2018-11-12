package com.manu.mapworldsamples.samples.overlay;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.manu.mapworldsamples.R;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.ItemizedOverlay;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.OverlayItem;

import java.util.ArrayList;

/**
 * Powered by jzman.
 * Created on 2018/6/22 0022.
 */
public class MItemOverlay extends ItemizedOverlay<OverlayItem> {

    private MapView mapView;
    private View view;
    private TextView tvItemOverlay;
    private ArrayList<OverlayItem> items;
    private ArrayList<GeoPoint> points;

    public MItemOverlay(Drawable defaultMarker, ArrayList<GeoPoint> geoPoints,MapView mapView,View view) {
        super(defaultMarker);
        this.mapView = mapView;
        this.view = view;
        tvItemOverlay = view.findViewById(R.id.tvItemOverlayData);
        items = new ArrayList<>();
        points = geoPoints;
        for (int i = 0; i < points.size(); i++) {
            OverlayItem overlayItem = new OverlayItem(points.get(i),"GeoPoint"+i,i+"");
            overlayItem.setMarker(defaultMarker);
            items.add(overlayItem);
        }
        //数据准备完成后调用该方法
        populate();
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    protected OverlayItem createItem(int i) {
        return items.get(i);
    }

    /**
     * 处理点击事件
     * @param index
     * @return
     */
    @Override
    protected boolean onTap(int index) {
        if (index == -1) return false;
        System.out.println("onTap(1)"+index);
        ViewParent v = view.getParent();
        mapView.updateViewLayout(view, new MapView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                points.get(index),
                MapView.LayoutParams.BOTTOM_CENTER));
        tvItemOverlay.setText(points.get(index).getLatitudeE6()+"");
        view.setVisibility(View.VISIBLE);
        return true;
    }

    @Override
    public boolean onTap(GeoPoint geoPoint, MapView mapView) {
        System.out.println("onTap(2)");
        return super.onTap(geoPoint, mapView);
    }
}
