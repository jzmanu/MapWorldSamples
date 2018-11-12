package com.manu.test.util;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.manu.test.bean.MarkerBean;
import com.manu.test.cluser.DefaultGeoPoint;
import com.manu.test.cluser.Kmeans;
import com.tianditu.android.maps.GeoPoint;

import org.w3c.dom.Node;

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

    private static String[] titles = {"1","2","3","4","1","2","3","4","1"};

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
            MarkerBean bean = new MarkerBean(titles[i], "snippet"+i,
                    String.valueOf((int)(points[i][0] * 1E6)), String.valueOf((int)(points[i][1] * 1E6)));
            beans.add(bean);
        }

        for (MarkerBean bean: beans){
            DefaultGeoPoint<MarkerBean> point = new DefaultGeoPoint<>(Integer.parseInt(bean.getLatitude()),Integer.parseInt(bean.getLongitude()),bean);
            point.attributes[0] = Double.parseDouble(bean.getLatitude());
            point.attributes[1] = Double.parseDouble(bean.getLongitude());
            list.add(point);
        }
        return list;
    }


    private ArrayList<DefaultGeoPoint<MarkerBean>> initData(ArrayList<MarkerBean> data) {
        ArrayList<DefaultGeoPoint<MarkerBean>> pointList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            MarkerBean bean = data.get(i);
            try {
                if (!TextUtils.isEmpty(bean.getLongitude()) && !TextUtils.isEmpty(bean.getLatitude())){
                    double longitude = Double.valueOf(bean.getLongitude());
                    double latitude = Double.valueOf(bean.getLatitude());
                    DefaultGeoPoint<MarkerBean> defaultGeoPoint = new DefaultGeoPoint<>(
                            (int)(latitude * 1E6), (int)(longitude * 1E6), bean);
//                    defaultGeoPoint.attributes[0] =
                    pointList.add(defaultGeoPoint);
                }
            } catch (NumberFormatException e) {
            }
        }

        return pointList;
    }

}
