package com.bintutu.shop.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.bintutu.shop.bean.ImageBean;
import com.bintutu.shop.bean.ScanBean;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.adapter.CoverFlowAdapter;
import com.bintutu.shop.ui.view.CloseDailog;
import com.bintutu.shop.ui.view.CommanDailog;
import com.bintutu.shop.ui.view.CoverFlowLayoutManger;
import com.bintutu.shop.ui.view.RecyclerCoverFlow;
import com.bintutu.shop.ui.view.SmoothLinearLayoutManager;
import com.bintutu.shop.ui.view.WifiDailog;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.ConfigManager;
import com.bintutu.shop.utils.Constant;
import com.bintutu.shop.utils.DebugLog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private WifiDailog wifiDailog;
    private CommanDailog commanDailog;
    private CloseDailog closeDailog;
    List<String> DetailList = new ArrayList<>();
    private Gson gson;
    private SmoothLinearLayoutManager layoutManager;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void init() {
        gson = new Gson();
        closeDailog = new CloseDailog(MainActivity.this);
        wifiDailog = new WifiDailog(MainActivity.this);
        commanDailog = new CommanDailog(MainActivity.this);

        mMainTextAuthorization.setText("授权门店:"+ConfigManager.Device.getShopID());

        //layoutManager = new SmoothLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
       //mReCverFlow.setLayoutManager(layoutManager);

        initData();
    }


    @Override
    protected void setListener() {
        mLinPowerOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (closeDailog != null) {
                    closeDailog.show();
                }
            }
        });
        mLinWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isWifiConnect() && wifiDailog != null) {
                    wifiDailog.show();
                }
            }
        });
        mLinCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (commanDailog != null) {
                    commanDailog.show();
                }
            }
        });
        mButScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ReadyToScanActivity.class));
            }
        });
        mButHibitionRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra(Constant.ItentKey1, AppConstant.WEBVIEW_SORT(ConfigManager.Foot.getCustomer_id(), ConfigManager.Foot.getCustomer_phone()));
                startActivity(intent);

            }
        });
        commanDailog.setSetClickListener(new CommanDailog.OnSetClickListener() {
            @Override
            public void onSetData(String command) {
                CommanD(command);
            }
        });
        closeDailog.setSetClickListener(new CloseDailog.OnSetClickListener() {
            @Override
            public void onSetData() {
                ScanClose();
            }
        });
        wifiDailog.setSetClickListener(new WifiDailog.OnSetClickListener() {
            @Override
            public void onSetData(String name, String pwd) {
                WifiData(name, pwd);
            }
        });
    }

    private void WifiData(String name, String pwd) {
        //修改Wi-Fi连接
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

    private void ScanClose() {
        //关闭扫描仪
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

    private void CommanD(String command) {
        //口令
        OkGo.<BaseResponse<String>>post(AppConstant.COMMAND)
                .params("phone", ConfigManager.Device.getShopPhone())
                .params("password", md5(command))
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {

                        CommandBean commandBean = gson.fromJson(String.valueOf(response.body()), CommandBean.class);
                        if (commandBean!=null&&commandBean.getCode()==0){

                            Intent intent = new Intent(MainActivity.this, WebActivity.class);
                            intent.putExtra(Constant.ItentKey1, AppConstant.WEBVIEW_EXHIBITIONROOM);
                            startActivity(intent);
                        }else {
                            ShowToast("口令错误");
                        }

                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {

                    }
                });
    }

    private void initData() {
        //登陆
        OkGo.<BaseResponse<String>>get(AppConstant.SOWING_MAP)
                .params("shop_id", ConfigManager.Device.getShopID())
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {

                        DatailImageBean datailImageBean = gson.fromJson(String.valueOf(response.body()), DatailImageBean.class);
                        if (datailImageBean.getResult() != null) {
                            Log.e("BaseResponse", datailImageBean.getResult().size() + ".....");
                            for (DatailImageBean.ResultBean resultBean : datailImageBean.getResult()) {
                                DetailList.add("http://resources_test.bintutu.com/merchandise_img/homepage_img" + resultBean.getImg());
                                mReCverFlow.setAdapter(new CoverFlowAdapter(MainActivity.this, DetailList));


                               /* mReCverFlow.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                    @Override
                                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                            int i = layoutManager.findFirstVisibleItemPosition() % DetailList.size();

                                        }
                                    }
                                });*/
                                ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
                                scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.e("initData","...33333333333333333333333...");
                                        mReCverFlow.smoothScrollToPosition(mReCverFlow.getCoverFlowLayout().getCenterPosition() + 1);
                                    }
                                }, 2000, 2000, TimeUnit.MILLISECONDS);


                            }

                        }


                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {

                    }
                });




        //        mList.setFlatFlow(true); //平面滚动
//        mList.setGreyItem(true); //设置灰度渐变
//        mList.setAlphaItem(true); //设置半透渐变

        mReCverFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                // ((TextView) findViewById(R.id.index)).setText((position + 1) + "/" + mList.getLayoutManager().getItemCount());
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
