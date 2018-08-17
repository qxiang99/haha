package com.bintutu.shop.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bintutu.shop.R;


public class ToastUtils {
    private static Toast toast;
    private Context context;

    public ToastUtils(Context context) {
        this.context = context;
    }

    public  void showToast(String message) {
        showToast(context,message);
    }

    /**
     * toast
     */
    public static void showToast(Context context, String message) {
        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.toast_item_layout, null);
        toast.setView(view);
        TextView textView = (TextView) view.findViewById(R.id.tv_toast);
        textView.setText(message);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
