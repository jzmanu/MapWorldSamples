package com.manu.test.cluser;

import com.tianditu.android.maps.GeoPoint;


/**
 * Powered by jzman.
 * Created on 2018/8/02 0025.
 */
public class DefaultGeoPoint<T> extends GeoPoint {

    private T t;
    public int label;// label 用来记录点属于第几个 cluster
    public double[] attributes;

    public DefaultGeoPoint() {
        super(0,0);
        attributes = new double[100];
    }

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
