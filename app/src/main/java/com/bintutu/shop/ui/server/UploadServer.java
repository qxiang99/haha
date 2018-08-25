package com.bintutu.shop.ui.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class UploadServer extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      /*  // 接收Intent传来的参数:
        title = getAppName(this);
        apkUrl = intent.getStringExtra("apk_url");
        appName = intent.getStringExtra("app_name");
        isUpdate = intent.getIntExtra("is_update", 1);
        downloadApk();*/
        return super.onStartCommand(intent, flags, startId);
    }
}
