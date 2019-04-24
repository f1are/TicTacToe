package com.example.tictactoe;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    EditText nameInputString;
    TextView welcomeText;

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

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_ENTER:
                String formattedText = String.format(getResources().getString(R.string.welcome_text), nameInputString.getText());

                welcomeText.setText(formattedText);

        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * Creates AlertDialog for Name Input
     */
    void buildAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.alert_view, initnull));

        builder.setMessage("Welcome Player, enter your name").setTitle("Your Name");

        AlertDialog dialog = builder.create();
        dialog.show();
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
    }
}
