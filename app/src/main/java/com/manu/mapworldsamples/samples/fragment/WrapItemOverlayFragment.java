package com.manu.mapworldsamples.samples.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manu.mapworldsamples.R;
import com.tianditu.android.Overlay.TextManager;
import com.tianditu.android.maps.MapView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Powered by jzman.
 * Created on 2018/6/22 0022 15:49.
 */
public class WrapItemOverlayFragment extends Fragment {

    @BindView(R.id.mapView1)
    MapView mapView;

    public WrapItemOverlayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wrap_item_overlay, container, false);
        ButterKnife.bind(this, view);
        mapView.removeCache();
        return view;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden){

        }else{
            mapView.removeAllOverlay();
            mapView.invalidate();
        }
    }
}
