package com.kd.wifi.widget;


import android.Manifest;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.WIFI_SERVICE;

public class KdWifiWidget extends AppWidgetProvider {

    private Timer mTimer = new Timer();
    private AppWidgetManager mAppWidgerManager;
    private Context mContext;
    //将0-9的液晶数字图片定义为数组
//    private int[] digits = new int[]{
//            R.drawable.led00,
//            R.drawable.led01,
//            R.drawable.led02,
//            R.drawable.led03,
//            R.drawable.led04,
//            R.drawable.led05,
//            R.drawable.led06,
//            R.drawable.led07,
//            R.drawable.led08,
//            R.drawable.led09,
//    };
    //将显示小时、分钟、秒钟的ImageView定义为数组
    private int[] digitViews = new int[]{
            R.id.time_text1,
            R.id.time_text2,
            R.id.time_text4,
            R.id.time_text5,
            R.id.time_text7,
            R.id.time_text8,
    };

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        this.mAppWidgerManager = appWidgetManager;
        this.mContext = context;
        //定义计时器
        mTimer = new Timer();
        //启动周期性调度
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //发送空消息，通知界面更新
                handler.sendEmptyMessage(0x123);
            }
        }, 0, 1000);



        RemoteViews myRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.main);
        //点击桌面组件时进入程序的设置界面,先预留一下功能,未来使用;
        //Intent intent=new Intent(context, SettingActivity.class);
        ////通过下面是设置可以打开 wifi链接界面;
        //Intent intent = new Intent("android.settings.WIFI_SETTINGS").setFlags(268468224);
        //PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, 0);
        ////刷新界面
        //Intent intent=new Intent("com.codef.clockwidget.UPDATE");
        Intent intent = new Intent(context, KdWifiWidget.class).setAction("com.kd.wifiwidget.UPDATE");
        PendingIntent pendingIntent= PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        myRemoteViews.setOnClickPendingIntent(R.id.root_layout, pendingIntent);
        //将APPWidgetProvider子类实例包装成ComponentName对象
        ComponentName componentName = new ComponentName(mContext, KdWifiWidget.class);
        //调用APPWidgetManager将RemoteViews添加到ComponentName中
        mAppWidgerManager.updateAppWidget(componentName, myRemoteViews);







        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        System.out.println("onEnabled");
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        Log.e("mlog", "GridWidgetProvider onReceive : "+intent.getAction());
//        Toast.makeText(context, intent.getAction(), Toast.LENGTH_LONG).show();
        onUpdate(context, appWidgetManager, null);
//        if (action.equals(COLLECTION_VIEW_ACTION)) {
//            // 接受“gridview”的点击事件的广播
//            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
//                    AppWidgetManager.INVALID_APPWIDGET_ID);
//            int viewIndex = intent.getIntExtra(COLLECTION_VIEW_EXTRA, 0);
//            Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
//        } else if (action.equals(BT_REFRESH_ACTION)) {
//            // 接受“bt_refresh”的点击事件的广播
//            Toast.makeText(context, "Click Button", Toast.LENGTH_SHORT).show();
//        }
        super.onReceive(context, intent);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123){


                RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.main);
                String wifi_ssid, wifi_ip = Utils.getLocalIpAddress(mContext);
                if(Utils.getWlanStatus(mContext)){
                    WifiManager wifiManager = (WifiManager) mContext.getApplicationContext().getSystemService(WIFI_SERVICE);
                    int state = wifiManager.getWifiState();
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    wifi_ssid = wifiInfo.getSSID();
                }
                else{
                    wifi_ssid = "wifi Not opened.";
                }

                //定义SimpleDateFormat对象
                SimpleDateFormat df = new SimpleDateFormat("HHmmss");
                //将当前时间格式化为HHmmss的形式
                String timeStr = df.format(new Date());
                for (int i = 0; i < timeStr.length(); i++) {
                    //将第i个数字字符zh转换为对应的数字
                    int num = timeStr.charAt(i) - 48;
                    //将第i个图片设为对应的液晶数字图片
                    //views.setImageViewResource(digitViews[i], digits[num]);
                    views.setTextViewText(digitViews[i],  timeStr.substring(i,i+1));
                }
                views.setTextViewText(R.id.wifi_ssid, "wifi_ssid: " + wifi_ssid.replace("\"", ""));
                views.setTextViewText(R.id.wifi_ip, "local_ip: " + wifi_ip);
                views.setTextViewText(R.id.time_title, "uptime:");
                //将APPWidgetProvider子类实例包装成ComponentName对象
                ComponentName componentName = new ComponentName(mContext, KdWifiWidget.class);
                //调用APPWidgetManager将RemoteViews添加到ComponentName中
                mAppWidgerManager.updateAppWidget(componentName, views);
            }
            super.handleMessage(msg);
        }
    };


}
