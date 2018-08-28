package com.bintutu.shop.utils;


public class AppConstant {

    //true 正式 测试
    public static boolean Toogle = false;
    //测试环境
    public static String TEST_URL = "http://116.62.145.154:8080/";
    //正式环境
    public static String Formal_URL = "";

    public static String BASE_URLS = Toogle ? Formal_URL : TEST_URL;

    //发送验证码
    public static final String VARIFICATIONCODE = BASE_URLS + "shop_3d/backend/web/index.php/customer/varificationcode";
    //登录
    public static final String LOGIN = BASE_URLS + "shop_3d/backend/web/index.php/customer/login";
    //商铺登录
    public static final String SHOP_LOGIN = BASE_URLS + "shop_3d/backend/web/index.php/shop/shopid";
    //轮播图
    public static final String SOWING_MAP = BASE_URLS + "shop_3d/backend/web/index.php/homepage/pictures";
    //上传数据
    public static final String NEW_DATA = BASE_URLS + "shop_3d/backend/web/index.php/userfoottypedata/newdata";
    //上传ZIP
    public static final String UPLOAD_ZIP = BASE_URLS + "shop_3d/backend/web/index.php/userfoottypedata/uploadzip";
    //上传图片
    public static final String UPLOAD_IMAGE = BASE_URLS + "shop_3d/backend/web/index.php/userfoottypedata/uploadpic";


    //web首页
    public static final String WEBVIEW_HOME = "http://ipadscan_test.bintutu.com/shop_3dscanner_learning/frontend/shutdown.html";
    //web选鞋页
    public static final String WEBVIEW_SORT(String customer_id,String customer_phone){
        return "http://ipadscan_test.bintutu.com/shop_3dscanner_learning/frontend/sort.html?customer_id="+customer_id+"&customer_phone="+customer_phone;
    };
    //web选鞋页
    public static final String WEBVIEW_CHOOSE(String customer_id,String phone,String id,String color,String fur,String sole_materials,String sole_accessory,String exclusive){
        return "http://ipadscan_test.bintutu.com/shop_3dscanner_learning/frontend/information.html?customer_id="+customer_id+"&phone="+phone+"&id="+id+"&color="+color+"&fur="+fur+"&sole_materials="+sole_materials+"&sole_accessory="+sole_accessory+"&exclusive="+exclusive;
    }


    //设备地址
    public static final String EQUIPMENT_URL = "http://192.168.12.1/";
    //查看设备是否在线----get
    public static final String GET_ID = EQUIPMENT_URL + "getID";
    //启动设备------------post
    public static final String BEGIN_SCAN = EQUIPMENT_URL + "beginScan";
    //请求设备拉取数据-----post
    public static final String REQUEST_DATA = EQUIPMENT_URL + "requestData";

    //获取数据LEFT_JSON----get
    public static final String LEFT_JSON(String leftjson) {
        return EQUIPMENT_URL + leftjson + "/left.json";
    }

    //获取数据RIGHT_JSON----get
    public static final String RIGHT_JSON(String rightjson) {
        return EQUIPMENT_URL + rightjson + "/right.json";
    }

    //获取数据图片1---------get
    public static String IMAGE_ONE(String one) {
        return EQUIPMENT_URL + one + "/1-show.jpg";
    }

    //获取数据图片2---------get
    public static String IAMGE_TWO(String two) {
        return EQUIPMENT_URL + two + "/0-show.jpg";
    }

    //获取数据图片3---------get
    public static String IMAGE_TREE(String tree) {
        return EQUIPMENT_URL + tree + "/5_l-show.jpg";
    }

    //获取数据图片4---------get
    public static String IAMGE_FOUR(String four) {
        return EQUIPMENT_URL + four + "/5_r-show.jpg";
    }

    //获取数据ZIP-----------get
    public static String DATA_ZIP(String zip) {
        return EQUIPMENT_URL + zip + "/data.tgz";
    }

}
