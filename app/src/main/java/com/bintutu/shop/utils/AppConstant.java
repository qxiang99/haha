package com.bintutu.shop.utils;


public class AppConstant {

    //true 正式 测试
    public static boolean Toogle = false;
    //测试环境
    public static String TEST_URL = "http://116.62.145.154:8080/";
    //正式环境
    public static String Formal_URL = "https://api-wuweidaojia.abhpd.com/api/";

    public static String BASE_URLS = Toogle ? Formal_URL : TEST_URL;

    //发送验证码
    public static final String VARIFICATIONCODE = BASE_URLS + "shop_3d/backend/web/index.php/customer/varificationcode";
    //登录
    public static final String LOGIN = BASE_URLS + "shop_3d/backend/web/index.php/customer/login";
    //上传数据
    public static final String NEW_DATA = BASE_URLS + "shop_3d/backend/web/index.php/userfoottypedata/newdata";
    //上传ZIP
    public static final String UPLOAD_ZIP = BASE_URLS + "shop_3d/backend/web/index.php/userfoottypedata/uploadzip";
    //上传图片
    public static final String UPLOAD_IMAGE = BASE_URLS + "shop_3d/backend/web/index.php/userfoottypedata/uploadpic";




    //web网址
    public static final String WEBVIEW_URL = "http://ipadscan_test.bintutu.com/shop_3dscanner_learning/frontend/shutdown.html";
    //设备地址
    public static final String EQUIPMENT_URL = "http://192.168.12.1/";
    //查看设备是否在线----get
    public static final String GET_ID = EQUIPMENT_URL + "getID";
    //启动设备------------post
    public static final String BEGIN_SCAN = EQUIPMENT_URL + "beginScan";
    //请求设备拉取数据-----post
    public static final String REQUEST_DATA = EQUIPMENT_URL + "requestData";
    //获取数据LEFT_JSON----get
    public static final String LEFT_JSON = EQUIPMENT_URL + "awawaw/left.json";
    //获取数据RIGHT_JSON----get
    public static final String RIGHT_JSON = EQUIPMENT_URL + "awawaw/right.json";
    //获取数据图片1---------get
    public static final String IMAGE_ONE = EQUIPMENT_URL + "awawaw/0-show.jpg";
    //获取数据图片2---------get
    public static final String IAMGE_TWO = EQUIPMENT_URL + "awawaw/1-show.jpg";
    //获取数据图片3---------get
    public static final String IMAGE_TREE = EQUIPMENT_URL + "awawaw/5_l-show.jpg";
    //获取数据图片4---------get
    public static final String IAMGE_FOUR = EQUIPMENT_URL + "awawaw/5_r-show.jpg";
    //获取数据ZIP-----------get
    public static final String DATA_ZIP = EQUIPMENT_URL + "awawaw/right.json";
}
