package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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


    String username;
    TextView welcomeText;

    Button changeNameButton;

    CheckBox themeCheckBox;
    private static final int DARK_THEME = 1;
    private static int PREF_THEME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(PREF_THEME != DARK_THEME){
            setTheme(R.style.AppTheme);
        }
        else{
            setTheme(R.style.Theme_AppCompat);
        }

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
                    PREF_THEME = 1;
                }
                else{
                    PREF_THEME = 0;
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
    }
}
