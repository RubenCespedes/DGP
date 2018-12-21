package com.elevensteps;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnUserMode = findViewById(R.id.btnUserMode);
        btnUserMode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(
                        new Intent(MainActivity.this, MainMenuActivity.class)
                );
            }
        });

        Button btnAdminMode = findViewById(R.id.btnAdminMode);
        btnAdminMode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(
                        new Intent(MainActivity.this, AdminOptionsActivity.class)
                );
            }
        });

        ImageButton ibAppLogo = findViewById(R.id.ibAppLogo);
        ibAppLogo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(
                        new Intent(MainActivity.this, AboutActivity.class)
                );
            }
        });
    }



}
