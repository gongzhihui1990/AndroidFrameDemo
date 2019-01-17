package com.starkrak.framedemo;

import android.content.Context;

import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.starkrak.framedemo.widget.MaterialHeader;

import net.gtr.framework.util.Loger;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class TestJavaFather {

    void t(){
        DefaultRefreshHeaderCreater creator   = new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
                return new MaterialHeader(context).setPercentCallback(new MaterialHeader.PercentCallback() {
                    @Override
                    public void onPercent(float percent) {
                        Loger.i("TestJavaFather"+percent);
                    }
                });
            }
        };

    }
}
