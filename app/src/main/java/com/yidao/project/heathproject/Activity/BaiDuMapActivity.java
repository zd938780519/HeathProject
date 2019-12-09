package com.yidao.project.heathproject.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.mchsdk.paysdk.mylibrary.BaseActivity;
import com.mchsdk.paysdk.retrofitutils.result.HttpResponseException;
import com.mchsdk.paysdk.retrofitutils.rxjava.observable.SchedulerTransformer;
import com.mchsdk.paysdk.retrofitutils.rxjava.observer.BaseObserver;
import com.yidao.project.heathproject.Adapters.GuideAdpter;
import com.yidao.project.heathproject.Beans.FTJBean;
import com.yidao.project.heathproject.Beans.RunBean;
import com.yidao.project.heathproject.MainActivity;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.RxJavaUtils.RetrofitHttpUtil;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Action;

public class BaiDuMapActivity extends BaseActivity implements SensorEventListener {
    StringBuilder currentPosition = new StringBuilder();
    // 定位相关
    LocationClient mLocClient;
    private BaiDuMapActivity instance;
    public MyLocationListenner myListener = new MyLocationListenner();
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private  String remov = "";
    private double juli;

    MapView mMapView;
    BaiduMap mBaiduMap;

    private TextView info;
    private RelativeLayout progressBarRl;

    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    float mCurrentZoom = 18f;//默认地图缩放比例值

    private SensorManager mSensorManager;
    private int userId;

    //起点图标
    BitmapDescriptor startBD = BitmapDescriptorFactory.fromResource(R.drawable.ic_me_history_startpoint);
    //终点图标
    BitmapDescriptor finishBD = BitmapDescriptorFactory.fromResource(R.drawable.ic_me_history_finishpoint);

    List<LatLng> points = new ArrayList<LatLng>();//位置点集合
    Polyline mPolyline;//运动轨迹图层
    LatLng last = new LatLng(0, 0);//上一个定位点
    MapStatus.Builder builder;
    LatLng p1;
    LatLng p2;
    private static final int TIME = 2000;
    private static final int GO_HOME = 1;




    private double sum_distance = 0;
    private final double EARTH_RADIUS = 6378137.0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidumap);
        instance = this;
        initView();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);// 获取传感器管理服务

        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 开启交通图
        mBaiduMap.setTrafficEnabled(true);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory
                .newMapStatus(new MapStatus.Builder().zoom(17).build()));// 设置缩放级别
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.FOLLOWING, true, null));

        /**
         * 添加地图缩放状态变化监听，当手动放大或缩小地图时，拿到缩放后的比例，然后获取到下次定位，
         *  给地图重新设置缩放比例，否则地图会重新回到默认的mCurrentZoom缩放比例
         */
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
                mCurrentZoom = arg0.zoom;
            }

            @Override
            public void onMapStatusChange(MapStatus arg0) {
                // TODO Auto-generated method stub

            }
        });

        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);//只用gps定位，需要在室外定位。
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setOpenGps(true); // 打开gps
        //option.setNeedDeviceDirect(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);

        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);
        mLocClient.setLocOption(option);
        mLocClient.start();
//        //设置并显示中心点
//        setPosition2Center(mBaiduMap, location, true);


    }


    /**
     * 跳转到聊天界面
     */
    private void goHome() {
        if (!remov.equals("")){

            setRunData(userId,remov);
        }
    }

    private void initView() {
        userId = SharedPreferencesUtility.getUserId(instance);
        Button start = (Button) findViewById(R.id.buttonStart);
        Button finish = (Button) findViewById(R.id.buttonFinish);
        info = (TextView) findViewById(R.id.info);
        progressBarRl = (RelativeLayout) findViewById(R.id.progressBarRl);
//        setRunData(userId,"14.0023");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastShort(instance, "请到户外进行测试！");
                if (mLocClient != null ) {
                    mLocClient.start();
                    BDLocation location = new BDLocation();
//                   Double location1 =  location.getLatitude();
//                    Double location2 =  location.getLongitude();
//                   Log.e("TAG","location1====="+location1);
//                   Log.e("TAG","location2====="+location2);
                    SharedPreferencesUtility.setLatitude(instance, String.valueOf(location.getLatitude()));
                    SharedPreferencesUtility.setLongitude(instance, String.valueOf(location.getLongitude()));
                    progressBarRl.setVisibility(View.VISIBLE);
                    info.setText("GPS信号搜索中，请稍后...");
                    mBaiduMap.clear();
                }
            }
        });
