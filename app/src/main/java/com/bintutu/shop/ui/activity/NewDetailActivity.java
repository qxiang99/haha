package com.bintutu.shop.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.BaseResponse;
import com.bintutu.shop.bean.DetailBean;
import com.bintutu.shop.bean.FootTagBean;
import com.bintutu.shop.bean.InstepBean;
import com.bintutu.shop.bean.LeftBean;
import com.bintutu.shop.bean.RightBean;
import com.bintutu.shop.bean.TAGBean;
import com.bintutu.shop.bean.ToleranceBean;
import com.bintutu.shop.bean.UploadBean;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.adapter.DetailAdapter;
import com.bintutu.shop.ui.server.UploadService;
import com.bintutu.shop.ui.view.ImageTwoDailog;
import com.bintutu.shop.ui.view.LoginDailog;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.ConfigManager;
import com.bintutu.shop.utils.Constant;
import com.bintutu.shop.utils.DebugLog;
import com.bintutu.shop.utils.EventMsg;
import com.bintutu.shop.utils.GlideUtil;
import com.bintutu.shop.utils.RxBus;
import com.bintutu.shop.utils.Utils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class NewDetailActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.detail_all_scroll)
    ScrollView detailALLScroll;
    @BindView(R.id.detail_lin_image)
    LinearLayout mLinImage;
    @BindView(R.id.detail_text_titletime)
    TextView mDetailTextTime;
    @BindView(R.id.detail_image_left)
    ImageView detailImageLeft;
    @BindView(R.id.detail_image_center)
    ImageView detailImageCenter;
    @BindView(R.id.detail_image_right)
    ImageView detailImageRight;
    @BindView(R.id.detail_image_left_two)
    ImageView detailImageLeftTwo;
    @BindView(R.id.detail_image_center_two)
    ImageView detailImageCenterTwo;
    @BindView(R.id.detail_image_right_two)
    ImageView detailImageRightTwo;
    @BindView(R.id.detail_but_left)
    Button detailButLeft;
    @BindView(R.id.detail_but_delete)
    Button detailButDelete;
    @BindView(R.id.detail_but_right)
    Button detailButRight;
    @BindView(R.id.ready_but_return)
    Button detailButReturn;
    @BindView(R.id.ready_but_home)
    Button detailButHome;
    @BindView(R.id.detail_but_upload)
    Button detailButUpload;
    @BindView(R.id.detail_but_save)
    Button detailButSave;
    @BindView(R.id.detail_image_footleft)
    ImageView detailImageFootleft;
    @BindView(R.id.detail_image_footright)
    ImageView detailImageFootright;
    @BindView(R.id.detail_image_plantarleft)
    ImageView detailImagePlantarleft;
    @BindView(R.id.detail_image_plantarright)
    ImageView detailImagePlantarright;
    @BindView(R.id.data_lin_addtag)
    LinearLayout dataLinAddtag;
    @BindView(R.id.detail_edit_remark)
    EditText dataEditRemark;

    private List<TAGBean> TaglList = new ArrayList<>();
    private List<DetailBean> DetailList = new ArrayList<>();
    private List<FootTagBean> footList = new ArrayList<>();
    private Gson gson;
    private LeftBean LeftFootBean;
    private RightBean RightFootBean;
    private String number = "0";
    private String loginnumber;
    private String loginphone;
    private String logincustomer_id;
    private String uploadid = "0";
    private HashMap<View, Bitmap> map;
    private LoginDailog loginDailog;
    private int Retryleft = 0;
    private int Retryright = 0;
    private boolean Responseleft = false;
    private boolean Responserighht = false;
    private boolean leftANDrighht = true;
    private String activtyurl = "";
    private Thread footThread;
    private String footlen;
    private ToleranceBean.DataBean TolerancedataBeans;
    private InstepBean.DataBean InstepdataBeans;
    private LeftBean newleftBean;
    private RightBean newRightBean;


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected void init() {
        gson = new Gson();
        //获取上一界面发送给扫描仪的指令
        getContent();
        //初始化Recyclerview
        showRecyclerview();
        //初始化LoginDailog
        loginDailog = new LoginDailog(this);
        //请求左右脚数据
        jumpLoading("加载足型数据");
        //
        getfoot();
        //加载四张图片
        LoadingImage();
        //设置采集数据时间
        DataTime();
        //设置六张足型图片
        SetSixFootImage();
        //
        Upload();
        detailButLeft.setEnabled(false);
        detailButRight.setEnabled(true);


    }

    private void getContent() {
        Intent intent = getIntent();
        number = intent.getStringExtra(Constant.ItentKey1);
        int type = intent.getIntExtra(Constant.ItentKey7, 0);
        if (type == 1) {
            int sex = intent.getIntExtra(Constant.ItentKey2, 0);
            int Forehand = intent.getIntExtra(Constant.ItentKey3, 0);
            int Instep = intent.getIntExtra(Constant.ItentKey4, 0);
            int edit = intent.getIntExtra(Constant.ItentKey5, 0);

            String SexName = "";
            String ForehandName = "";
            String InstepName = "";
            if (sex == 1) {
                SexName = "m";
            } else if (sex == 2) {
                SexName = "f";
            }
            if (Forehand == 1) {
                ForehandName = "S";
            } else if (Forehand == 2) {
                ForehandName = "M";
            } else if (Forehand == 2) {
                ForehandName = "L";
            }
            if (Instep == 1) {
                InstepName = "S";
            } else if (Instep == 2) {
                InstepName = "M";
            } else if (Instep == 2) {
                InstepName = "L";
            }
            //得到需要编辑的数据
            getTolerance(SexName+edit+ForehandName);
            getInStep(SexName+edit+InstepName);
        }
    }


    @Override
    protected void setListener() {
        //清除小红点标注
        detailButDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                SetSixFootImage();
                TaglList.clear();
            }
        });
        //返回上一页
        detailButReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfigManager.Foot.setCustomer_id("");
                ConfigManager.Foot.setCustomer_phone("");
                ConfigManager.Foot.setIdid("");
                ConfigManager.Foot.setchoosed_fur_id("");
                ConfigManager.Foot.setchoosed_color_id("");
                ConfigManager.Foot.setchoosed_exclusive_id("");
                ConfigManager.Foot.setchoosed_sole_accessory_id("");
                ConfigManager.Foot.setchoosed_sole_material_id("");

                finish();
            }
        });
        //返回首页
        detailButHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfigManager.Foot.setCustomer_id("");
                ConfigManager.Foot.setCustomer_phone("");
                ConfigManager.Foot.setIdid("");
                ConfigManager.Foot.setchoosed_fur_id("");
                ConfigManager.Foot.setchoosed_color_id("");
                ConfigManager.Foot.setchoosed_exclusive_id("");
                ConfigManager.Foot.setchoosed_sole_accessory_id("");
                ConfigManager.Foot.setchoosed_sole_material_id("");
                startActivity(new Intent(NewDetailActivity.this, MainActivity.class));
                finish();
            }
        });
        //切换左脚
        detailButLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailButLeft.setEnabled(false);
                detailButRight.setEnabled(true);
                detailImageLeft.setVisibility(View.VISIBLE);
                detailImageCenter.setVisibility(View.VISIBLE);
                detailImageRight.setVisibility(View.VISIBLE);
                detailImageLeftTwo.setVisibility(View.GONE);
                detailImageCenterTwo.setVisibility(View.GONE);
                detailImageRightTwo.setVisibility(View.GONE);
            }
        });
        //切换右脚
        detailButRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailButLeft.setEnabled(true);
                detailButRight.setEnabled(false);
                detailImageLeft.setVisibility(View.GONE);
                detailImageCenter.setVisibility(View.GONE);
                detailImageRight.setVisibility(View.GONE);
                detailImageLeftTwo.setVisibility(View.VISIBLE);
                detailImageCenterTwo.setVisibility(View.VISIBLE);
                detailImageRightTwo.setVisibility(View.VISIBLE);

            }
        });

        detailImageLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpaceImage("左脚内侧", detailImageLeft, map.get(detailImageLeft), footList);
            }
        });
        detailImageCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpaceImage("左脚脚面", detailImageCenter, map.get(detailImageCenter), footList);
            }
        });
        detailImageRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpaceImage("左脚外侧", detailImageRight, map.get(detailImageRight), footList);
            }
        });
        detailImageLeftTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpaceImage("右脚内侧", detailImageLeftTwo, map.get(detailImageLeftTwo), footList);
            }
        });
        detailImageCenterTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpaceImage("右脚脚面", detailImageCenterTwo, map.get(detailImageCenterTwo), footList);
            }
        });
        detailImageRightTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpaceImage("右脚外侧", detailImageRightTwo, map.get(detailImageRightTwo), footList);
            }
        });

        //上传扫描数据
        detailButUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConfigManager.Foot.getCustomer_id() != null && !ConfigManager.Foot.getCustomer_id().equals("")) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date date = new Date(System.currentTimeMillis());
                    loginnumber = simpleDateFormat.format(date);
                    loginphone = ConfigManager.Foot.getCustomer_phone();
                    logincustomer_id = ConfigManager.Foot.getCustomer_id();
                    upload(loginnumber, loginphone, logincustomer_id);
                } else if (ConfigManager.Foot.getWebFitting_id() != null && !ConfigManager.Foot.getWebFitting_id().equals("")) {
                    upDataload(ConfigManager.Foot.getWebFitting_id());
                } else {
                    loginDailog.show();
                }

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
                loginnumber = number;
                loginphone = phone;
                ConfigManager.Foot.setCustomer_id(customer_id);
                ConfigManager.Foot.setCustomer_phone(phone);
                logincustomer_id = customer_id;
                upload(loginnumber, loginphone, logincustomer_id);
            }
        });

        //点击截屏按钮
        detailButSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                Date date = new Date(System.currentTimeMillis());
                String scanNametime = simpleDateFormat.format(date);
                try {
                    saveMyBitmap(Utils.getScrollViewBitmap(detailALLScroll), scanNametime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 得到需要编辑的数据
     * @param s
     */
    private void getTolerance(String id) {
        InputStream is;
        StringBuffer buffer = null;
        try {
            is = getAssets().open("foot.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ToleranceBean toleranceBean = gson.fromJson(buffer.toString(), ToleranceBean.class);
        for (ToleranceBean.DataBean dataBean :toleranceBean.getData()){
            if (id.equals(dataBean.getId())){
                TolerancedataBeans = dataBean;
            }
        }
    }

    private void getInStep(String id) {
        InputStream is;
        StringBuffer buffer = null;
        try {
            is = getAssets().open("instep.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        InstepBean instepBean = gson.fromJson(buffer.toString(), InstepBean.class);
        for (InstepBean.DataBean dataBean :instepBean.getData()){
            if (id.equals(dataBean.getId())){
                InstepdataBeans = dataBean;
            }
        }
    }

    /**
     * 初始化Recyclerview
     */
    private void showRecyclerview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 设置采集数据时间
     */
    private void DataTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String scanNametime = simpleDateFormat.format(date);
        mDetailTextTime.setText("(数据采集日期 " + scanNametime + ")");
    }

    /**
     * 取出该图片小红点标注数据，然后从原list移除 传入Dailog
     *
     * @param name
     * @param view
     * @param imageRe
     * @param List
     */
    private void showSpaceImage(String name, ImageView view, Bitmap imageRe, List<FootTagBean> List) {

        List<FootTagBean> newFoot = new ArrayList<>();
        newFoot.addAll(List);
        List<FootTagBean> footl = new ArrayList<>();
        if (newFoot != null && newFoot.size() > 0) {
            for (FootTagBean TagBean : newFoot) {
                if (TagBean.getName().equals(name)) {
                    footl.add(TagBean);
                    footList.remove(TagBean);
                }
            }
        }
        ImageTwoDailog imageTwoDailog = new ImageTwoDailog(this);
        imageTwoDailog.show();
        imageTwoDailog.setImage(name, view, imageRe, footl);
        imageTwoDailog.setImageClickListener(new ImageTwoDailog.OnImageClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSetData(ImageView view, Bitmap viewBitmap, List<FootTagBean> mFootList) {
                view.setBackground(new BitmapDrawable(getResources(), viewBitmap));
                if (mFootList != null && mFootList.size() > 0) {
                    if (!footList.contains(mFootList)) {
                        footList.addAll(mFootList);
                    }
                }
                addTag();
            }
        });
    }

    public void getfoot() {

        footThread = new Thread(new Runnable() {
            @Override
            public void run() {

                long one = System.currentTimeMillis();
                long two;
                do {

                    if (!Responseleft) {
                        OkGo.<BaseResponse<String>>get(AppConstant.LEFT_JSON(number))
                                .execute(new JsonCallback<BaseResponse<String>>() {
                                    @Override
                                    public void onSuccess(Response<BaseResponse<String>> response) {
                                        LeftFootBean = gson.fromJson(String.valueOf(response.body()), LeftBean.class);
                                        Responseleft = true;
                                        if (Responseleft == true && Responserighht == true) {
                                            getFootData(2);
                                        }
                                    }

                                    @Override
                                    public void onError(Response<BaseResponse<String>> response) {
                                    }
                                });
                    }

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (!Responserighht) {
                        OkGo.<BaseResponse<String>>get(AppConstant.RIGHT_JSON(number))
                                .execute(new JsonCallback<BaseResponse<String>>() {
                                    @Override
                                    public void onSuccess(Response<BaseResponse<String>> response) {
                                        RightFootBean = gson.fromJson(String.valueOf(response.body()), RightBean.class);
                                        Responserighht = true;
                                        if (Responseleft == true && Responserighht == true) {
                                            getFootData(2);
                                        }
                                    }

                                    @Override
                                    public void onError(Response<BaseResponse<String>> response) {
                                    }
                                });
                    }

                    two = System.currentTimeMillis();

                }
                while ((!Responseleft || !Responserighht) && ((two - one) < 20000) && leftANDrighht);
                getFootData(1);
            }
        });
        footThread.start();

    }

    public void getFootData(int type) {
        Message message = new Message();
        message.what = type;
        mhandler.sendMessage(message);
    }

    Handler mhandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (mDetailTextTime != null) {
                        closeLoading();
                        ShowToast("数据拉取失败");
                    }
                    break;
                case 2:
                    closeLoading();
                    getData();
                    break;
            }
            return true;
        }
    });


    public void getData() {

        newleftBean = new LeftBean();
        newRightBean = new RightBean();

        if (TolerancedataBeans!=null){
            Tolerance(LeftFootBean,RightFootBean);
        }else {
            newleftBean=LeftFootBean;
            newRightBean=RightFootBean;
        }

        if (InstepdataBeans!=null){
            Instep(LeftFootBean,RightFootBean);
        }else {
            newleftBean=LeftFootBean;
            newRightBean=RightFootBean;
        }


        DetailList.clear();
        //DetailList.add(new DetailBean(getResources().getString(R.string.DouWei), 4, leftBean.get_6_JiaoZhiZhou(), rightBean.get_6_JiaoZhiZhou()));//6
        //DetailList.add(new DetailBean(getResources().getString(R.string.JiaoWanWei), 5, leftBean.get_14_JiaoZhiKuan(), rightBean.get_14_JiaoZhiKuan()));//14
        DetailList.add(new DetailBean(getResources().getString(R.string.FootLen), 1, newleftBean.get_1_FootLen(), newRightBean.get_1_FootLen()));//1
        DetailList.add(new DetailBean(getResources().getString(R.string.ZhiWei), 2, newleftBean.get_2_ZhiWei(), newRightBean.get_2_ZhiWei()));//2
        DetailList.add(new DetailBean(getResources().getString(R.string.FuWei), 3, newleftBean.get_3_FuWei(), newRightBean.get_3_FuWei()));//3
        DetailList.add(new DetailBean(getResources().getString(R.string.JiaoZhiZhou), 4, newleftBean.get_15_ZhiWeiKuan(), newRightBean.get_15_ZhiWeiKuan()));//4
        DetailList.add(new DetailBean(getResources().getString(R.string.WaiHuaiXiaGao), 5, newleftBean.get_16_DiBanKuan(), newRightBean.get_16_DiBanKuan()));//5
        DetailList.add(new DetailBean(getResources().getString(R.string.HouGenTuGao), 6, newleftBean.get_7_WaiHuaiXiaGao(), newRightBean.get_7_WaiHuaiXiaGao()));//6
        DetailList.add(new DetailBean(getResources().getString(R.string.ZhouShangGao), 7, newleftBean.get_10_FuWeiGao(), newRightBean.get_10_FuWeiGao()));//7
        DetailList.add(new DetailBean(getResources().getString(R.string.FuWeiGao), 8, newleftBean.get_11_1ZhiZhiGao(), newRightBean.get_11_1ZhiZhiGao()));//8
        DetailList.add(new DetailBean(getResources().getString(R.string.ZhiZhiGao), 9, newleftBean.get_12_DaMoZhiGao(), newRightBean.get_12_DaMoZhiGao()));//9
        DetailList.add(new DetailBean(getResources().getString(R.string.DaMoZhiGao), 10, newleftBean.get_17_MuZhiLiKuan(), newRightBean.get_17_MuZhiLiKuan()));//10
        DetailList.add(new DetailBean(getResources().getString(R.string.JiaoWanGao), 11, newleftBean.get_18_XiaoZhiWaiKuan(), newRightBean.get_18_XiaoZhiWaiKuan()));//11
        DetailList.add(new DetailBean(getResources().getString(R.string.JiaoZhiKuan), 12, newleftBean.get_19_1ZhizhiLiKuan(), newRightBean.get_19_1ZhizhiLiKuan()));//12
        DetailList.add(new DetailBean(getResources().getString(R.string.ZhiWeiKuan), 13, newleftBean.get_20_5ZhizhiLiKuan(), newRightBean.get_20_5ZhizhiLiKuan()));//13
        DetailList.add(new DetailBean(getResources().getString(R.string.DiBanKuan), 14, newleftBean.get_21_YaoWoWaiKuan(), newRightBean.get_21_YaoWoWaiKuan()));//14
        DetailList.add(new DetailBean(getResources().getString(R.string.MuZhiLiKuan), 15, newleftBean.get_22_ZhongXinDiKuan(), newRightBean.get_22_ZhongXinDiKuan()));//15
        DetailList.add(new DetailBean(getResources().getString(R.string.XiaoZhiWaiKuan), 16, newleftBean.get_24_MuZhiWaiTuChang(), newRightBean.get_24_MuZhiWaiTuChang()));//16
        DetailList.add(new DetailBean(getResources().getString(R.string.lZhizhiLiKuan), 17, newleftBean.get_25_XiaoZhiDuanChang(), newRightBean.get_25_XiaoZhiDuanChang()));//17
        DetailList.add(new DetailBean(getResources().getString(R.string.SZhizhiLiKuan), 18, newleftBean.get_26_XiaoZhiWaiTuChang(), newRightBean.get_26_XiaoZhiWaiTuChang()));//18
        DetailList.add(new DetailBean(getResources().getString(R.string.YaoWoWaiKuan), 19, newleftBean.get_27_1ZhiZhiChang(), newRightBean.get_27_1ZhiZhiChang()));//19
        DetailList.add(new DetailBean(getResources().getString(R.string.ZhongXinDiKuan), 20, newleftBean.get_28_5ZhiZhiChang(), newRightBean.get_28_5ZhiZhiChang()));//20
        DetailList.add(new DetailBean(getResources().getString(R.string.JiaoHuaiNeiKuan), 21, newleftBean.get_29_FuGuChang(), newRightBean.get_29_FuGuChang()));//21
        DetailList.add(new DetailBean(getResources().getString(R.string.MuZhiWaiTuChang), 22, newleftBean.get_30_YaoWoChang(), newRightBean.get_30_YaoWoChang()));//22
        DetailList.add(new DetailBean(getResources().getString(R.string.XiaoZhiDuanChang), 23, newleftBean.get_31_ZhouShangDianChang(), newRightBean.get_31_ZhouShangDianChang()));//23
        DetailList.add(new DetailBean(getResources().getString(R.string.XiaoZhiWaiTuChang), 24, newleftBean.get_32_WaiHuaiGuZhongChang(), newRightBean.get_32_WaiHuaiGuZhongChang()));//24
        DetailList.add(new DetailBean(getResources().getString(R.string.lZhiZhiChang), 25, newleftBean.get_33_ZhongXinChang(), newRightBean.get_33_ZhongXinChang()));//25
        DetailList.add(new DetailBean(getResources().getString(R.string.SZhiZhiChang), 26, newleftBean.get_34_HouGenBianChang(), newRightBean.get_34_HouGenBianChang()));//26
        DetailList.add(new DetailBean(getResources().getString(R.string.FuGuChang), 27, newleftBean.get_35_QianZhangTuDianChang(), newRightBean.get_35_QianZhangTuDianChang()));//27

        DetailAdapter detailAdapter = new DetailAdapter(DetailList);
        mRecyclerview.setAdapter(detailAdapter);
        footlen = String.valueOf(newleftBean.get_1_FootLen());
    }

    private void Instep(LeftBean LeftFootBean, RightBean RightFootBean) {
        newleftBean.set_3_FuWei(getNewValue(InstepdataBeans.get_3_FuWei(), InstepdataBeans.get_3_FuWei_F(), LeftFootBean.get_3_FuWei()));
        newleftBean.set_10_FuWeiGao(getNewValue(InstepdataBeans.get_10_FuWeiGao(), InstepdataBeans.get_10_FuWeiGao_F(), LeftFootBean.get_10_FuWeiGao()));

        newRightBean.set_3_FuWei(getNewValue(InstepdataBeans.get_3_FuWei(), InstepdataBeans.get_3_FuWei_F(), RightFootBean.get_3_FuWei()));
        newRightBean.set_10_FuWeiGao(getNewValue(InstepdataBeans.get_10_FuWeiGao(), InstepdataBeans.get_10_FuWeiGao_F(), RightFootBean.get_10_FuWeiGao()));
    }

    private void Tolerance( LeftBean LeftFootBean, RightBean RightFootBean) {
        newleftBean.set_1_FootLen(getNewValue(TolerancedataBeans.get_1_FootLen(), TolerancedataBeans.get_1_FootLen_F(), LeftFootBean.get_1_FootLen()));
        newleftBean.set_2_ZhiWei(getNewValue(TolerancedataBeans.get_2_ZhiWei(), TolerancedataBeans.get_2_ZhiWei_F(), LeftFootBean.get_2_ZhiWei()));
        newleftBean.set_15_ZhiWeiKuan(getNewValue(TolerancedataBeans.get_15_ZhiWeiKuan(), TolerancedataBeans.get_15_ZhiWeiKuan_F(), LeftFootBean.get_15_ZhiWeiKuan()));
        newleftBean.set_16_DiBanKuan(getNewValue(TolerancedataBeans.get_16_DiBanKuan(), TolerancedataBeans.get_16_DiBanKuan_F(), LeftFootBean.get_16_DiBanKuan()));
        newleftBean.set_7_WaiHuaiXiaGao(getNewValue(TolerancedataBeans.get_7_WaiHuaiXiaGao(), TolerancedataBeans.get_7_WaiHuaiXiaGao_F(), LeftFootBean.get_7_WaiHuaiXiaGao()));
        newleftBean.set_11_1ZhiZhiGao(getNewValue(TolerancedataBeans.get_11_1ZhiZhiGao(), TolerancedataBeans.get_11_1ZhiZhiGao_F(), LeftFootBean.get_11_1ZhiZhiGao()));
        newleftBean.set_12_DaMoZhiGao(getNewValue(TolerancedataBeans.get_12_DaMoZhiGao(), TolerancedataBeans.get_12_DaMoZhiGao_F(), LeftFootBean.get_12_DaMoZhiGao()));
        newleftBean.set_17_MuZhiLiKuan(getNewValue(TolerancedataBeans.get_17_MuZhiLiKuan(), TolerancedataBeans.get_17_MuZhiLiKuan_F(), LeftFootBean.get_17_MuZhiLiKuan()));
        newleftBean.set_18_XiaoZhiWaiKuan(getNewValue(TolerancedataBeans.get_18_XiaoZhiWaiKuan(), TolerancedataBeans.get_18_XiaoZhiWaiKuan_F(), LeftFootBean.get_18_XiaoZhiWaiKuan()));
        newleftBean.set_19_1ZhizhiLiKuan(getNewValue(TolerancedataBeans.get_19_1ZhizhiLiKuan(), TolerancedataBeans.get_19_1ZhizhiLiKuan_F(), LeftFootBean.get_19_1ZhizhiLiKuan()));
        newleftBean.set_20_5ZhizhiLiKuan(getNewValue(TolerancedataBeans.get_20_5ZhizhiLiKuan(), TolerancedataBeans.get_20_5ZhizhiLiKuan_F(), LeftFootBean.get_20_5ZhizhiLiKuan()));
        newleftBean.set_21_YaoWoWaiKuan(getNewValue(TolerancedataBeans.get_21_YaoWoWaiKuan(), TolerancedataBeans.get_21_YaoWoWaiKuan_F(), LeftFootBean.get_21_YaoWoWaiKuan()));
        newleftBean.set_22_ZhongXinDiKuan(getNewValue(TolerancedataBeans.get_22_ZhongXinDiKuan(), TolerancedataBeans.get_22_ZhongXinDiKuan_F(), LeftFootBean.get_22_ZhongXinDiKuan()));
        newleftBean.set_24_MuZhiWaiTuChang(getNewValue(TolerancedataBeans.get_24_MuZhiWaiTuChang(), TolerancedataBeans.get_24_MuZhiWaiTuChang_F(), LeftFootBean.get_24_MuZhiWaiTuChang()));
        newleftBean.set_25_XiaoZhiDuanChang(getNewValue(TolerancedataBeans.get_25_XiaoZhiDuanChang(), TolerancedataBeans.get_25_XiaoZhiDuanChang_F(), LeftFootBean.get_25_XiaoZhiDuanChang()));
        newleftBean.set_26_XiaoZhiWaiTuChang(getNewValue(TolerancedataBeans.get_26_XiaoZhiWaiTuChang(), TolerancedataBeans.get_26_XiaoZhiWaiTuChang_F(), LeftFootBean.get_26_XiaoZhiWaiTuChang()));
        newleftBean.set_27_1ZhiZhiChang(getNewValue(TolerancedataBeans.get_27_1ZhiZhiChang(), TolerancedataBeans.get_27_1ZhiZhiChang_F(), LeftFootBean.get_27_1ZhiZhiChang()));
        newleftBean.set_28_5ZhiZhiChang(getNewValue(TolerancedataBeans.get_28_5ZhiZhiChang(), TolerancedataBeans.get_28_5ZhiZhiChang_F(), LeftFootBean.get_28_5ZhiZhiChang()));
        newleftBean.set_29_FuGuChang(getNewValue(TolerancedataBeans.get_29_FuGuChang(), TolerancedataBeans.get_29_FuGuChang_F(), LeftFootBean.get_29_FuGuChang()));
        newleftBean.set_30_YaoWoChang(getNewValue(TolerancedataBeans.get_30_YaoWoChang(), TolerancedataBeans.get_30_YaoWoChang_F(), LeftFootBean.get_30_YaoWoChang()));
        newleftBean.set_31_ZhouShangDianChang(getNewValue(TolerancedataBeans.get_31_ZhouShangDianChang(), TolerancedataBeans.get_31_ZhouShangDianChang_F(), LeftFootBean.get_31_ZhouShangDianChang()));
        newleftBean.set_32_WaiHuaiGuZhongChang(getNewValue(TolerancedataBeans.get_32_WaiHuaiGuZhongChang(), TolerancedataBeans.get_32_WaiHuaiGuZhongChang_F(), LeftFootBean.get_32_WaiHuaiGuZhongChang()));
        newleftBean.set_33_ZhongXinChang(getNewValue(TolerancedataBeans.get_33_ZhongXinChang(), TolerancedataBeans.get_33_ZhongXinChang_F(), LeftFootBean.get_33_ZhongXinChang()));
        newleftBean.set_34_HouGenBianChang(getNewValue(TolerancedataBeans.get_34_HouGenBianChang(), TolerancedataBeans.get_34_HouGenBianChang_F(), LeftFootBean.get_34_HouGenBianChang()));
        newleftBean.set_35_QianZhangTuDianChang(getNewValue(TolerancedataBeans.get_35_QianZhangTuDianChang(), TolerancedataBeans.get_35_QianZhangTuDianChang_F(), LeftFootBean.get_35_QianZhangTuDianChang()));


        this.newRightBean.set_1_FootLen(getNewValue(TolerancedataBeans.get_1_FootLen(), TolerancedataBeans.get_1_FootLen_F(), RightFootBean.get_1_FootLen()));
        this.newRightBean.set_2_ZhiWei(getNewValue(TolerancedataBeans.get_2_ZhiWei(), TolerancedataBeans.get_2_ZhiWei_F(), RightFootBean.get_2_ZhiWei()));
        this.newRightBean.set_15_ZhiWeiKuan(getNewValue(TolerancedataBeans.get_15_ZhiWeiKuan(), TolerancedataBeans.get_15_ZhiWeiKuan_F(), RightFootBean.get_15_ZhiWeiKuan()));
        this.newRightBean.set_16_DiBanKuan(getNewValue(TolerancedataBeans.get_16_DiBanKuan(), TolerancedataBeans.get_16_DiBanKuan_F(), RightFootBean.get_16_DiBanKuan()));
        this.newRightBean.set_7_WaiHuaiXiaGao(getNewValue(TolerancedataBeans.get_7_WaiHuaiXiaGao(), TolerancedataBeans.get_7_WaiHuaiXiaGao_F(), RightFootBean.get_7_WaiHuaiXiaGao()));
        this.newRightBean.set_11_1ZhiZhiGao(getNewValue(TolerancedataBeans.get_11_1ZhiZhiGao(), TolerancedataBeans.get_11_1ZhiZhiGao_F(), RightFootBean.get_11_1ZhiZhiGao()));
        this.newRightBean.set_12_DaMoZhiGao(getNewValue(TolerancedataBeans.get_12_DaMoZhiGao(), TolerancedataBeans.get_12_DaMoZhiGao_F(), RightFootBean.get_12_DaMoZhiGao()));
        this.newRightBean.set_17_MuZhiLiKuan(getNewValue(TolerancedataBeans.get_17_MuZhiLiKuan(), TolerancedataBeans.get_17_MuZhiLiKuan_F(), RightFootBean.get_17_MuZhiLiKuan()));
        this.newRightBean.set_18_XiaoZhiWaiKuan(getNewValue(TolerancedataBeans.get_18_XiaoZhiWaiKuan(), TolerancedataBeans.get_18_XiaoZhiWaiKuan_F(), RightFootBean.get_18_XiaoZhiWaiKuan()));
        this.newRightBean.set_19_1ZhizhiLiKuan(getNewValue(TolerancedataBeans.get_19_1ZhizhiLiKuan(), TolerancedataBeans.get_19_1ZhizhiLiKuan_F(), RightFootBean.get_19_1ZhizhiLiKuan()));
        this.newRightBean.set_20_5ZhizhiLiKuan(getNewValue(TolerancedataBeans.get_20_5ZhizhiLiKuan(), TolerancedataBeans.get_20_5ZhizhiLiKuan_F(), RightFootBean.get_20_5ZhizhiLiKuan()));
        this.newRightBean.set_21_YaoWoWaiKuan(getNewValue(TolerancedataBeans.get_21_YaoWoWaiKuan(), TolerancedataBeans.get_21_YaoWoWaiKuan_F(), RightFootBean.get_21_YaoWoWaiKuan()));
        this.newRightBean.set_22_ZhongXinDiKuan(getNewValue(TolerancedataBeans.get_22_ZhongXinDiKuan(), TolerancedataBeans.get_22_ZhongXinDiKuan_F(), RightFootBean.get_22_ZhongXinDiKuan()));
        this.newRightBean.set_24_MuZhiWaiTuChang(getNewValue(TolerancedataBeans.get_24_MuZhiWaiTuChang(), TolerancedataBeans.get_24_MuZhiWaiTuChang_F(), RightFootBean.get_24_MuZhiWaiTuChang()));
        this.newRightBean.set_25_XiaoZhiDuanChang(getNewValue(TolerancedataBeans.get_25_XiaoZhiDuanChang(), TolerancedataBeans.get_25_XiaoZhiDuanChang_F(), RightFootBean.get_25_XiaoZhiDuanChang()));
        this.newRightBean.set_26_XiaoZhiWaiTuChang(getNewValue(TolerancedataBeans.get_26_XiaoZhiWaiTuChang(), TolerancedataBeans.get_26_XiaoZhiWaiTuChang_F(), RightFootBean.get_26_XiaoZhiWaiTuChang()));
        this.newRightBean.set_27_1ZhiZhiChang(getNewValue(TolerancedataBeans.get_27_1ZhiZhiChang(), TolerancedataBeans.get_27_1ZhiZhiChang_F(), RightFootBean.get_27_1ZhiZhiChang()));
        this.newRightBean.set_28_5ZhiZhiChang(getNewValue(TolerancedataBeans.get_28_5ZhiZhiChang(), TolerancedataBeans.get_28_5ZhiZhiChang_F(), RightFootBean.get_28_5ZhiZhiChang()));
        this.newRightBean.set_29_FuGuChang(getNewValue(TolerancedataBeans.get_29_FuGuChang(), TolerancedataBeans.get_29_FuGuChang_F(), RightFootBean.get_29_FuGuChang()));
        this.newRightBean.set_30_YaoWoChang(getNewValue(TolerancedataBeans.get_30_YaoWoChang(), TolerancedataBeans.get_30_YaoWoChang_F(), RightFootBean.get_30_YaoWoChang()));
        this.newRightBean.set_31_ZhouShangDianChang(getNewValue(TolerancedataBeans.get_31_ZhouShangDianChang(), TolerancedataBeans.get_31_ZhouShangDianChang_F(), RightFootBean.get_31_ZhouShangDianChang()));
        this.newRightBean.set_32_WaiHuaiGuZhongChang(getNewValue(TolerancedataBeans.get_32_WaiHuaiGuZhongChang(), TolerancedataBeans.get_32_WaiHuaiGuZhongChang_F(), RightFootBean.get_32_WaiHuaiGuZhongChang()));
        this.newRightBean.set_33_ZhongXinChang(getNewValue(TolerancedataBeans.get_33_ZhongXinChang(), TolerancedataBeans.get_33_ZhongXinChang_F(), RightFootBean.get_33_ZhongXinChang()));
        this.newRightBean.set_34_HouGenBianChang(getNewValue(TolerancedataBeans.get_34_HouGenBianChang(), TolerancedataBeans.get_34_HouGenBianChang_F(), RightFootBean.get_34_HouGenBianChang()));
        this.newRightBean.set_35_QianZhangTuDianChang(getNewValue(TolerancedataBeans.get_35_QianZhangTuDianChang(), TolerancedataBeans.get_35_QianZhangTuDianChang_F(), RightFootBean.get_35_QianZhangTuDianChang()));


    }

    private void upDataload(final String webFitting_id) {
        jumpLoading("上传数据中");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("left", newleftBean);
        map.put("right", newRightBean);
        String detailData = gson.toJson(map);
        sortListTag(footList);
        String foot_remark = gson.toJson(TaglList);
        Log.e("foot_remark", "......" + foot_remark);
        final String remark = dataEditRemark.getText().toString().trim();
        //上传数据
        OkGo.<BaseResponse<String>>post(AppConstant.FITTING_ADDFOOTDATAWITHFITTING)
                .params("id", webFitting_id)//数据的id
                .params("foot_remark", foot_remark)//图片的标记
                .params("detail_data", detailData)//足型数据详情
                .params("device_id", ConfigManager.Device.getEquipmentID())//设备id
                .params("remark", remark + "")//备注
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        closeLoading();
                        String data = String.valueOf(response.body());
                        UploadBean uploadBean = gson.fromJson(data, UploadBean.class);
                        if (uploadBean != null & uploadBean.getCode() == 0) {
                            ShowToast("上传成功");


                            try {
                                EventMsg eventMsg = new EventMsg();
                                eventMsg.setCode(200);
                                eventMsg.setMsg(webFitting_id);
                                RxBus.getInstance().post(eventMsg);
                            } catch (Exception e) {
                            }

                            EventMsg eventMsgtwo = new EventMsg();
                            eventMsgtwo.setCode(800);
                            RxBus.getInstance().post(eventMsgtwo);

                            ConfigManager.Foot.setWebFitting_id("");

                            String url = AppConstant.WEBVIEW_INFORMATION(ConfigManager.Device.getShopID(), ConfigManager.Device.getShopPhone());
                            Intent intent = new Intent(NewDetailActivity.this, WebActivity.class);
                            intent.putExtra(Constant.ItentKey1, url);
                            startActivity(intent);
                            finish();

                            finish();
                        } else {
                            ShowToast("上传失败");
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                        ShowToast("上传失败");
                        closeLoading();
                    }
                });
    }

    private void upload(String number, String phone, String customer_id) {
        jumpLoading("上传数据中");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("left", newleftBean);
        map.put("right", newRightBean);
        String detailData = gson.toJson(map);
        sortListTag(footList);
        String foot_remark = gson.toJson(TaglList);
        Log.e("foot_remark", "......" + foot_remark);
        final String remark = dataEditRemark.getText().toString().trim();
        //上传数据
        OkGo.<BaseResponse<String>>post(AppConstant.NEW_DATA)
                .params("name", number)//自定的一个名字
                .params("customer_id", customer_id)//手机号验证码请求成功返回的id
                .params("customer_phone", phone)//手机号
                .params("shop_id", ConfigManager.Device.getShopID())//商铺号
                .params("device_id", ConfigManager.Device.getEquipmentID())//判断设备在不在线的返回的数据
                .params("foot_remark", foot_remark)//数据界面上面需要标记的图片json
                .params("detail_data", detailData)//left.json+right.json
                .params("remark", remark + "")//备注
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        closeLoading();
                        String data = String.valueOf(response.body());
                        UploadBean uploadBean = gson.fromJson(data, UploadBean.class);
                        if (uploadBean != null & uploadBean.getCode() == 0) {
                            ShowToast("上传成功");
                            uploadid = uploadBean.getResult().getId();
                            EventMsg eventMsg = new EventMsg();
                            eventMsg.setCode(200);
                            eventMsg.setMsg(uploadid);
                            RxBus.getInstance().post(eventMsg);

                            EventMsg eventMsgtwo = new EventMsg();
                            eventMsgtwo.setCode(800);
                            RxBus.getInstance().post(eventMsgtwo);


                            if (ConfigManager.Foot.getIdid() != null && !ConfigManager.Foot.getIdid().equals("")) {
                                Intent intent = new Intent(NewDetailActivity.this, WebActivity.class);
                                intent.putExtra(Constant.ItentKey1, AppConstant.WEBVIEW_CHOOSE(
                                        ConfigManager.Foot.getCustomer_id(),
                                        ConfigManager.Foot.getCustomer_phone(),
                                        ConfigManager.Foot.getIdid(),
                                        ConfigManager.Foot.getchoosed_color_id(),
                                        ConfigManager.Foot.getchoosed_fur_id(),
                                        ConfigManager.Foot.getchoosed_sole_material_id(),
                                        ConfigManager.Foot.getchoosed_sole_accessory_id(),
                                        ConfigManager.Foot.getchoosed_exclusive_id()));
                                startActivity(intent);
                                finish();
                                ConfigManager.Foot.setCustomer_id("");
                                ConfigManager.Foot.setCustomer_phone("");
                                ConfigManager.Foot.setIdid("");
                                ConfigManager.Foot.setchoosed_fur_id("");
                                ConfigManager.Foot.setchoosed_fur_id("");
                                ConfigManager.Foot.setchoosed_color_id("");
                                ConfigManager.Foot.setchoosed_exclusive_id("");
                                ConfigManager.Foot.setchoosed_sole_accessory_id("");
                                ConfigManager.Foot.setchoosed_sole_material_id("");

                            } else {
                                Intent intent = new Intent(NewDetailActivity.this, UploadSucessActivity.class);
                                intent.putExtra(Constant.ItentKey1, uploadid);
                                intent.putExtra(Constant.ItentKey2, footlen);
                                startActivity(intent);
                                finish();
                            }

                        } else {
                            ShowToast("上传失败");
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                        ShowToast("上传失败");
                        closeLoading();
                    }
                });
    }


    private void Upload() {
        /*启动服务*/
        Intent intent = new Intent(this, UploadService.class);
        intent.putExtra(Constant.ItentKey1, number);
        startService(intent);
    }


    private double getNewValue(double footLen, double footLen_f, double footLen1) {
        if ((footLen + footLen_f) > footLen1 && (footLen - footLen_f) < footLen1) {
            return footLen1;
        } else {
            double a =  Math.random()>0.5?0.1:0.2;
            return footLen+a;
        }
    }

    public void addTag() {
        dataLinAddtag.setVisibility(View.VISIBLE);
        dataLinAddtag.removeAllViews();
        if (footList != null && footList.size() > 0) {
            for (int i = 0; i < footList.size(); i++) {
                View view = LayoutInflater.from(NewDetailActivity.this).inflate(R.layout.detail_tag_item, null);
                TextView number = view.findViewById(R.id.tag_text_number);
                number.setText(footList.get(i).getIndex() + "");
                TextView title = view.findViewById(R.id.tag_text_title);
                title.setText("[" + footList.get(i).getName() + "] : ");
                TextView content = view.findViewById(R.id.tag_text_content);
                content.setText(footList.get(i).getContent());
                dataLinAddtag.addView(view);

            }
        } else {
            dataLinAddtag.setVisibility(View.GONE);
        }
    }


    private void SetSixFootImage() {
        Bitmap leftfoot_internal = BitmapFactory.decodeResource(getResources(), R.mipmap.leftfoot_internal);
        Bitmap leftfoot_surface = BitmapFactory.decodeResource(getResources(), R.mipmap.leftfoot_surface);
        Bitmap leftfoot_outside = BitmapFactory.decodeResource(getResources(), R.mipmap.leftfoot_outside);
        Bitmap rightfoot_internal = BitmapFactory.decodeResource(getResources(), R.mipmap.rightfoot_internal);
        Bitmap rightfoot_surface = BitmapFactory.decodeResource(getResources(), R.mipmap.rightfoot_surface);
        Bitmap rightfoot_outside = BitmapFactory.decodeResource(getResources(), R.mipmap.rightfoot_outside);

        map = new HashMap<>();
        map.put(detailImageLeft, leftfoot_internal);
        map.put(detailImageCenter, leftfoot_surface);
        map.put(detailImageRight, leftfoot_outside);
        map.put(detailImageLeftTwo, rightfoot_internal);
        map.put(detailImageCenterTwo, rightfoot_surface);
        map.put(detailImageRightTwo, rightfoot_outside);

        detailImageLeft.setBackground(new BitmapDrawable(getResources(), map.get(detailImageLeft)));
        detailImageCenter.setBackground(new BitmapDrawable(getResources(), map.get(detailImageCenter)));
        detailImageRight.setBackground(new BitmapDrawable(getResources(), map.get(detailImageRight)));
        detailImageLeftTwo.setBackground(new BitmapDrawable(getResources(), map.get(detailImageLeftTwo)));
        detailImageCenterTwo.setBackground(new BitmapDrawable(getResources(), map.get(detailImageCenterTwo)));
        detailImageRightTwo.setBackground(new BitmapDrawable(getResources(), map.get(detailImageRightTwo)));

    }

    private void LoadingImage() {

        GlideUtil.load(NewDetailActivity.this, AppConstant.IMAGE_ONE(number), detailImageFootleft);
        GlideUtil.load(NewDetailActivity.this, AppConstant.IAMGE_TWO(number), detailImageFootright);
        GlideUtil.load(NewDetailActivity.this, AppConstant.IMAGE_TREE(number), detailImagePlantarleft);
        GlideUtil.load(NewDetailActivity.this, AppConstant.IAMGE_FOUR(number), detailImagePlantarright);


        GlideUtil.load(NewDetailActivity.this, AppConstant.IMAGE_ONE(number), detailImageFootleft, "1-show.jpg");
        GlideUtil.load(NewDetailActivity.this, AppConstant.IAMGE_TWO(number), detailImageFootright, "0-show.jpg");
        GlideUtil.load(NewDetailActivity.this, AppConstant.IMAGE_TREE(number), detailImagePlantarleft, "5_l-show.jpg");
        GlideUtil.load(NewDetailActivity.this, AppConstant.IAMGE_FOUR(number), detailImagePlantarright, "5_r-show.jpg");

    }

    private void sortListTag(List<FootTagBean> mFootList) {


        TAGBean medial_left = null;
        TAGBean face_left = null;
        TAGBean lateral_left = null;
        TAGBean medial_right = null;
        TAGBean face_right = null;
        TAGBean lateral_right = null;
        //左脚内侧
        List<TAGBean.DetailListBean> detailmedial_left = new ArrayList<>();
        //左脚脚面
        List<TAGBean.DetailListBean> detailface_left = new ArrayList<>();
        //左脚外侧
        List<TAGBean.DetailListBean> detaillateral_left = new ArrayList<>();
        //右脚内侧
        List<TAGBean.DetailListBean> detailmedial_right = new ArrayList<>();
        //右脚脚面
        List<TAGBean.DetailListBean> detailface_right = new ArrayList<>();
        //右脚外侧
        List<TAGBean.DetailListBean> detaillateral_right = new ArrayList<>();


        for (int i = 0; i < TaglList.size(); i++) {
            if (TaglList.get(i).getName().equals("左脚内侧")) {
                medial_left = TaglList.get(i);
                detailmedial_left.addAll(detailmedial_left);
            }
            if (TaglList.get(i).getName().equals("左脚脚面")) {
                face_left = TaglList.get(i);
                detailface_left.addAll(TaglList.get(i).getDetailList());
            }
            if (TaglList.get(i).getName().equals("左脚外侧")) {
                lateral_left = TaglList.get(i);
                detaillateral_left.addAll(TaglList.get(i).getDetailList());
            }
            if (TaglList.get(i).getName().equals("右脚内侧")) {
                medial_right = TaglList.get(i);
                detailmedial_right.addAll(TaglList.get(i).getDetailList());
            }
            if (TaglList.get(i).getName().equals("右脚脚面")) {
                face_right = TaglList.get(i);
                detailface_right.addAll(TaglList.get(i).getDetailList());
            }
            if (TaglList.get(i).getName().equals("右脚外侧")) {
                lateral_right = TaglList.get(i);
                detaillateral_right.addAll(TaglList.get(i).getDetailList());
            }
        }

        TaglList.clear();


        for (FootTagBean footTagBean : mFootList) {

            TAGBean tagBean = new TAGBean();
            tagBean.setFoot(footTagBean.getFoot());
            tagBean.setId(footTagBean.getId());
            tagBean.setItem(footTagBean.getItem());
            tagBean.setName(footTagBean.getName());

            TAGBean.DetailListBean detailListBean = new TAGBean.DetailListBean();
            detailListBean.setContent(footTagBean.getContent());
            detailListBean.setPageX(footTagBean.getPageX());
            detailListBean.setPageY(footTagBean.getPageY());
            detailListBean.setX(footTagBean.getX());
            detailListBean.setY(footTagBean.getY());
            detailListBean.setIndex(footTagBean.getIndex());
            if (footTagBean.getName().equals("左脚内侧")) {
                medial_left = tagBean;
                detailmedial_left.add(detailListBean);
            }
            if (footTagBean.getName().equals("左脚脚面")) {
                face_left = tagBean;
                detailface_left.add(detailListBean);
            }
            if (footTagBean.getName().equals("左脚外侧")) {
                lateral_left = tagBean;
                detaillateral_left.add(detailListBean);
            }
            if (footTagBean.getName().equals("右脚内侧")) {
                medial_right = tagBean;
                detailmedial_right.add(detailListBean);
            }
            if (footTagBean.getName().equals("右脚脚面")) {
                face_right = tagBean;
                detailface_right.add(detailListBean);
            }
            if (footTagBean.getName().equals("右脚外侧")) {
                lateral_right = tagBean;
                detaillateral_right.add(detailListBean);
            }
        }


        if (medial_left != null) {
            medial_left.setDetailList(detailmedial_left);
            TaglList.add(medial_left);
        }
        if (face_left != null) {
            face_left.setDetailList(detailface_left);
            TaglList.add(face_left);
        }
        if (lateral_left != null) {
            lateral_left.setDetailList(detaillateral_left);
            TaglList.add(lateral_left);
        }
        if (medial_right != null) {
            medial_right.setDetailList(detailmedial_right);
            TaglList.add(medial_right);
        }
        if (face_right != null) {
            face_right.setDetailList(detailface_right);
            TaglList.add(face_right);
        }
        if (lateral_right != null) {
            lateral_right.setDetailList(detaillateral_right);
            TaglList.add(lateral_right);
        }

    }


    public void saveMyBitmap(Bitmap mBitmap, String bitName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = null;
            FileOutputStream out = null;

            filePath = AppConstant.IMAGE_LONG;
            File imgDir = new File(filePath);
            if (!imgDir.exists()) {
                imgDir.mkdirs();
            }
            String imgNames = filePath + "/" + bitName + ".png";
            try {
                out = new FileOutputStream(imgNames);
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ShowToast("已保存，请到扫描页点击右上角图片按钮查看");

        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && uploadid.equals("0")) {
            EventMsg eventMsg = new EventMsg();
            eventMsg.setCode(500);
            RxBus.getInstance().post(eventMsg);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (footThread != null) {
            leftANDrighht = false;
            footThread.currentThread().interrupt();
            footThread = null;
        }
    }

}
