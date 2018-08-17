package com.bintutu.shop.bean;

public class DetailBean {


    /**
     * name : BeJson
     * left : http://www.bejson.com
     * right : http://www.bejson.com
     */

    private String name;
    private String left;
    private String right;

    public DetailBean(String name, String left, String right) {
        setName(name);
        setLeft(left);
        setRight(right);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
