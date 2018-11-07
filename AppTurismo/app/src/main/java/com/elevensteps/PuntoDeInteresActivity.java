package com.elevensteps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

public class PuntoDeInteresActivity extends AppCompatActivity {
    TextView descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punto_de_interes);

        Toolbar toolbar = findViewById(R.id.toolbar);
        descripcion = findViewById(R.id.descripcion);
        descripcion.setText(R.string.cadena_prueba_descripcion);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Alhambra");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_menu, menu);
        return true;
    }
}
