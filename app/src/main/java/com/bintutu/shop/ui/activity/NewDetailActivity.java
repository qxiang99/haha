package com.bintutu.shop.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.bintutu.shop.bean.LeftBean;
import com.bintutu.shop.bean.RightBean;
import com.bintutu.shop.bean.TAGBean;
import com.bintutu.shop.bean.ToleranceBean;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.adapter.DetailAdapter;
import com.bintutu.shop.ui.view.ImageTwoDailog;
import com.bintutu.shop.ui.view.LoginDailog;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.Constant;
import com.bintutu.shop.utils.DebugLog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.BufferedReader;
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
    private LeftBean leftBean;
    private RightBean rightBean;
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
    private ToleranceBean toleranceBean;
    private ToleranceBean.DataBean dataBeans;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected void init() {
        gson = new Gson();
        //得到需要编辑的数据
        getTolerance();
        //获取上一界面发送给扫描仪的指令
        Intent intent = getIntent();
        number = intent.getStringExtra(Constant.ItentKey1);
        //初始化Recyclerview
        showRecyclerview();
        //初始化LoginDailog
        loginDailog = new LoginDailog(this);
        //请求左右脚数据
        jumpLoading("加载足型数据");
        //
        //getfoot();
        getData();
        //加载四张图片
        //LoadingImage();
        //设置采集数据时间
        DataTime();
        //设置六张足型图片
        SetSixFootImage();
        //
        //Upload();
        detailButLeft.setEnabled(false);
        detailButRight.setEnabled(true);


    }


    @Override
    protected void setListener() {

    }

    /**
     * 得到需要编辑的数据
     */
    private void getTolerance() {
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
        toleranceBean = gson.fromJson(buffer.toString(), ToleranceBean.class);
        dataBeans =  toleranceBean.getData().get(0);
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

                long one =  System.currentTimeMillis();
                long two;
                do {

                    if (!Responseleft){
                        OkGo.<BaseResponse<String>>get(AppConstant.LEFT_JSON(number))
                                .execute(new JsonCallback<BaseResponse<String>>() {
                                    @Override
                                    public void onSuccess(Response<BaseResponse<String>> response) {
                                        leftBean = gson.fromJson(String.valueOf(response.body()), LeftBean.class);
                                        Responseleft = true;
                                        if (Responseleft==true&&Responserighht==true){
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

                    if (!Responserighht){
                        OkGo.<BaseResponse<String>>get(AppConstant.RIGHT_JSON(number))
                                .execute(new JsonCallback<BaseResponse<String>>() {
                                    @Override
                                    public void onSuccess(Response<BaseResponse<String>> response) {
                                        rightBean = gson.fromJson(String.valueOf(response.body()), RightBean.class);
                                        Responserighht = true;
                                        if (Responseleft==true&&Responserighht==true){
                                            getFootData(2);
                                        }
                                    }
                                    @Override
                                    public void onError(Response<BaseResponse<String>> response) {
                                    }
                                });
                    }

                    two =  System.currentTimeMillis();

                } while ((!Responseleft||!Responserighht)&&((two-one)<20000)&&leftANDrighht);
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
                    if (mDetailTextTime!=null){
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
        String left = "{ \"10_FuWeiGao\" : 0.0, \"11_1ZhiZhiGao\" : 0.0, \"12_DaMoZhiGao\" : 0.0, \"13_JiaoWanGao\" : 0.0, \"14_JiaoZhiKuan\" : 0.0, \"15_ZhiWeiKuan\" : 75.0062, \"16_DiBanKuan\" : 75.0062, \"17_MuZhiLiKuan\" : 9.89464, \"18_XiaoZhiWaiKuan\" : 56.4657, \"19_1ZhizhiLiKuan\" : 17.5741, \"1_FootLen\" : 221.054, \"20_5ZhizhiLiKuan\" : 55.9658, \"21_YaoWoWaiKuan\" : 73.5143, \"22_ZhongXinDiKuan\" : 56.0287, \"23_JiaoHuaiNeiKuan\" : 0.0, \"24_MuZhiWaiTuChang\" : 198.949, \"25_XiaoZhiDuanChang\" : 182.37, \"26_XiaoZhiWaiTuChang\" : 172.422, \"27_1ZhiZhiChang\" : 160.264, \"28_5ZhiZhiChang\" : 140.369, \"29_FuGuChang\" : 121.58, \"2_ZhiWei\" : 0.0, \"30_YaoWoChang\" : 90.6323, \"31_ZhouShangDianChang\" : 85.1059, \"32_WaiHuaiGuZhongChang\" : 49.7372, \"33_ZhongXinChang\" : 39.7898, \"34_HouGenBianChang\" : 8.84217, \"35_QianZhangTuDianChang\" : 152.085, \"36_ZuGongDu\" : 0.0, \"37_NeiWaiFanDu\" : 0.0, \"3_FuWei\" : 0.0, \"4_DouWei\" : 0.0, \"5_JiaoWanWei\" : 0.0, \"6_JiaoZhiZhou\" : 0.0, \"7_WaiHuaiXiaGao\" : 0.0, \"8_HouGenTuGao\" : 0.0, \"9_ZhouShangGao\" : 0.0 }";
        String right = "{ \"10_FuWeiGao\" : 0.0, \"11_1ZhiZhiGao\" : 0.0, \"12_DaMoZhiGao\" : 0.0, \"13_JiaoWanGao\" : 0.0, \"14_JiaoZhiKuan\" : 0.0, \"15_ZhiWeiKuan\" : 45.1571, \"16_DiBanKuan\" : 45.1571, \"17_MuZhiLiKuan\" : 0.0, \"18_XiaoZhiWaiKuan\" : 0.0, \"19_1ZhizhiLiKuan\" : 0.0, \"1_FootLen\" : 212.314, \"20_5ZhizhiLiKuan\" : 0.0, \"21_YaoWoWaiKuan\" : 0.0, \"22_ZhongXinDiKuan\" : 0.0, \"23_JiaoHuaiNeiKuan\" : 0.0, \"24_MuZhiWaiTuChang\" : 0.0, \"25_XiaoZhiDuanChang\" : 0.0, \"26_XiaoZhiWaiTuChang\" : 0.0, \"27_1ZhiZhiChang\" : 0.0, \"28_5ZhiZhiChang\" : 0.0, \"29_FuGuChang\" : 0.0, \"2_ZhiWei\" : 0.0, \"30_YaoWoChang\" : 87.0485, \"31_ZhouShangDianChang\" : 81.7407, \"32_WaiHuaiGuZhongChang\" : 47.7705, \"33_ZhongXinChang\" : 0.0, \"34_HouGenBianChang\" : 8.49254, \"35_QianZhangTuDianChang\" : 146.072, \"36_ZuGongDu\" : 0.0, \"37_NeiWaiFanDu\" : 0.0, \"3_FuWei\" : 0.0, \"4_DouWei\" : 0.0, \"5_JiaoWanWei\" : 0.0, \"6_JiaoZhiZhou\" : 0.0, \"7_WaiHuaiXiaGao\" : 0.0, \"8_HouGenTuGao\" : 0.0, \"9_ZhouShangGao\" : 0.0 }";
        LeftBean leftBean = gson.fromJson(left, LeftBean.class);
        RightBean rightBean = gson.fromJson(right, RightBean.class);

        LeftBean NewleftBean =new LeftBean();
        NewleftBean.set_1_FootLen(getNewValue(dataBeans.get_1_FootLen(),dataBeans.get_1_FootLen_F(),leftBean.get_1_FootLen()));
        NewleftBean.set_2_ZhiWei(getNewValue(dataBeans.get_2_ZhiWei(),dataBeans.get_2_ZhiWei_F(),leftBean.get_2_ZhiWei()));
        NewleftBean.set_6_JiaoZhiZhou(getNewValue(dataBeans.get_6_JiaoZhiZhou(),dataBeans.get_6_JiaoZhiZhou_F(),leftBean.get_6_JiaoZhiZhou()));
        NewleftBean.set_16_DiBanKuan(getNewValue(dataBeans.get_16_DiBanKuan(),dataBeans.get_16_DiBanKuan_F(),leftBean.get_16_DiBanKuan()));
        NewleftBean.set_7_WaiHuaiXiaGao(getNewValue(dataBeans.get_7_WaiHuaiXiaGao(),dataBeans.get_7_WaiHuaiXiaGao_F(),leftBean.get_7_WaiHuaiXiaGao()));
        NewleftBean.set_11_1ZhiZhiGao(getNewValue(dataBeans.get_11_1ZhiZhiGao(),dataBeans.get_11_1ZhiZhiGao_F(),leftBean.get_11_1ZhiZhiGao()));
        NewleftBean.set_12_DaMoZhiGao(getNewValue(dataBeans.get_12_DaMoZhiGao(),dataBeans.get_12_DaMoZhiGao_F(),leftBean.get_12_DaMoZhiGao()));
        NewleftBean.set_17_MuZhiLiKuan(getNewValue(dataBeans.get_17_MuZhiLiKuan(),dataBeans.get_17_MuZhiLiKuan_F(),leftBean.get_17_MuZhiLiKuan()));
        NewleftBean.set_18_XiaoZhiWaiKuan(getNewValue(dataBeans.get_18_XiaoZhiWaiKuan(),dataBeans.get_18_XiaoZhiWaiKuan_F(),leftBean.get_18_XiaoZhiWaiKuan()));
        NewleftBean.set_19_1ZhizhiLiKuan(getNewValue(dataBeans.get_19_1ZhizhiLiKuan(),dataBeans.get_19_1ZhizhiLiKuan_F(),leftBean.get_19_1ZhizhiLiKuan()));
        NewleftBean.set_20_5ZhizhiLiKuan(getNewValue(dataBeans.get_20_5ZhizhiLiKuan(),dataBeans.get_20_5ZhizhiLiKuan_F(),leftBean.get_20_5ZhizhiLiKuan()));
        NewleftBean.set_21_YaoWoWaiKuan(getNewValue(dataBeans.get_21_YaoWoWaiKuan(),dataBeans.get_21_YaoWoWaiKuan_F(),leftBean.get_21_YaoWoWaiKuan()));
        NewleftBean.set_22_ZhongXinDiKuan(getNewValue(dataBeans.get_22_ZhongXinDiKuan(),dataBeans.get_22_ZhongXinDiKuan_F(),leftBean.get_22_ZhongXinDiKuan()));
        NewleftBean.set_24_MuZhiWaiTuChang(getNewValue(dataBeans.get_24_MuZhiWaiTuChang(),dataBeans.get_24_MuZhiWaiTuChang_F(),leftBean.get_24_MuZhiWaiTuChang()));
        NewleftBean.set_25_XiaoZhiDuanChang(getNewValue(dataBeans.get_25_XiaoZhiDuanChang(),dataBeans.get_25_XiaoZhiDuanChang_F(),leftBean.get_25_XiaoZhiDuanChang()));
        NewleftBean.set_26_XiaoZhiWaiTuChang(getNewValue(dataBeans.get_26_XiaoZhiWaiTuChang(),dataBeans.get_26_XiaoZhiWaiTuChang_F(),leftBean.get_26_XiaoZhiWaiTuChang()));
        NewleftBean.set_27_1ZhiZhiChang(getNewValue(dataBeans.get_27_1ZhiZhiChang(),dataBeans.get_27_1ZhiZhiChang_F(),leftBean.get_27_1ZhiZhiChang()));
        NewleftBean.set_28_5ZhiZhiChang(getNewValue(dataBeans.get_28_5ZhiZhiChang(),dataBeans.get_28_5ZhiZhiChang_F(),leftBean.get_28_5ZhiZhiChang()));
        NewleftBean.set_29_FuGuChang(getNewValue(dataBeans.get_29_FuGuChang(),dataBeans.get_29_FuGuChang_F(),leftBean.get_29_FuGuChang()));
        NewleftBean.set_30_YaoWoChang(getNewValue(dataBeans.get_30_YaoWoChang(),dataBeans.get_30_YaoWoChang_F(),leftBean.get_30_YaoWoChang()));
        NewleftBean.set_31_ZhouShangDianChang(getNewValue(dataBeans.get_31_ZhouShangDianChang(),dataBeans.get_31_ZhouShangDianChang_F(),leftBean.get_31_ZhouShangDianChang()));
        NewleftBean.set_32_WaiHuaiGuZhongChang(getNewValue(dataBeans.get_32_WaiHuaiGuZhongChang(),dataBeans.get_32_WaiHuaiGuZhongChang_F(),leftBean.get_32_WaiHuaiGuZhongChang()));
        NewleftBean.set_33_ZhongXinChang(getNewValue(dataBeans.get_33_ZhongXinChang(),dataBeans.get_33_ZhongXinChang_F(),leftBean.get_33_ZhongXinChang()));
        NewleftBean.set_34_HouGenBianChang(getNewValue(dataBeans.get_34_HouGenBianChang(),dataBeans.get_34_HouGenBianChang_F(),leftBean.get_34_HouGenBianChang()));
        NewleftBean.set_35_QianZhangTuDianChang(getNewValue(dataBeans.get_35_QianZhangTuDianChang(),dataBeans.get_35_QianZhangTuDianChang_F(),leftBean.get_35_QianZhangTuDianChang()));

        RightBean NewRightBean =new RightBean();
        NewRightBean.set_1_FootLen(getNewValue(dataBeans.get_1_FootLen(),dataBeans.get_1_FootLen_F(),rightBean.get_1_FootLen()));
        NewRightBean.set_2_ZhiWei(getNewValue(dataBeans.get_2_ZhiWei(),dataBeans.get_2_ZhiWei_F(),rightBean.get_2_ZhiWei()));
        NewRightBean.set_6_JiaoZhiZhou(getNewValue(dataBeans.get_6_JiaoZhiZhou(),dataBeans.get_6_JiaoZhiZhou_F(),rightBean.get_6_JiaoZhiZhou()));
        NewRightBean.set_16_DiBanKuan(getNewValue(dataBeans.get_16_DiBanKuan(),dataBeans.get_16_DiBanKuan_F(),rightBean.get_16_DiBanKuan()));
        NewRightBean.set_7_WaiHuaiXiaGao(getNewValue(dataBeans.get_7_WaiHuaiXiaGao(),dataBeans.get_7_WaiHuaiXiaGao_F(),rightBean.get_7_WaiHuaiXiaGao()));
        NewRightBean.set_11_1ZhiZhiGao(getNewValue(dataBeans.get_11_1ZhiZhiGao(),dataBeans.get_11_1ZhiZhiGao_F(),rightBean.get_11_1ZhiZhiGao()));
        NewRightBean.set_12_DaMoZhiGao(getNewValue(dataBeans.get_12_DaMoZhiGao(),dataBeans.get_12_DaMoZhiGao_F(),rightBean.get_12_DaMoZhiGao()));
        NewRightBean.set_17_MuZhiLiKuan(getNewValue(dataBeans.get_17_MuZhiLiKuan(),dataBeans.get_17_MuZhiLiKuan_F(),rightBean.get_17_MuZhiLiKuan()));
        NewRightBean.set_18_XiaoZhiWaiKuan(getNewValue(dataBeans.get_18_XiaoZhiWaiKuan(),dataBeans.get_18_XiaoZhiWaiKuan_F(),rightBean.get_18_XiaoZhiWaiKuan()));
        NewRightBean.set_19_1ZhizhiLiKuan(getNewValue(dataBeans.get_19_1ZhizhiLiKuan(),dataBeans.get_19_1ZhizhiLiKuan_F(),rightBean.get_19_1ZhizhiLiKuan()));
        NewRightBean.set_20_5ZhizhiLiKuan(getNewValue(dataBeans.get_20_5ZhizhiLiKuan(),dataBeans.get_20_5ZhizhiLiKuan_F(),rightBean.get_20_5ZhizhiLiKuan()));
        NewRightBean.set_21_YaoWoWaiKuan(getNewValue(dataBeans.get_21_YaoWoWaiKuan(),dataBeans.get_21_YaoWoWaiKuan_F(),rightBean.get_21_YaoWoWaiKuan()));
        NewRightBean.set_22_ZhongXinDiKuan(getNewValue(dataBeans.get_22_ZhongXinDiKuan(),dataBeans.get_22_ZhongXinDiKuan_F(),rightBean.get_22_ZhongXinDiKuan()));
        NewRightBean.set_24_MuZhiWaiTuChang(getNewValue(dataBeans.get_24_MuZhiWaiTuChang(),dataBeans.get_24_MuZhiWaiTuChang_F(),rightBean.get_24_MuZhiWaiTuChang()));
        NewRightBean.set_25_XiaoZhiDuanChang(getNewValue(dataBeans.get_25_XiaoZhiDuanChang(),dataBeans.get_25_XiaoZhiDuanChang_F(),rightBean.get_25_XiaoZhiDuanChang()));
        NewRightBean.set_26_XiaoZhiWaiTuChang(getNewValue(dataBeans.get_26_XiaoZhiWaiTuChang(),dataBeans.get_26_XiaoZhiWaiTuChang_F(),rightBean.get_26_XiaoZhiWaiTuChang()));
        NewRightBean.set_27_1ZhiZhiChang(getNewValue(dataBeans.get_27_1ZhiZhiChang(),dataBeans.get_27_1ZhiZhiChang_F(),rightBean.get_27_1ZhiZhiChang()));
        NewRightBean.set_28_5ZhiZhiChang(getNewValue(dataBeans.get_28_5ZhiZhiChang(),dataBeans.get_28_5ZhiZhiChang_F(),rightBean.get_28_5ZhiZhiChang()));
        NewRightBean.set_29_FuGuChang(getNewValue(dataBeans.get_29_FuGuChang(),dataBeans.get_29_FuGuChang_F(),rightBean.get_29_FuGuChang()));
        NewRightBean.set_30_YaoWoChang(getNewValue(dataBeans.get_30_YaoWoChang(),dataBeans.get_30_YaoWoChang_F(),rightBean.get_30_YaoWoChang()));
        NewRightBean.set_31_ZhouShangDianChang(getNewValue(dataBeans.get_31_ZhouShangDianChang(),dataBeans.get_31_ZhouShangDianChang_F(),rightBean.get_31_ZhouShangDianChang()));
        NewRightBean.set_32_WaiHuaiGuZhongChang(getNewValue(dataBeans.get_32_WaiHuaiGuZhongChang(),dataBeans.get_32_WaiHuaiGuZhongChang_F(),rightBean.get_32_WaiHuaiGuZhongChang()));
        NewRightBean.set_33_ZhongXinChang(getNewValue(dataBeans.get_33_ZhongXinChang(),dataBeans.get_33_ZhongXinChang_F(),rightBean.get_33_ZhongXinChang()));
        NewRightBean.set_34_HouGenBianChang(getNewValue(dataBeans.get_34_HouGenBianChang(),dataBeans.get_34_HouGenBianChang_F(),rightBean.get_34_HouGenBianChang()));
        NewRightBean.set_35_QianZhangTuDianChang(getNewValue(dataBeans.get_35_QianZhangTuDianChang(),dataBeans.get_35_QianZhangTuDianChang_F(),rightBean.get_35_QianZhangTuDianChang()));
        DetailList.clear();
        DetailList.add(new DetailBean(getResources().getString(R.string.FootLen), 1, leftBean.get_1_FootLen(), rightBean.get_1_FootLen()));//1
        DetailList.add(new DetailBean(getResources().getString(R.string.ZhiWei), 2, leftBean.get_2_ZhiWei(), rightBean.get_2_ZhiWei()));//2
        DetailList.add(new DetailBean(getResources().getString(R.string.FuWei), 3, leftBean.get_3_FuWei(), rightBean.get_3_FuWei()));//3
        DetailList.add(new DetailBean(getResources().getString(R.string.DouWei), 4, leftBean.get_6_JiaoZhiZhou(), rightBean.get_6_JiaoZhiZhou()));//6
        DetailList.add(new DetailBean(getResources().getString(R.string.JiaoWanWei), 5, leftBean.get_14_JiaoZhiKuan(), rightBean.get_14_JiaoZhiKuan()));//14
        DetailList.add(new DetailBean(getResources().getString(R.string.JiaoZhiZhou), 6, leftBean.get_15_ZhiWeiKuan(), rightBean.get_15_ZhiWeiKuan()));//15
        DetailList.add(new DetailBean(getResources().getString(R.string.WaiHuaiXiaGao), 7, leftBean.get_16_DiBanKuan(), rightBean.get_16_DiBanKuan()));//16
        DetailList.add(new DetailBean(getResources().getString(R.string.HouGenTuGao), 8, leftBean.get_7_WaiHuaiXiaGao(), rightBean.get_7_WaiHuaiXiaGao()));//7
        DetailList.add(new DetailBean(getResources().getString(R.string.ZhouShangGao), 9, leftBean.get_10_FuWeiGao(), rightBean.get_10_FuWeiGao()));//10
        DetailList.add(new DetailBean(getResources().getString(R.string.FuWeiGao), 10, leftBean.get_11_1ZhiZhiGao(), rightBean.get_11_1ZhiZhiGao()));//11
        DetailList.add(new DetailBean(getResources().getString(R.string.ZhiZhiGao), 11, leftBean.get_12_DaMoZhiGao(), rightBean.get_12_DaMoZhiGao()));//12
        DetailList.add(new DetailBean(getResources().getString(R.string.DaMoZhiGao), 12, leftBean.get_17_MuZhiLiKuan(), rightBean.get_17_MuZhiLiKuan()));//17
        DetailList.add(new DetailBean(getResources().getString(R.string.JiaoWanGao), 13, leftBean.get_18_XiaoZhiWaiKuan(), rightBean.get_18_XiaoZhiWaiKuan()));//18
        DetailList.add(new DetailBean(getResources().getString(R.string.JiaoZhiKuan), 14,  leftBean.get_19_1ZhizhiLiKuan(), rightBean.get_19_1ZhizhiLiKuan()));//19
        DetailList.add(new DetailBean(getResources().getString(R.string.ZhiWeiKuan), 15, leftBean.get_20_5ZhizhiLiKuan(), rightBean.get_20_5ZhizhiLiKuan()));//20
        DetailList.add(new DetailBean(getResources().getString(R.string.DiBanKuan), 16,  leftBean.get_21_YaoWoWaiKuan(), rightBean.get_21_YaoWoWaiKuan()));//21
        DetailList.add(new DetailBean(getResources().getString(R.string.MuZhiLiKuan), 17, leftBean.get_22_ZhongXinDiKuan(), rightBean.get_22_ZhongXinDiKuan()));//22
        DetailList.add(new DetailBean(getResources().getString(R.string.XiaoZhiWaiKuan), 18,  leftBean.get_24_MuZhiWaiTuChang(), rightBean.get_24_MuZhiWaiTuChang()));//24
        DetailList.add(new DetailBean(getResources().getString(R.string.lZhizhiLiKuan), 19,  leftBean.get_25_XiaoZhiDuanChang(), rightBean.get_25_XiaoZhiDuanChang()));//25
        DetailList.add(new DetailBean(getResources().getString(R.string.SZhizhiLiKuan), 20, leftBean.get_26_XiaoZhiWaiTuChang(), rightBean.get_26_XiaoZhiWaiTuChang()));//26
        DetailList.add(new DetailBean(getResources().getString(R.string.YaoWoWaiKuan), 21, leftBean.get_27_1ZhiZhiChang(), rightBean.get_27_1ZhiZhiChang()));//27
        DetailList.add(new DetailBean(getResources().getString(R.string.ZhongXinDiKuan), 22, leftBean.get_28_5ZhiZhiChang(), rightBean.get_28_5ZhiZhiChang()));//28
        DetailList.add(new DetailBean(getResources().getString(R.string.JiaoHuaiNeiKuan), 23, leftBean.get_29_FuGuChang(), rightBean.get_29_FuGuChang()));//29
        DetailList.add(new DetailBean(getResources().getString(R.string.MuZhiWaiTuChang), 24, leftBean.get_30_YaoWoChang(), rightBean.get_30_YaoWoChang()));//30
        DetailList.add(new DetailBean(getResources().getString(R.string.XiaoZhiDuanChang), 25, leftBean.get_31_ZhouShangDianChang(), rightBean.get_31_ZhouShangDianChang()));//31
        DetailList.add(new DetailBean(getResources().getString(R.string.XiaoZhiWaiTuChang), 26, leftBean.get_32_WaiHuaiGuZhongChang(), rightBean.get_32_WaiHuaiGuZhongChang()));//32
        DetailList.add(new DetailBean(getResources().getString(R.string.lZhiZhiChang), 27, leftBean.get_33_ZhongXinChang(), rightBean.get_33_ZhongXinChang()));//33
        DetailList.add(new DetailBean(getResources().getString(R.string.SZhiZhiChang), 28, leftBean.get_34_HouGenBianChang(), rightBean.get_34_HouGenBianChang()));//34
        DetailList.add(new DetailBean(getResources().getString(R.string.FuGuChang), 29, leftBean.get_35_QianZhangTuDianChang(), rightBean.get_35_QianZhangTuDianChang()));//35

        DetailAdapter detailAdapter = new DetailAdapter(DetailList);
        mRecyclerview.setAdapter(detailAdapter);
        footlen =  String.valueOf(leftBean.get_1_FootLen());
    }

    private double getNewValue(double footLen, double footLen_f, double footLen1) {
        if ((footLen+footLen_f)>footLen1||(footLen-footLen_f)<footLen1){
            return footLen1;
        }else {
            return footLen;
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

}
