package com.manu.mapworldsamples.samples.overlay;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.manu.mapworldsamples.R;
import com.manu.mapworldsamples.core.DefaultGeoPoint;
import com.manu.mapworldsamples.samples.bean.MarkerBean;
import com.tianditu.android.maps.ItemizedOverlay;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.OverlayItem;
import java.util.ArrayList;

/**
 * Powered by jzman.
 * Created on 2018/6/25 0025.
 */
public class MItemOverlay2 extends ItemizedOverlay<OverlayItem> {

    private MapView mapView;
    private View view;
    private TextView tvItemOverlay;
    private ArrayList<OverlayItem> items;
    private ArrayList<DefaultGeoPoint<MarkerBean>> points;
    private Context context;

    public MItemOverlay2(Context context,Drawable defaultMarker, ArrayList<DefaultGeoPoint<MarkerBean>> geoPoints, MapView mapView, View view) {
        super(defaultMarker);
        this.context = context;
        this.mapView = mapView;
        this.view = view;
        tvItemOverlay = view.findViewById(R.id.tvItemOverlayData);
        items = new ArrayList<>();
        points = geoPoints;
        for (int i = 0; i < points.size(); i++) {
//            OverlayItem overlayItem = new OverlayItem(points.get(i),
//                    points.get(i).getT().getTitle(),
//                    points.get(i).getT().getSnippet());
            OverlayItem overlayItem1 = new OverlayItem(points.get(i),null,null);
            overlayItem1.setMarker(defaultMarker);
            items.add(overlayItem1);
        }
        populate();
    }

    /**
     * 覆盖物条目总数
     * @return
     */
    @Override
    public int size() {
        return items.size();
    }

    /**
     * 创建覆盖物条目
     * @param i
     * @return
     */
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
//        mapView.updateViewLayout(view, new MapView.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                points.get(index),
//                MapView.LayoutParams.BOTTOM_CENTER));
//        tvItemOverlay.setText(points.get(index).getT().getTitle());
//        view.setVisibility(View.VISIBLE);

        Toast.makeText(context, ""+index, Toast.LENGTH_SHORT).show();
        return true;
    }
}
