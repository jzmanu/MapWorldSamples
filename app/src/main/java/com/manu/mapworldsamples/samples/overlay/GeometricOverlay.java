package com.manu.mapworldsamples.samples.overlay;

import android.graphics.Color;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MapViewRender;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.renderoption.CircleArcOption;

import javax.microedition.khronos.opengles.GL10;

/**
 * Powered by jzman.
 * Created on 2018/6/25 0025.
 */
public class GeometricOverlay extends Overlay{
    private CircleArcOption mCircleArcOption;
    private GeoPoint mGeoPoint;

    public GeometricOverlay() {
        mCircleArcOption = new CircleArcOption();
        //设置覆盖物是否是虚线
        mCircleArcOption.setDottedLine(true);
        //设置填充颜色
        mCircleArcOption.setFillColor(Color.GRAY);
        //设置边框颜色
        mCircleArcOption.setStrokeColor(Color.BLACK);
        //设置边框宽度
        mCircleArcOption.setStrokeWidth(5);
        //是否使用中心点,设置true,绘制出的是扇形，反之则是圆弧
        mCircleArcOption.setUseCenter(true);
        //设置起始角度以及扫描角度
        mCircleArcOption.setAngle(0,100);
    }

    @Override
    public boolean draw(GL10 gl, MapView mapView, boolean shadow, long when) {
        MapViewRender render = mapView.getMapViewRender();
        render.drawCircleArc(gl,mCircleArcOption,mGeoPoint,1000);
        return true;
    }

    @Override
    public boolean onTap(GeoPoint p, MapView mapView) {
        //指定当前点击的位置添加覆盖物，也可以自己指定
        mGeoPoint = p;
        return true;
    }
}
