package com.starkrak.framedemo.game;

import android.view.View;

import com.starkrak.framedemo.R;

import androidx.annotation.NonNull;

public class GameBall implements GameView {
    GameColor gameColor;
    boolean used = false;
    //0-5
    int currentIndex = -1;
    View view;

    public View getView() {
        return view;
    }

    public GameBall(@NonNull View view) {
        this.view = view;
        switch (view.getId()) {
            case R.id.item1View:
                gameColor = GameColor.Red;
                break;
            case R.id.item2View:
                gameColor = GameColor.Yellow;
                break;
            case R.id.item3View:
                gameColor = GameColor.Green;
                break;
            case R.id.item4View:
                gameColor = GameColor.SkyBlue;
                break;
            case R.id.item5View:
                gameColor = GameColor.DarkBlue;
                break;
            case R.id.item6View:
                gameColor = GameColor.Pink;
                break;
            default:
                throw new RuntimeException("不支持");
        }
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
