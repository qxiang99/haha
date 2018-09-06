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
    private Gson gson;
    private Timer timerscan;
    private boolean RetryScan = true;//一次扫描重试机会
    private Timer timerRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
        gson = new Gson();
        //gif动画开始
        Scanstart();
        //发出扫描命令
        ScanPost();
        //发出循环请求
        RequestScan();

    }

    /**
     * 向扫描仪发起开始扫描指令
     * 不返回结果
     */
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

    /**
     * 5秒钟之后去请求扫描结果
     */
    private void RequestScan() {
        timerRequest = new Timer();
        timerRequest.schedule(new TimerTask() {
            public void run() {
                requestData();
            }
        }, 5000,10000);

    }



    /**
     * 请求扫描结果
     * result==1 扫描仪扫描成功
     * result==2 扫描仪正在运行
     * result==0 扫描仪出错
     * 设置一次重试机会
     */
    private void requestData() {
        OkGo.<BaseResponse<String>>post(AppConstant.REQUEST_DATA)
                .params("id", scanNametime)
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        ScanBean scanBean = gson.fromJson(String.valueOf(response.body()), ScanBean.class);
                        if (scanBean.getResult() == 1) {
                            if (timerscan!=null){
                                timerscan.cancel();
                            }
                            if (timerRequest!=null){
                                timerRequest.cancel();
                            }
                            Intent intent = new Intent();
                            intent.putExtra(Constant.ItentKey1, scanNametime);
                            GifActivity.this.setResult(200, intent);
                            finish();

                        } else  if (scanBean.getResult() == 0) {


                                if (timerscan!=null){
                                    timerscan.cancel();
                                }
                                if (timerRequest!=null){
                                    timerRequest.cancel();
                                }
                                Intent intent = new Intent();
                                intent.putExtra(Constant.ItentKey1, scanNametime);
                                GifActivity.this.setResult(500, intent);
                                finish();

                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                        super.onError(response);
                    }
                });


    }


    private void Scanstart() {
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
            int type = msg.what;

            if (type > 0 && type < 21) {
                startface();
            }
            if (type > 20 && type < 41) {
                startleft();
            }
            if (type > 40 && type < 61) {
                startright();
            }
            if (type > 60 && type < 81) {
                startbottom();
            }
            if (type > 81) {
                startface();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerscan!=null){
            timerscan.cancel();
        }
        if (timerRequest!=null){
            timerRequest.cancel();
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
            ToastUtils.showToast(GifActivity.this, "如果想退出扫描，再按一次返回");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }


}
