package com.starkrak.framedemo.playgame;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.starkrak.framedemo.App;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.core.content.ContextCompat;

public class GameBox extends GameView {
    private GameBall gameBall;
    private GameBall gameBallPrv;
    private View view;
    private ImageView imageView;
    private View colorView;

    public View getColorView() {
        return colorView;
    }

    public View getView() {
        return view;
    }

    public ImageView getImageView() {
        return imageView;
    }

    private GameColor rightColor;

    public GameBox(@NonNull View view) {
        this.view = view;
        this.imageView = (ImageView) ((ViewGroup) view).getChildAt(0);
        this.colorView = ((ViewGroup) view).getChildAt(1);
    }

    public void setRightColor(@NonNull GameColor color) {
        this.rightColor = color;
    }

    public void setGameBall(@Nullable GameBall gameBall) {
        if (gameBall != null) {
            gameBall.use();
        }
        clear();
        this.gameBall = gameBall;
        this.imageView.setTag(this);
    }

    public boolean isFilled() {
        return this.gameBall != null;
    }

    public GameBall getGameBall() {
        return gameBall;
    }

    private void clear() {
        if (this.gameBall != null) {
            this.gameBallPrv = this.gameBall;
            this.gameBallPrv.release(this);
            this.gameBall = null;
        }
    }

    @Override
    @UiThread
    public void invalidate() {
        view.setEnabled(gameBall != null);
        if (gameBall != null) {
            gameBall.invalidate();
            colorView.setBackground(ContextCompat.getDrawable(App.getContext(), gameBall.getGameColor().colorMin));
            view.setOnClickListener(v -> {
                clear();
                invalidate();
            });
        } else {
            colorView.setBackground(null);
        }
        if (this.gameBallPrv != null) {
            this.gameBallPrv.invalidate();
        }
    }

    public boolean isRight() {
        if (gameBall == null) {
            return false;
        }
        if (gameBall.getGameColor() == null) {
            return false;
        }
        return rightColor == gameBall.getGameColor();
    }

    @Override
    public void init() {
        if (gameBall != null) {
            gameBall.init();
        }
        setGameBall(null);
        invalidate();
        super.init();

    }
    public void initDelay(int delayMillis) {
        getView().postDelayed(this::init,delayMillis);
    }
}
