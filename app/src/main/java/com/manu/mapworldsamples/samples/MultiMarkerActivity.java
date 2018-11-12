package com.manu.mapworldsamples.samples;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.manu.mapworldsamples.R;
import com.manu.mapworldsamples.samples.fragment.NativeItemOverlayFragment;
import com.manu.mapworldsamples.samples.fragment.WrapItemOverlayFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultiMarkerActivity extends AppCompatActivity {

    @BindView(R.id.btnShowMarker)
    Button btnShowMarker;
    @BindView(R.id.flContainer)
    FrameLayout flContainer;
    @BindView(R.id.btnWrapMarker)
    Button btnWrapMarker;

    private NativeItemOverlayFragment mNativeItemOverlayFragment;
    private WrapItemOverlayFragment mWrapItemOverlayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mult_marker);
        ButterKnife.bind(this);
        initFragmentStatus();
    }

    private void initFragmentStatus() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        mNativeItemOverlayFragment = (NativeItemOverlayFragment) manager.findFragmentByTag("NativeItemOverlayFragment");
        if (mNativeItemOverlayFragment == null) {
            mNativeItemOverlayFragment = new NativeItemOverlayFragment();
            transaction.add(R.id.flContainer, mNativeItemOverlayFragment, "NativeItemOverlayFragment");
        }

        mWrapItemOverlayFragment = (WrapItemOverlayFragment) manager.findFragmentByTag("WrapItemOverlayFragment");
        if (mWrapItemOverlayFragment == null) {
            mWrapItemOverlayFragment = new WrapItemOverlayFragment();
            transaction.add(R.id.flContainer, mWrapItemOverlayFragment, "WrapItemOverlayFragment");
        }

        transaction.hide(mWrapItemOverlayFragment).show(mNativeItemOverlayFragment);
        transaction.commit();
    }

    @OnClick({R.id.btnShowMarker, R.id.btnWrapMarker})
    public void onViewClicked(View v) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (v.getId()) {
            case R.id.btnShowMarker:
                transaction.hide(mWrapItemOverlayFragment).show(mNativeItemOverlayFragment);
                break;
            case R.id.btnWrapMarker:
                transaction.hide(mNativeItemOverlayFragment).show(mWrapItemOverlayFragment);
                break;
        }
        transaction.commit();
    }

}
