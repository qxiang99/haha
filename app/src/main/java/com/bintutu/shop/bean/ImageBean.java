package com.bintutu.shop.bean;

import java.util.List;

public class ImageBean {


    /**
     * code : 0
     * message : http://www.bejson.com
     * data : {"links":[{"url":"http://opzhpptsb.bkt.clouddn.com/one.jpg"},{"url":"http://opzhpptsb.bkt.clouddn.com/two.jpg"},{"url":"http://opzhpptsb.bkt.clouddn.com/tree.jpg"},{"url":"http://opzhpptsb.bkt.clouddn.com/four.jpg"},{"url":"http://opzhpptsb.bkt.clouddn.com/five.jpg"},{"url":"http://opzhpptsb.bkt.clouddn.com/six.jpg"},{"url":"http://opzhpptsb.bkt.clouddn.com/nine.jpg"}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<LinksBean> links;

        public List<LinksBean> getLinks() {
            return links;
        }

        public void setLinks(List<LinksBean> links) {
            this.links = links;
        }

        public static class LinksBean {
            /**
             * url : http://opzhpptsb.bkt.clouddn.com/one.jpg
             */

            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
