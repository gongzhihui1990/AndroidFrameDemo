package com.starkrak.framedemo.play;

public abstract class GameView implements GameViewInterface {
    private boolean isInit = false;

    public void init() {
        isInit = true;
    }

    public boolean isInit() {
        return isInit;
    }
}