package com.bintutu.shop.ui.server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.bintutu.shop.okgo.DialogCallback;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.okgo.LzyResponse;
import com.bintutu.shop.okgo.ServerModel;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UploadServer extends Service {

    private String Imagefile;
    private Context mContext;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        GetImage();
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


    private void GetImage() {
        try {
            Imagefile = Environment.getExternalStorageDirectory().getCanonicalPath() + "/Bintutu";
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> imageliat =Utils.getAllFiles(Imagefile,"jpg");

        UploadImage(imageliat);

    }

    private void UploadImage(List<String> imageliat) {
        //上传图片
        OkGo.<LzyResponse<ServerModel>>post(AppConstant.UPLOAD_IMAGE)
                .headers("id", "headerValue1")//
                .upFile(new File(""))//
                .execute(new JsonCallback<LzyResponse<ServerModel>>() {
                    @Override
                    public void onSuccess(Response<LzyResponse<ServerModel>> response) {

                    }

                    @Override
                    public void onError(Response<LzyResponse<ServerModel>> response) {

                    }
                });
    }
}
