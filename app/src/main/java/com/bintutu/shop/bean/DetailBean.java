package com.bintutu.shop.bean;

import java.text.DecimalFormat;

public class DetailBean {


    /**
     * name : BeJson
     * left : http://www.bejson.com
     * right : http://www.bejson.com
     */

    private String name;
    private int number;
    private String left;
    private String right;

    public DetailBean(String name,int number, double left, double right) {
        setName(name);
        setNumber(number);
        DecimalFormat decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String newLeft = decimalFormat.format(left);//format 返回的是字符串
        setLeft(String.valueOf(newLeft));
        String newRight = decimalFormat.format(right);//format 返回的是字符串
        setRight(String.valueOf(newRight));
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }
}
