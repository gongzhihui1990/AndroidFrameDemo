package com.starkrak.framedemo.play;

import com.starkrak.framedemo.R;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

public enum GameColor {
    Red(R.color.main1,R.drawable.round_1_mini),
    Yellow(R.color.main2,R.drawable.round_2_mini),
    Green(R.color.main3,R.drawable.round_3_mini),
    SkyBlue(R.color.main4,R.drawable.round_4_mini),
    DarkBlue(R.color.main5,R.drawable.round_5_mini),
    Pink(R.color.main6,R.drawable.round_6_mini);
    @ColorRes
    public int color;
    @DrawableRes
    public int colorMin;

    GameColor( @ColorRes int color, @DrawableRes int colorMin) {
        this.color = color;
        this.colorMin = colorMin;
    }
}