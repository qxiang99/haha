package com.bintutu.shop.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.BaseResponse;
import com.bintutu.shop.bean.CommandBean;
import com.bintutu.shop.bean.DatailImageBean;
import com.bintutu.shop.bean.ScanBean;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.adapter.CoverFlowAdapter;
import com.bintutu.shop.ui.view.CloseDailog;
import com.bintutu.shop.ui.view.CommanDailog;
import com.bintutu.shop.ui.view.CoverFlowLayoutManger;
import com.bintutu.shop.ui.view.LoginDailog;
import com.bintutu.shop.ui.view.RecyclerCoverFlow;
import com.bintutu.shop.ui.view.WifiDailog;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.ConfigManager;
import com.bintutu.shop.utils.Constant;
import com.bintutu.shop.utils.DebugLog;
import com.bintutu.shop.utils.GlideUtil;
import com.bintutu.shop.utils.NetworkUtil;
import com.bintutu.shop.utils.Utils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.mian_coverflow)
    RecyclerCoverFlow mReCverFlow;
    @BindView(R.id.main_lin_poweroff)
    LinearLayout mLinPowerOff;
    @BindView(R.id.main_lin_wifi)
    LinearLayout mLinWifi;
    @BindView(R.id.main_lin_command)
    LinearLayout mLinCommand;
    @BindView(R.id.main_but_scan)
    Button mButScan;
    @BindView(R.id.main_but_hibitionroom)
    Button mButHibitionRoom;
    @BindView(R.id.main_text_authorization)
    TextView mMainTextAuthorization;
    @BindView(R.id.main_but_fitting)
    Button mMainButFitting;
    private WifiDailog wifiDailog;
    private CommanDailog commanDailog;
    private CloseDailog closeDailog;
    List<String> DetailList = new ArrayList<>();
    private Gson gson;
    private LoginDailog loginDailog;


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void init() {
        gson = new Gson();
        //
        closeDailog = new CloseDailog(MainActivity.this);
        //
        wifiDailog = new WifiDailog(MainActivity.this);
        //
        commanDailog = new CommanDailog(MainActivity.this);
        //
        mMainTextAuthorization.setText("授权门店:" + ConfigManager.Device.getShopID());
        //
        //初始化LoginDailog
        loginDailog = new LoginDailog(this);
        //
        DetailList.clear();
        List<String> imageliat = Utils.getAllFiles(AppConstant.IMAGE_BANNER, "jpg");
        if (imageliat != null && imageliat.size() > 0) {
            mReCverFlow.setVisibility(View.VISIBLE);
            DetailList.addAll(imageliat);
            CoverFlowAdapter coverFlowAdapter = new CoverFlowAdapter(MainActivity.this, DetailList);
            mReCverFlow.setAdapter(coverFlowAdapter);
        } else {
            mReCverFlow.setVisibility(View.GONE);
            mReCverFlow.setVisibility(View.GONE);
        }
        if (NetworkUtil.isNetworkAvailable(this)) {
            initData();
        }
    }


    @Override
    protected void setListener() {
        //打开关闭扫描仪界面
        mLinPowerOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (closeDailog != null) {
                    closeDailog.show();
                }
            }
        });
        //关闭扫描仪按钮
        closeDailog.setSetClickListener(new CloseDailog.OnSetClickListener() {
            @Override
            public void onSetData() {
                ScanClose();
            }
        });

        //打开Wi-Fi操作界面
        mLinWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isWifiConnect() && wifiDailog != null) {
                    wifiDailog.show();
                }
            }
        });
        //wifi确定按钮
        wifiDailog.setSetClickListener(new WifiDailog.OnSetClickListener() {
            @Override
            public void onSetData(String name, String pwd) {
                WifiData(name, pwd);
            }
        });

        //打开口令界面
        mLinCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (commanDailog != null) {
                    commanDailog.show();
                }
            }
        });
        //口令登录按钮
        commanDailog.setSetClickListener(new CommanDailog.OnSetClickListener() {
            @Override
            public void onSetData(String command) {
                CommanD(command);
            }
        });

        //打开扫描界面
        mButScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReadyToScanActivity.class);
                intent.putExtra(Constant.ItentKey1, "MainActivity");
                startActivity(intent);
            }
        });
        //打开展厅界面
        mButHibitionRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra(Constant.ItentKey1, AppConstant.WEBVIEW_HOME);
                startActivity(intent);

            }
        });
        //点击fittin按钮
        mMainButFitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDailog.show();
            }
        });
        //登录按钮监听回调
        loginDailog.seLogintListener(new LoginDailog.OnLoginClickListener() {
            private long lastClick;

            @Override
            public void Data(String number, String phone, String customer_id) {
                if (System.currentTimeMillis() - lastClick <= 3000) {
                    return;
                }
                lastClick = System.currentTimeMillis();
                startfitting(number, phone, customer_id);
            }
        });
    }

    private void startfitting(String number, String phone, String customer_id) {
        Intent intent = new Intent(MainActivity.this, FittingActivity.class);
        intent.putExtra(Constant.ItentKey7, 1);
        intent.putExtra(Constant.ItentKey1, number);
        intent.putExtra(Constant.ItentKey2, phone);
        intent.putExtra(Constant.ItentKey3, customer_id);
        startActivity(intent);
    }

    /**
     * 获取首页轮播图
     */
    private void initData() {
        OkGo.<BaseResponse<String>>get(AppConstant.SOWING_MAP)
                .params("shop_id", ConfigManager.Device.getShopID())
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        DatailImageBean datailImageBean = gson.fromJson(String.valueOf(response.body()), DatailImageBean.class);
                        if (datailImageBean.getResult() != null) {
                            mReCverFlow.setVisibility(View.VISIBLE);
                            DetailList.clear();
                            for (DatailImageBean.ResultBean resultBean : datailImageBean.getResult()) {
                                DetailList.add(AppConstant.IMAGE_SPLIT + resultBean.getImg());
                                CoverFlowAdapter coverFlowAdapter = new CoverFlowAdapter(MainActivity.this, DetailList);
                                mReCverFlow.setAdapter(coverFlowAdapter);
                                GlideUtil.download(MainActivity.this, AppConstant.IMAGE_SPLIT + resultBean.getImg());
                            }
                            mReCverFlow.scrollToPosition(DetailList.size() * 100);
                        } else {
                            mReCverFlow.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                    }
                });
    }

    /**
     * 修改Wi-Fi连接
     *
     * @param name
     * @param pwd
     */
    private void WifiData(String name, String pwd) {
        OkGo.<BaseResponse<String>>post(AppConstant.CHOOSE_WIFI)
                .params("id", name)
                .params("pass", pwd)
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        ScanBean scanBean = gson.fromJson(String.valueOf(response.body()), ScanBean.class);
                        if (scanBean != null && scanBean.getResult() == 0) {
                            ShowToast("已修改");
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {

                    }
                });
    }

    /**
     * 关闭扫描仪
     */
    private void ScanClose() {
        OkGo.<BaseResponse<String>>post(AppConstant.SHUTDOWN)
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
     * 口令
     *
     * @param command
     */
    private void CommanD(String command) {
        OkGo.<BaseResponse<String>>post(AppConstant.COMMAND)
                .params("phone", ConfigManager.Device.getShopPhone())
                .params("password", md5(command))
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {

                        CommandBean commandBean = gson.fromJson(String.valueOf(response.body()), CommandBean.class);
                        if (commandBean != null && commandBean.getCode() == 0) {

                            Intent intent = new Intent(MainActivity.this, WebActivity.class);
                            intent.putExtra(Constant.ItentKey1, AppConstant.WEBVIEW_EXHIBITIONROOM);
                            startActivity(intent);
                        } else {
                            ShowToast("口令错误");
                        }

                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {

                    }
                });
    }


    /**
     * 检查wifi是否处开连接状态
     *
     * @return
     */
    public boolean isWifiConnect() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifiInfo.isConnected();


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
            ShowToast("再按一次退出");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }


    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";

    }

}
