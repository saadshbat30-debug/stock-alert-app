package com.abutalal.launcher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String URL = "http://127.0.0.1:8080/";
    private final Handler handler = new Handler(Looper.getMainLooper());
    private WebView web;
    private TextView status;
    private int attempts;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        root.setPadding(16, 18, 16, 8);
        status = new TextView(this);
        status.setTextSize(16);
        status.setText("اضغط تشغيل لبدء المحرك عبر Termux");
        Button start = new Button(this);
        start.setText("تشغيل أبو طلال");
        start.setOnClickListener(v -> launchTermux());
        Button reload = new Button(this);
        reload.setText("تحديث الواجهة");
        reload.setOnClickListener(v -> web.loadUrl(URL));
        web = new WebView(this);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.setWebViewClient(new WebViewClient());
        root.addView(status);
        root.addView(start);
        root.addView(reload);
        root.addView(web, new LinearLayout.LayoutParams(-1, 0, 1));
        setContentView(root);
        if (checkSelfPermission("com.termux.permission.RUN_COMMAND") != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{"com.termux.permission.RUN_COMMAND"}, 42);
        }
    }

    private void launchTermux() {
        if (checkSelfPermission("com.termux.permission.RUN_COMMAND") != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{"com.termux.permission.RUN_COMMAND"}, 42);
            status.setText("اسمح بإذن تشغيل أوامر Termux ثم اضغط تشغيل مرة أخرى");
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setClassName("com.termux", "com.termux.app.RunCommandService");
            intent.setAction("com.termux.RUN_COMMAND");
            intent.putExtra("com.termux.RUN_COMMAND_PATH", "/data/data/com.termux/files/usr/bin/bash");
            intent.putExtra("com.termux.RUN_COMMAND_ARGUMENTS", new String[]{"/data/data/com.termux/files/home/raqami_v2/start_android.sh"});
            intent.putExtra("com.termux.RUN_COMMAND_WORKDIR", "/data/data/com.termux/files/home/raqami_v2");
            intent.putExtra("com.termux.RUN_COMMAND_BACKGROUND", true);
            startService(intent);
            status.setText("أُرسل طلب التشغيل إلى Termux؛ جارٍ انتظار الخادم...");
            attempts = 0;
            handler.postDelayed(this::refreshUntilReady, 1800);
        } catch (Exception e) {
            status.setText("تعذر إرسال أمر Termux: " + e.getMessage());
        }
    }

    private void refreshUntilReady() {
        web.loadUrl(URL);
        attempts++;
        if (attempts < 6) handler.postDelayed(this::refreshUntilReady, 2500);
    }
}
