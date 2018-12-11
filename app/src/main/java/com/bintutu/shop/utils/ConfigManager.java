package com.bintutu.shop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.bintutu.shop.ShopApplication;

public class ConfigManager {

    private static final String CONFIG_FOOT = "config_foot";
    private static final String CONFIG_DEVICE = "config_devices";
    private static final String CONFIG_PERMISSION = "config_permission";

    private static int getInt(String configFile, String key, int defaultValue) {
        ShopApplication instance = ShopApplication.getInstance();
        SharedPreferences sp = instance.getSharedPreferences(configFile, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    private static void commitInt(String configFile, String key, int value) {
        ShopApplication instance = ShopApplication.getInstance();
        instance.getSharedPreferences(configFile, Context.MODE_PRIVATE).edit().putInt(key, value).commit();
    }


    private static String getString(String configFile, String key, String defaultValue) {
        ShopApplication instance = ShopApplication.getInstance();
        SharedPreferences sp = instance.getSharedPreferences(configFile, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    private static void commitString(String configFile, String key, String value) {
        ShopApplication instance = ShopApplication.getInstance();
        instance.getSharedPreferences(configFile, Context.MODE_PRIVATE).edit().putString(key, value).commit();
    }

    private static void commitBitmap(String configFile, String key, String value) {
        ShopApplication instance = ShopApplication.getInstance();
        instance.getSharedPreferences(configFile, Context.MODE_PRIVATE).edit().putString(key, value).commit();
    }

    private static boolean getBoolean(String configFile, String key, boolean defaultValue) {
        ShopApplication instance = ShopApplication.getInstance();
        SharedPreferences sp = instance.getSharedPreferences(configFile, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    private static void commitBoolean(String configFile, String key, boolean value) {
        ShopApplication instance = ShopApplication.getInstance();
        instance.getSharedPreferences(configFile, Context.MODE_PRIVATE).edit().putBoolean(key, value).commit();
    }

    private static long getLong(String configFile, String key, long defaultValue) {
        ShopApplication instance = ShopApplication.getInstance();
        SharedPreferences sp = instance.getSharedPreferences(configFile, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    private static void commitLong(String configFile, String key, long value) {
        ShopApplication instance = ShopApplication.getInstance();
        instance.getSharedPreferences(configFile, Context.MODE_PRIVATE).edit().putLong(key, value).commit();
    }

    private static String getVersionBindKey(String originKey) {
        return Utils.getAppVersionName(ShopApplication.getInstance()) + "_" + originKey;
    }

    public static class Device {

        public static final String SHOW_SPLASH = "show_splash";
        public static final String EQUIPMENTID = "device_equipment";
        public static final String TOKEN = "device_token";
        public static final String LOGINNAME = "device_loginname";
        public static final String LOGINPWD = "device_loginpwd";
        public static final String SHOPID = "device_shopid";
        public static final String SHOPPHONE = "device_shopphone";
        public static final String DETAILTAG = "device_Ddetail_tag";

        private static String getConfigFile() {
            return CONFIG_DEVICE;
        }


        public static boolean isShowSplash() {
            return getBoolean(getConfigFile(), getVersionBindKey(SHOW_SPLASH), false);
        }

        public static void setShowSplash(boolean showSplash) {
            commitBoolean(getConfigFile(), getVersionBindKey(SHOW_SPLASH), showSplash);
        }


        public static String getToken() {
            return getString(getConfigFile(), TOKEN, "token");
        }

        public static void setToken(String token) {
            commitString(getConfigFile(), TOKEN, token);
        }

        public static String getLoginName() {
            return getString(getConfigFile(), LOGINNAME, "");
        }

        public static void setLoginName(String name) {
            commitString(getConfigFile(), LOGINNAME, name);
        }

        public static String getLoginPwd() {
            return getString(getConfigFile(), LOGINPWD, "");
        }

        public static void setLoginPwd(String pwd) {
            commitString(getConfigFile(), LOGINPWD, pwd);
        }

        public static String getEquipmentID() {
            return getString(getConfigFile(), EQUIPMENTID, "");
        }

        public static void setEquipmentID(String id) {
            commitString(getConfigFile(), EQUIPMENTID, id);
        }

        public static String getShopID() {
            return getString(getConfigFile(), SHOPID, "");
        }

        public static void setShopID(String id) {
            commitString(getConfigFile(), SHOPID, id);
        }

        public static String getShopPhone() {
            return getString(getConfigFile(), SHOPPHONE, "");
        }

        public static void setShopPhone(String phone) {
            commitString(getConfigFile(), SHOPPHONE, phone);
        }



       /* public static int getTag() {
            return getInt(getConfigFile(), DETAILTAG, 0);
        }

        public static void setTag(int tag) {
            commitInt(getConfigFile(), DETAILTAG, tag);
        }*/

    }



    public static class Foot {


        public static final String People_id = "device_People_id";
        public static final String customer_id = "device_customer_id";
        public static final String webfitting_id = "device_webfitting_id";
        public static final String customer_phone = "device_customer_phone";
        public static final String idid = "device_idid";
        public static final String choosed_color_id = "device_choosed_color_id";
        public static final String choosed_fur_id = "device_choosed_fur_id";
        public static final String choosed_sole_material_id = "device_choosed_sole_material_id";
        public static final String choosed_sole_accessory_id = "device_choosed_sole_accessory_id";
        public static final String choosed_exclusive_id = "device_choosed_exclusive_id";

        private static String getConfigFile() {
            return CONFIG_FOOT;
        }

        public static String getPeople_id() {
            return getString(getConfigFile(), People_id, "");
        }

        public static void setPeople_id(String id) {
            commitString(getConfigFile(), People_id, id);
        }

        public static String getCustomer_id() {
            return getString(getConfigFile(), customer_id, "");
        }

        public static void setCustomer_id(String id) {
            commitString(getConfigFile(), customer_id, id);
        }

        public static String getWebFitting_id() {
            return getString(getConfigFile(), webfitting_id, "");
        }

        public static void setWebFitting_id(String id) {
            commitString(getConfigFile(), webfitting_id, id);
        }

        public static String getCustomer_phone() {
            return getString(getConfigFile(), customer_phone, "");
        }

        public static void setCustomer_phone(String phone) {
            commitString(getConfigFile(), customer_phone, phone);
        }

        public static String getIdid() {
            return getString(getConfigFile(), idid, "");
        }

        public static void setIdid(String id) {
            commitString(getConfigFile(), idid, id);
        }

        public static String getchoosed_color_id() {
            return getString(getConfigFile(), choosed_color_id, "");
        }

        public static void setchoosed_color_id(String id) {
            commitString(getConfigFile(), choosed_color_id, id);
        }

        public static String getchoosed_fur_id() {
            return getString(getConfigFile(), choosed_fur_id, "");
        }

        public static void setchoosed_fur_id(String id) {
            commitString(getConfigFile(), choosed_fur_id, id);
        }

        public static String getchoosed_sole_material_id() {
            return getString(getConfigFile(), choosed_sole_material_id, "");
        }

        public static void setchoosed_sole_material_id(String id) {
            commitString(getConfigFile(), choosed_sole_material_id, id);
        }

        public static String getchoosed_sole_accessory_id() {
            return getString(getConfigFile(), choosed_sole_accessory_id, "");
        }

        public static void setchoosed_sole_accessory_id(String id) {
            commitString(getConfigFile(), choosed_sole_accessory_id, id);
        }

        public static String getchoosed_exclusive_id() {
            return getString(getConfigFile(), choosed_exclusive_id, "");
        }

        public static void setchoosed_exclusive_id(String id) {
            commitString(getConfigFile(), choosed_exclusive_id, id);
        }

    }



}