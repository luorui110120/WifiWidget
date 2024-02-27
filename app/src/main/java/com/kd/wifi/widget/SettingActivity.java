package com.kd.wifi.widget;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SettingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    setContentView(R.layout.activity_main);
        ////当控件超出屏幕是可滑动
        ScrollView mainroot = new ScrollView(this);
        LinearLayout rootFloatLinearLayout = new LinearLayout(this);
        mainroot.addView(rootFloatLinearLayout);
        rootFloatLinearLayout.setOrientation(LinearLayout.VERTICAL);
//        rootFloatLinearLayout.getBackground().setAlpha(153);
        TextView localTextView1 = new TextView(this);
        localTextView1.setText("设置Toke 登入");
        localTextView1.setTextSize(22.0F);
        //localTextView1.setTextColor(Color.rgb(0x00, 0xFF, 0xFF));
        ViewGroup.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rootFloatLinearLayout.addView(localTextView1, localLayoutParams1);
        //////设置token
        TextView inputTokenTextTitle = new TextView(this);
        inputTokenTextTitle.setText("输入token");
        //inputTokenTextTitle.setTextColor(Color.rgb(0xFF, 0xFF, 0xFF));
        inputTokenTextTitle.setTextSize(17.0F);
        rootFloatLinearLayout.addView(inputTokenTextTitle, localLayoutParams1);
        /// token
        final EditText editTokenTextView = new EditText(this);
        editTokenTextView.setText("input str");
        //editTokenTextView.setTextColor(Color.rgb(0x00, 0xFF, 0xFF));
        editTokenTextView.setTextSize(17.0F);
        editTokenTextView.setGravity(Gravity.CENTER);
        rootFloatLinearLayout.addView(editTokenTextView, localLayoutParams1);
        Button btn = new Button(this);
        btn.setText("OK");
        btn.setGravity(Gravity.CENTER);
        rootFloatLinearLayout.addView(btn, localLayoutParams1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "click:", Toast.LENGTH_LONG).show();
                //安卓9.0以上版本进行定位权限获取, 并且要在应用设置中,设置成始终获取GPS权限才行;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
                        if(ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
                            //申请定位权限,200是标识码
                            ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 200);
                        }
                    }
                    else{
                        if(ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
                            //申请定位权限,200是标识码
                            ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
                        }
                    }

                }
            }
        });
        setContentView(mainroot, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));


    }
    @Override
    protected void onResume(){

        super.onResume();
    }

}
