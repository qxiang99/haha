package com.bintutu.shop.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.LeftBean;
import com.bintutu.shop.bean.RightBean;
import com.google.gson.Gson;

public class DetailActivity  extends BaseActivity{


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected void init() {
        getData();



        findViewById(R.id.detail_image_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });




    }

    @Override
    protected void setListener() {

    }

    public void getData() {
        Gson gson = new Gson();

        String leftJson = "{ \"10_FuWeiGao\" : 0.0, \"11_1ZhiZhiGao\" : 0.0, \"12_DaMoZhiGao\" : 0.0, \"13_JiaoWanGao\" : 0.0, \"14_JiaoZhiKuan\" : 0.0, \"15_ZhiWeiKuan\" : 75.0062, \"16_DiBanKuan\" : 75.0062, \"17_MuZhiLiKuan\" : 9.89464, \"18_XiaoZhiWaiKuan\" : 56.4657, \"19_1ZhizhiLiKuan\" : 17.5741, \"1_FootLen\" : 221.054, \"20_5ZhizhiLiKuan\" : 55.9658, \"21_YaoWoWaiKuan\" : 73.5143, \"22_ZhongXinDiKuan\" : 56.0287, \"23_JiaoHuaiNeiKuan\" : 0.0, \"24_MuZhiWaiTuChang\" : 198.949, \"25_XiaoZhiDuanChang\" : 182.37, \"26_XiaoZhiWaiTuChang\" : 172.422, \"27_1ZhiZhiChang\" : 160.264, \"28_5ZhiZhiChang\" : 140.369, \"29_FuGuChang\" : 121.58, \"2_ZhiWei\" : 0.0, \"30_YaoWoChang\" : 90.6323, \"31_ZhouShangDianChang\" : 85.1059, \"32_WaiHuaiGuZhongChang\" : 49.7372, \"33_ZhongXinChang\" : 39.7898, \"34_HouGenBianChang\" : 8.84217, \"35_QianZhangTuDianChang\" : 152.085, \"36_ZuGongDu\" : 0.0, \"37_NeiWaiFanDu\" : 0.0, \"3_FuWei\" : 0.0, \"4_DouWei\" : 0.0, \"5_JiaoWanWei\" : 0.0, \"6_JiaoZhiZhou\" : 0.0, \"7_WaiHuaiXiaGao\" : 0.0, \"8_HouGenTuGao\" : 0.0, \"9_ZhouShangGao\" : 0.0 }";

        LeftBean leftBean = gson.fromJson(leftJson, LeftBean.class);//将json数据转换成user类实体
        Log.e("resJson", leftBean.get_15_ZhiWeiKuan() + "......1");

        String rightJson = "{ \"10_FuWeiGao\" : 0.0, \"11_1ZhiZhiGao\" : 0.0, \"12_DaMoZhiGao\" : 0.0, \"13_JiaoWanGao\" : 0.0, \"14_JiaoZhiKuan\" : 0.0, \"15_ZhiWeiKuan\" : 45.1571, \"16_DiBanKuan\" : 45.1571, \"17_MuZhiLiKuan\" : 0.0, \"18_XiaoZhiWaiKuan\" : 0.0, \"19_1ZhizhiLiKuan\" : 0.0, \"1_FootLen\" : 212.314, \"20_5ZhizhiLiKuan\" : 0.0, \"21_YaoWoWaiKuan\" : 0.0, \"22_ZhongXinDiKuan\" : 0.0, \"23_JiaoHuaiNeiKuan\" : 0.0, \"24_MuZhiWaiTuChang\" : 0.0, \"25_XiaoZhiDuanChang\" : 0.0, \"26_XiaoZhiWaiTuChang\" : 0.0, \"27_1ZhiZhiChang\" : 0.0, \"28_5ZhiZhiChang\" : 0.0, \"29_FuGuChang\" : 0.0, \"2_ZhiWei\" : 0.0, \"30_YaoWoChang\" : 87.0485, \"31_ZhouShangDianChang\" : 81.7407, \"32_WaiHuaiGuZhongChang\" : 47.7705, \"33_ZhongXinChang\" : 0.0, \"34_HouGenBianChang\" : 8.49254, \"35_QianZhangTuDianChang\" : 146.072, \"36_ZuGongDu\" : 0.0, \"37_NeiWaiFanDu\" : 0.0, \"3_FuWei\" : 0.0, \"4_DouWei\" : 0.0, \"5_JiaoWanWei\" : 0.0, \"6_JiaoZhiZhou\" : 0.0, \"7_WaiHuaiXiaGao\" : 0.0, \"8_HouGenTuGao\" : 0.0, \"9_ZhouShangGao\" : 0.0 }";
        RightBean rightBean = gson.fromJson(rightJson, RightBean.class);//将json数据转换成user类实体
        Log.e("resJson", rightBean.get_15_ZhiWeiKuan() + "......2");
    }
}
