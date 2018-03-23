package com.example.hongtao.baidu_poi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

class Activity2 extends AppCompatActivity implements OnGetPoiSearchResultListener{
    private final int requestCode = 1;
    private PoiSearch mPoiSearch = null;
    private int loadIndex = 0;
    private String path = "";
    int radius = 1000;
    String[] strings = new String[5];
    String[] middleResult = new String[20];
    LatLng [] latLngs = new LatLng[20];

    int i =0;
    int j =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        //寻找应用一所得到的经纬度信息文件
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,requestCode);
            }
        });
    }
    public void  searchNearbyProcess(View v) {

        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption().keyword(strings[i]).
                sortType(PoiSortType.distance_from_near_to_far).location(latLngs[j]).radius(radius).pageNum(loadIndex);

        mPoiSearch.searchNearby(nearbySearchOption);
    }

    public void onGetPoiResult(PoiResult var1){

    }

    public void onGetPoiDetailResult(PoiDetailResult var1){

    }

    public void onGetPoiIndoorResult(PoiIndoorResult var1){}
}
