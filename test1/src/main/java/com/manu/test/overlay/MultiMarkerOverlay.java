package com.manu.test.overlay;


import android.content.Context;
import android.graphics.drawable.Drawable;

import com.manu.test.cluser.DefaultGeoPoint;
import com.tianditu.android.maps.ItemizedOverlay;
import com.tianditu.android.maps.OverlayItem;

import java.util.ArrayList;

/**
 * Powered by jzman.
 * Created on 2018/8/02 0025.
 */
public abstract class MultiMarkerOverlay<T> extends ItemizedOverlay<OverlayItem> {

    protected Context context;
    protected ArrayList<OverlayItem> items;
    private ArrayList<DefaultGeoPoint<T>> points;

    public MultiMarkerOverlay(Context context, Drawable defaultMarker, ArrayList<DefaultGeoPoint<T>> geoPoints) {
        super(defaultMarker);
        this.context = context;
        items = new ArrayList<>();
        points = geoPoints;
        addMultiMarker(geoPoints);
        populate();
    }

    protected abstract void addMultiMarker(ArrayList<DefaultGeoPoint<T>> geoPoints) ;

    protected ArrayList<DefaultGeoPoint<T>> getPointList(){
        if (points!=null){
            return points;
        }
        return null;
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
}
