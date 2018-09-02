package com.bintutu.shop.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.MediaController;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.BaseResponse;
import com.bintutu.shop.bean.ScanBean;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.Constant;
import com.bintutu.shop.utils.DebugLog;
import com.bintutu.shop.utils.ToastUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GifActivity extends Activity {


    @BindView(R.id.imageface)
    GifImageView imageface;
    private String scanNametime;
    private Timer timeres;
    private Gson gson;
    private Timer timer;
    private Timer timerscan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
        gson = new Gson();
        Timerstart();
        //发送扫描命令加循环请求
        RequestScan();

    }


    private void RequestScan() {
        //发出扫描命令
        ScanPost();
        //循环请求结果
        timeres = new Timer();
        timeres.schedule(new TimerTask() {
            public void run() {
                requestData();
            }
        }, 5000,10000);
    }

    private void ScanPost() {
        scanNametime = System.currentTimeMillis() + "";
        OkGo.<BaseResponse<String>>post(AppConstant.BEGIN_SCAN)
                .params("id", scanNametime)//名称
                .params("begin", "1")
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {

                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                    }
                });


    }


    private void requestData() {
        //AppConstant.REQUEST_DATA
        OkGo.<BaseResponse<String>>post(AppConstant.REQUEST_DATA)
                .params("id", scanNametime)
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        ScanBean scanBean = gson.fromJson(String.valueOf(response.body()), ScanBean.class);
                        DebugLog.e(scanBean.getResult()+"。。。。。1。。。。。");
                        if (scanBean.getResult() == 1) {
                            timeres.cancel();
                            timerscan.cancel();

                            //数据是使用Intent返回
                            Intent intent = new Intent();
                            //把返回数据存入Intent
                            intent.putExtra(Constant.ItentKey1, scanNametime);
                            //设置返回数据
                            GifActivity.this.setResult(200, intent);
                            finish();

                        } else if (scanBean.getResult() == 0) {
                            timeres.cancel();
                            timerscan.cancel();


                            //数据是使用Intent返回
                            Intent intent = new Intent();
                            //把返回数据存入Intent
                            intent.putExtra(Constant.ItentKey1, scanNametime);
                            //设置返回数据
                            GifActivity.this.setResult(500, intent);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                        timeres.cancel();
                        timerscan.cancel();
                    }
                });


    }



    private void Timerstart() {
        final int[] count = {0};
        timerscan = new Timer();
        timerscan.schedule(new TimerTask() {
            @Override
            public void run() {
                count[0] = 1 + count[0];
                Message message = new Message();
                message.what = count[0];
                mHandler.sendMessage(message);
            }
        }, 100, 1000);
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int type = msg.what % 80;

            if (type >0&&type<21) {
                startface();
            }
            if (type >20&&type<41) {
                startleft();
            }
            if (type >40&&type<61) {
                startright();
            }
            if (type >60&&type<81) {
                startbottom();
            }
        }
    };

    private void startface() {
        try {
            GifDrawable gifDrawableface = new GifDrawable(getResources(), R.drawable.scan_face);
            imageface.setImageDrawable(gifDrawableface);
            gifDrawableface.setLoopCount(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startleft() {
        try {
            GifDrawable gifDrawableleft = new GifDrawable(getResources(), R.drawable.scan_left);
            imageface.setImageDrawable(gifDrawableleft);
            gifDrawableleft.setLoopCount(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startright() {
        try {
            GifDrawable gifDrawableright = new GifDrawable(getResources(), R.drawable.scan_right);
            imageface.setImageDrawable(gifDrawableright);
            gifDrawableright.setLoopCount(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startbottom() {
        try {
            GifDrawable gifDrawablebottom = new GifDrawable(getResources(), R.drawable.scan_bottom);
            imageface.setImageDrawable(gifDrawablebottom);
            gifDrawablebottom.setLoopCount(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    //退出时的时间
    private long mExitTime;

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.showToast(GifActivity.this, "如果想退出扫描，再按一次退出");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }


}
