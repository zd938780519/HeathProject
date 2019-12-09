package com.yidao.project.heathproject.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.mchsdk.paysdk.mylibrary.BaseActivity;
import com.yidao.project.heathproject.MainActivity;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;

public class SplashActivity extends BaseActivity {
    private static final int TIME = 2000;
    private static final int GO_HOME = 1;
    private SplashActivity instance;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        instance = this;
        initData();
    }

    private void initData() {
        handler.sendEmptyMessageDelayed(GO_HOME, TIME);
    }


    /**
     * 跳转到聊天界面
     */
    private void goHome() {
        int userId = SharedPreferencesUtility.getUserId(instance);
        if (userId !=0){
            jumpToActivity(MainActivity.class);
        }else {
            jumpToActivity(LoginActivity.class);
        }

        finish();
    }





}
