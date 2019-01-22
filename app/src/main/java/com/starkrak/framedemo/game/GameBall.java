package com.starkrak.framedemo.game;

import android.view.View;

import androidx.annotation.NonNull;

public class GameBall implements GameView {
    private GameColor gameColor;
      boolean used = false;
    private View view;

    public GameColor getGameColor() {
        return gameColor;
    }

    public void cancel() {
        used = false;
    }

    public View getView() {
        return view;
    }

    public GameBall(@NonNull View view, @NonNull GameColor gameColor) {
        this.view = view;
        this.gameColor = gameColor;
        view.setTag(this);
    }

    @Override
    public void invalidate() {
        if (used) {
            view.setEnabled(false);
            view.setAlpha(0.05f);
        } else {
            view.setEnabled(true);
            view.setAlpha(1f);
        }
    }
}
