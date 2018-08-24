package com.bintutu.shop.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bintutu.shop.R;
import com.bintutu.shop.bean.WebDataBean;
import com.bintutu.shop.ui.BaseActivity;
import com.bintutu.shop.ui.view.ImageDailog;
import com.bintutu.shop.ui.view.PictureTagLayout;
import com.bintutu.shop.utils.AppConstant;
import com.bintutu.shop.utils.ConfigManager;
import com.bintutu.shop.utils.Constant;
import com.bintutu.shop.utils.DebugLog;
import com.google.gson.Gson;

import java.net.URLDecoder;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.web_progressbar)
    ProgressBar mWebProgressbar;
    @BindView(R.id.webView)
    WebView mWebView;
    private String url;
    private String callback;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void init() {


        Intent intent = getIntent();
        String weburl = intent.getStringExtra(Constant.ItentKey1);
        if (weburl != null) {
            url = weburl;
        } else {
            url = AppConstant.WEBVIEW_HOME;
        }
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUserAgentString("scheme//caigou_ANDROID/1.1.1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 开启DOM缓存。
        settings.setDomStorageEnabled(true);
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
        // mWebView.loadUrl(url);
        DebugLog.e("新连接：" + url);
    }

    @Override
    protected void setListener() {

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ReadyToScanActivity.class));
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                ShowToast("网络连接失败 ,请连接网络");
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("bintutu://")) {
                    DebugLog.e(url + "");
                    Takeout(url);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    mWebProgressbar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    mWebProgressbar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    mWebProgressbar.setProgress(progress);//设置进度值
                }
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
        if (url.startsWith("bintutu://startscanning")) {
            Map<String, String> map = getParamsMap(url, "bintutu://startscanning");
            String data = map.get("param");
            callback = map.get("callback");
            DebugLog.e("param:" + data);
            Gson gson = new Gson();
            WebDataBean webDataBean = gson.fromJson(data, WebDataBean.class);
            if (webDataBean.getShop_id() != null) {
                ConfigManager.Device.setShopID(webDataBean.getShop_id());
            }
            if (webDataBean.getShop_phone() != null) {
                ConfigManager.Device.setShopPhone(webDataBean.getShop_phone());
            }
            startActivity(new Intent(MainActivity.this, ReadyToScanActivity.class));
        }
        if (url.startsWith("bintutu://cookies")) {
            Map<String, String> map = getParamsMap(url, "bintutu://cookies");
            String data = map.get("param");
            callback = map.get("callback");
            DebugLog.e("param:" + data);
            startActivity(new Intent(MainActivity.this, ReadyToScanActivity.class));

        }


    }


    //退出时的时间
    private long mExitTime;

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ShowToast("再按一次退出");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
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

}
