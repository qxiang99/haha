package com.bintutu.shop.bean;

import java.util.List;

public class TAGBean {


    /**
     * detailList : [{"Content":"1","PageX":0.37272727272727274,"PageY":0.42,"X":205,"Y":231,"index":1},{"Content":"2","PageX":0.4290909090909091,"PageY":0.5581818181818182,"X":236,"Y":307,"index":2}]
     * foot : left
     * id : medial-left
     * item : medial
     * name : 左脚内侧
     */

    private String foot;
    private String id;
    private String item;
    private String name;
    private List<DetailListBean> detailList;

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

    public List<DetailListBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailListBean> detailList) {
        this.detailList = detailList;
    }

    public static class DetailListBean {
        /**
         * Content : 1
         * PageX : 0.37272727272727274
         * PageY : 0.42
         * X : 205
         * Y : 231
         * index : 1
         */

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
    }
}
