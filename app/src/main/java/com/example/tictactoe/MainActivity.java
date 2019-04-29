package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class  MainActivity extends AppCompatActivity {


    String username = "Player";
    public static final String EXTRA_USERNAME = "com.example.tictactoe.USERNAME";

    TextView welcomeText;

    Button changeNameButton;
    Button startGameButton;

    CheckBox themeCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ThemeChanger.getTheme(this);
        setContentView(R.layout.activity_main);


        initializeGUIComponents();

        buildAlertDialog();


    }

    /**
     * Creates AlertDialog for Name Input
     */
    @SuppressLint("InflateParams")
    void buildAlertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


        LayoutInflater inflater = getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.alert_view, null));

        builder.setMessage("Welcome Player, enter your name")
                .setTitle("Your Name")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog dia = (Dialog) dialog;
                        EditText nameInput;

                        nameInput = dia.findViewById(R.id.username);
                        username = nameInput.getText().toString();
                        welcomeText.setText(String.format(getResources().getString(R.string.welcome_text),username));
                    }
                });
        builder.create().show();
    }

    /**
     * Initializes GUICompoents (IDs and so on)
     */
    void initializeGUIComponents(){


        welcomeText = findViewById(R.id.welcomeText);

        themeCheckBox = findViewById(R.id.changeTheme_Check);

        themeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(themeCheckBox.isChecked()){
                    ThemeChanger.setPrefTheme(1);
                }
                else{
                    ThemeChanger.setPrefTheme(0);
                }

                MainActivity.this.recreate();
            }
        });

        changeNameButton = findViewById(R.id.changeName_Button);
        changeNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildAlertDialog();
            }
        });

        startGameButton = findViewById(R.id.startGame_Button);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra(EXTRA_USERNAME, username);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });
    }
}
