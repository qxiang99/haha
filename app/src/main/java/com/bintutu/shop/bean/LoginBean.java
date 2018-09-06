package com.bintutu.shop.bean;

import java.io.Serializable;

public class LoginBean implements Serializable {


    /**
     * code : 0
     * result : {"customer_id":"44"}
     */

    private int code;
    private ResultBean result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * customer_id : 44
         */

        private String customer_id;

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }
    }
}
