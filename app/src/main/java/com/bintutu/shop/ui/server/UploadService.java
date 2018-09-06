package com.bintutu.shop.ui.server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bintutu.shop.IUploadServiceface;
import com.bintutu.shop.okgo.DialogCallback;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.okgo.LzyResponse;
import com.bintutu.shop.okgo.ServerModel;
import com.bintutu.shop.ui.activity.DetailActivity;
import com.bintutu.shop.ui.activity.UploadSucessActivity;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.Constant;
import com.bintutu.shop.utils.DebugLog;
import com.bintutu.shop.utils.EventMsg;
import com.bintutu.shop.utils.RxBus;
import com.bintutu.shop.utils.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.MediaType;

public class UploadService extends Service {

    private String Imagefile;
    private Context mContext;
    private String randomNumber="";
    private String UploadID;
    private boolean loadZip = false;
    private boolean upload = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        RxBusMsg();
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        randomNumber = intent.getStringExtra(Constant.ItentKey1);
        Log.e("onStartCommand","....."+randomNumber);
        downloadZip();
        return super.onStartCommand(intent, flags, startId);
    }



    private void downloadZip() {
        OkGo.<File>get(AppConstant.DATA_ZIP(randomNumber))
                .tag(this)
                .execute(new FileCallback(AppConstant.ZIP_DATAIL, "data.tgz") {
                    @Override
                    public void onSuccess(Response<File> response) {
                        DebugLog.d("checkUpdateReceiver", "文件下载完成");
                        loadZip = true;
                        if (loadZip == true && upload == true) {
                            UploadZip();
                        }
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        Log.d("checkUpdateReceiver", "文件下载中");
                    }

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        Log.d("checkUpdateReceiver", "开始下载");
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                    }
                });
    }

    private void GetImage() {
        List<String> imageliat = Utils.getAllFiles(AppConstant.IMAGE_DATAIL, "jpg");
        if (imageliat != null && imageliat.size() > 0) {
            UploadImage(imageliat);
        }
    }

    private void UploadImage(List<String> imageliat) {
        for (String file : imageliat) {
            //上传图片
            OkGo.<LzyResponse<ServerModel>>post(AppConstant.UPLOAD_IMAGE)
                    .params("id", UploadID)
                    .params("file", new File(file))
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

    private void UploadZip() {
        //上传图片
        GetImage();
        File files = new File(AppConstant.ZIP_DATAIL + "/data.tgz");
        //上传图片
        OkGo.<LzyResponse<ServerModel>>post(AppConstant.UPLOAD_ZIP)

                .params("id", UploadID)
                .params("file", files, "data.tgz", MediaType.parse("application/x-tar"))
                .execute(new JsonCallback<LzyResponse<ServerModel>>() {
                    @Override
                    public void onSuccess(Response<LzyResponse<ServerModel>> response) {

                    }

                    @Override
                    public void onError(Response<LzyResponse<ServerModel>> response) {

                    }
                });
    }


    private void RxBusMsg() {
        RxBus.getInstance().toObservable().map(new Function<Object, EventMsg>() {
            @Override
            public EventMsg apply(Object o) throws Exception {
                return (EventMsg) o;
            }
        }).subscribe(new Consumer<EventMsg>() {
            @Override
            public void accept(EventMsg eventMsg) throws Exception {
                if (eventMsg != null) {
                    UploadID =eventMsg.getMsg();
                    upload= true;
                    if (loadZip == true && upload == true) {
                        UploadZip();
                    }
                }
            }
        });
    }

    private void FinishService(){
        stopSelf();
    }


}
