package com.bintutu.shop.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.ChooseBean;
import com.bintutu.shop.bean.RelateFootDataBean;
import com.bintutu.shop.bean.WebDataBean;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.ConfigManager;
import com.bintutu.shop.utils.Constant;
import com.bintutu.shop.utils.DebugLog;
import com.bintutu.shop.utils.EventMsg;
import com.bintutu.shop.utils.RxBus;
import com.google.gson.Gson;

import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class WebActivity extends BaseActivity {

    @BindView(R.id.web_progressbar)
    ProgressBar mWebProgressbar;
    @BindView(R.id.webView)
    WebView mWebView;
    private String url;
    private String callback;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_web);
    }
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void init() {


        Intent intent = getIntent();
        String weburl = intent.getStringExtra(Constant.ItentKey1);
        if (weburl != null) {
            url = weburl;
        } else {
            url = AppConstant.WEBVIEW_HOME;
        }


        mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示


        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setInitialScale(10);//为10%，最小缩放等
        mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示
        //mWebView.setInitialScale(10);//为10%，最小缩放等级

        WebSettings settings = mWebView.getSettings();
        //主要用于平板，针对特定屏幕代码调整分辨率
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setUseWideViewPort(true);//设置webview推荐使用的窗口
        settings.setLoadWithOverviewMode(true);//设置webview加载的页面的模式
        settings.setDisplayZoomControls(false);//隐藏webview缩放按钮
        settings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setBuiltInZoomControls(false); // 设置显示缩放按钮
        settings.setSupportZoom(false); // 支持缩放
        settings.setUserAgentString("scheme//caigou_ANDROID/1.1.1");
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }*/
        // 开启DOM缓存。
        //settings.setDomStorageEnabled(true);
       /* settings.setDatabaseEnabled(true);
        settings.setDatabasePath(getCacheDir().getAbsolutePath());*/
        //settings.setBuiltInZoomControls(true);
        //WebView加载web资源
      /*  if (ConfigManager.User.getToken() != "token") {
            if (url.indexOf("?") != -1) {
                url = url + "&token=" + ConfigManager.User.getToken();
            } else {
                url = url + "?token=" + ConfigManager.User.getToken();
            }
        }*/
        syncCookie(WebActivity.this,getDomain(url));
        mWebView.loadUrl(url);
        DebugLog.e("新连接：" + url);


        ConfigManager.Foot.setCustomer_id("");
        ConfigManager.Foot.setCustomer_phone("");
        ConfigManager.Foot.setIdid("");
        ConfigManager.Foot.setchoosed_color_id("");
        ConfigManager.Foot.setchoosed_exclusive_id("");
        ConfigManager.Foot.setchoosed_sole_accessory_id("");
        ConfigManager.Foot.setchoosed_sole_material_id("");
        RxBusMsg();
    }

    @Override
    protected void setListener() {






        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {

                AlertDialog.Builder b2 = new AlertDialog.Builder(WebActivity.this)
                        .setTitle("提示").setMessage(message)
                        .setPositiveButton("ok",
                                new AlertDialog.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        result.confirm();
                                    }
                                });

                b2.setCancelable(false);
                b2.create();
                b2.show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                // TODO Auto-generated method stub
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
                                      JsPromptResult result) {
                // TODO Auto-generated method stub
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });


        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                DebugLog.e("shouldOverrideUrlLoading");
                if (url.startsWith("bintutu://")) {
                    DebugLog.e(url + "");
                    Takeout(url);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

        });

    }

    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
    private void Takeout(String url) {
        Gson gson = new Gson();
        if (url.startsWith("bintutu://startscanning")) {
            Map<String, String> map = getParamsMap(url, "bintutu://startscanning");
            String data = map.get("param");
            callback = map.get("callback");
            DebugLog.e("param:" + data);
            WebDataBean webDataBean = gson.fromJson(data, WebDataBean.class);
            if (webDataBean.getShop_id() != null) {
                ConfigManager.Device.setShopID(webDataBean.getShop_id());
            }
            if (webDataBean.getShop_phone() != null) {
                ConfigManager.Device.setShopPhone(webDataBean.getShop_phone());
            }
            Intent intent = new Intent(WebActivity.this, ReadyToScanActivity.class);
            intent.putExtra(Constant.ItentKey1, "WebActivity");
            intent.putExtra(Constant.ItentKey2, url);
            startActivity(intent);
        }
        if (url.startsWith("bintutu://home")) {

            startActivity(new Intent(WebActivity.this, MainActivity.class));
            finish();
        }
        if (url.startsWith("bintutu://cookies")) {
            Map<String, String> map = getParamsMap(url, "bintutu://cookies");
            String data = map.get("param");
            callback = map.get("callback");
            DebugLog.e("param:" + data);
            ChooseBean chooseBean = gson.fromJson(data, ChooseBean.class);
            ConfigManager.Foot.setCustomer_id(chooseBean.getCustomer_id());
            ConfigManager.Foot.setCustomer_phone(chooseBean.getCustomer_phone());
            ConfigManager.Foot.setIdid(chooseBean.getIdid());
            ConfigManager.Foot.setchoosed_fur_id(chooseBean.getChoosed_fur_id());
            ConfigManager.Foot.setchoosed_color_id(chooseBean.getChoosed_color_id());
            ConfigManager.Foot.setchoosed_exclusive_id(chooseBean.getChoosed_exclusive_id());
            ConfigManager.Foot.setchoosed_sole_accessory_id(chooseBean.getChoosed_sole_accessory_id());
            ConfigManager.Foot.setchoosed_sole_material_id(chooseBean.getChoosed_sole_material_id());

            Intent intent = new Intent(WebActivity.this, ReadyToScanActivity.class);
            intent.putExtra(Constant.ItentKey1, "WebActivity");
            intent.putExtra(Constant.ItentKey2, url);
            startActivity(intent);

        }

        if (url.startsWith("bintutu://relatefootdata")) {
            Map<String, String> map = getParamsMap(url, "bintutu://relatefootdata");
            String data = map.get("param");
            callback = map.get("callback");
            DebugLog.e("param:" + data);
            RelateFootDataBean chooseBean = gson.fromJson(data, RelateFootDataBean.class);
            ConfigManager.Foot.setWebFitting_id(chooseBean.getId());
            Intent intent = new Intent(WebActivity.this, ReadyToScanActivity.class);
            intent.putExtra(Constant.ItentKey1, "WebActivity");
            intent.putExtra(Constant.ItentKey2, url);
            startActivity(intent);

        }


    }





    private Map<String, String> getParamsMap(String url, String pre) {
        ArrayMap<String, String> queryStringMap = new ArrayMap<>();
        if (url.contains(pre)) {
            int index = url.indexOf(pre);
            int end = index + pre.length();
            String queryString = url.substring(end + 1);

            String[] queryStringSplit = queryString.split("&");

            String[] queryStringParam;
            for (String qs : queryStringSplit) {
                Log.e("WebActivitydata", qs + "");
                if (qs.toLowerCase().startsWith("callback=")) {
                    //单独处理data项，避免data内部的&被拆分
                    int dataIndex = qs.indexOf("callback=");
                    String dataValue = qs.substring(dataIndex + 9);
                    queryStringMap.put("callback", dataValue);
                } else if (qs.toLowerCase().startsWith("param=")) {
                    //单独处理data项，避免data内部的&被拆分
                    int dataIndex = qs.indexOf("param=");
                    String dataValue = qs.substring(dataIndex + 6);
                    try {
                        String AA = URLDecoder.decode(dataValue, "UTF-8");
                        queryStringMap.put("param", AA);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return queryStringMap;
    }


    /**
     * 获取URL的域名
     */
    private String getDomain(String url){
        url = url.replace("http://", "").replace("https://", "");
        if (url.contains("/")) {
            url = url.substring(0, url.indexOf('/'));
        }
        return url;
    }


    /**
     * 给WebView同步Cookie
     *
     * @param context 上下文
     * @param url     可以使用[domain][host]
     */
    private void syncCookie(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();// 移除旧的[可以省略]

        String value = "shop_id" + "=" + ConfigManager.Device.getShopID();
        cookieManager.setCookie(url, value);

        String value2 = "shop_phone" + "=" + ConfigManager.Device.getShopPhone();
        cookieManager.setCookie(url, value2);

        String value3 = "stronglogin" + "=" + "1";
        cookieManager.setCookie(url, value3);

        CookieSyncManager.getInstance().sync();// To get instant sync instead of waiting for the timer to trigger, the host can call this.
    }

    private void RxBusMsg() {
        RxBus.getInstance().toObservable().map(new Function<Object, EventMsg>() {
            @Override
            public EventMsg apply(Object o) throws Exception {
                return (EventMsg) o;
            }
        }).subscribe(new Consumer<EventMsg>() {
            @Override
            public void accept(EventMsg eventMsg) throws Exception {
                if (eventMsg != null) {
                    if (eventMsg.getCode()==800){
                      finish();
                    }

                }
            }
        });
    }



}
