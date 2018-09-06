package com.bintutu.shop.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import android.widget.HorizontalScrollView;
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
import com.bintutu.shop.bean.UploadBean;
import com.bintutu.shop.okgo.DialogCallback;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.okgo.LzyResponse;
import com.bintutu.shop.okgo.ServerModel;
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
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class DetailActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.detai_scroll)
    HorizontalScrollView detaiScroll;
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



    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail);
    }


    @Override
    protected void init() {
        gson = new Gson();
        //获取上一界面发送给扫描仪的指令
        Intent intent = getIntent();
        number = intent.getStringExtra(Constant.ItentKey1);
        //初始化Recyclerview
        showRecyclerview();
        //初始化LoginDailog
        loginDailog = new LoginDailog(this);
        //请求左右脚数据
        jumpLoading("加载足型数据");
        getLeft();
        //加载四张图片
        LoadingImage();
        //设置采集数据时间
        DataTime();
        //设置六张足型图片
        SetSixFootImage();
        //Upload();
        Upload();
        detailButLeft.setEnabled(false);
        detailButRight.setEnabled(true);
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
                ConfigManager.Foot.setchoosed_color_id("");
                ConfigManager.Foot.setchoosed_exclusive_id("");
                ConfigManager.Foot.setchoosed_sole_accessory_id("");
                ConfigManager.Foot.setchoosed_sole_material_id("");
                startActivity(new Intent(DetailActivity.this, MainActivity.class));
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
                    if (!DetailActivity.this.footList.contains(mFootList)) {
                        DetailActivity.this.footList.addAll(mFootList);
                    }
                }
                addTag();
            }
        });
    }

    /**
     * 左右脚请求数据有偶尔失败情况
     * 现增加重试机制
     */
  /*  private Handler handlerTimer = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                getLeft();
            }
            if (msg.what == 200) {
                getRight();
            }
        }
    };*/



    /**
     * 请求左脚数据
     */
    public void getLeft() {
        OkGo.<BaseResponse<String>>get(AppConstant.LEFT_JSON(number))
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        leftBean = gson.fromJson(String.valueOf(response.body()), LeftBean.class);
                        Responseleft = true;
                        if (Responserighht==false){
                            getRight();
                        }else if (Responseleft==true&&Responserighht==true){
                            getData();
                        }
                    }
                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                        if (Responserighht==false){
                            getRight();
                        }else {
                            ShowToast("数据拉取失败");
                            closeLoading();
                        }
                    }
                });
    }

    /**
     * 请求右脚数据
     */
    private void getRight() {
        OkGo.<BaseResponse<String>>get(AppConstant.RIGHT_JSON(number))
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        rightBean = gson.fromJson(String.valueOf(response.body()), RightBean.class);
                        Responserighht = true;
                        if (Responseleft==true){
                            getData();
                        }else {
                            getLeft();
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                        if (Responserighht==false&&Retryright<1){
                            ++Retryright;
                            getRight();
                        }else {
                            ShowToast("数据拉取失败");
                            closeLoading();
                        }
                    }
                });
    }

    public void getData() {
        closeLoading();
        DetailList.clear();
        DetailList.add(new DetailBean(getResources().getString(R.string.FootLen), 1, leftBean.get_1_FootLen(), rightBean.get_1_FootLen()));
        DetailList.add(new DetailBean(getResources().getString(R.string.ZhiWei), 2, leftBean.get_2_ZhiWei(), rightBean.get_2_ZhiWei()));
        DetailList.add(new DetailBean(getResources().getString(R.string.FuWei), 3, leftBean.get_3_FuWei(), rightBean.get_3_FuWei()));
        DetailList.add(new DetailBean(getResources().getString(R.string.DouWei), 4, leftBean.get_4_DouWei(), rightBean.get_4_DouWei()));
        DetailList.add(new DetailBean(getResources().getString(R.string.JiaoWanWei), 5, leftBean.get_5_JiaoWanWei(), rightBean.get_5_JiaoWanWei()));
        DetailList.add(new DetailBean(getResources().getString(R.string.JiaoZhiZhou), 6, leftBean.get_6_JiaoZhiZhou(), rightBean.get_6_JiaoZhiZhou()));
        DetailList.add(new DetailBean(getResources().getString(R.string.WaiHuaiXiaGao), 7, leftBean.get_7_WaiHuaiXiaGao(), rightBean.get_7_WaiHuaiXiaGao()));
        DetailList.add(new DetailBean(getResources().getString(R.string.HouGenTuGao), 8, leftBean.get_8_HouGenTuGao(), rightBean.get_8_HouGenTuGao()));
        DetailList.add(new DetailBean(getResources().getString(R.string.ZhouShangGao), 9, leftBean.get_9_ZhouShangGao(), rightBean.get_9_ZhouShangGao()));
        DetailList.add(new DetailBean(getResources().getString(R.string.FuWeiGao), 10, leftBean.get_10_FuWeiGao(), rightBean.get_10_FuWeiGao()));
        DetailList.add(new DetailBean(getResources().getString(R.string.ZhiZhiGao), 11, leftBean.get_11_1ZhiZhiGao(), rightBean.get_11_1ZhiZhiGao()));
        DetailList.add(new DetailBean(getResources().getString(R.string.DaMoZhiGao), 12, leftBean.get_12_DaMoZhiGao(), rightBean.get_12_DaMoZhiGao()));
        DetailList.add(new DetailBean(getResources().getString(R.string.JiaoWanGao), 13, leftBean.get_13_JiaoWanGao(), rightBean.get_13_JiaoWanGao()));
        DetailList.add(new DetailBean(getResources().getString(R.string.JiaoZhiKuan), 14, leftBean.get_14_JiaoZhiKuan(), rightBean.get_14_JiaoZhiKuan()));
        DetailList.add(new DetailBean(getResources().getString(R.string.ZhiWeiKuan), 15, leftBean.get_15_ZhiWeiKuan(), rightBean.get_15_ZhiWeiKuan()));
        DetailList.add(new DetailBean(getResources().getString(R.string.DiBanKuan), 16, leftBean.get_16_DiBanKuan(), rightBean.get_16_DiBanKuan()));
        DetailList.add(new DetailBean(getResources().getString(R.string.MuZhiLiKuan), 17, leftBean.get_17_MuZhiLiKuan(), rightBean.get_17_MuZhiLiKuan()));
        DetailList.add(new DetailBean(getResources().getString(R.string.XiaoZhiWaiKuan), 18, leftBean.get_18_XiaoZhiWaiKuan(), rightBean.get_18_XiaoZhiWaiKuan()));
        DetailList.add(new DetailBean(getResources().getString(R.string.lZhizhiLiKuan), 19, leftBean.get_19_1ZhizhiLiKuan(), rightBean.get_19_1ZhizhiLiKuan()));
        DetailList.add(new DetailBean(getResources().getString(R.string.SZhizhiLiKuan), 20, leftBean.get_20_5ZhizhiLiKuan(), rightBean.get_20_5ZhizhiLiKuan()));
        DetailList.add(new DetailBean(getResources().getString(R.string.YaoWoWaiKuan), 21, leftBean.get_21_YaoWoWaiKuan(), rightBean.get_21_YaoWoWaiKuan()));
        DetailList.add(new DetailBean(getResources().getString(R.string.ZhongXinDiKuan), 22, leftBean.get_22_ZhongXinDiKuan(), rightBean.get_22_ZhongXinDiKuan()));
        DetailList.add(new DetailBean(getResources().getString(R.string.JiaoHuaiNeiKuan), 23, leftBean.get_23_JiaoHuaiNeiKuan(), rightBean.get_23_JiaoHuaiNeiKuan()));
        DetailList.add(new DetailBean(getResources().getString(R.string.MuZhiWaiTuChang), 24, leftBean.get_24_MuZhiWaiTuChang(), rightBean.get_24_MuZhiWaiTuChang()));
        DetailList.add(new DetailBean(getResources().getString(R.string.XiaoZhiDuanChang), 25, leftBean.get_25_XiaoZhiDuanChang(), rightBean.get_25_XiaoZhiDuanChang()));
        DetailList.add(new DetailBean(getResources().getString(R.string.XiaoZhiWaiTuChang), 26, leftBean.get_26_XiaoZhiWaiTuChang(), rightBean.get_26_XiaoZhiWaiTuChang()));
        DetailList.add(new DetailBean(getResources().getString(R.string.lZhiZhiChang), 27, leftBean.get_27_1ZhiZhiChang(), rightBean.get_27_1ZhiZhiChang()));
        DetailList.add(new DetailBean(getResources().getString(R.string.SZhiZhiChang), 28, leftBean.get_28_5ZhiZhiChang(), rightBean.get_28_5ZhiZhiChang()));
        DetailList.add(new DetailBean(getResources().getString(R.string.FuGuChang), 29, leftBean.get_29_FuGuChang(), rightBean.get_29_FuGuChang()));

        DetailAdapter detailAdapter = new DetailAdapter(DetailList);
        mRecyclerview.setAdapter(detailAdapter);
    }


    private void upload(String number, String phone, String customer_id) {
        jumpLoading("上传数据中");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("left", leftBean);
        map.put("right", rightBean);
        String detailData = gson.toJson(map);
        sortListTag(footList);
        String foot_remark = gson.toJson(TaglList);
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

                            Intent intent = new Intent(DetailActivity.this, UploadSucessActivity.class);
                            intent.putExtra(Constant.ItentKey1, uploadid);
                            startActivity(intent);
                            finish();
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


    public void addTag() {
        dataLinAddtag.setVisibility(View.VISIBLE);
        dataLinAddtag.removeAllViews();
        if (footList != null && footList.size() > 0) {
            for (int i = 0; i < footList.size(); i++) {
                View view = LayoutInflater.from(DetailActivity.this).inflate(R.layout.detail_tag_item, null);
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

        GlideUtil.load(DetailActivity.this, AppConstant.IMAGE_ONE(number), detailImageFootleft);
        GlideUtil.load(DetailActivity.this, AppConstant.IAMGE_TWO(number), detailImageFootright);
        GlideUtil.load(DetailActivity.this, AppConstant.IMAGE_TREE(number), detailImagePlantarleft);
        GlideUtil.load(DetailActivity.this, AppConstant.IAMGE_FOUR(number), detailImagePlantarright);


        GlideUtil.load(DetailActivity.this, AppConstant.IMAGE_ONE(number), detailImageFootleft, "1-show.jpg");
        GlideUtil.load(DetailActivity.this, AppConstant.IAMGE_TWO(number), detailImageFootright, "0-show.jpg");
        GlideUtil.load(DetailActivity.this, AppConstant.IMAGE_TREE(number), detailImagePlantarleft, "5_l-show.jpg");
        GlideUtil.load(DetailActivity.this, AppConstant.IAMGE_FOUR(number), detailImagePlantarright, "5_r-show.jpg");

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
            detailListBean.setPageY(footTagBean.getY());
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





    public  void saveMyBitmap(Bitmap mBitmap, String bitName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = null;
            FileOutputStream out = null;

            filePath =  AppConstant.IMAGE_LONG;
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

             DetailActivity.this.ShowToast("已保存，请到扫描页点击右上角图片按钮查看");

        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0&&uploadid.equals("0")) {
            EventMsg eventMsg = new EventMsg();
            eventMsg.setCode(500);
            RxBus.getInstance().post(eventMsg);
        }
        return super.onKeyDown(keyCode, event);
    }
}
