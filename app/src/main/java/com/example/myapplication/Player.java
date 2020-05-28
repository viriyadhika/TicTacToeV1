package com.example.myapplication;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.Arrays;

class Player {
    private boolean[][] buttonList;
    private int color;

    public Player(int color) {
        this.color = color;
        buttonList = new boolean[][] {
                {false, false, false},
                {false, false, false},
                {false, false, false}
        };
    }

    public void clickButton(View v, int[] position){
        v.setBackgroundColor(color);
        buttonList[position[0]][position[1]] = true;
    };

    public boolean isWinning(){
        return winningHorizontal() || winningVertical() || winningDiagonal();
    }

    public boolean winningHorizontal() {
        return
                (buttonList[0][0] && buttonList[0][1] && buttonList[0][2]) ||
                (buttonList[1][0] && buttonList[1][1] && buttonList[1][2]) ||
                (buttonList[2][0] && buttonList[2][1] && buttonList[2][2]);
    }

    public boolean winningVertical() {
        return
                (buttonList[0][0] && buttonList[1][0] && buttonList[2][0]) ||
                (buttonList[0][1] && buttonList[1][1] && buttonList[2][1]) ||
                (buttonList[0][2] && buttonList[1][2] && buttonList[2][2]);
    }

    public boolean winningDiagonal() {
        return
               (buttonList[0][0] && buttonList[1][1] && buttonList[2][2]) ||
               (buttonList[0][2] && buttonList[1][1] && buttonList[2][0]);
    }

    @NonNull
    @Override
    public String toString() {
    String text = "";

    switch (color) {
            case MainActivity.RED_COLOR:
                text = "Red";
                break;
            case MainActivity.BLUE_COLOR:
                text = "Blue";
                break;
    }
    return text;
    }

}
