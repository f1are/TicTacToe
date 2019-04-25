package com.example.tictactoe;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private String username;

    TextView usernameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        username = intent.getStringExtra(MainActivity.EXTRA_USERNAME);

        ThemeChanger.getTheme(this);
        setContentView(R.layout.activity_game);

        usernameTV = findViewById(R.id.playername);



        usernameTV.setText(username);
    }
}
