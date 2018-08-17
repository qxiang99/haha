package com.bintutu.shop.bean;

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

    public DetailBean(String name,int number, String left, String right) {
        setName(name);
        setNumber(number);
        setLeft(left);
        setRight(right);
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
