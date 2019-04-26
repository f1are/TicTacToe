package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private String username;

    TextView usernameTV;
    TextView aiName;
    TextView gameTitle;

    ImageView[][] gameBoard;
    static int[][] gameBoardValues;

    static boolean playerturn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        username = intent.getStringExtra(MainActivity.EXTRA_USERNAME);

        ThemeChanger.getTheme(this);
        setContentView(R.layout.activity_game);

        usernameTV = findViewById(R.id.playername);
        usernameTV.setText(username);

        aiName = findViewById(R.id.ainame);

        gameTitle = findViewById(R.id.gameTitle);

        initializeGameBoard();

        decideFirstMover();

        gameBoardValues = new int[3][3];

    }

    /**
     * Binds IDs to a View[][] gameBoard and creates OnClicklistener
     */
    void initializeGameBoard(){
        gameBoard = new ImageView[3][3];

        gameBoard[0][0] = findViewById(R.id.cell00);
        gameBoard[0][1] = findViewById(R.id.cell01);
        gameBoard[0][2] = findViewById(R.id.cell02);
        gameBoard[1][0] = findViewById(R.id.cell10);
        gameBoard[1][1] = findViewById(R.id.cell11);
        gameBoard[1][2] = findViewById(R.id.cell12);
        gameBoard[2][0] = findViewById(R.id.cell20);
        gameBoard[2][1] = findViewById(R.id.cell21);
        gameBoard[2][2] = findViewById(R.id.cell22);

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                final int finalI = i;
                final int finalJ = j;
                gameBoard[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setSymbol(finalI, finalJ);
                    }
                });
            }
        }
    }

    /**
     * Sets x or o on gameboard
     * @param counterI row index of current field
     * @param counterJ column index of current field
     */
    void setSymbol(int counterI, int counterJ){
        if (playerturn){
            gameBoard[counterI][counterJ].setImageDrawable(getResources().getDrawable(R.drawable.x));
            gameBoardValues[counterI][counterJ] = 1;

            checkForWin(gameBoardValues, counterI, counterJ, username);

            usernameTV.setTextColor(getResources().getColor(R.color.colorPrimary));
            aiName.setTextColor(Color.CYAN);
            playerturn = !playerturn;
        }
        else{
            gameBoard[counterI][counterJ].setImageDrawable(getResources().getDrawable(R.drawable.o));
            gameBoardValues[counterI][counterJ] = -1;

            checkForWin(gameBoardValues, counterI, counterJ, aiName.getText().toString());

            aiName.setTextColor(getResources().getColor(R.color.colorPrimary));
            usernameTV.setTextColor(Color.CYAN);
            playerturn = !playerturn;
        }
    }

    /**
     * Decide for first one to play with random function
     */
    void decideFirstMover(){
        int random = new Random().nextInt(2);
        if(random <= 0){
            playerturn = true;
            usernameTV.setTextColor(Color.CYAN);
        }
        else{
            playerturn = false;
            aiName.setTextColor(Color.CYAN);
        }
    }

    /**
     * Check board for a winning trio
     * @param gameBoardValues gameboardarray with values
     * @param counterI  current row index of field
     * @param counterJ current column index of field
     * @param name name of current player
     */
    void checkForWin(int[][] gameBoardValues, int counterI, int counterJ, String name){

        //Horizontal
        int horizontalResult = 0;
        for (int j = 0; j < gameBoardValues[counterI].length; j++){
            horizontalResult += gameBoardValues[counterI][j];
        }
        checkWinner(horizontalResult, name);

        //Vertical
        int verticalResult = 0;
        for(int i = 0; i < gameBoardValues.length; i++){
            verticalResult += gameBoardValues[i][counterJ];
        }
        checkWinner(verticalResult, name);

        //Diagonal
        int diagResult = 0;
        if(counterI == counterJ){
            for (int i = 0; i < gameBoardValues.length; i++){
                diagResult += gameBoardValues[i][i];
            }
        }
        checkWinner(diagResult, name);

        //Anti-diagonal
        int antiDiagResult = 0;
        if(counterI + counterJ == gameBoardValues.length-1){
            for (int i = 0; i < gameBoardValues.length; i++){
                antiDiagResult += gameBoardValues[i][gameBoardValues.length-i-1];
            }
        }
        checkWinner(antiDiagResult, name);
    }

    /**
     * Check if winner is the user or AI
     * @param result sum of addition from win-check pattern
     * @param name name of current player
     */
    void checkWinner(int result, String name){
        if(result == 3 || result == -3){
            showGameOverDialog(name);
        }
    }

    /**
     * Creates AlertDialog for Game over with choice to restart or close activity
     */
    @SuppressLint("InflateParams")
    void showGameOverDialog(String winner){

        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);


        LayoutInflater inflater = getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.game_over, null));

        builder.setMessage(winner + " has won.")
                .setTitle("Game Over")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recreate();
            }
        });
        builder.create().show();
    }
}
