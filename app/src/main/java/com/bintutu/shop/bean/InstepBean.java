package com.bintutu.shop.bean;

import java.util.List;

public class InstepBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : m34S
         * _3_FuWei : 215
         * _3_FuWei_F : 2
         * _10_FuWeiGao : 50.068
         * _10_FuWeiGao_F : 1
         */

        private String id;
        private double _3_FuWei;
        private double _3_FuWei_F;
        private double _10_FuWeiGao;
        private double _10_FuWeiGao_F;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double get_3_FuWei() {
            return _3_FuWei;
        }

        public void set_3_FuWei(double _3_FuWei) {
            this._3_FuWei = _3_FuWei;
        }

        public double get_3_FuWei_F() {
            return _3_FuWei_F;
        }

        public void set_3_FuWei_F(double _3_FuWei_F) {
            this._3_FuWei_F = _3_FuWei_F;
        }

        public double get_10_FuWeiGao() {
            return _10_FuWeiGao;
        }

        public void set_10_FuWeiGao(double _10_FuWeiGao) {
            this._10_FuWeiGao = _10_FuWeiGao;
        }

        public double get_10_FuWeiGao_F() {
            return _10_FuWeiGao_F;
        }

        public void set_10_FuWeiGao_F(double _10_FuWeiGao_F) {
            this._10_FuWeiGao_F = _10_FuWeiGao_F;
        }
    }
}
