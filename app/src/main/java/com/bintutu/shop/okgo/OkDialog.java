package com.bintutu.shop.okgo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;

import com.lzy.okgo.request.base.Request;

public abstract class OkDialog<T> extends JsonCallback<T> {


    private ProgressDialog dialog;

    private void initDialog(Activity activity, String title) {
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
    }

    private void initDialog(Activity activity) {
        if (activity != null)
            initDialog(activity, "");
    }

    public OkDialog(Activity activity) {
        super();
        if (activity != null)
            initDialog(activity);
    }

    public OkDialog(Activity activity, String title) {
        super();
        if (activity != null)
            initDialog(activity, title);
    }


    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }


    @Override
    public void onFinish() {
        //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}

