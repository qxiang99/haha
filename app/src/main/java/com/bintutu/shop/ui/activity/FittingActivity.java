package com.bintutu.shop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.BaseResponse;
import com.bintutu.shop.bean.FittingBean;
import com.bintutu.shop.bean.UploadBean;
import com.bintutu.shop.bean.UploadFittingBean;
import com.bintutu.shop.okgo.JsonCallback;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.adapter.FittingAdapter;
import com.bintutu.shop.ui.view.LabelPopWin;
import com.bintutu.shop.ui.view.LabelsView;
import com.bintutu.shop.ui.view.LoginDailog;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.ConfigManager;
import com.bintutu.shop.utils.Constant;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FittingActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;


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
    @BindView(R.id.detail_lin_left)
    LinearLayout detailLinLeft;
    @BindView(R.id.detail_lin_right)
    LinearLayout detailLinRight;
    @BindView(R.id.fitting_but_return)
    Button fittingButReturn;
    @BindView(R.id.fitting_but_home)
    Button fittingButHome;
    @BindView(R.id.fitting_but_upload)
    Button fittingButUpload;
    @BindView(R.id.fitting_edit_remark)
    EditText fittingEditRemark;
    @BindView(R.id.fitting_left_one_b)
    ImageView fittingLeftOneB;
    @BindView(R.id.fitting_left_one_d)
    ImageView fittingLeftOneD;
    @BindView(R.id.fitting_left_one_f)
    ImageView fittingLeftOneF;
    @BindView(R.id.fitting_left_one_g)
    ImageView fittingLeftOneG;
    @BindView(R.id.fitting_left_one_h)
    ImageView fittingLeftOneH;
    @BindView(R.id.fitting_left_two_a)
    ImageView fittingLeftTwoA;
    @BindView(R.id.fitting_left_three_e)
    ImageView fittingLeftThreeE;
    @BindView(R.id.fitting_left_three_c)
    ImageView fittingLeftThreeC;
    @BindView(R.id.fitting_right_one_r)
    ImageView fittingRightOneR;
    @BindView(R.id.fitting_right_one_q)
    ImageView fittingRightOneQ;
    @BindView(R.id.fitting_right_one_p)
    ImageView fittingRightOneP;
    @BindView(R.id.fitting_right_one_v)
    ImageView fittingRightOneV;
    @BindView(R.id.fitting_right_one_s)
    ImageView fittingRightOneS;
    @BindView(R.id.fitting_right_two_o)
    ImageView fittingRightTwoO;
    @BindView(R.id.fitting_right_three_k)
    ImageView fittingRightThreeK;
    @BindView(R.id.fitting_right_three_l)
    ImageView fittingRightThreeL;

    private ArrayList<String> fitList = new ArrayList<>();
    private List<FittingBean> leftList = new ArrayList<>();
    private List<FittingBean> rightList = new ArrayList<>();
    private FittingAdapter fittingAdapter;
    private float number = (float) 0;
    private String footlen = "";
    private String uploadid = "";
    private String shoesData = "";
    private Gson gson;
    private int goType;
    private LoginDailog loginDailog;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fitting);
    }

    @Override
    protected void init() {
        gson = new Gson();
        //
        getFootLenght();
        //
        //初始化LoginDailog
        loginDailog = new LoginDailog(this);
        //
        showRecyclerview();
        leftList.add(new FittingBean("A", "A",1));
        leftList.add(new FittingBean("B", "B",1));
        leftList.add(new FittingBean("C","C", 1));
        leftList.add(new FittingBean("D","D", 1));
        leftList.add(new FittingBean("E", "E",1));
        leftList.add(new FittingBean("F", "F",1));
        leftList.add(new FittingBean("G", "G",1));
        leftList.add(new FittingBean("H", "H",1));
        leftList.add(new FittingBean("左前掌", "left_sole",1));
        leftList.add(new FittingBean("左后掌", "left_heel",1));

        rightList.add(new FittingBean("K", "K",1));
        rightList.add(new FittingBean("L", "L",1));
        rightList.add(new FittingBean("O", "O",1));
        rightList.add(new FittingBean("P", "P",1));
        rightList.add(new FittingBean("Q", "Q",1));
        rightList.add(new FittingBean("R", "R",1));
        rightList.add(new FittingBean("S", "S",1));
        rightList.add(new FittingBean("V", "V",1));
        rightList.add(new FittingBean("右前掌", "right_sole",1));
        rightList.add(new FittingBean("右后掌", "right_heel",1));

        detailLinLeft.setEnabled(false);
        detailLinRight.setEnabled(true);
        detailButLeft.setEnabled(false);
        detailButRight.setEnabled(true);
    }

    @Override
    protected void setListener() {
        detailLinLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailLinLeft.setEnabled(false);
                detailLinRight.setEnabled(true);
                detailButLeft.setEnabled(false);
                detailButRight.setEnabled(true);
                fittingFremeLeftOne.setVisibility(View.VISIBLE);
                fittingFremeLeftTwo.setVisibility(View.VISIBLE);
                fittingFremeLeftThree.setVisibility(View.VISIBLE);
                fittingFremeRightOne.setVisibility(View.GONE);
                fittingFremeRightTwo.setVisibility(View.GONE);
                fittingFremeRightThree.setVisibility(View.GONE);
                fittingAdapter.setNewData(leftList);
            }
        });
        detailLinRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailLinLeft.setEnabled(true);
                detailLinRight.setEnabled(false);
                detailButLeft.setEnabled(true);
                detailButRight.setEnabled(false);
                fittingFremeLeftOne.setVisibility(View.GONE);
                fittingFremeLeftTwo.setVisibility(View.GONE);
                fittingFremeLeftThree.setVisibility(View.GONE);
                fittingFremeRightOne.setVisibility(View.VISIBLE);
                fittingFremeRightTwo.setVisibility(View.VISIBLE);
                fittingFremeRightThree.setVisibility(View.VISIBLE);
                fittingAdapter.setNewData(rightList);
            }
        });
        detailButLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailLinLeft.setEnabled(false);
                detailLinRight.setEnabled(true);
                detailButLeft.setEnabled(false);
                detailButRight.setEnabled(true);
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
                detailLinLeft.setEnabled(true);
                detailLinRight.setEnabled(false);
                detailButLeft.setEnabled(true);
                detailButRight.setEnabled(false);
                fittingFremeLeftOne.setVisibility(View.GONE);
                fittingFremeLeftTwo.setVisibility(View.GONE);
                fittingFremeLeftThree.setVisibility(View.GONE);
                fittingFremeRightOne.setVisibility(View.VISIBLE);
                fittingFremeRightTwo.setVisibility(View.VISIBLE);
                fittingFremeRightThree.setVisibility(View.VISIBLE);
                fittingAdapter.setNewData(rightList);
            }
        });
        //返回上一页
        fittingButReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goType == 1) {
                    startActivity(new Intent(FittingActivity.this, MainActivity.class));
                } else {
                    Intent intent = new Intent(FittingActivity.this, UploadSucessActivity.class);
                    intent.putExtra(Constant.ItentKey1, uploadid);
                    intent.putExtra(Constant.ItentKey2, footlen);
                    startActivity(intent);
                }
                finish();
            }
        });
        //返回首页
        fittingButHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FittingActivity.this, MainActivity.class));
                finish();
            }
        });
        fittingButUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* startActivity(new Intent(FittingActivity.this, FitTestActivity.class));
                finish();*/
                if (goType == 1) {
                    loginDailog.show();
                } else if (goType == 2) {
                    UploadData();
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
                NewFittingData(number, phone, customer_id);
            }
        });
        fittingAdapter.setSetClickListener(new FittingAdapter.OnSetClickListener() {
            @Override
            public void onSetData(FittingBean data, List<FittingBean> infos) {
                if (data.getName().equals("B") || data.getName().equals("D")|| data.getName().equals("F")|| data.getName().equals("G")|| data.getName().equals("H")) {
                    SetImageleftone(data);
                }
                if (data.getName().equals("A") ) {
                    SetImagelefttwo(data);
                }
                if (data.getName().equals("E") || data.getName().equals("C") ) {
                    SetImageleftthree(data);
                }

                if (data.getName().equals("R") || data.getName().equals("Q")|| data.getName().equals("P")|| data.getName().equals("V")|| data.getName().equals("S")) {
                    SetImagerightone(data);
                }
                if (data.getName().equals("O")) {
                    SetImagerighttwo(data);
                }
                if (data.getName().equals("K") || data.getName().equals("L")) {
                    SetImagerightthree(data);
                }

            }
        });


    }

    private void NewFittingData(final String name, String customer_phone, final String customer_id) {
        jumpLoading("上传数据中");
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (FittingBean fittingBean : leftList) {
            map.put(fittingBean.getTwoname(), fittingBean.getType());
        }
        for (FittingBean fittingBean : rightList) {
            map.put(fittingBean.getTwoname(), fittingBean.getType());
        }
        final String remark = fittingEditRemark.getText().toString().trim();
        String Data = gson.toJson(map);
        OkGo.<BaseResponse<String>>post(AppConstant.FITTING_NEWFITTING)
                .params("customer_id", customer_id)//客户的id
                .params("name", name)//数据的名称
                .params("customer_phone", customer_phone)//客户的手机
                .params("shop_id", ConfigManager.Device.getShopID())//商铺号
                .params("shoes", shoesData)//选择的fitting鞋
                .params("detail", Data)//fitting数据详情
                .params("remark", remark + "")//备注
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        closeLoading();
                        String data = String.valueOf(response.body());
                        UploadBean uploadBean = gson.fromJson(data, UploadBean.class);
                        if (uploadBean != null & uploadBean.getCode() == 0) {
                            ShowToast("上传成功");

                            Intent intent = new Intent(FittingActivity.this, FitTestActivity.class);
                            intent.putExtra(Constant.ItentKey1, 2);
                            intent.putExtra(Constant.ItentKey2, name);
                            if (uploadBean.getResult() != null && uploadBean.getResult().getId() != null) {
                                intent.putExtra(Constant.ItentKey3, uploadBean.getResult().getId());
                            } else {
                                intent.putExtra(Constant.ItentKey3, "0");
                            }

                            startActivity(intent);
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


    private void UploadData() {
        jumpLoading("上传数据中");
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (FittingBean fittingBean : leftList) {
            map.put(fittingBean.getName(), fittingBean.getType());
        }
        for (FittingBean fittingBean : rightList) {
            map.put(fittingBean.getName(), fittingBean.getType());
        }
        final String remark = fittingEditRemark.getText().toString().trim();
        String Data = gson.toJson(map);
        OkGo.<BaseResponse<String>>post(AppConstant.FITTING_MODIFYFITTING)
                .params("id", uploadid)//上传数据成功返回的
                .params("shoes", shoesData)//选择的fitting鞋
                .params("detail", Data)//
                .params("remark", remark + "")//备注
                .execute(new JsonCallback<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<String>> response) {
                        closeLoading();
                        String data = String.valueOf(response.body());
                        UploadFittingBean uploadBean = gson.fromJson(data, UploadFittingBean.class);
                        if (uploadBean != null & uploadBean.getCode() == 0) {
                            ShowToast("上传成功");
                            Intent intent = new Intent(FittingActivity.this, FitTestActivity.class);
                            intent.putExtra(Constant.ItentKey1, 1);
                            intent.putExtra(Constant.ItentKey2, uploadid);
                            startActivity(intent);
                            finish();
                        } else {
                            ShowToast(uploadBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<String>> response) {
                        ShowToast("上传失败");
                        closeLoading();
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
        if (data.getName().equals("B")) {
            if (data.getType() == 0) {
                fittingLeftOneB.setVisibility(View.VISIBLE);
                fittingLeftOneB.setBackgroundResource(R.mipmap.left_one_b_green);
            }
            if (data.getType() == 1) {
                fittingLeftOneB.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingLeftOneB.setVisibility(View.VISIBLE);
                fittingLeftOneB.setBackgroundResource(R.mipmap.left_one_b_red);
            }

        }
        if (data.getName().equals("D")) {
            if (data.getType() == 0) {
                fittingLeftOneD.setVisibility(View.VISIBLE);
                fittingLeftOneD.setBackgroundResource(R.mipmap.left_one_d_green);
            }
            if (data.getType() == 1) {
                fittingLeftOneD.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingLeftOneD.setVisibility(View.VISIBLE);
                fittingLeftOneD.setBackgroundResource(R.mipmap.left_one_d_red);
            }
        }
        if (data.getName().equals("F")) {
            if (data.getType() == 0) {
                fittingLeftOneF.setVisibility(View.VISIBLE);
                fittingLeftOneF.setBackgroundResource(R.mipmap.left_one_f_green);
            }
            if (data.getType() == 1) {
                fittingLeftOneF.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingLeftOneF.setVisibility(View.VISIBLE);
                fittingLeftOneF.setBackgroundResource(R.mipmap.left_one_f_red);
            }
        }
        if (data.getName().equals("G")) {
            if (data.getType() == 0) {
                fittingLeftOneG.setVisibility(View.VISIBLE);
                fittingLeftOneG.setBackgroundResource(R.mipmap.left_one_g_green);
            }
            if (data.getType() == 1) {
                fittingLeftOneG.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingLeftOneG.setVisibility(View.VISIBLE);
                fittingLeftOneG.setBackgroundResource(R.mipmap.left_one_g_red);
            }
        }
        if (data.getName().equals("H")) {
            if (data.getType() == 0) {
                fittingLeftOneH.setVisibility(View.VISIBLE);
                fittingLeftOneH.setBackgroundResource(R.mipmap.left_one_h_green);
            }
            if (data.getType() == 1) {
                fittingLeftOneH.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingLeftOneH.setVisibility(View.VISIBLE);
                fittingLeftOneH.setBackgroundResource(R.mipmap.left_one_h_red);
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

    }

    private void SetImageleftthree(FittingBean data) {
        if (data.getName().equals("E")) {
            if (data.getType() == 0) {
                fittingLeftThreeE.setVisibility(View.VISIBLE);
                fittingLeftThreeE.setBackgroundResource(R.mipmap.left_three_e_green);
            }
            if (data.getType() == 1) {
                fittingLeftThreeE.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingLeftThreeE.setVisibility(View.VISIBLE);
                fittingLeftThreeE.setBackgroundResource(R.mipmap.left_three_e_red);
            }
        }
        if (data.getName().equals("C")) {
            if (data.getType() == 0) {
                fittingLeftThreeC.setVisibility(View.VISIBLE);
                fittingLeftThreeC.setBackgroundResource(R.mipmap.left_three_c_green);
            }
            if (data.getType() == 1) {
                fittingLeftThreeC.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingLeftThreeC.setVisibility(View.VISIBLE);
                fittingLeftThreeC.setBackgroundResource(R.mipmap.left_three_c_red);
            }
        }

    }

    private void SetImagerightone(FittingBean data) {
        if (data.getName().equals("R")) {
            if (data.getType() == 0) {
                fittingRightOneR.setVisibility(View.VISIBLE);
                fittingRightOneR.setBackgroundResource(R.mipmap.right_one_r_green);
            }
            if (data.getType() == 1) {
                fittingRightOneR.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingRightOneR.setVisibility(View.VISIBLE);
                fittingRightOneR.setBackgroundResource(R.mipmap.right_one_r_red);
            }

        }
        if (data.getName().equals("Q")) {
            if (data.getType() == 0) {
                fittingRightOneQ.setVisibility(View.VISIBLE);
                fittingRightOneQ.setBackgroundResource(R.mipmap.right_one_q_green);
            }
            if (data.getType() == 1) {
                fittingRightOneQ.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingRightOneQ.setVisibility(View.VISIBLE);
                fittingRightOneQ.setBackgroundResource(R.mipmap.right_one_q_red);
            }
        }
        if (data.getName().equals("P")) {
            if (data.getType() == 0) {
                fittingRightOneP.setVisibility(View.VISIBLE);
                fittingRightOneP.setBackgroundResource(R.mipmap.right_one_p_green);
            }
            if (data.getType() == 1) {
                fittingRightOneP.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingRightOneP.setVisibility(View.VISIBLE);
                fittingRightOneP.setBackgroundResource(R.mipmap.right_one_p_red);
            }
        }
        if (data.getName().equals("V")) {
            if (data.getType() == 0) {
                fittingRightOneV.setVisibility(View.VISIBLE);
                fittingRightOneV.setBackgroundResource(R.mipmap.right_one_v_green);
            }
            if (data.getType() == 1) {
                fittingRightOneV.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingRightOneV.setVisibility(View.VISIBLE);
                fittingRightOneV.setBackgroundResource(R.mipmap.right_one_v_red);
            }
        }
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
    }

    private void SetImagerighttwo(FittingBean data) {
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
        if (data.getName().equals("K")) {
            if (data.getType() == 0) {
                fittingRightThreeK.setVisibility(View.VISIBLE);
                fittingRightThreeK.setBackgroundResource(R.mipmap.right_three_k_green);
            }
            if (data.getType() == 1) {
                fittingRightThreeK.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingRightThreeK.setVisibility(View.VISIBLE);
                fittingRightThreeK.setBackgroundResource(R.mipmap.right_three_k_red);
            }
        }
        if (data.getName().equals("L")) {
            if (data.getType() == 0) {
                fittingRightThreeL.setVisibility(View.VISIBLE);
                fittingRightThreeL.setBackgroundResource(R.mipmap.right_three_l_green);
            }
            if (data.getType() == 1) {
                fittingRightThreeL.setVisibility(View.GONE);
            }
            if (data.getType() == 2) {
                fittingRightThreeL.setVisibility(View.VISIBLE);
                fittingRightThreeL.setBackgroundResource(R.mipmap.right_three_l_red);
            }
        }

    }


    public void getFootLenght() {
        Intent intent = getIntent();
        goType = intent.getIntExtra(Constant.ItentKey7, 0);
        if (goType == 2) {
            uploadid = intent.getStringExtra(Constant.ItentKey1);
            footlen = intent.getStringExtra(Constant.ItentKey2);
            number = Float.valueOf(footlen);
        }
        //number = Float.valueOf(248f);
        if (number >= 228 && number < 233) {
            fitList.add("MO235M1");
        }
        if (number >= 233 && number < 238) {
            fitList.add("MO235M1");
            fitList.add("MO240S1");
        }
        if (number >= 238 && number < 243) {
            fitList.add("MO235M1");
            fitList.add("MO240S1");
            fitList.add("MO240M1");
            fitList.add("MO240L1");
            fitList.add("MO245S1");
        }
        if (number >= 243 && number < 248) {
            fitList.add("MO240L1");
            fitList.add("MO245S1");
            fitList.add("MO245M1");
            fitList.add("MO245L1");
            fitList.add("MO250S1");
        }
        if (number >= 248 && number < 253) {
            fitList.add("MO245L1");
            fitList.add("MO250S1");
            fitList.add("MO250M1");
            fitList.add("MO250L1");
            fitList.add("MO255S1");
        }
        if (number >= 253 && number < 258) {
            fitList.add("MO250L1");
            fitList.add("MO255S1");
            fitList.add("MO255M1");
            fitList.add("MO255L1");
            fitList.add("MO260S1");
        }
        if (number >= 258 && number < 263) {
            fitList.add("MO255L1");
            fitList.add("MO260S1");
            fitList.add("MO260M1");
            fitList.add("MO260L1");
            fitList.add("MO265S1");
        }
        if (number >= 263 && number < 268) {
            fitList.add("MO260M1");
            fitList.add("MO260L1");
            fitList.add("MO265S1");
            fitList.add("MO265M1");
            fitList.add("MO265L1");
        }
        if (number >= 268 && number < 273) {
            fitList.add("MO265L1");
            fitList.add("MO270S1");
            fitList.add("MO270M1");
            fitList.add("MO270L1");
            fitList.add("MO275M1");
        }
        if (number >= 273 && number < 278) {
            fitList.add("MO270L1");
            fitList.add("MO275M1");
        }

        fitList.add("自定义  ∨");
        if (fitList != null && fitList.size() > 0) {
            labelsView.setSelectType(LabelsView.SelectType.SINGLE_IRREVOCABLY);
            shoesData = fitList.get(0);
        }
        labelsView.setLabels(fitList, new LabelsView.LabelTextProvider<String>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, String data) {
                return data;
            }
        });
        final LabelPopWin editMoneyPopWin = new LabelPopWin(FittingActivity.this);
        labelsView.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(final TextView label, Object data, final int position) {
                shoesData = String.valueOf(data);
                if ("自定义  ∨".equals(shoesData)) {
                    editMoneyPopWin.showAsDropDown(label, 0, 0);
                    editMoneyPopWin.setOnMClickListner(new LabelPopWin.OnMClickListener() {
                        @Override
                        public void onmClick(String result) {
                            Log.e("onmClick", "..." + result);
                            fitList.add(position, result);
                            shoesData = String.valueOf(result);
                            label.setText(result + "  ∨");

                        }
                    });
                }

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
