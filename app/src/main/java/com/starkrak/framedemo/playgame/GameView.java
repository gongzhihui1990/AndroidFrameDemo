package com.starkrak.framedemo.playgame;

public abstract class GameView implements GameViewInterface {
    private boolean isInit = false;

    public void init() {
        isInit = true;
    }

    public boolean isInit() {
        return isInit;
    }
}
