package com.manu.mapworldsamples.widget;

import android.content.Context;
import android.graphics.Point;
import android.location.Location;

import com.manu.mapworldsamples.R;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MyLocationOverlay;
import com.tianditu.maps.AndroidJni;
import com.tianditu.maps.Texture.UtilTextureDrawable;
import javax.microedition.khronos.opengles.GL10;

import static com.tianditu.maps.Texture.UtilTextureBase.BoundType.BOUND_TYPE_CENTER;


/**
 * Powered by jzman.
 * Created on 2018/6/21 0021.
 */
public class MLocationOverlay extends MyLocationOverlay {

    private Context mContext;

    public MLocationOverlay(Context context, MapView mapView) {
        super(context, mapView);
        this.mContext = context;
    }

    @Override
    protected void drawMyLocation(GL10 gl, MapView mapView, Location lastFix, GeoPoint myLocation, long when) {
        //获得屏幕坐标
        Point point = new Point();
        mapView.getProjection().toPixels(myLocation,point);
        //精度范围
        float accuracy = getAccuracy();
        //获得以米为单位的距离对应屏幕的距离
        float distance = mapView.getProjection().metersToEquatorPixels(500);
        AndroidJni.OpenglFillRound(point.x, point.y, (int)distance, 0, 360, 137, 170, 213, 77);
        //创建Drawable
        UtilTextureDrawable drawable = new UtilTextureDrawable(mContext, R.drawable.ic_location, BOUND_TYPE_CENTER);
        drawable.DrawTexture(gl,point,0.0F);
    }
}
