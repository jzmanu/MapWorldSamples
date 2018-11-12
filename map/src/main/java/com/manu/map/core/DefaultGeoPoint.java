package com.manu.map.core;

import com.tianditu.android.maps.GeoPoint;


/**
 * Powered by jzman.
 * Created on 2018/8/02 0025.
 */
public class DefaultGeoPoint<T> extends GeoPoint {

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
}
