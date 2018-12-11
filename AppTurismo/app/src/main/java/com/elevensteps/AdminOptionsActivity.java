
package com.elevensteps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AdminOptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button aniadir_ruta;
    private Button aniadir_punto_interes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);

        aniadir_ruta = (Button) findViewById(R.id.b_gestionar_rutas);
        aniadir_punto_interes = (Button) findViewById(R.id.b_gestionar_puntos_interes);

        aniadir_ruta.setOnClickListener(this);
        aniadir_punto_interes.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Opciones Administrador");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        Context context = AdminOptionsActivity.this;

        Class destinationActivity = AdminOptionsActivity.class;

        switch(id) {
            case R.id.b_gestionar_rutas:
                destinationActivity = GestionRutasActivity.class;
                break;
            case R.id.b_gestionar_puntos_interes:
                destinationActivity = GestionPuntosDeInteresActivity.class;
                break;
        }

        Intent intent = new Intent(context, destinationActivity);
        startActivity(intent);


    }
}
