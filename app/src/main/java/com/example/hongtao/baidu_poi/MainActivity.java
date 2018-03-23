package com.example.hongtao.baidu_poi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.TextureMapView;

public class MainActivity extends AppCompatActivity {
    TextureMapView mMapView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
       // mMapView = (TextureMapView) findViewById(R.id.bmapView);
    }
    //一下方法用于跳转到另外来那个activity
    public void skipToOne(View v){
        if(v.getId()==R.id.activity1){
            Intent intent = new Intent(MainActivity.this,Activity1.class);
            startActivity(intent);
        }
    }
    public void skipToTwo(View v){
        if(v.getId()==R.id.activity2){
            Intent intent = new Intent(MainActivity.this,Activity2.class);
            startActivityForResult(intent,111);
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        //mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        //mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
       // mMapView.onPause();
    }
}
