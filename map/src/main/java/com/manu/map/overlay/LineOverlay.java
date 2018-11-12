package com.manu.map.overlay;

import android.graphics.Color;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MapViewRender;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.renderoption.LineOption;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

/**
 * Powered by jzman.
 * Created on 2018/7/2 0002.
 */
public class LineOverlay extends Overlay {
    private ArrayList<GeoPoint> mPoints;
    private LineOption mLineOption;

    public LineOverlay(ArrayList<GeoPoint> mPoints) {
        this.mPoints = mPoints;
        mLineOption = new LineOption();
        setUpLineOption(mLineOption);
    }

    protected void setUpLineOption(LineOption option) {
        option.setDottedLine(false);
//        option.setIntervals(new int[]{10,100});
        option.setStrokeColor(Color.RED);
        option.setStrokeWidth(10);
    }

    @Override
    public boolean draw(GL10 gl, MapView mapView, boolean shadow, long when) {
        MapViewRender render = mapView.getMapViewRender();
        render.drawPolyLine(gl,mLineOption,mPoints);
        return true;
    }

}
