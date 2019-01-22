package com.starkrak.framedemo.game;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.starkrak.framedemo.App;
import com.starkrak.framedemo.R;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

public class GameBox implements GameView {
    private GameBall gameBall;
    private GameBall gameBallPrv;
    private View view;

    public View getView() {
        return view;
    }

    public ImageView getImageView() {
        return (ImageView) ((ViewGroup) view).getChildAt(0);
    }

    private GameColor rightColor;

    public GameBox(@NonNull View view) {
        this.view = view;
    }

    public void setRightColor(@NonNull GameColor color) {
        this.rightColor = color;
    }

    public void setGameBall(GameBall gameBall) {
        gameBall.used = true;
        clear();
        this.gameBall = gameBall;
    }

    public boolean isFilled() {
        return this.gameBall != null;
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
            switch (gameBall.gameColor) {
                case Red:
                    view.setBackgroundColor(App.getContext().getResources().getColor(R.color.main1_bg));
                    break;
                case SkyBlue:
                    view.setBackgroundColor(App.getContext().getResources().getColor(R.color.main4_bg));
                    break;
                case Pink:
                    view.setBackgroundColor(App.getContext().getResources().getColor(R.color.main6_bg));
                    break;
                case Green:
                    view.setBackgroundColor(App.getContext().getResources().getColor(R.color.main3_bg));
                    break;
                case DarkBlue:
                    view.setBackgroundColor(App.getContext().getResources().getColor(R.color.main5_bg));
                    break;
                case Yellow:
                    view.setBackgroundColor(App.getContext().getResources().getColor(R.color.main2_bg));
                    break;
                default:
                    view.setBackgroundColor(App.getContext().getResources().getColor(android.R.color.white));
                    break;
            }
            view.setOnClickListener(v -> {
                clear();
                invalidate();
            });
        } else {
            view.setBackgroundColor(App.getContext().getResources().getColor(android.R.color.white));
        }
        if (this.gameBallPrv != null) {
            this.gameBallPrv.invalidate();
        }
    }

    public boolean isRight() {
        if (gameBall == null) {
            return false;
        }
        if (gameBall.gameColor == null) {
            return false;
        }
        return rightColor == gameBall.gameColor;
    }
}
