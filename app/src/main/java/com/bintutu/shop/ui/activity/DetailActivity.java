package com.bintutu.shop.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.DetailBean;
import com.bintutu.shop.bean.LeftBean;
import com.bintutu.shop.bean.RightBean;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.adapter.DetailAdapter;
import com.bintutu.shop.ui.adapter.ViewPagerAdapter;
import com.bintutu.shop.ui.view.LoginDailog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    /*@BindView(R.id.detail_lin_image)
    LinearLayout mLinImage;*/
    private List<DetailBean> DetailList = new ArrayList<>();
    private LoginDailog loginDailog;

    private int[] ImageRes = {
            R.mipmap.leftfoot_internal,
            R.mipmap.leftfoot_surface,
            R.mipmap.leftfoot_outside,
            R.mipmap.rightfoot_internal,
            R.mipmap.rightfoot_surface,
            R.mipmap.rightfoot_outside};

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected void init() {
        getData();
        showDailog();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        DetailAdapter detailAdapter = new DetailAdapter(DetailList);
        mRecyclerview.setAdapter(detailAdapter);






       /* findViewById(R.id.detail_text_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });*/

    }



    @Override
    protected void setListener() {

    }

    public void getData() {
        Gson gson = new Gson();

        String leftJson = "{ \"10_FuWeiGao\" : 0.0, \"11_1ZhiZhiGao\" : 0.0, \"12_DaMoZhiGao\" : 0.0, \"13_JiaoWanGao\" : 0.0, \"14_JiaoZhiKuan\" : 0.0, \"15_ZhiWeiKuan\" : 75.0062, \"16_DiBanKuan\" : 75.0062, \"17_MuZhiLiKuan\" : 9.89464, \"18_XiaoZhiWaiKuan\" : 56.4657, \"19_1ZhizhiLiKuan\" : 17.5741, \"1_FootLen\" : 221.054, \"20_5ZhizhiLiKuan\" : 55.9658, \"21_YaoWoWaiKuan\" : 73.5143, \"22_ZhongXinDiKuan\" : 56.0287, \"23_JiaoHuaiNeiKuan\" : 0.0, \"24_MuZhiWaiTuChang\" : 198.949, \"25_XiaoZhiDuanChang\" : 182.37, \"26_XiaoZhiWaiTuChang\" : 172.422, \"27_1ZhiZhiChang\" : 160.264, \"28_5ZhiZhiChang\" : 140.369, \"29_FuGuChang\" : 121.58, \"2_ZhiWei\" : 0.0, \"30_YaoWoChang\" : 90.6323, \"31_ZhouShangDianChang\" : 85.1059, \"32_WaiHuaiGuZhongChang\" : 49.7372, \"33_ZhongXinChang\" : 39.7898, \"34_HouGenBianChang\" : 8.84217, \"35_QianZhangTuDianChang\" : 152.085, \"36_ZuGongDu\" : 0.0, \"37_NeiWaiFanDu\" : 0.0, \"3_FuWei\" : 0.0, \"4_DouWei\" : 0.0, \"5_JiaoWanWei\" : 0.0, \"6_JiaoZhiZhou\" : 0.0, \"7_WaiHuaiXiaGao\" : 0.0, \"8_HouGenTuGao\" : 0.0, \"9_ZhouShangGao\" : 0.0 }";
        LeftBean leftBean = gson.fromJson(leftJson, LeftBean.class);

        String rightJson = "{ \"10_FuWeiGao\" : 0.0, \"11_1ZhiZhiGao\" : 0.0, \"12_DaMoZhiGao\" : 0.0, \"13_JiaoWanGao\" : 0.0, \"14_JiaoZhiKuan\" : 0.0, \"15_ZhiWeiKuan\" : 45.1571, \"16_DiBanKuan\" : 45.1571, \"17_MuZhiLiKuan\" : 0.0, \"18_XiaoZhiWaiKuan\" : 0.0, \"19_1ZhizhiLiKuan\" : 0.0, \"1_FootLen\" : 212.314, \"20_5ZhizhiLiKuan\" : 0.0, \"21_YaoWoWaiKuan\" : 0.0, \"22_ZhongXinDiKuan\" : 0.0, \"23_JiaoHuaiNeiKuan\" : 0.0, \"24_MuZhiWaiTuChang\" : 0.0, \"25_XiaoZhiDuanChang\" : 0.0, \"26_XiaoZhiWaiTuChang\" : 0.0, \"27_1ZhiZhiChang\" : 0.0, \"28_5ZhiZhiChang\" : 0.0, \"29_FuGuChang\" : 0.0, \"2_ZhiWei\" : 0.0, \"30_YaoWoChang\" : 87.0485, \"31_ZhouShangDianChang\" : 81.7407, \"32_WaiHuaiGuZhongChang\" : 47.7705, \"33_ZhongXinChang\" : 0.0, \"34_HouGenBianChang\" : 8.49254, \"35_QianZhangTuDianChang\" : 146.072, \"36_ZuGongDu\" : 0.0, \"37_NeiWaiFanDu\" : 0.0, \"3_FuWei\" : 0.0, \"4_DouWei\" : 0.0, \"5_JiaoWanWei\" : 0.0, \"6_JiaoZhiZhou\" : 0.0, \"7_WaiHuaiXiaGao\" : 0.0, \"8_HouGenTuGao\" : 0.0, \"9_ZhouShangGao\" : 0.0 }";
        RightBean rightBean = gson.fromJson(rightJson, RightBean.class);

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
    }


    private void showDailog() {
        loginDailog = new LoginDailog(this);
    }

}
