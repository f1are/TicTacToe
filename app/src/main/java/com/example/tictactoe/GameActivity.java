package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.Image;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class GameActivity extends AppCompatActivity {

    private String username;

    TextView usernameTV;
    TextView aiName;
    TextView gameTitle;

    ImageView[][] gameBoard;
    static int[][] gameBoardValues;
    boolean gameOver = false;

    static boolean playerturn;

    AI ai = new AI();

    private final int ROW = 0;
    private final int COLUMN = 1;

    static List<Vector<Integer>> freeTilesList = new ArrayList<>();

    Handler handler = new Handler();

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(!playerturn && !gameOver) {
                int[] aiTile = ai.makeMove(GameActivity.this, gameBoard, gameBoardValues);

                Vector vector = new Vector();
                vector.add(aiTile[ROW]);
                vector.add(aiTile[COLUMN]);

                Log.d("removed", "run: " + vector);

                freeTilesList.remove(vector);
                for(int i = 0; i < freeTilesList.size(); i++){
                    Log.d("Listrunnable", "setSymbol: " + freeTilesList.toArray()[i]);

                }

                checkForWin(gameBoardValues, aiTile, aiName.getText().toString());

                aiName.setTextColor(getResources().getColor(R.color.colorPrimary));
                usernameTV.setTextColor(Color.CYAN);
                playerturn = true;
            }
        }
    };


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

        gameBoardValues = new int[3][3];

        freeTilesList.clear();
        for(int i = 0; i < gameBoard.length; i++){
            for(int j = 0; j < gameBoard[i].length; j++){
                if(gameBoardValues[i][j] == 0){
                    Vector<Integer> freeTile = new Vector<>();
                    freeTile.add(i);
                    freeTile.add(j);
                    freeTilesList.add(freeTile);
                }
            }
        }

        decideFirstMover();
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
        if(playerturn && !gameOver) {
            gameBoard[counterI][counterJ].setImageDrawable(getResources().getDrawable(R.drawable.x));
            gameBoardValues[counterI][counterJ] = 1;

            Vector vector = new Vector();
            vector.add(counterI);
            vector.add(counterJ);

            freeTilesList.remove(vector);
            Log.d("removed", "setSymbol: " + vector);

            checkForWin(gameBoardValues, new int[]{counterI, counterJ}, username);

            usernameTV.setTextColor(getResources().getColor(R.color.colorPrimary));
            aiName.setTextColor(Color.CYAN);
            playerturn = false;
            handler.postDelayed(runnable, 2000);

            for(int i = 0; i < freeTilesList.size(); i++){
                Log.d("Listplayer", "setSymbol: " + freeTilesList.toArray()[i]);

            }
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
            handler.post(runnable);
        }
    }

    /**
     * Check board for a winning trio
     * @param gameBoardValues gameboardarray with values
     * @param tile int[] of tile coordinates
     * @param name name of current player
     */
    void checkForWin(int[][] gameBoardValues, int[] tile, String name){
        if(!freeTilesList.isEmpty()) {
            //Horizontal
            int horizontalResult = 0;
            for (int j = 0; j < gameBoardValues[tile[ROW]].length; j++) {
                horizontalResult += gameBoardValues[tile[ROW]][j];
            }
            checkWinner(horizontalResult, name);

            //Vertical
            int verticalResult = 0;
            for (int i = 0; i < gameBoardValues.length; i++) {
                verticalResult += gameBoardValues[i][tile[COLUMN]];
            }
            checkWinner(verticalResult, name);

            //Diagonal
            int diagResult = 0;
            if (tile[ROW] == tile[COLUMN]) {
                for (int i = 0; i < gameBoardValues.length; i++) {
                    diagResult += gameBoardValues[i][i];
                }
            }
            checkWinner(diagResult, name);

            //Anti-diagonal
            int antiDiagResult = 0;
            if (tile[ROW] + tile[COLUMN] == gameBoardValues.length - 1) {
                for (int i = 0; i < gameBoardValues.length; i++) {
                    antiDiagResult += gameBoardValues[i][gameBoardValues.length - i - 1];
                }
            }
            checkWinner(antiDiagResult, name);
        }
        else{
            gameOver = true;
            showGameOverDialog("Nobody");
        }
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