//        setRunData(userId,"14.0023");
        finish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mLocClient != null && mLocClient.isStarted()) {

                    Log.e("TAH","currentPosition===="+currentPosition);
                    for (int i=0;i<points.size()-1;i++){
                        remov  =  getDistance(points.get(i),points.get(i+1));
                    }
                    setRunData(userId,remov);
//
                    mLocClient.stop();

                    progressBarRl.setVisibility(View.GONE);

                    if (isFirstLoc) {

                        points.clear();
                        last = new LatLng(0, 0);
                        return;
                    }

                    MarkerOptions oFinish = new MarkerOptions();// 地图标记覆盖物参数配置类
                    oFinish.position(points.get(points.size() - 1));
                    oFinish.icon(finishBD);// 设置覆盖物图片
                    mBaiduMap.addOverlay(oFinish); // 在地图上添加此图层

                    //复位
                    points.clear();
                    last = new LatLng(0, 0);
                    isFirstLoc = true;
//                    ToastShort(instance,"remov===="+remov);

//                    handler.sendEmptyMessageDelayed(GO_HOME, TIME);
                }
            }
        });

    }






    /**
     * 12mine跑步测试
     */
    private void setRunData(int userId,String xfRunMeter) {

        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getRunMeterTest(userId,xfRunMeter,application)
                .compose(this.<FTJBean>bindToLifecycle())
                .compose(SchedulerTransformer.<FTJBean>transformer())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
//                        Log.i(TAG, "--- doFinally ---");
//                        if (isRefresh)
//                            mRefreshLayout.finishRefreshing();
//                        else
//                            mRefreshLayout.finishLoadmore();
                    }
                })
                .subscribe(new BaseObserver<FTJBean>() {
                    @Override
                    protected void onSuccess(FTJBean mFTJBean) {
                        if (mFTJBean != null) {
                            if (mFTJBean.getCode() == 200) {

                                Intent TestResultIntent = new Intent(instance, TestResultActivty.class);
                                TestResultIntent.putExtra("tv_name", "跑步测试");
                                TestResultIntent.putExtra("xfRunMeter", xfRunMeter);
                                TestResultIntent.putExtra("data", mFTJBean.getData());
                                startActivity(TestResultIntent);
//                                mTvIndex.setText(mFTJBean.getData());
//                                mRunData = mRunBean.getData();
//                                GuideAdpter mGuideAdpter = new GuideAdpter(instance , mRunData,mRecyclerViewItemClickListener);
//                                mRecycleGuide.setAdapter(mGuideAdpter);
//                                mData = mActiveBean.getData().get活动准备();
//                                AnswerAdapter mAnswerAdapter = new AnswerAdapter(instance ,mData, mHotRecyclerViewItemClickListener);
//                                recycleAnswer.setAdapter(mAnswerAdapter);

                            } else {

                                Toast.makeText(instance, mFTJBean.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    protected void onFailed(HttpResponseException responseException) {
                        super.onFailed(responseException);
                        Toast.makeText(instance, "error code : " + responseException.getStatus(), Toast.LENGTH_SHORT).show();
//                        ToastShort(instance, "error code : " + responseException.getStatus());
//                        ToastShort(instance, "网络有误！");
                    }
                });
    }



//
//    // 计算两点距离
////    final Double EARTH_RADIUS = 6378137.0;
//    private Double gps2m ( double lat_a,double lng_a, double lat_b, double lng_b) {
//        double radLat1 = (lat_a * Math.PI / 180.0);
//        double radLat2 = (lat_b * Math.PI / 180.0);
//        double a = radLat1 - radLat2;
//        double b = (lng_a - lng_b) * Math.PI / 180.0;
//        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
//                + Math.cos(radLat1) * Math.cos(radLat2)
//                * Math.pow(Math.sin(b / 2), 2)));
//        s = s * EARTH_RADIUS;
//        s = Math.round(s * 10000) / 10000;
//        return s;
//    }


    /**
     * 计算两点之间距离
     * @param start
     * @param end
     * @return 米
     */
    public String getDistance(LatLng start,LatLng end){
        double lat1 = (Math.PI/180)*start.latitude;
        double lat2 = (Math.PI/180)*end.latitude;

        double lon1 = (Math.PI/180)*start.longitude;
        double lon2 = (Math.PI/180)*end.longitude;

        //地球半径
        double R = 6371;

        //两点间距离 km，如果想要米的话，结果*1000
        double d =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;
        juli = juli + d;

//        if(juli<1) {
//            ToastShort(instance, "juli333333====" + juli * 1000 );
            return juli * 1000 + "";
//        }
//        else {
//            return String.format("%.2f", juli) + "km";
//        }
    }








    double lastX;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];

        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;

            if (isFirstLoc) {
                lastX = x;
                return;
            }

            locData = new MyLocationData.Builder().accuracy(0)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat).longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation location) {

            if (location == null || mMapView == null) {
                return;
            }
//
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
//            if (isFirstLoc) {
////                isFirstLoc = false;
////                // p1=new LatLng(39.93923, 116.357428);
//
////                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(p2);
////                // count++;
////                // points.add(p2);
////                // pointstwo.add(ll);
////                mBaiduMap.animateMapStatus(u);
////                //设置并显示中心点
//////                setPosition2Center(mBaiduMap, location, true);
//////                //显示当前定位点，缩放地图
//////                locateAndZoom(location, ll);
//            }
//            else {
//                // count++;
//                LatLng p0 = p2;
//                p2 = new LatLng(location.getLatitude(), location.getLongitude());
//                sum_distance = sum_distance + gps2m(p0.latitude,p0.longitude,p2.latitude,p2.longitude);
////                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(p2);
////                // points.add(p2);
////                Log.d("points", "wwww");
////                OverlayOptions ooDot = new DotOptions().center(p2).radius(6)
////                        .color(0xAAFF0000);
////                mBaiduMap.addOverlay(ooDot);
////
////                mBaiduMap.animateMapStatus(u);
//
//            }
////
//            Log.e("TAG","sum_distance====="+sum_distance);
//            ToastLong(instance,"sum_distance==" +sum_distance);
////


            //注意这里只接受gps点，需要在室外定位。
            if (location.getLocType() == BDLocation.TypeGpsLocation) {

                info.setText("GPS信号弱，请稍后...");

                if (isFirstLoc) {//首次定位
                    //第一个点很重要，决定了轨迹的效果，gps刚开始返回的一些点精度不高，尽量选一个精度相对较高的起始点
                    LatLng ll = null;

                    ll = getMostAccuracyLocation(location);
                    if(ll == null){
                        return;
                    }
                    isFirstLoc = false;
                    points.add(ll);//加入集合
                    last = ll;



                    //显示当前定位点，缩放地图
                    locateAndZoom(location, ll);

                    //标记起点图层位置
                    MarkerOptions oStart = new MarkerOptions();// 地图标记覆盖物参数配置类
                    oStart.position(points.get(0));// 覆盖物位置点，第一个点为起点
                    oStart.icon(startBD);// 设置覆盖物图片
                    mBaiduMap.addOverlay(oStart); // 在地图上添加此图层

                    progressBarRl.setVisibility(View.GONE);

                    return;//画轨迹最少得2个点，首地定位到这里就可以返回了
                }

                //从第二个点开始
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                //sdk回调gps位置的频率是1秒1个，位置点太近动态画在图上不是很明显，可以设置点之间距离大于为5米才添加到集合中
                if (DistanceUtil.getDistance(last, ll) < 5) {
                    return;
                }

               Double Latitude = Double.valueOf(SharedPreferencesUtility.getLatitude(instance));
                Double Longitude =  Double.valueOf(SharedPreferencesUtility.getLongitude(instance));
                LatLng locNow = new LatLng(Latitude,Longitude);

                double lat = location.getLatitude(),longi=location.getLongitude(); //自行修改终点的 维度 和 精度
                LatLng locComy = new LatLng(lat,longi);

                int dis = (int) DistanceUtil. getDistance(locNow, locComy);

                currentPosition.append("距离：").append(dis+"米").append("\n");
//                gps2m(gps2m)

                points.add(ll);//如果要运动完成后画整个轨迹，位置点都在这个集合中

                last = ll;

                //显示当前定位点，缩放地图
                locateAndZoom(location, ll);

                //清除上一次轨迹，避免重叠绘画
                mMapView.getMap().clear();

                //起始点图层也会被清除，重新绘画
                MarkerOptions oStart = new MarkerOptions();
                oStart.position(points.get(0));
                oStart.icon(startBD);
                mBaiduMap.addOverlay(oStart);

                //将points集合中的点绘制轨迹线条图层，显示在地图上
                OverlayOptions ooPolyline = new PolylineOptions().width(13).color(0xAAFF0000).points(points);

//                float distance = AMapUtils.calculateLineDistance(latLng,latLng2);
//                float qianmifload =(float) distance/1000;
//                Log.i("lgq","sss===="+round(qianmifload,2)+" 千米");

                mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
//                output += plan.getDistance(true) + "\n";            //获取距离




//                mBaiduMap.get

//                BMapLib.GeoUtils.getPolylineDistance();
//                mPolyline.
            }
        }

    }

