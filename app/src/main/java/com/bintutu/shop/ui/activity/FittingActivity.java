package com.bintutu.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.FittingBean;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.adapter.FittingAdapter;
import com.bintutu.shop.ui.view.LabelsView;
import com.bintutu.shop.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FittingActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.fitting_left_one_i)
    ImageView fittingLeftOneI;
    @BindView(R.id.fitting_left_one_j)
    ImageView fittingLeftOneJ;
    @BindView(R.id.fitting_left_two_a)
    ImageView fittingLeftTwoA;
    @BindView(R.id.fitting_left_two_b)
    ImageView fittingLeftTwoB;
    @BindView(R.id.fitting_left_two_c)
    ImageView fittingLeftTwoC;
    @BindView(R.id.fitting_left_two_d)
    ImageView fittingLeftTwoD;
    @BindView(R.id.fitting_left_two_e)
    ImageView fittingLeftTwoE;
    @BindView(R.id.fitting_left_three_f)
    ImageView fittingLeftThreeF;
    @BindView(R.id.fitting_left_three_g)
    ImageView fittingLeftThreeG;
    @BindView(R.id.fitting_left_three_h)
    ImageView fittingLeftThreeH;
    @BindView(R.id.fitting_right_one_s)
    ImageView fittingRightOneS;
    @BindView(R.id.fitting_right_one_t)
    ImageView fittingRightOneT;
    @BindView(R.id.fitting_right_two_k)
    ImageView fittingRightTwoK;
    @BindView(R.id.fitting_right_two_m)
    ImageView fittingRightTwoM;
    @BindView(R.id.fitting_right_two_l)
    ImageView fittingRightTwoL;
    @BindView(R.id.fitting_right_two_n)
    ImageView fittingRightTwoN;
    @BindView(R.id.fitting_right_two_o)
    ImageView fittingRightTwoO;
    @BindView(R.id.fitting_right_three_r)
    ImageView fittingRightThreeR;
    @BindView(R.id.fitting_right_three_q)
    ImageView fittingRightThreeQ;
    @BindView(R.id.fitting_right_three_p)
    ImageView fittingRightThreeP;

    @BindView(R.id.detail_but_left)
    Button detailButLeft;
    @BindView(R.id.detail_but_right)
    Button detailButRight;
    @BindView(R.id.fitting_freme_left_one)
    FrameLayout fittingFremeLeftOne;
    @BindView(R.id.fitting_freme_left_two)
    FrameLayout fittingFremeLeftTwo;
    @BindView(R.id.fitting_freme_left_three)
    FrameLayout fittingFremeLeftThree;
    @BindView(R.id.fitting_freme_right_one)
    FrameLayout fittingFremeRightOne;
    @BindView(R.id.fitting_freme_right_two)
    FrameLayout fittingFremeRightTwo;
    @BindView(R.id.fitting_freme_right_three)
    FrameLayout fittingFremeRightThree;

    @BindView(R.id.labels)
    LabelsView labelsView;

    private ArrayList<String> fitList = new ArrayList<>();
    private List<FittingBean> leftList = new ArrayList<>();
    private List<FittingBean> rightList = new ArrayList<>();
    private FittingAdapter fittingAdapter;
    private float number;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fitting);
    }

    @Override
    protected void init() {
        //
        getFootLenght();
       //
        showRecyclerview();
        for (int i = 65; i < 85; i++) {
            if (i < 75) {
                leftList.add(new FittingBean(String.valueOf((char) i), 1));
            }
            if (i > 74) {
                rightList.add(new FittingBean(String.valueOf((char) i), 1));
            }
        }

    }

    @Override
    protected void setListener() {
        detailButLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fittingFremeLeftOne.setVisibility(View.VISIBLE);
                fittingFremeLeftTwo.setVisibility(View.VISIBLE);
                fittingFremeLeftThree.setVisibility(View.VISIBLE);
                fittingFremeRightOne.setVisibility(View.GONE);
                fittingFremeRightTwo.setVisibility(View.GONE);
                fittingFremeRightThree.setVisibility(View.GONE);
                fittingAdapter.setNewData(leftList);
            }
        });
        detailButRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fittingFremeLeftOne.setVisibility(View.GONE);
                fittingFremeLeftTwo.setVisibility(View.GONE);
                fittingFremeLeftThree.setVisibility(View.GONE);
                fittingFremeRightOne.setVisibility(View.VISIBLE);
                fittingFremeRightTwo.setVisibility(View.VISIBLE);
                fittingFremeRightThree.setVisibility(View.VISIBLE);
                fittingAdapter.setNewData(rightList);
            }
        });
        fittingAdapter.setSetClickListener(new FittingAdapter.OnSetClickListener() {
            @Override
            public void onSetData(FittingBean data, List<FittingBean> infos) {
                if (data.getName().equals("A") || data.getName().equals("B") || data.getName().equals("C") || data.getName().equals("D") || data.getName().equals("E")) {
                    SetImagelefttwo(data);
                }
                if (data.getName().equals("F") || data.getName().equals("G") || data.getName().equals("H")) {
                    SetImageleftthree(data);
                }
                if (data.getName().equals("I") || data.getName().equals("J")) {
                    SetImageleftone(data);
                }
                if (data.getName().equals("K") || data.getName().equals("L") || data.getName().equals("M") || data.getName().equals("N") || data.getName().equals("O")) {
                    SetImagerighttwo(data);
                }
                if (data.getName().equals("P") || data.getName().equals("Q") || data.getName().equals("R")) {
                    SetImagerightthree(data);
                }
                if (data.getName().equals("S") || data.getName().equals("T")) {
                    SetImagerightone(data);
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
        fittingAdapter = new FittingAdapter(leftList);
        mRecyclerview.setAdapter(fittingAdapter);
    }

    private void SetImageleftone(FittingBean data) {
        if (data.getName().equals("I")) {
            if (data.getType() == 0) {
                fittingLeftOneI.setVisibility(View.VISIBLE);
                fittingLeftOneI.setBackgroundResource(R.mipmap.left_one_i_green);
            }
            if (data.getType() == 1) {
                fittingLeftOneI.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingLeftOneI.setVisibility(View.VISIBLE);
                fittingLeftOneI.setBackgroundResource(R.mipmap.left_one_i_red);
            }

        }
        if (data.getName().equals("J")) {
            if (data.getType() == 0) {
                fittingLeftOneJ.setVisibility(View.VISIBLE);
                fittingLeftOneJ.setBackgroundResource(R.mipmap.left_one_j_green);
            }
            if (data.getType() == 1) {
                fittingLeftOneJ.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingLeftOneJ.setVisibility(View.VISIBLE);
                fittingLeftOneJ.setBackgroundResource(R.mipmap.left_one_j_red);
            }
        }
    }

    private void SetImagelefttwo(FittingBean data) {
        if (data.getName().equals("A")) {
            if (data.getType() == 0) {
                fittingLeftTwoA.setVisibility(View.VISIBLE);
                fittingLeftTwoA.setBackgroundResource(R.mipmap.left_two_a_green);
            }
            if (data.getType() == 1) {
                fittingLeftTwoA.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingLeftTwoA.setVisibility(View.VISIBLE);
                fittingLeftTwoA.setBackgroundResource(R.mipmap.left_two_a_red);
            }
        }
        if (data.getName().equals("B")) {
            if (data.getType() == 0) {
                fittingLeftTwoB.setVisibility(View.VISIBLE);
                fittingLeftTwoB.setBackgroundResource(R.mipmap.left_two_b_green);
            }
            if (data.getType() == 1) {
                fittingLeftTwoB.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingLeftTwoB.setVisibility(View.VISIBLE);
                fittingLeftTwoB.setBackgroundResource(R.mipmap.left_two_b_red);
            }
        }
        if (data.getName().equals("C")) {
            if (data.getType() == 0) {
                fittingLeftTwoC.setVisibility(View.VISIBLE);
                fittingLeftTwoC.setBackgroundResource(R.mipmap.left_two_c_green);
            }
            if (data.getType() == 1) {
                fittingLeftTwoC.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingLeftTwoC.setVisibility(View.VISIBLE);
                fittingLeftTwoC.setBackgroundResource(R.mipmap.left_two_c_red);
            }
        }
        if (data.getName().equals("D")) {
            if (data.getType() == 0) {
                fittingLeftTwoD.setVisibility(View.VISIBLE);
                fittingLeftTwoD.setBackgroundResource(R.mipmap.left_two_d_green);
            }
            if (data.getType() == 1) {
                fittingLeftTwoD.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingLeftTwoD.setVisibility(View.VISIBLE);
                fittingLeftTwoD.setBackgroundResource(R.mipmap.left_two_d_red);
            }
        }
        if (data.getName().equals("E")) {
            if (data.getType() == 0) {
                fittingLeftTwoE.setVisibility(View.VISIBLE);
                fittingLeftTwoE.setBackgroundResource(R.mipmap.left_two_e_green);
            }
            if (data.getType() == 1) {
                fittingLeftTwoE.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingLeftTwoE.setVisibility(View.VISIBLE);
                fittingLeftTwoE.setBackgroundResource(R.mipmap.left_two_e_red);
            }
        }
    }

    private void SetImageleftthree(FittingBean data) {
        if (data.getName().equals("F")) {
            if (data.getType() == 0) {
                fittingLeftThreeF.setVisibility(View.VISIBLE);
                fittingLeftThreeF.setBackgroundResource(R.mipmap.left_three_f_green);
            }
            if (data.getType() == 1) {
                fittingLeftThreeF.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingLeftThreeF.setVisibility(View.VISIBLE);
                fittingLeftThreeF.setBackgroundResource(R.mipmap.left_three_f_red);
            }
        }
        if (data.getName().equals("G")) {
            if (data.getType() == 0) {
                fittingLeftThreeG.setVisibility(View.VISIBLE);
                fittingLeftThreeG.setBackgroundResource(R.mipmap.left_three_g_green);
            }
            if (data.getType() == 1) {
                fittingLeftThreeG.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingLeftThreeG.setVisibility(View.VISIBLE);
                fittingLeftThreeG.setBackgroundResource(R.mipmap.left_three_g_red);
            }
        }
        if (data.getName().equals("H")) {
            if (data.getType() == 0) {
                fittingLeftThreeH.setVisibility(View.VISIBLE);
                fittingLeftThreeH.setBackgroundResource(R.mipmap.left_three_h_green);
            }
            if (data.getType() == 1) {
                fittingLeftThreeH.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingLeftThreeH.setVisibility(View.VISIBLE);
                fittingLeftThreeH.setBackgroundResource(R.mipmap.left_three_h_red);
            }
        }
    }

    private void SetImagerightone(FittingBean data) {
        if (data.getName().equals("S")) {
            if (data.getType() == 0) {
                fittingRightOneS.setVisibility(View.VISIBLE);
                fittingRightOneS.setBackgroundResource(R.mipmap.right_one_s_green);
            }
            if (data.getType() == 1) {
                fittingRightOneS.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingRightOneS.setVisibility(View.VISIBLE);
                fittingRightOneS.setBackgroundResource(R.mipmap.right_one_s_red);
            }

        }
        if (data.getName().equals("T")) {
            if (data.getType() == 0) {
                fittingRightOneT.setVisibility(View.VISIBLE);
                fittingRightOneT.setBackgroundResource(R.mipmap.right_one_t_green);
            }
            if (data.getType() == 1) {
                fittingRightOneT.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingRightOneT.setVisibility(View.VISIBLE);
                fittingRightOneT.setBackgroundResource(R.mipmap.right_one_t_red);
            }
        }
    }

    private void SetImagerighttwo(FittingBean data) {
        if (data.getName().equals("K")) {
            if (data.getType() == 0) {
                fittingRightTwoK.setVisibility(View.VISIBLE);
                fittingRightTwoK.setBackgroundResource(R.mipmap.right_two_k_green);
            }
            if (data.getType() == 1) {
                fittingRightTwoK.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingRightTwoK.setVisibility(View.VISIBLE);
                fittingRightTwoK.setBackgroundResource(R.mipmap.right_two_k_red);
            }
        }
        if (data.getName().equals("L")) {
            if (data.getType() == 0) {
                fittingRightTwoL.setVisibility(View.VISIBLE);
                fittingRightTwoL.setBackgroundResource(R.mipmap.right_two_l_green);
            }
            if (data.getType() == 1) {
                fittingRightTwoL.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingRightTwoL.setVisibility(View.VISIBLE);
                fittingRightTwoL.setBackgroundResource(R.mipmap.right_two_l_red);
            }
        }
        if (data.getName().equals("M")) {
            if (data.getType() == 0) {
                fittingRightTwoM.setVisibility(View.VISIBLE);
                fittingRightTwoM.setBackgroundResource(R.mipmap.right_two_m_green);
            }
            if (data.getType() == 1) {
                fittingRightTwoM.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingRightTwoM.setVisibility(View.VISIBLE);
                fittingRightTwoM.setBackgroundResource(R.mipmap.right_two_m_red);
            }
        }
        if (data.getName().equals("N")) {
            if (data.getType() == 0) {
                fittingRightTwoN.setVisibility(View.VISIBLE);
                fittingRightTwoN.setBackgroundResource(R.mipmap.right_two_n_green);
            }
            if (data.getType() == 1) {
                fittingRightTwoN.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingRightTwoN.setVisibility(View.VISIBLE);
                fittingRightTwoN.setBackgroundResource(R.mipmap.right_two_n_red);
            }
        }
        if (data.getName().equals("O")) {
            if (data.getType() == 0) {
                fittingRightTwoO.setVisibility(View.VISIBLE);
                fittingRightTwoO.setBackgroundResource(R.mipmap.right_two_o_green);
            }
            if (data.getType() == 1) {
                fittingRightTwoO.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingRightTwoO.setVisibility(View.VISIBLE);
                fittingRightTwoO.setBackgroundResource(R.mipmap.right_two_o_red);
            }
        }

    }

    private void SetImagerightthree(FittingBean data) {
        if (data.getName().equals("P")) {
            if (data.getType() == 0) {
                fittingRightThreeP.setVisibility(View.VISIBLE);
                fittingRightThreeP.setBackgroundResource(R.mipmap.right_three_p_green);
            }
            if (data.getType() == 1) {
                fittingRightThreeP.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingRightThreeP.setVisibility(View.VISIBLE);
                fittingRightThreeP.setBackgroundResource(R.mipmap.right_three_p_red);
            }
        }
        if (data.getName().equals("Q")) {
            if (data.getType() == 0) {
                fittingRightThreeQ.setVisibility(View.VISIBLE);
                fittingRightThreeQ.setBackgroundResource(R.mipmap.right_three_q_green);
            }
            if (data.getType() == 1) {
                fittingRightThreeQ.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingRightThreeQ.setVisibility(View.VISIBLE);
                fittingRightThreeQ.setBackgroundResource(R.mipmap.right_three_q_red);
            }
        }
        if (data.getName().equals("R")) {
            if (data.getType() == 0) {
                fittingRightThreeR.setVisibility(View.VISIBLE);
                fittingRightThreeR.setBackgroundResource(R.mipmap.right_three_r_green);
            }
            if (data.getType() == 1) {
                fittingRightThreeR.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingRightThreeR.setVisibility(View.VISIBLE);
                fittingRightThreeR.setBackgroundResource(R.mipmap.right_three_r_red);
            }
        }
    }


    public void getFootLenght() {
        Intent intent = getIntent();
       // String footlen = intent.getStringExtra(Constant.ItentKey2);
        // number = Float.valueOf(footlen);
         number = Float.valueOf("248.45789");
        if (number>=228&&number<233){
            fitList.add("MO235M1");
        }
        if (number>=233&&number<238){
            fitList.add("MO235M1");
            fitList.add("MO240S1");
        }
        if (number>=238&&number<243){
            fitList.add("MO235M1");
            fitList.add("MO240S1");
            fitList.add("MO240M1");
            fitList.add("MO240L1");
            fitList.add("MO245S1");
        }
        if (number>=243&&number<248){
            fitList.add("MO240L1");
            fitList.add("MO245S1");
            fitList.add("MO245M1");
            fitList.add("MO245L1");
            fitList.add("MO250S1");
        }
        if (number>=248&&number<253){
            fitList.add("MO245L1");
            fitList.add("MO250S1");
            fitList.add("MO250M1");
            fitList.add("MO250L1");
            fitList.add("MO255S1");
        }
        if (number>=253&&number<258){
            fitList.add("MO250L1");
            fitList.add("MO255S1");
            fitList.add("MO255M1");
            fitList.add("MO255L1");
            fitList.add("MO260S1");
        }
        if (number>=258&&number<263){
            fitList.add("MO255L1");
            fitList.add("MO260S1");
            fitList.add("MO260M1");
            fitList.add("MO260L1");
            fitList.add("MO265S1");
        }
        if (number>=263&&number<268){
            fitList.add("MO260M1");
            fitList.add("MO260L1");
            fitList.add("MO265S1");
            fitList.add("MO265M1");
            fitList.add("MO265L1");
        }
        if (number>=268&&number<273){
            fitList.add("MO265L1");
            fitList.add("MO270S1");
            fitList.add("MO270M1");
            fitList.add("MO270L1");
            fitList.add("MO275M1");
        }
        if (number>=273&&number<278){
            fitList.add("MO270L1");
            fitList.add("MO275M1");
        }
        labelsView.setLabels(fitList, new LabelsView.LabelTextProvider<String>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, String data) {
                return data;
            }
        });
    }
}
