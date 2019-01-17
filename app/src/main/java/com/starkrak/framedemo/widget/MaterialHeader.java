package com.starkrak.framedemo.widget;

import android.content.Context;
import android.util.AttributeSet;

import net.gtr.framework.util.Loger;


public class MaterialHeader extends com.scwang.smartrefresh.header.MaterialHeader {
    public MaterialHeader(Context context) {
        super(context);
    }

    public MaterialHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MaterialHeader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onPullingDown(float percent, int offset, int headHeight, int extendHeight) {
        super.onPullingDown(percent, offset, headHeight, extendHeight);
        Loger.i("percent:" + percent + "offset:" + offset + "headHeight:" + headHeight + "extendHeight:" + extendHeight);
        percentCallback.onPercent(percent);
    }

    @Override
    public void onReleasing(float percent, int offset, int headHeight, int extendHeight) {
        super.onReleasing(percent, offset, headHeight, extendHeight);
        Loger.i("percent:" + percent + "offset:" + offset + "headHeight:" + headHeight + "extendHeight:" + extendHeight);
        percentCallback.onPercent(percent);
    }
    private PercentCallback percentCallback = percent -> Loger.i("internal percent:" + percent);

    public MaterialHeader setPercentCallback(PercentCallback percentCallback) {
        this.percentCallback = percentCallback;
        return this;
    }

    public interface PercentCallback{
        void onPercent(float percent);
    }
}
