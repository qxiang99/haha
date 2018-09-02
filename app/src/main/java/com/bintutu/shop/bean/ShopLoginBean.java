package com.bintutu.shop.bean;

import java.io.Serializable;

public class ShopLoginBean implements Serializable {


    /**
     * result : {"id":"9853","phone":"13022887700","name":"菩智3","province":"天津","city":"天津市","town":"河西区","is_identified":"1","belong":null,"last_login":null,"update_time":"2018-07-26 22:46:18","create_time":"2018-06-18 22:09:52","is_delete":"0"}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 9853
         * phone : 13022887700
         * name : 菩智3
         * province : 天津
         * city : 天津市
         * town : 河西区
         * is_identified : 1
         * belong : null
         * last_login : null
         * update_time : 2018-07-26 22:46:18
         * create_time : 2018-06-18 22:09:52
         * is_delete : 0
         */

        private String id;
        private String phone;
        private String name;
        private String province;
        private String city;
        private String town;
        private String is_identified;
        private Object belong;
        private Object last_login;
        private String update_time;
        private String create_time;
        private String is_delete;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getIs_identified() {
            return is_identified;
        }

        public void setIs_identified(String is_identified) {
            this.is_identified = is_identified;
        }

        public Object getBelong() {
            return belong;
        }

        public void setBelong(Object belong) {
            this.belong = belong;
        }

        public Object getLast_login() {
            return last_login;
        }

        public void setLast_login(Object last_login) {
            this.last_login = last_login;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }
    }
}

