package com.example.tictactoe;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    ThemeChanger themeChanger;

    private String username;
    private int theme;

    TextView usernameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        username = intent.getStringExtra(MainActivity.EXTRA_USERNAME);
        theme = intent.getIntExtra(MainActivity.EXTRA_THEME_ID, theme);

        themeChanger = new ThemeChanger();
        themeChanger.setPrefTheme(theme);
        themeChanger.getTheme(this);

        setContentView(R.layout.activity_game);

        usernameTV = findViewById(R.id.playername);



        usernameTV.setText(username);
    }
}
