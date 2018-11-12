package com.manu.cluser.util;

import com.tianditu.android.maps.GeoPoint;


/**
 * Powered by jzman.
 * Created on 2018/8/02 0025.
 */
public class DefaultGeoPoint<T extends IMarkerBean> extends GeoPoint implements  IMarker{

    private T t;

    public DefaultGeoPoint(int latitudeE6, int longitudeE6, T t) {
        super(latitudeE6, longitudeE6);
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    @Override
    public double getLatitude() {
        return t.getLatitude();
    }

    @Override
    public double getLongitude() {
        return t.getLongitude();
    }
}