//
//    /**
//     * 设置中心点和添加marker
//     *
//     * @param map
//     * @param bdLocation
//     * @param isShowLoc
//     */
//    public void setPosition2Center(BaiduMap map, BDLocation bdLocation, Boolean isShowLoc) {
//        MyLocationData locData = new MyLocationData.Builder()
//                .accuracy(bdLocation.getRadius())
//                .direction(bdLocation.getRadius()).latitude(bdLocation.getLatitude())
//                .longitude(bdLocation.getLongitude()).build();
//        map.setMyLocationData(locData);
//
//        if (isShowLoc) {
//            LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
//            MapStatus.Builder builder = new MapStatus.Builder();
//            builder.target(ll).zoom(18.0f);
//            map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//        }
//    }


    private void locateAndZoom(final BDLocation location, LatLng ll) {
        mCurrentLat = location.getLatitude();
        mCurrentLon = location.getLongitude();
        locData = new MyLocationData.Builder().accuracy(0)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(mCurrentDirection).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);

        builder = new MapStatus.Builder();
        builder.target(ll).zoom(mCurrentZoom);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    /**
     * 首次定位很重要，选一个精度相对较高的起始点
     * 注意：如果一直显示gps信号弱，说明过滤的标准过高了，
     你可以将location.getRadius()>25中的过滤半径调大，比如>40，
     并且将连续5个点之间的距离DistanceUtil.getDistance(last, ll ) > 5也调大一点，比如>10，
     这里不是固定死的，你可以根据你的需求调整，如果你的轨迹刚开始效果不是很好，你可以将半径调小，两点之间距离也调小，
     gps的精度半径一般是10-50米
     */
    private LatLng getMostAccuracyLocation(BDLocation location){

        if (location.getRadius()>40) {//gps位置精度大于40米的点直接弃用
            return null;
        }

        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());

        if (DistanceUtil.getDistance(last, ll ) > 10) {
            last = ll;
            points.clear();//有任意连续两点位置大于10，重新取点
            return null;
        }
        points.add(ll);
        last = ll;
        //有5个连续的点之间的距离小于10，认为gps已稳定，以最新的点为起始点
        if(points.size() >= 5){
            points.clear();
            return ll;
        }
        return null;
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        // 为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        // 取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.unRegisterLocationListener(myListener);
        if (mLocClient != null && mLocClient.isStarted()) {
            mLocClient.stop();
        }
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.getMap().clear();
        mMapView.onDestroy();
        mMapView = null;
        startBD.recycle();
        finishBD.recycle();
        super.onDestroy();
    }



}
