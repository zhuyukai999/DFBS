package gdut.edu.datingforballsports.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import android.Manifest;
import android.content.pm.PackageManager;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/17.
 * 获取用户的地理位置
 */
public class GPSUtils {

    private static GPSUtils instance;
    private LocationManager locationManager;
    public static final int LOCATION_CODE = 1000;
    public static final int OPEN_GPS_CODE = 1001;

    public String province = "";

    public static GPSUtils getInstance() {
        if (instance == null) {
            instance = new GPSUtils();
        }
        return instance;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void getProvince(Context context, Callback callback) {
        Log.i("GPS: ", "getProvince");
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);        // 默认Android GPS定位实例
        Location location = null;
        // 是否已经授权
        if (context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //判断GPS是否开启，没有开启，则开启
            /*if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                //跳转到手机打开GPS页面
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                //设置完成后返回原来的界面
                context.startActivityForResult(intent,OPEN_GPS_CODE);
            }*/
//
//            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);      // GPS芯片定位 需要开启GPS
//            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);      // 利用网络定位 需要开启GPS
            location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);      // 其他应用使用定位更新了定位信息 需要开启GPS
            System.out.println("location:" + location);
        }
        if (location != null) {
            Log.i("GPS: ", "获取位置信息成功");
            Log.i("GPS: ", "经度：" + location.getLatitude());
            Log.i("GPS: ", "纬度：" + location.getLongitude());
            String detailedLocation = location.getLatitude() + "," + location.getLongitude();
            // 获取地址信息
            getLocationMsg(detailedLocation, callback);
        } else {
            Log.i("GPS: ", "获取位置信息失败，请检查是够开启GPS,是否授权");
        }
    }

    protected void getLocationMsg(String location, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.map.baidu.com/geocoder").newBuilder();
        urlBuilder.addQueryParameter("output", "json");
        urlBuilder.addQueryParameter("location", location);
        urlBuilder.addQueryParameter("ak", "esNPFDwwsXWtsQfw4NMNmur1");
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }
}
