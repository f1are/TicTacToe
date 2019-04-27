package com.example.tictactoe;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class AI {
    private final int ROW = 0;
    private final int COLUMN = 1;

    private float thinkTime;

    int[] makeMove(Activity activity, ImageView[][] gameBoard, int[][] gameBoardValues){
        //List<Vector<Integer>> freeTilesList = new ArrayList<>();


        Vector<Integer> freeTile = GameActivity.freeTilesList.get(new Random().nextInt(GameActivity.freeTilesList.size()));
        int counterI = freeTile.get(ROW);
        int counterJ = freeTile.get(COLUMN);

        gameBoard[counterI][counterJ].setImageDrawable(activity.getResources().getDrawable(R.drawable.o));
        gameBoardValues[counterI][counterJ] = -1;

        return new int[]{counterI, counterJ};
    }

    void startThinkProcess(){

    }


    void stopThinkProcess(){

    }
}
