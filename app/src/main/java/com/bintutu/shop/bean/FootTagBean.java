package com.bintutu.shop.bean;

import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

public class FootTagBean {

    private String foot;
    private String id;
    private String item;
    private String name;
    private List<TAGBean.DetailListBean> detailList;
    private View view;
    private RelativeLayout.LayoutParams layoutParams;

    public String getFoot() {
        return foot;
    }

    public void setFoot(String foot) {
        this.foot = foot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private String Content;
    private double PageX;
    private double PageY;
    private int X;
    private int Y;
    private int index;

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public double getPageX() {
        return PageX;
    }

    public void setPageX(double PageX) {
        this.PageX = PageX;
    }

    public double getPageY() {
        return PageY;
    }

    public void setPageY(double PageY) {
        this.PageY = PageY;
    }

    public int getX() {
        return X;
    }

    public void setX(int X) {
        this.X = X;
    }

    public int getY() {
        return Y;
    }

    public void setY(int Y) {
        this.Y = Y;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public RelativeLayout.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public void setLayoutParams(RelativeLayout.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }
}
