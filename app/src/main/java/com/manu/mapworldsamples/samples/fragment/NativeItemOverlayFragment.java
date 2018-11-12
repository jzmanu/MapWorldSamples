package com.manu.mapworldsamples.samples.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.manu.mapworldsamples.R;
import com.manu.mapworldsamples.samples.overlay.MItemOverlay;
import com.manu.mapworldsamples.samples.util.DataUtil;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Powered by jzman.
 * Created on 2018/6/22 0022 15:34.
 */
public class NativeItemOverlayFragment extends Fragment {

    @BindView(R.id.mapView)
    MapView mapView;

    private ArrayList<GeoPoint> points;

    public NativeItemOverlayFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_native_item_overlay, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popupwindow_layout,null);
        mapView.addView(view,new MapView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                null,
                MapView.LayoutParams.TOP_LEFT));
        view.setVisibility(View.GONE);
        points = DataUtil.getData();
        Drawable drawable = getResources().getDrawable(R.drawable.selected_marker);
        MItemOverlay mItemOverlay = new MItemOverlay(drawable,points,mapView,view);
        mapView.getOverlays().add(mItemOverlay);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden){

        }else{
            mapView.invalidate();
        }
    }
}
