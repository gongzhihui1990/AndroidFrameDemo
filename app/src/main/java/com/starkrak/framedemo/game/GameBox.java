package com.starkrak.framedemo.game;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.starkrak.framedemo.App;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.core.content.ContextCompat;

public class GameBox implements GameView {
    private GameBall gameBall;
    private GameBall gameBallPrv;
    private View view;
    private ImageView imageView;
    private View colorView;

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
            gameBall.used = true;
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
            this.gameBallPrv.used = false;
            this.gameBall = null;
        }
    }

    @Override
    @UiThread
    public void invalidate() {
        view.setEnabled(gameBall != null);
        if (gameBall != null) {
            gameBall.invalidate();
            //view.setBackgroundColor(App.getContext().getResources().getColor(android.R.color.white));
            colorView.setBackground(ContextCompat.getDrawable(App.getContext(), gameBall.getGameColor().colorMin));
            view.setOnClickListener(v -> {
                clear();
                invalidate();
            });
        } else {
            colorView.setBackground(null);
           // view.setBackgroundColor(App.getContext().getResources().getColor(android.R.color.white));
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
}
