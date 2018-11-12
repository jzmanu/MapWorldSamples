package com.manu.cluser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.manu.cluser.data.DataUtil;
import com.manu.cluser.data.MarkerBean;
import com.manu.cluser.util.DefaultGeoPoint;
import com.manu.cluser.util.MarkerCluster;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MarkerCluster markerCluster = new MarkerCluster(50, 100);

        List<DefaultGeoPoint<MarkerBean>> points = DataUtil.getRealData();
        markerCluster.cluster(points);
    }
}
