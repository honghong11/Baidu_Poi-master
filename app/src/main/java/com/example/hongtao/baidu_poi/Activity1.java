package com.example.hongtao.baidu_poi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Activity1 extends AppCompatActivity implements OnGetGeoCoderResultListener {
    private final int requestCode = 1;
    TextView textView;
    GeoCoder msearch = null;
    private String[] strings2;
    String [][] strings3 = new String[21][2];
    int count = 0;
    int number = 1;
    int END = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        // 初始化搜索模块，注册事件监听
        msearch = GeoCoder.newInstance();
        msearch.setOnGetGeoCodeResultListener(this);


        Button button = (Button)findViewById(R.id.fileToStringArray);
        textView = (TextView)findViewById(R.id.text);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,requestCode);
            }
        });
        for(int i =0;i<20;i++){
            strings3[i][0] = "a";
            strings3[i][1] = "a";
        }
    }
    String path = "";
    String result = "";
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            path = uri.getPath();
            //textView.setText(path);
            String Locations[] = new String[21];
            try{
                FileInputStream fileInputStream = new FileInputStream(path);
                textView.setText(path);
                int length = fileInputStream.available();
                byte []buffer = new byte[length];
                final int out;
                out = fileInputStream.read(buffer);
                if(out != -1){
                    String string = new String(buffer,"utf-8");
                    final String []strings;
                    strings =  string.split("\\]");
                    String []strings1 = strings[0].split("\\[");
                    strings2 = strings1[1].split(",");
                    int i =0;
                    for(;i<20;i++){
                        strings2[i] = strings2[i].replace("\"","").replace("\"","");
                    }

                    Button button1 = (Button)findViewById(R.id.qurryTheLgtandLot);
                    button1.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            String City = "成都";
                            int j = 0;
                            for(;j<20;j++){
                                textView.setText(strings2[j]);
                                msearch.geocode(new GeoCodeOption().city(City).address(strings2[j]));
                            }
//                              //将结果写到文件中
//                              String string ="";
//                              j =0;
//                              for(;j<=5;j++){
//                                  string = string + strings3[j][0]+","+strings3[j][1];
//                              }
//                              //Toast.makeText(Activity1.this, string, Toast.LENGTH_LONG).show();
//                              //number是为了之后自动读取所有的json文件生成对应的txt文件
//                              File file = new File("/storage/emulated/0/hongtao/"+number+".txt");
//                              FileOutputStream outputStream = null;
//                              number++;
//                              if(!file.exists()){
//                                  try{
//                                      file.createNewFile();
//                                  }catch (IOException e){
//                                      e.printStackTrace();
//                                  }
//                              }
//                              try{
//                                  outputStream = new FileOutputStream(file);
//                                  outputStream.write(string.getBytes());
//                                  outputStream.flush();
//                                  outputStream.close();
//                              }catch (FileNotFoundException e){
//                                  e.printStackTrace();
//                              }catch (IOException e){
//                                  e.printStackTrace();
//                              }
                        }
                    });
                    //在获取经纬度后再转到下一个任务
//                      Intent intent = new Intent(this,Activity2.class);
//                      intent.putExtra("middleString",strings2);
//                      startActivity(intent);
                    fileInputStream.close();
                }else{
                    textView.setText("has some questions");
                }
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void onGetGeoCodeResult(GeoCodeResult result){
        TextView textView = (TextView) findViewById(R.id.text1);
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(Activity1.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
                    .show();
            //这个return就是我的strings3中不能顺序赋值的原因.
            strings3[count][0] = "1";
            strings3[count][1] = "1";
            count++;
            return;
        }
        String strInfo = String.format("纬度：%f 经度：%f",
                result.getLocation().latitude, result.getLocation().longitude);
        Toast.makeText(Activity1.this, strInfo, Toast.LENGTH_LONG).show();
         strings3[count][0] = String.valueOf(result.getLocation().latitude);
         strings3[count][1] = String.valueOf(result.getLocation().longitude);
         count++;


        //将结果写到文件中
        // 当收到所有的结果后再写文件。标志位END
        String string ="";
        int j =0;
        for(;j<count;j++){
            string = string + strings3[j][0]+","+strings3[j][1]+"-"+strings2[j]+"+";
        }
        if(count == 20){
            Toast.makeText(Activity1.this, string+"hhhh", Toast.LENGTH_LONG).show();
            //number是为了之后自动读取所有的json文件生成对应的txt文件
            File file = new File("/storage/emulated/0/hongtao/"+number+".txt");
            FileOutputStream outputStream = null;
            number++;
            if(!file.exists()){
                try{
                    file.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            try{
                outputStream = new FileOutputStream(file);
                outputStream.write(string.getBytes());
                outputStream.flush();
                outputStream.close();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult var1){

    }
}
