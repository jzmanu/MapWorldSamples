package com.manu.mapworldsamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.manu.mapworldsamples.samples.MapShowActivity;
import com.manu.mapworldsamples.samples.MultiMarkersActivity;
import com.manu.mapworldsamples.samples.TestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnMapShow)
    Button btnMapShow;
    @BindView(R.id.btnShowMarker)
    Button btnShowMarker;
    @BindView(R.id.btnBaseFragment)
    Button btnBaseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnMapShow, R.id.btnShowMarker})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btnMapShow:
                startActivity(new Intent(this, MapShowActivity.class));
                break;
            case R.id.btnShowMarker:
                startActivity(new Intent(this, MultiMarkersActivity.class));
                break;
            case R.id.btnBaseFragment:
                startActivity(new Intent(this, TestActivity.class));
                break;
        }
    }

}
