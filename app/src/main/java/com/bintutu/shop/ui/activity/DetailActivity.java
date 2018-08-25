package com.bintutu.shop.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.BaseResponse;
import com.bintutu.shop.bean.DetailBean;
import com.bintutu.shop.bean.LeftBean;
import com.bintutu.shop.bean.LoginBean;
import com.bintutu.shop.bean.RightBean;
import com.bintutu.shop.bean.TAGBean;
import com.bintutu.shop.okgo.DialogCallback;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.okgo.LzyResponse;
import com.bintutu.shop.okgo.ServerModel;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.adapter.DetailAdapter;
import com.bintutu.shop.ui.view.ImageDailog;
import com.bintutu.shop.ui.view.LoginDailog;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.ConfigManager;
import com.bintutu.shop.utils.Constant;
import com.bintutu.shop.utils.DebugLog;
import com.bintutu.shop.utils.GlideUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
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
    @BindView(R.id.detail_lin_image)
    LinearLayout mLinImage;
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
    @BindView(R.id.detail_but_right)
    Button detailButRight;
    @BindView(R.id.detail_but_upload)
    Button detailButUpload;
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
    private List<DetailBean> DetailList = new ArrayList<>();
    private List<TAGBean> TaglList = new ArrayList<>();
    private LoginDailog loginDailog;

    private int[] ImageRes = {
            R.mipmap.leftfoot_internal,
            R.mipmap.leftfoot_surface,
            R.mipmap.leftfoot_outside,
            R.mipmap.rightfoot_internal,
            R.mipmap.rightfoot_surface,
            R.mipmap.rightfoot_outside};
    private Gson gson;
    private LeftBean leftBean;
    private RightBean rightBean;
    private String number = "0";
    private String loginnumber;
    private String loginphone;
    private String logincustomer_id;
    private String customerid;
    private HashMap<View, Bitmap> map;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail);
    }


    @Override
    protected void init() {
        gson = new Gson();
        //设置Tag
        ConfigManager.Device.setTag(1);

        Intent intent = getIntent();
        number = intent.getStringExtra(Constant.ItentKey1);
        //getLeft();
        getData();
        //
        loginDailog = new LoginDailog(this);
        //初始化Recyclerview
        showRecyclerview();
        //加载四张图片
        LoadingImage();




        SetImage();

        detailButLeft.setEnabled(false);
        detailButRight.setEnabled(true);
        detaiScroll.scrollTo(200, 0);

    }




    @Override
    protected void setListener() {

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
                showSpaceImage("左脚内侧",detailImageLeft, map.get(detailImageLeft));
            }
        });
        detailImageCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpaceImage("左脚脚面", detailImageCenter, map.get(detailImageCenter));
            }
        });
        detailImageRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpaceImage("左脚外侧", detailImageRight, map.get(detailImageRight));
            }
        });
        detailImageLeftTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpaceImage("右脚内侧", detailImageLeftTwo, map.get(detailImageLeftTwo));
            }
        });
        detailImageCenterTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpaceImage("右脚脚面", detailImageCenterTwo, map.get(detailImageCenterTwo));
            }
        });
        detailImageRightTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpaceImage("右脚外侧", detailImageRightTwo, map.get(detailImageRightTwo));
            }
        });


        detailButUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginDailog.show();
            }
        });

        loginDailog.seLogintListener(new LoginDailog.OnLoginClickListener() {
            @Override
            public void Data(String number, String phone, String customer_id) {
                loginnumber = number;
                loginphone = phone;
                logincustomer_id = customer_id;
            }
        });

    }

    private void showSpaceImage(String name, ImageView view, Bitmap imageRe) {
        ImageDailog imageDailog = new ImageDailog(this);
        imageDailog.show();
        imageDailog.setImage(name,view, imageRe);
        imageDailog.setImageClickListener(new ImageDailog.OnImageClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSetData(ImageView view, Bitmap viewBitmap, TAGBean tagBean) {
                map.put(view, viewBitmap);
                view.setBackground(new BitmapDrawable(getResources(), viewBitmap));

                if(!TaglList.contains(tagBean)) {
                    TaglList.add(tagBean);
                }
                addTag();
            }
        });

    }

    private void showRecyclerview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
    }


    public void getLeft() {
        OkGo.<BaseResponse<String>>get(AppConstant.LEFT_JSON(number))
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        DebugLog.e("......" + response.body());
                        getRight();
                        String leftJson = String.valueOf(response.body());
                        leftBean = gson.fromJson(leftJson, LeftBean.class);

                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                    }
                });
    }

    private void getRight() {
        OkGo.<BaseResponse<String>>get(AppConstant.RIGHT_JSON(number))
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        DebugLog.e("......" + response.body());
                        String rightJson = String.valueOf(response.body());
                        rightBean = gson.fromJson(rightJson, RightBean.class);
                        getData();
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                    }
                });
    }

    public void getData() {

        String left = "{ \"10_FuWeiGao\" : 0.0, \"11_1ZhiZhiGao\" : 0.0, \"12_DaMoZhiGao\" : 0.0, \"13_JiaoWanGao\" : 0.0, \"14_JiaoZhiKuan\" : 0.0, \"15_ZhiWeiKuan\" : 75.0062, \"16_DiBanKuan\" : 75.0062, \"17_MuZhiLiKuan\" : 9.89464, \"18_XiaoZhiWaiKuan\" : 56.4657, \"19_1ZhizhiLiKuan\" : 17.5741, \"1_FootLen\" : 221.054, \"20_5ZhizhiLiKuan\" : 55.9658, \"21_YaoWoWaiKuan\" : 73.5143, \"22_ZhongXinDiKuan\" : 56.0287, \"23_JiaoHuaiNeiKuan\" : 0.0, \"24_MuZhiWaiTuChang\" : 198.949, \"25_XiaoZhiDuanChang\" : 182.37, \"26_XiaoZhiWaiTuChang\" : 172.422, \"27_1ZhiZhiChang\" : 160.264, \"28_5ZhiZhiChang\" : 140.369, \"29_FuGuChang\" : 121.58, \"2_ZhiWei\" : 0.0, \"30_YaoWoChang\" : 90.6323, \"31_ZhouShangDianChang\" : 85.1059, \"32_WaiHuaiGuZhongChang\" : 49.7372, \"33_ZhongXinChang\" : 39.7898, \"34_HouGenBianChang\" : 8.84217, \"35_QianZhangTuDianChang\" : 152.085, \"36_ZuGongDu\" : 0.0, \"37_NeiWaiFanDu\" : 0.0, \"3_FuWei\" : 0.0, \"4_DouWei\" : 0.0, \"5_JiaoWanWei\" : 0.0, \"6_JiaoZhiZhou\" : 0.0, \"7_WaiHuaiXiaGao\" : 0.0, \"8_HouGenTuGao\" : 0.0, \"9_ZhouShangGao\" : 0.0 }";
        String right = "{ \"10_FuWeiGao\" : 0.0, \"11_1ZhiZhiGao\" : 0.0, \"12_DaMoZhiGao\" : 0.0, \"13_JiaoWanGao\" : 0.0, \"14_JiaoZhiKuan\" : 0.0, \"15_ZhiWeiKuan\" : 45.1571, \"16_DiBanKuan\" : 45.1571, \"17_MuZhiLiKuan\" : 0.0, \"18_XiaoZhiWaiKuan\" : 0.0, \"19_1ZhizhiLiKuan\" : 0.0, \"1_FootLen\" : 212.314, \"20_5ZhizhiLiKuan\" : 0.0, \"21_YaoWoWaiKuan\" : 0.0, \"22_ZhongXinDiKuan\" : 0.0, \"23_JiaoHuaiNeiKuan\" : 0.0, \"24_MuZhiWaiTuChang\" : 0.0, \"25_XiaoZhiDuanChang\" : 0.0, \"26_XiaoZhiWaiTuChang\" : 0.0, \"27_1ZhiZhiChang\" : 0.0, \"28_5ZhiZhiChang\" : 0.0, \"29_FuGuChang\" : 0.0, \"2_ZhiWei\" : 0.0, \"30_YaoWoChang\" : 87.0485, \"31_ZhouShangDianChang\" : 81.7407, \"32_WaiHuaiGuZhongChang\" : 47.7705, \"33_ZhongXinChang\" : 0.0, \"34_HouGenBianChang\" : 8.49254, \"35_QianZhangTuDianChang\" : 146.072, \"36_ZuGongDu\" : 0.0, \"37_NeiWaiFanDu\" : 0.0, \"3_FuWei\" : 0.0, \"4_DouWei\" : 0.0, \"5_JiaoWanWei\" : 0.0, \"6_JiaoZhiZhou\" : 0.0, \"7_WaiHuaiXiaGao\" : 0.0, \"8_HouGenTuGao\" : 0.0, \"9_ZhouShangGao\" : 0.0 }";
        leftBean = gson.fromJson(left, LeftBean.class);
        rightBean = gson.fromJson(right, RightBean.class);

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

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("left", leftBean);
        map.put("right", rightBean);
        String detailData = gson.toJson(map);
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
                .params("remark", remark+"")//备注
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        String data = String.valueOf(response.body());
                        LoginBean loginBean = gson.fromJson(data, LoginBean.class);
                        if (loginBean != null & loginBean.getCode() == 0) {
                            Intent intent = new Intent(DetailActivity.this, UploadSucessActivity.class);
                            customerid = loginBean.getResult().getCustomer_id();
                            intent.putExtra(Constant.ItentKey1, loginBean.getResult().getCustomer_id());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                    }
                });

      /*  new Thread(new Runnable() {
            @Override
            public void run() {
                //上传zip
                UploadZip();
                //上传图片
                UploadImage();

            }
        }).start();*/

    }

    private void UploadZip() {

        //上传图片
        OkGo.<LzyResponse<ServerModel>>post(AppConstant.UPLOAD_ZIP)
                .headers("id", "headerValue1")//
               .params("","")
                .params("file",new File(""))
                .execute(new DialogCallback<LzyResponse<ServerModel>>(this) {
                    @Override
                    public void onSuccess(Response<LzyResponse<ServerModel>> response) {

                    }

                    @Override
                    public void onError(Response<LzyResponse<ServerModel>> response) {

                    }
                });
    }

    MediaType Image = MediaType.parse("image/png; charset=utf-8");

    private void UploadImage() {

    }


    public void addTag() {
        dataLinAddtag.setVisibility(View.VISIBLE);
        dataLinAddtag.removeAllViews();
        DebugLog.e("TAGBean", TaglList.size() + ".....1");
        for (int i = 0; i < TaglList.size(); i++) {
            TAGBean tagBean = TaglList.get(i);
            DebugLog.e("TAGBean", tagBean.getDetailList().size() + ".....2");
            for (int a = 0; a < tagBean.getDetailList().size(); a++) {
                TAGBean.DetailListBean detailListBean = TaglList.get(i).getDetailList().get(a);
                View view = LayoutInflater.from(DetailActivity.this).inflate(R.layout.detail_tag_item, null);
                TextView number = view.findViewById(R.id.tag_text_number);
                number.setText(detailListBean.getIndex()+"");
                TextView title = view.findViewById(R.id.tag_text_title);
                title.setText("[" + tagBean.getName() + "] : ");
                TextView content = view.findViewById(R.id.tag_text_content);
                content.setText(detailListBean.getContent());
                dataLinAddtag.addView(view);
            }
        }
    }


    private void SetImage() {
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

        /* GlideUtil.load(DetailActivity.this, AppConstant.IMAGE_ONE(number), detailImageFootleft);
        GlideUtil.load(DetailActivity.this, AppConstant.IAMGE_TWO(number), detailImageFootright);
        GlideUtil.load(DetailActivity.this, AppConstant.IMAGE_TREE(number), detailImagePlantarleft);
        GlideUtil.load(DetailActivity.this, AppConstant.IAMGE_FOUR(number), detailImagePlantarright);*/

        String URL ="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535237432187&di=f140fa616bff9996b541fcebe348ec99&imgtype=0&src=http%3A%2F%2Fwww.shishiwww.com%2Fuploads%2Fallimg%2F180825%2F2252495646-1.jpg";

        GlideUtil.load(DetailActivity.this, URL, detailImageFootleft,"one.jpg");

      /*  Glide.with(this).load(URL).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                detailImageFootleft.setBackground(new BitmapDrawable(getResources(), resource));
            }
        });*/
        Glide.with(this).load(AppConstant.IAMGE_TWO(number)).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                detailImageFootright.setBackground(new BitmapDrawable(getResources(), resource));
            }
        });
        Glide.with(this).load(AppConstant.IMAGE_TREE(number)).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                detailImagePlantarleft.setBackground(new BitmapDrawable(getResources(), resource));
            }
        });
        Glide.with(this).load(AppConstant.IAMGE_FOUR(number)).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                detailImagePlantarright.setBackground(new BitmapDrawable(getResources(), resource));
            }
        });
    }
}
