package com.zhongchuangtiyu.denarau.Demos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zhongchuangtiyu.denarau.R;
import com.zhongchuangtiyu.denarau.Utils.ActivityCollector;

/**
 * 作者：WangMeng on 2015/11/10 12:26
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class Test2 extends Activity
{

    public TextView show_position;
    public Button getPositionOnce;
    private LocationClient mLocationClient;
    private LocationClientOption mOption;
    public double Latitude, Longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);
        show_position = (TextView) findViewById(R.id.show_position);
        getPositionOnce = (Button) findViewById(R.id.control_button);
        mLocationClient = new LocationClient(this);
        mOption = new LocationClientOption();
    /* 设置选项 */
        mOption.setOpenGps(true);
        mOption.setCoorType("bd09ll");
        mOption.setScanSpan(100);		   //每隔0.1s, 扫描一次 (应该就是卫星定位的意思)
    /* 本地取址Client 端设置 Option选项 */
        mLocationClient.setLocOption(mOption);
    /* 设置监听器，监听服务器发送过来的地址信息 */
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if(bdLocation == null)
                    return;
                StringBuffer sb = new StringBuffer(256);
        /* 获取经纬度 */
                Latitude = bdLocation.getLatitude();
                Longitude = bdLocation.getLongitude();
                sb.append("latitude:  "+Latitude+"\n");
                sb.append("longitude: "+Longitude);
                show_position.setText(sb.toString());
                mLocationClient.stop();
            }
        });
        getPositionOnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLocationClient == null)
                    return;
                mLocationClient.start();
            }
        });
    }
    @Override
    public void onDestroy() {
        if(mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
            mLocationClient=null;
        }
        super.onDestroy();
    }

}
