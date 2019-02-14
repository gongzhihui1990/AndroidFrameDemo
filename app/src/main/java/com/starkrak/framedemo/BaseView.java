package com.starkrak.framedemo;


import android.content.Context;


/**
 * @author caroline
 */
public interface BaseView {
    /**
     * 应用上下文
     * @return Context
     */
    Context getContext();
    /**
     * 遇到错误，退出当前页面
     * @param msg 错误原因
     */
    void initErrorQuit(String msg);

}
