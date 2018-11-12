package com.manu.mapworldsamples.samples.util;

import com.manu.mapworldsamples.core.DefaultGeoPoint;
import com.manu.mapworldsamples.samples.bean.MarkerBean;
import com.tianditu.android.maps.GeoPoint;

import java.util.ArrayList;

/**
 * Powered by jzman.
 * Created on 2018/6/22 0022.
 */
public class DataUtil {

    private static double[][] points = new double[][]{
            {39.90923, 116.397428}, {39.9022, 116.3922}, {39.917723, 116.3722},
            {39.891826, 116.366157}, {39.885768, 116.394996}, {39.929485, 116.421089},
            {39.888664, 116.424522}, {39.910263, 116.353454}, {39.911315, 116.416282}};

    public static ArrayList<GeoPoint> getData(){
        ArrayList<GeoPoint> list = new ArrayList<>();
        for (double[] point1 : points) {
            int latitude = (int) (point1[0] * 1E6);
            int longitude = (int) (point1[1] * 1E6);
            GeoPoint point = new GeoPoint(latitude, longitude);
            list.add(point);
        }
        return list;
    }

    public static ArrayList<DefaultGeoPoint<MarkerBean>> getRealData(){
        ArrayList<DefaultGeoPoint<MarkerBean>> list = new ArrayList<>();
        ArrayList<MarkerBean> beans = new ArrayList<>();
        for (int i = 0; i < points.length; i++){
            MarkerBean bean = new MarkerBean("title"+i, "snippet"+i,
                    (int) (points[i][0] * 1E6), (int) (points[i][1] * 1E6));
            beans.add(bean);
        }

        for (MarkerBean bean: beans){
            DefaultGeoPoint<MarkerBean> point = new DefaultGeoPoint<>(bean.getLatitude(),bean.getLongitude(),bean);
            list.add(point);
        }

        return list;
    }
}
