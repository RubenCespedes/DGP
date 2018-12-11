package com.elevensteps;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.elevensteps.model.PuntoInteres;
import com.elevensteps.model.Ruta;

public class PuntoDeInteresActivity extends AppCompatActivity implements View.OnClickListener {
    TextView descripcion;
    FloatingActionButton siguiente;
    PuntoInteres puntoInteres;
    int puntoActual;
    Ruta ruta;
    Bundle args;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punto_de_interes);


        descripcion = findViewById(R.id.descripcion);
        descripcion.setText(R.string.cadena_prueba_descripcion);
        siguiente = findViewById(R.id.siguiente_camino);
        siguiente.setOnClickListener(this);

        args = getIntent().getExtras();
        String str2 = args.get("PuntoDeInteres").toString();
        puntoInteres = Utils.getGsonParser().fromJson(str2, PuntoInteres.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Alhambra");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_menu, menu);
        return true;
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

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.siguiente_camino :
                Intent intent = new Intent(this, MapsActivity.class);
                String str = args.get("RutaSeleccionada").toString();
                ruta = Utils.getGsonParser().fromJson(str, Ruta.class);
                puntoActual = args.getInt("PuntoActual");

                Bundle args2 = new Bundle();
                String personJsonString = Utils.getGsonParser().toJson(ruta);
                args2.putString("RutaSeleccionada", personJsonString);
                args2.putInt("PuntoInicial", puntoActual);
                intent.putExtras(args2);
                startActivity(intent);
                break;
        }
    }

}
