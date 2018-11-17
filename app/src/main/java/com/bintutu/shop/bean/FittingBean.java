package com.bintutu.shop.bean;

public class FittingBean {


    private String name;
    private int type;


    public FittingBean(String name, int type) {
        setName(name);
        setType(type);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
