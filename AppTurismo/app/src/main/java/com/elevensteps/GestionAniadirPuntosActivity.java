package com.elevensteps;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.elevensteps.model.PuntoInteres;
import com.elevensteps.model.Ruta;
import com.elevensteps.provider.sqlite.SqliteProvider;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class GestionAniadirPuntosActivity extends AppCompatActivity implements AniadirPuntosInteresAdapter.ListItemClickListener, View.OnClickListener {

    AniadirPuntosInteresAdapter adapter;
    FloatingActionButton aceptar;
    Ruta ruta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);

        Bundle args = getIntent().getExtras();
        String str = args.get("RutaSeleccionada").toString();
        ruta = Utils.getGsonParser().fromJson(str, Ruta.class);
        this.setTitle(ruta.getNombre());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Añadir puntos de interés");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        FloatingActionButton aniadir = (FloatingActionButton) findViewById(R.id.b_aniadir);
        aniadir.setVisibility(View.INVISIBLE);

        aceptar = (FloatingActionButton) findViewById(R.id.b_aceptar);
        aceptar.setVisibility(View.VISIBLE);
        aceptar.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new AniadirPuntosInteresAdapter(this, getBaseContext(), ruta);
        recyclerView.setAdapter(adapter);

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

        if (view.getId() == R.id.b_aceptar) {
            SqliteProvider provider = new SqliteProvider(this);
            ArrayList<PuntoInteres> puntosInteres = adapter.getPuntosInteresSeleccionados();

            for (PuntoInteres pi: puntosInteres) {
                provider.asociarRutaConPunto(ruta.getNombre(), pi.getNombre());
            }

            Intent intent = new Intent(this, GestionRutaEspecificaActivity.class);
            String personJsonString = Utils.getGsonParser().toJson(ruta);
            Bundle args = new Bundle();
            args.putString("RutaSeleccionada", personJsonString);
            intent.putExtras(args);
            startActivity(intent);
        }
    }

    @Override
    public void onListItemClick(int itemIndex) {
    }
}
