package com.bintutu.shop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.bintutu.shop.ShopApplication;

public class ConfigManager {

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

        private static String getConfigFile() {
            return CONFIG_DEVICE;
        }


        public static boolean isShowSplash() {
            return getBoolean(getConfigFile(), getVersionBindKey(SHOW_SPLASH), true);
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

    }



}