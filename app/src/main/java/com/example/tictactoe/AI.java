package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Vector;

class AI {

    private String name;
    private TextView aiName;


    private final int ROW = 0;
    private final int COLUMN = 1;

    private Activity activity;
    private AlertDialog dialog;

    AI(Activity activity){
        this.activity = activity;
        name = "Super AI";
        aiName = activity.findViewById(R.id.ainame);
        aiName.setText(name);

    }

    /**
     * Get Name
     * @return Name of AI
     */
    String getName(){
        return name;
    }

    /**
     * Get AI Textview Reference
     * @return the reference to the Textview
     */
    TextView getAiNameTV(){
        return aiName;
    }

    /**
     * Generates a random time to think
     * @return an int between 3 and 8
     */
    int getRandomThinkingTime(){
        return new Random().nextInt(6)+3;
    }

    /**
     * Sets the symbol on the gameboard
     * @param gameBoard gameboard UI to use
     * @param gameBoardValues values of the gameboard
     * @return an int[] with the coordinates of setted tile on the gameboard
     */
    int[] makeMove(ImageView[][] gameBoard, int[][] gameBoardValues){

        Vector<Integer> freeTile = GameActivity.freeTilesList.get(new Random().nextInt(GameActivity.freeTilesList.size()));
        int counterI = freeTile.get(ROW);
        int counterJ = freeTile.get(COLUMN);

        gameBoard[counterI][counterJ].setImageDrawable(activity.getResources().getDrawable(R.drawable.o));
        gameBoardValues[counterI][counterJ] = -1;

        return new int[]{counterI, counterJ};
    }


    /**
     * Creates AlertDialog for the Time the AI takes to think
     */
    @SuppressLint("InflateParams")
    void showAITurnDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);


        LayoutInflater inflater = activity.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.ai_thinking_view, null));

        builder.setMessage(name + " is thinking.")
                .setTitle("Please wait.");
        dialog = builder.create();
        dialog.show();

    }

    /**
     * Closes the thinking alert dialog
     */
    void closeAITurnDialog(){
        dialog.dismiss();
    }


}
