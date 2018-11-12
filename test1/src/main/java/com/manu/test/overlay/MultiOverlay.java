package com.manu.test.overlay;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.manu.test.R;
import com.manu.test.bean.MarkerBean;
import com.manu.test.cluser.DefaultGeoPoint;
import com.tianditu.android.maps.OverlayItem;

import java.util.ArrayList;


/**
 * Powered by jzman.
 * Created on 2018/8/2 0002.
 */
public class MultiOverlay extends MultiMarkerOverlay<MarkerBean> {

    private OnMarkerItemClickListener onMarkerItemClickListener;

    public void setOnMarkerItemClickListener(OnMarkerItemClickListener onMarkerItemClickListener) {
        this.onMarkerItemClickListener = onMarkerItemClickListener;
    }

    public MultiOverlay(Context context, Drawable defaultMarker, ArrayList<DefaultGeoPoint<MarkerBean>> defaultGeoPoints) {
        super(context, defaultMarker, defaultGeoPoints);
    }

    @Override
    protected void addMultiMarker(ArrayList<DefaultGeoPoint<MarkerBean>> defaultGeoPoints) {
        for (int i = 0; i < defaultGeoPoints.size(); i++) {
            OverlayItem overlayItem = new OverlayItem(defaultGeoPoints.get(i),null,null);

            if ("1".equals(defaultGeoPoints.get(i).getT().getTitle())){
                Drawable drawable = context.getResources().getDrawable(R.drawable.drawable_ic_map_red);
                overlayItem.setMarker(drawable);
            }else if ("2".equals(defaultGeoPoints.get(i).getT().getTitle())){
                Drawable drawable = context.getResources().getDrawable(R.drawable.drawable_ic_map_orange);
                overlayItem.setMarker(drawable);
            }else if ("3".equals(defaultGeoPoints.get(i).getT().getTitle())){
                Drawable drawable = context.getResources().getDrawable(R.drawable.drawable_ic_map_yellow);
                overlayItem.setMarker(drawable);
            }else if ("4".equals(defaultGeoPoints.get(i).getT().getTitle())){
                Drawable drawable = context.getResources().getDrawable(R.drawable.drawable_ic_map_blue);
                overlayItem.setMarker(drawable);
            }
            items.add(overlayItem);
        }
        System.out.println("----------items--------->"+items.size());
    }

    @Override
    protected boolean onTap(int index) {
        if (index == -1) return false;
        showFocusAlways(false);
        if (onMarkerItemClickListener!=null){
            onMarkerItemClickListener.onMarkerItemClick(getPointList().get(index).getT());
        }
        return true;
    }

    public interface OnMarkerItemClickListener{
        void onMarkerItemClick(MarkerBean bean);
    }
}
