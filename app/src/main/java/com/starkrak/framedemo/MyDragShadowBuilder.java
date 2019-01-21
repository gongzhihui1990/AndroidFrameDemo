package com.starkrak.framedemo;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.View;

import androidx.annotation.NonNull;

/**
 *
 * @author qikai
 * @date 16/1/28
 */
public class MyDragShadowBuilder extends View.DragShadowBuilder {

    private final Point touchPoint = new Point();

    public MyDragShadowBuilder(View view, Point touchPoint) {
        super(view);
        this.touchPoint.set(touchPoint.x, touchPoint.y);
    }

    @Override
    public void onProvideShadowMetrics(@NonNull Point shadowSize, @NonNull Point shadowTouchPoint) {
        final View view = getView();
        if (view != null) {
            shadowSize.set(view.getWidth(), view.getHeight());
            shadowTouchPoint.set(touchPoint.x, touchPoint.y);
        }
    }

    @Override
    public void onDrawShadow(@NonNull Canvas canvas) {
         canvas.scale(0.9f,0.9f);
         super.onDrawShadow(canvas);
    }
}
