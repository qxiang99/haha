package com.bintutu.shop.ui.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.BaseResponse;
import com.bintutu.shop.bean.LoginBean;
import com.bintutu.shop.okgo.Convert;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.utils.AppConstant;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import pl.droidsonroids.gif.MultiCallback;

public class TextActivity extends AppCompatActivity {

    @BindView(R.id.text_image)
    GifImageView image;

    private GifDrawable gifDrawableThree;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        ButterKnife.bind(this);
        MultiCallback multiCallback = new MultiCallback();

        try {
            gifDrawableThree = new GifDrawable(getResources(), R.drawable.scan_face);
            image.setImageDrawable(gifDrawableThree);

            multiCallback.addView(image);

            gifDrawableThree.setCallback(multiCallback);

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private void setData() {

        //get
        OkGo.<BaseResponse<LoginBean>>get(AppConstant.LOGIN)
                .params("name", "")
                .execute(new JsonCallback<BaseResponse<LoginBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<LoginBean>> response) {
                    }

                    @Override
                    public void onError(Response<BaseResponse<LoginBean>> response) {
                    }
                });

        //post
        OkGo.<BaseResponse<LoginBean>>post(AppConstant.LOGIN)//
                .params("terminalType", "2")
                .execute(new JsonCallback<BaseResponse<LoginBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<LoginBean>> response) {
                    }

                    @Override
                    public void onError(Response<BaseResponse<LoginBean>> response) {
                        super.onError(response);
                    }
                });

        //put
        OkGo.<BaseResponse<LoginBean>>put(AppConstant.LOGIN)
                .upJson(Convert.toJson(""))
                .execute(new JsonCallback<BaseResponse<LoginBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<LoginBean>> response) {
                    }

                    @Override
                    public void onError(Response<BaseResponse<LoginBean>> response) {
                        super.onError(response);
                    }
                });

        HashMap<String, String> params = new HashMap<>();
        params.put("key1", "value1");
        params.put("key2", "这里是需要提交的json格式数据");
        params.put("key3", "也可以使用三方工具将对象转成json字符串");
        params.put("key4", "其实你怎么高兴怎么写都行");
        JSONObject jsonObject = new JSONObject(params);

        //上传长String
        /*  OkGo.<LzyResponse<ServerModel>>post(Urls.URL_TEXT_UPLOAD)//
                .tag(this)//
                .headers("header1", "headerValue1")//
//                .params("param1", "paramValue1")// 这里不要使用params，upString 与 params 是互斥的，只有 upString 的数据会被上传
                .upString("这是要上传的长文本数据！")//
//                .upString("这是要上传的长文本数据！", MediaType.parse("application/xml"))// 比如上传xml数据，这里就可以自己指定请求头
                .execute(new DialogCallback<LzyResponse<ServerModel>>(this) {
                    @Override
                    public void onSuccess(Response<LzyResponse<ServerModel>> response) {
                        handleResponse(response);
                    }

                    @Override
                    public void onError(Response<LzyResponse<ServerModel>> response) {
                        handleError(response);
                    }
                });*/

        //上传文件&上传图片
      /*  OkGo.<LzyResponse<ServerModel>>post(Urls.URL_TEXT_UPLOAD)//
                .tag(this)//
                .headers("header1", "headerValue1")//
//                .params("param1", "paramValue1")// 这里不要使用params，upBytes 与 params 是互斥的，只有 upBytes 的数据会被上传
                .upFile(new File(imageItem.path))//
                .execute(new DialogCallback<LzyResponse<ServerModel>>(this) {
                    @Override
                    public void onSuccess(Response<LzyResponse<ServerModel>> response) {
                        handleResponse(response);
                    }

                    @Override
                    public void onError(Response<LzyResponse<ServerModel>> response) {
                        handleError(response);
                    }
                });*/




    }

    private void downloadApk() {
        OkGo.<File>get("")
                .tag(this)
                .execute(new FileCallback("", "") {
                    @Override
                    public void onSuccess(Response<File> response) {
                        Log.d("checkUpdateReceiver", "文件下载完成");
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        Log.d("checkUpdateReceiver" , "文件下载中");
                    }

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        Log.d("checkUpdateReceiver" , "开始下载");
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                    }
                });



        /* mList = (RecyclerCoverFlow) findViewById(R.id.list);
//        mList.setFlatFlow(true); //平面滚动
//        mList.setGreyItem(true); //设置灰度渐变
//        mList.setAlphaItem(true); //设置半透渐变
        mList.setAdapter(new Adapter(this, this));
        mList.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                ((TextView)findViewById(R.id.index)).setText((position+1)+"/"+mList.getLayoutManager().getItemCount());
            }
        });*/
    }


    /*    private void ErrorShow(Response<BaseResponse<DataPersonBean>> response) {
        DebugLog.e(response.code() + "....");
        if (response.code() == -1) {
            ToastUtils.showToast(EmdataAppliaction.getInstance(), "网络出错");
            mMultipleStatusView.showNoNetwork();
        } else {
            ToastUtils.showToast(EmdataAppliaction.getInstance(), response.getException().getMessage());
            ToastUtils.showToast(EmdataAppliaction.getInstance(), response.getException().getMessage());
        }
    }*/
}
