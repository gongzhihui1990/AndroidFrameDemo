/*
 * Copyright (c) 2017. heisenberg.gong
 */

package net.gtr.framework.util;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

/**
 * 封装默认的Log类
 *
 * @author caroline
 */
public class Loger {
    private final static boolean DO_PRINT_STREAM = false;
    private final static boolean IS_DEBUG = true;
    static Proxy proxy;
    static Proxy netProxy;
    static TextView proxyText;
    static Handler handler = new Handler();

    public static void setProxy(Proxy proxy) {
        Loger.proxy = proxy;
    }

    public static void setNetProxy(Proxy netProxy) {
        Loger.netProxy = netProxy;
    }

    public static void registerTextlog(@NonNull TextView textView) {
        proxyText = textView;
    }

    public static void unregisterTextView() {
        proxyText = null;
    }

    public static void v(String msg) {
        v(msg, "");
    }

    public static void v(String msg, String tag) {

        if (IS_DEBUG) {
            if (proxyText != null) {
                postTextLog(msg);
            }

            if (TextUtils.isEmpty(tag)) {
                tag = generateTag();
                msg = generateMsg(msg);
            }
            if (netProxy != null) {
                netProxy.v(tag, msg);
            }
            if (proxy != null) {
                proxy.v(tag, msg);
            } else {
                Log.v(tag, msg);
            }
        }
    }

    private static void postTextLog(String msg) {
        handler.post(() -> {
            if (proxyText == null) {
                return;
            }
            String content = proxyText.getText() + "\n" + msg;
            proxyText.setText(content);
        });

    }

    public static void i(String msg) {
        i(msg, "");
    }

    public static void i(String msg, String tag) {

        if (IS_DEBUG) {
            if (proxyText != null) {
                postTextLog(msg);
            }
            if (TextUtils.isEmpty(tag)) {
                tag = generateTag();
                msg = generateMsg(msg);
            }
            if (netProxy != null) {
                netProxy.i(tag, msg);
            }
            if (proxy != null) {
                proxy.i(tag, msg);
            } else {
                Log.i(tag, msg);
            }
        }
    }

    public static void d(String msg) {
        d(msg, "");
    }

    public static void d(String msg, String tag) {
        if (IS_DEBUG) {
            if (proxyText != null) {
                postTextLog(msg);
            }
            if (TextUtils.isEmpty(tag)) {
                tag = generateTag();
                msg = generateMsg(msg);
            }
            if (proxy != null) {
                proxy.d(tag, msg);
            } else {
                Log.d(tag, msg);
            }
        }
    }

    public static void w(String msg) {
        w(msg, "");
    }

    public static void w(String msg, String tag) {

        if (IS_DEBUG) {
            if (proxyText != null) {
                postTextLog(msg);
            }
            if (TextUtils.isEmpty(tag)) {
                tag = generateTag();
                msg = generateMsg(msg);
            }
            if (netProxy != null) {
                netProxy.w(tag, msg);
            }
            if (proxy != null) {
                proxy.w(tag, msg);
            } else {
                Log.w(tag, msg);
            }
        }
    }

    public static void e(String msg) {
        e(msg, "");
    }

    public static void e(String msg, String tag) {

        if (IS_DEBUG) {
            if (proxyText != null) {
                postTextLog(msg);
            }
            if (TextUtils.isEmpty(tag)) {
                tag = generateTag();
                msg = generateMsg(msg);
            }
            if (netProxy != null) {
                netProxy.e(tag, msg);
            }
            if (proxy != null) {
                proxy.e(tag, msg);
            } else {
                Log.e(tag, msg);
            }
        }
    }

    public static void a(String msg) {
        a(msg, "");
    }

    public static void a(String msg, String tag) {

        if (IS_DEBUG) {
            if (proxyText != null) {
                postTextLog(msg);
            }
            if (TextUtils.isEmpty(tag)) {
                tag = generateTag();
                msg = generateMsg(msg);
            }
            if (netProxy != null) {
                netProxy.e(tag, msg);
            }
            if (proxy != null) {
                proxy.e(tag, msg);
            } else {
                Log.e(tag, msg);
            }
            Log.println(Log.ASSERT, tag, msg);
        }
    }

    private static String generateTag() {
        final StackTraceElement[] stack = new Throwable().getStackTrace();
        int i = 0;
        for (StackTraceElement stackItem : stack) {

            if (stackItem.getClassName().equals(Loger.class.getName())) {
                //当前类堆栈不打印
                i = i + 1;
                continue;
            }
            break;
        }
        final StackTraceElement ste = stack[i];
        return ste.getClassName();
    }

    @SuppressLint("DefaultLocale")
    private static String generateMsg(String msg) {
        final StackTraceElement[] stack = new Throwable().getStackTrace();
        int i = 0;
        for (StackTraceElement stackItem : stack) {
            if (stackItem.getClassName().equals(Loger.class.getName())) {
                //当前类堆栈不打印
                i = i + 1;
                continue;
            }
            if (DO_PRINT_STREAM) {
                msg += ("\n\tat " + stackItem);
            }
            break;
        }
        final StackTraceElement ste = stack[i];
        return String.format("[%s][%d]%s", ste.getMethodName(),
                ste.getLineNumber(), msg);
    }

    public interface Proxy {

        void v(String tag, String msg);

        void i(String tag, String msg);

        void d(String tag, String msg);

        void w(String tag, String msg);

        void e(String tag, String msg);
    }

}
