/*
 * Copyright (c) 2017. heisenberg.gong
 */

package net.gtr.framework.app;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;

import com.krak.android.R;

import net.gtr.framework.util.Loger;
import net.gtr.framework.util.ToastUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import androidx.multidex.MultiDexApplication;


/**
 * @author caroline
 */

public abstract class BaseApp extends MultiDexApplication {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getContext() {
        return context;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @return 进程名
     */
    public static String getProcessName() {
        int pid = android.os.Process.myPid();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return "unKnow";
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return "unKnow";
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getBaseContext();
        ToastUtil.init(R.layout.dialog_toast,R.id.tv_toast_msg);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        initApk();
    }

    @Override
    public void onTrimMemory(int level) {
        Loger.i("level "+level);
        super.onTrimMemory(level);
    }

    @Override
    public void onTerminate() {
        Loger.i("onTerminate");
        super.onTerminate();
    }

    /**
     * 初始化各种sdk,各种工具
     */
    public abstract void initApk();

}
