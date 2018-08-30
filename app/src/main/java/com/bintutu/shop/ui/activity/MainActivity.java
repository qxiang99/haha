package com.bintutu.shop.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.BaseResponse;
import com.bintutu.shop.bean.ImageBean;
import com.bintutu.shop.bean.ScanBean;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.adapter.CoverFlowAdapter;
import com.bintutu.shop.ui.view.CloseDailog;
import com.bintutu.shop.ui.view.CommanDailog;
import com.bintutu.shop.ui.view.CoverFlowLayoutManger;
import com.bintutu.shop.ui.view.RecyclerCoverFlow;
import com.bintutu.shop.ui.view.WifiDailog;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.ConfigManager;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
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

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void init() {
        initData();
    }


    @Override
    protected void setListener() {
        mLinPowerOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseDailog closeDailog = new CloseDailog(MainActivity.this);
                closeDailog.show();
            }
        });
        mLinWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiDailog wifiDailog = new WifiDailog(MainActivity.this);
                wifiDailog.show();
            }
        });
        mLinCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommanDailog commanDailog = new CommanDailog(MainActivity.this);
                commanDailog.show();
            }
        });
        mButScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ReadyToScanActivity.class));
            }
        });
        mButHibitionRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,WebActivity.class));
            }
        });
    }

    private void initData() {
      /*  //登陆
        OkGo.<BaseResponse<String>>post("http://opzhpptsb.bkt.clouddn.com/image.json")
                .params("shop_id", "67901")
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        Log.e("BaseResponse",response.body()+".....11");
                        Gson gson = new Gson();
                        ImageBean imageBean = gson.fromJson(String.valueOf(response.body()), ImageBean.class);
                        Log.e("BaseResponse",imageBean.getData().getLinks().size()+".....");
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {

                    }
                });*/

        List<String> DetailList = new ArrayList<>();
        DetailList.add("http://opzhpptsb.bkt.clouddn.com/one.jpg");
        DetailList.add("http://opzhpptsb.bkt.clouddn.com/two.jpg");
        DetailList.add("http://opzhpptsb.bkt.clouddn.com/tree.jpg");
        DetailList.add("http://opzhpptsb.bkt.clouddn.com/four.jpg");
        DetailList.add("http://opzhpptsb.bkt.clouddn.com/five.jpg");
        DetailList.add("http://opzhpptsb.bkt.clouddn.com/six.jpg");
        DetailList.add("http://opzhpptsb.bkt.clouddn.com/nine.jpg");

        //        mList.setFlatFlow(true); //平面滚动
//        mList.setGreyItem(true); //设置灰度渐变
//        mList.setAlphaItem(true); //设置半透渐变
        mReCverFlow.setAdapter(new CoverFlowAdapter(this, DetailList));
        mReCverFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
               // ((TextView) findViewById(R.id.index)).setText((position + 1) + "/" + mList.getLayoutManager().getItemCount());
            }
        });
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
}
