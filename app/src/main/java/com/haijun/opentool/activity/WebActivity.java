package com.haijun.opentool.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.haijun.opentool.R;
import com.haijun.opentool.util.ChooseAlertDialogUtil;
import com.haijun.opentool.util.LogUtil;

public class WebActivity extends Activity {

    private static final Class<WebActivity> TAG = WebActivity.class;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mWebView = findViewById(R.id.webView);
        initWebView();

        mWebView.loadUrl("https://www.chsi.com.cn/xlcx/lscx/query.do");
    }

    private void initWebView(){
        mWebView = findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //支持缓存
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); //默认不使用缓存！
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //WebView.setWebContentsDebuggingEnabled(true);
        }

        //解决Android webview加载https网页时http图片无法显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("说明")
                        .setMessage(message)
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                result.confirm();
                            }
                        })
                        .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        result.cancel();
                    }
                });

                // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
                builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        Log.v("onJsConfirm", "keyCode=="+   keyCode +  "event=" + event);
                        return true;
                    }
                });
                // 禁止响应按back键的事件
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                if (!WebActivity.this.isDestroyed() && !WebActivity.this.isFinishing()){
                    ChooseAlertDialogUtil.showTipDialog(WebActivity.this,message,"确定");
                    result.cancel();
                }
                return true;
            }


        });

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //学历查询的URL： https://www.chsi.com.cn/xlcx/lscx/xlresult.do?rndid=ssdmhpx6tl5ozb2m7i2ktxodq7a6g49r
                if (url.contains("xlcx/lscx/xlresult.do")){
                    //原生调用H5里的方法
                    mWebView.loadUrl("javascript:window.inter_education.showEducationResult("
                            + "document.getElementsByClassName('col-right')[0].innerText+','+"
                            + "document.getElementsByClassName('col-right')[5].innerText+','+"
                            + "document.getElementsByClassName('col-right')[9].innerText+','+"
                            + "document.getElementsByClassName('col-right')[10].innerText+','+"
                            +"document.getElementsByClassName('col-right')[13].innerText"
                            + ");");
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LogUtil.e(TAG,"onPageStarted:"+url);
            }

        });

        mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "inter_education");

    }

    public final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showEducationResult(String str) {
            //***,北京大学,普通全日制,本科,105891****04626
            LogUtil.e(TAG,"showDescription:"+str);
            String[] split = str.split(",");
            if (split.length>=5){
                ChooseAlertDialogUtil.showTipDialog(WebActivity.this, "授权提示", "学历查询成功，是否同意授权App用于学历认证？",
                        "同意授权", "拒绝", (dialog, which) -> {
                            Intent intent = getIntent();
                            intent.putExtra("educationData",str);
                            setResult(RESULT_OK, intent);
                            finish();
                        }, (dialog, which) -> {

                        });
            }
        }
    }
}