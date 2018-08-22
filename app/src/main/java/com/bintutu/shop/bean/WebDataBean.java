package com.bintutu.shop.bean;

import java.io.Serializable;

public class WebDataBean implements Serializable {


    /**
     * shop_id : BeJson
     * shop_phone : http://www.bejson.com
     */

    private String shop_id;
    private String shop_phone;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_phone() {
        return shop_phone;
    }

    public void setShop_phone(String shop_phone) {
        this.shop_phone = shop_phone;
    }
}
