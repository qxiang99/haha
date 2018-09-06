package com.bintutu.shop.utils;


import android.os.Environment;

import java.io.IOException;

public class AppConstant {

    public static boolean Toogle = false;


    //TODO 服务器  请在“Formal_URL=”填写正式服务器地址   并把“Toogle”修改为 true
    public static String TEST_URL = "http://116.62.145.154:8080/";//测试环境
    public static String Formal_URL = "";//正式环境
    public static String BASE_URLS = Toogle ? Formal_URL : TEST_URL;


    //TODO  WEB   请在“Formal_WEB_URL=”填写正式服务器地址   并把“Toogle”修改为 true
    public static String TEST_WEB_URL = "http://ipadscan_test.bintutu.com/";//测试环境
    public static String Formal_WEB_URL = "";//正式环境
    public static String BASE_WEB_URLS = Toogle ? Formal_WEB_URL : TEST_WEB_URL;


    //TODO 设备地址需要修改吗
    public static final String EQUIPMENT_URL = "http://192.168.12.1/";



    //TODO 这是首页轮播图需要拼接的地址  需要修改吗？
    public static final String IMAGE_SPLIT = "http://resources_test.bintutu.com/merchandise_img/homepage_img";
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
    //口令
    public static final String COMMAND = BASE_URLS + "shop_3d/backend/web/index.php/user/login";




    //web首页
    public static final String WEBVIEW_HOME = BASE_WEB_URLS+"shop_3dscanner_learning/frontend/shutdown.html";
    //exhibition room
    public static final String WEBVIEW_EXHIBITIONROOM = BASE_WEB_URLS+"shop_3dscanner_learning/frontend/shopinformation.html";
    //web选鞋页
    public static final String WEBVIEW_SORT(String customer_id,String customer_phone){
        return BASE_WEB_URLS+"shop_3dscanner_learning/frontend/sort.html?customer_id="+customer_id+"&customer_phone="+customer_phone;
    };
    //web选鞋页
    public static final String WEBVIEW_CHOOSE(String customer_id,String phone,String id,String color,String fur,String sole_materials,String sole_accessory,String exclusive){
        return BASE_WEB_URLS+"shop_3dscanner_learning/frontend/information.html?customer_id="+customer_id+"&phone="+phone+"&id="+id+"&color="+color+"&fur="+fur+"&sole_materials="+sole_materials+"&sole_accessory="+sole_accessory+"&exclusive="+exclusive;
    }




    //修改Wi-Fi连接-------post
    public static final String CHOOSE_WIFI = EQUIPMENT_URL + "wifi";
    //关闭扫描仪----------post
    public static final String SHUTDOWN = EQUIPMENT_URL + "shutDown";
    //启动设备------------post
    public static final String BEGIN_SCAN = EQUIPMENT_URL + "beginScan";
    //请求设备拉取数据-----post
    public static final String REQUEST_DATA = EQUIPMENT_URL + "requestData";
    //查看设备是否在线-----get
    public static final String GET_ID = EQUIPMENT_URL + "getID";
    //获取数据左脚---------get
    public static final String LEFT_JSON(String leftjson) {
        return EQUIPMENT_URL + leftjson + "/left.json";
    }
    //获取数据右脚---------get
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
    //SD卡路径
    public static String FILE(){
        try {
            return  Environment.getExternalStorageDirectory().getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    //足详情页图片
    public static final String ZIP_DATAIL = FILE() + "/Bintutu/zip";
    //轮播图
    public static final String IMAGE_BANNER = FILE() + "/Bintutu/Banner";
    //足详情页图片
    public static final String IMAGE_DATAIL = FILE() + "/Bintutu/datail";
    //保存长图
    public static final String IMAGE_LONG = FILE() + "/Bintutu/image";
    //错误日志
    public static final String CRASH = FILE() + "/Bintutu/crash";

}
