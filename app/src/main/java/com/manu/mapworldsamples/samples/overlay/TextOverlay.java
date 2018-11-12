package com.manu.mapworldsamples.samples.overlay;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.manu.mapworldsamples.R;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MapViewRender;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.renderoption.CircleArcOption;
import com.tianditu.android.maps.renderoption.FontOption;

import javax.microedition.khronos.opengles.GL10;

/**
 * Powered by jzman.
 * Created on 2018/6/25 0025.
 */
public class TextOverlay extends Overlay{

    private FontOption mFontOption;
    private GeoPoint mGeoPoint;

    public TextOverlay(Context context) {
        mFontOption = new FontOption();
        //设置填充颜色
        mFontOption.setFillColor(Color.RED);
        //设置字体颜色、字体大小
        mFontOption.setFontColor(Color.BLACK);
        mFontOption.setFontSize(60);
        //设置文字边框宽度、边框颜色
        mFontOption.setStrokeWidth(4);
        mFontOption.setStrokeColor(Color.GRAY);
        //设置文字旋转角度
        mFontOption.setTextRotate(60);
        //设置字体
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/font.ttf");
        mFontOption.setFontTypeface(typeface);
    }

    @Override
    public boolean draw(GL10 gl, MapView mapView, boolean shadow, long when) {
        MapViewRender render = mapView.getMapViewRender();
        if (mGeoPoint!=null){
            render.drawText(gl,mFontOption,"这是文字覆盖物",mGeoPoint);
        }
        return true;
    }

    @Override
    public boolean onTap(GeoPoint p, MapView mapView) {
        //指定当前点击的位置添加覆盖物，也可以自己指定
        mGeoPoint = p;
        return true;
    }
}
