package com.starkrak.framedemo.game;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.starkrak.framedemo.App;

import net.gtr.framework.util.Loger;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class GameBall extends GameView {
    private GameColor gameColor;
    private boolean used = false;
    public  View proxyView;
    private View ballView;
    private GameBox releaseHost = null;
    //ballView 的中心点
    private Point centerPoint;

    public void setCenterPoint(@NonNull Point centerPoint) {
        this.centerPoint = centerPoint;
    }

    public GameColor getGameColor() {
        return gameColor;
    }

    public void cancel() {
        used = false;
    }

    public View getView() {
        return ballView;
    }

    public GameBall(@NonNull View view, @NonNull GameColor gameColor, @NonNull View proxyView) {
        this.ballView = view;
        this.proxyView = proxyView;
        this.gameColor = gameColor;
        view.setTag(this);
    }


    @Override
    public void invalidate() {
        if (used) {
            ballView.setEnabled(false);
            ballView.setAlpha(0.05f);
        } else {
            ballView.setEnabled(true);
            if (releaseHost == null) {
                ballView.setAlpha(1f);
            } else {
                Rect rect = new Rect();
                Point start = new Point();
                releaseHost.getColorView().getGlobalVisibleRect(rect, start);
                // Point start =  new Point(rect.centerX(), rect.centerY());
                proxyView.setBackground(ContextCompat.getDrawable(App.getContext(), gameColor.colorMin));
                proxyView.setVisibility(View.VISIBLE);
                Loger.i("平移动画  从" + start.x + "," + start.y + ",平移到" + centerPoint.x + "," + centerPoint.y);
                Animation translateAnimation = new TranslateAnimation(start.x, centerPoint.x + 30, start.y - 48, centerPoint.y - 24);
                translateAnimation.setDuration(500);//动画持续的时间为1.5s
                proxyView.setAnimation(translateAnimation);//给imageView添加的动画效果
                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        proxyView.setVisibility(View.GONE);
                        ballView.setAlpha(1f);
                    }


                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                translateAnimation.startNow();//动画开始执行 放在最后即可

                releaseHost = null;
            }
        }


    }

    public void use() {
        used = true;
    }

    public void release(GameBox gameBox) {
        used = false;
        releaseHost = gameBox;
    }
}
