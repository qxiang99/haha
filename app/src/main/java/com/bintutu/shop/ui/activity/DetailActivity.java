package com.bintutu.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.BaseResponse;
import com.bintutu.shop.bean.DetailBean;
import com.bintutu.shop.bean.LeftBean;
import com.bintutu.shop.bean.RightBean;
import com.bintutu.shop.okgo.DialogCallback;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.okgo.LzyResponse;
import com.bintutu.shop.okgo.ServerModel;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.adapter.DetailAdapter;
import com.bintutu.shop.ui.view.LoginDailog;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.ConfigManager;
import com.bintutu.shop.utils.Constant;
import com.bintutu.shop.utils.DebugLog;
import com.bintutu.shop.utils.GlideUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;

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
    private static final int SELECT_ONE = 1;
    private List<DetailBean> DetailList = new ArrayList<>();
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

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected void init() {
        gson = new Gson();
        getLeft();
        showDailog();
        showRecyclerview();
        GlideUtil.load(DetailActivity.this,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534919414212&di=fbedfe4b189d0ea45c4baf65ed766163&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201605%2F31%2F20160531224207_P5trE.jpeg",detailImageFootleft );
        GlideUtil.load(DetailActivity.this,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534919414212&di=fbedfe4b189d0ea45c4baf65ed766163&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201605%2F31%2F20160531224207_P5trE.jpeg",detailImageFootright );
        GlideUtil.load(DetailActivity.this,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534919414212&di=fbedfe4b189d0ea45c4baf65ed766163&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201605%2F31%2F20160531224207_P5trE.jpeg",detailImagePlantarleft );
        GlideUtil.load(DetailActivity.this,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534919414212&di=fbedfe4b189d0ea45c4baf65ed766163&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201605%2F31%2F20160531224207_P5trE.jpeg",detailImagePlantarright );

        detailButLeft.setEnabled(false);
        detailButRight.setEnabled(true);
        detaiScroll.scrollTo(200, 0);
    }

    @Override
    protected void setListener() {

        detailImageLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!detailButLeft.isEnabled()) {
                    showSpaceImage(detailImageLeft, ImageRes[0]);
                } else {
                    showSpaceImage(detailImageLeft, ImageRes[3]);
                }

            }
        });
        detailImageCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!detailButLeft.isEnabled()) {
                    showSpaceImage(detailImageCenter, ImageRes[1]);
                } else {
                    showSpaceImage(detailImageCenter, ImageRes[4]);
                }
            }
        });
        detailImageRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!detailButLeft.isEnabled()) {
                    showSpaceImage(detailImageRight, ImageRes[2]);
                } else {
                    showSpaceImage(detailImageRight, ImageRes[5]);
                }
            }
        });

        detailButLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailButLeft.setEnabled(false);
                detailButRight.setEnabled(true);
                detailImageLeft.setImageResource(R.mipmap.leftfoot_internal);
                detailImageCenter.setImageResource(R.mipmap.leftfoot_surface);
                detailImageRight.setImageResource(R.mipmap.leftfoot_outside);
            }
        });
        detailButRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailButLeft.setEnabled(true);
                detailButRight.setEnabled(false);
                detailImageLeft.setImageResource(R.mipmap.rightfoot_internal);
                detailImageCenter.setImageResource(R.mipmap.rightfoot_surface);
                detailImageRight.setImageResource(R.mipmap.rightfoot_outside);
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
                upload(number, phone, customer_id);
            }
        });
    }

    private void showDailog() {
        loginDailog = new LoginDailog(this);
    }

    private void showSpaceImage(ImageView view, int imageRe) {
        Intent intent = new Intent(DetailActivity.this, SpaceImageDetailActivity.class);
        intent.putExtra("images", imageRe);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        intent.putExtra("locationX", location[0]);
        intent.putExtra("locationY", location[1]);
        intent.putExtra("width", view.getWidth());
        intent.putExtra("height", view.getHeight());
        startActivityForResult(intent, SELECT_ONE);
        overridePendingTransition(0, 0);
    }

    private void showRecyclerview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
    }


    public void getLeft() {
        OkGo.<BaseResponse<String>>get("http://opzhpptsb.bkt.clouddn.com/left.json")
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
        OkGo.<BaseResponse<String>>get("http://opzhpptsb.bkt.clouddn.com/right.json")
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
        //上传数据
        OkGo.<BaseResponse<String>>post(AppConstant.NEW_DATA)
                .params("name", number)//自定的一个名字
                .params("customer_id", customer_id)//手机号验证码请求成功返回的id
                .params("customer_phone", phone)//手机号
                .params("shop_id", ConfigManager.Device.getShopID())//商铺号
                .params("device_id", ConfigManager.Device.getEquipmentID())//判断设备在不在线的返回的数据
                .params("foot_remark", "1")//数据界面上面需要标记的图片json
                .params("detail_data", detailData)//left.json+right.json
                .params("remark", "1")//备注
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        if (true){
                            Intent intent = new Intent(DetailActivity.this, UploadSucessActivity.class);
                            intent.putExtra(Constant.ItentKey1, "");
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                    }
                });

        new Thread(new Runnable() {
            @Override
            public void run() {
                //上传zip
                UploadZip();
                //上传图片
                UploadImage();

            }
        }).start();

    }

    private void UploadZip() {
        MediaType Image = MediaType.parse("image/png; charset=utf-8");
        //上传图片
        OkGo.<LzyResponse<ServerModel>>post(AppConstant.UPLOAD_ZIP)
                .headers("id", "headerValue1")//
                .upFile(new File(""),Image)//
                .execute(new DialogCallback<LzyResponse<ServerModel>>(this) {
                    @Override
                    public void onSuccess(Response<LzyResponse<ServerModel>> response) {

                    }

                    @Override
                    public void onError(Response<LzyResponse<ServerModel>> response) {

                    }
                });
    }

    private void UploadImage() {
        //上传图片
        OkGo.<LzyResponse<ServerModel>>post(AppConstant.UPLOAD_IMAGE)
                .headers("id", "headerValue1")//
                .upFile(new File(""))//
                .execute(new DialogCallback<LzyResponse<ServerModel>>(this) {
                    @Override
                    public void onSuccess(Response<LzyResponse<ServerModel>> response) {

                    }

                    @Override
                    public void onError(Response<LzyResponse<ServerModel>> response) {

                    }
                });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && null != data) {
         if (requestCode == SELECT_ONE) {


         }
        }
    }
}
