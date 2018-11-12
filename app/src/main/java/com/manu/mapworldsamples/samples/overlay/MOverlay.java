package com.manu.mapworldsamples.samples.overlay;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.manu.mapworldsamples.R;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MapViewRender;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.renderoption.DrawableOption;

import javax.microedition.khronos.opengles.GL10;

/**
 * Powered by jzman.
 * Created on 2018/6/25 0025.
 */
public class MOverlay extends Overlay{
    private Drawable mDrawable;
    private DrawableOption mDrawableOption;
    private GeoPoint mGeoPoint;

    public MOverlay(Context context) {
        mDrawable = context.getResources().getDrawable(R.drawable.selected_marker);
        mDrawableOption = new DrawableOption();
        //设置锚点位置
        mDrawableOption.setAnchor(0.5f,1.0f);
        //设置旋转角度
        mDrawableOption.setRotate(0);
        //设置状态
        mDrawableOption.setState(DrawableOption.STATE_NORMAL);
        //设置多少帧刷新一次图片资源
        mDrawableOption.setPeriod(200);
    }

    @Override
    public boolean draw(GL10 gl, MapView mapView, boolean shadow, long when) {
        MapViewRender render = mapView.getMapViewRender();
        render.drawDrawable(gl,mDrawableOption,mDrawable,mGeoPoint);
        return true;
    }

    @Override
    public boolean onTap(GeoPoint p, MapView mapView) {
        //指定当前点击的位置添加覆盖物，也可以自己指定
        mGeoPoint = p;
        return true;
    }
}
