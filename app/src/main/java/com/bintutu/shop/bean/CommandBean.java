package com.bintutu.shop.bean;

public class CommandBean {


    /**
     * code : 1
     * msg : 用户名或密码错误！
     */


    private String msg;
    /**
     * code : 0
     * result : {"id":"8128967","phone":"13022887700","username":"wu超级哈哈","sex":"0","email":"13022887700@163.com"}
     */

    private int code;
    private ResultBean result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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
         * id : 8128967
         * phone : 13022887700
         * username : wu超级哈哈
         * sex : 0
         * email : 13022887700@163.com
         */

        private String id;
        private String phone;
        private String username;
        private String sex;
        private String email;

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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
