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
import android.widget.Toast;

import com.elevensteps.model.PuntoInteres;
import com.elevensteps.model.Ruta;
import com.elevensteps.provider.sqlite.SqliteProvider;

import java.util.Collection;

public class PuntoDeInteresActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView descripcion;
    private FloatingActionButton siguiente;
    private PuntoInteres puntoInteres;
    private int puntoActual;
    private Ruta ruta;
    private Bundle args;
    SqliteProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punto_de_interes);


        descripcion = findViewById(R.id.descripcion);
        descripcion.setText(R.string.cadena_prueba_descripcion);
        siguiente = findViewById(R.id.siguiente_camino);
        provider = new SqliteProvider(this);
        siguiente.setOnClickListener(this);
        args = getIntent().getExtras();
        String str2 = args.get("PuntoDeInteres").toString();
        puntoInteres = Utils.getGsonParser().fromJson(str2, PuntoInteres.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(puntoInteres.getNombre());
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
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.siguiente_camino:
                String str = args.get("RutaSeleccionada").toString();
                ruta = Utils.getGsonParser().fromJson(str, Ruta.class);
                puntoActual = args.getInt("PuntoActual");
                Collection<PuntoInteres> puntosBD = provider.retrieveCamino(ruta);
                if (puntoActual == puntosBD.size() - 2) {
                    Intent intent = new Intent(this, MainMenuActivity.class);
                    Toast.makeText(this, "¡Ruta finalizada! Esperamos que haya sido de su agrado", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, MapsActivity.class);
                    String str2 = args.get("RutaSeleccionada").toString();
                    ruta = Utils.getGsonParser().fromJson(str2, Ruta.class);
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
}
