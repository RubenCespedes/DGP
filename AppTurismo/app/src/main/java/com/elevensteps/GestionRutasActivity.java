package com.elevensteps;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.elevensteps.model.Ruta;

public class GestionRutasActivity extends AppCompatActivity implements RutasAdapter.ListItemClickListener, View.OnClickListener {

    RutasAdapter rutasAdapter;
    FloatingActionButton aniadir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gestión de Rutas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerViewRutas = findViewById(R.id.recyclerView);

        aniadir = findViewById(R.id.b_aniadir);
        aniadir.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewRutas.setLayoutManager(linearLayoutManager);
        recyclerViewRutas.setHasFixedSize(true);

        rutasAdapter = new RutasAdapter(this, getBaseContext(), "Todas");
        recyclerViewRutas.setAdapter(rutasAdapter);
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
    public void onListItemClick(int itemIndex) {

        Intent intent = new Intent(GestionRutasActivity.this, GestionRutaEspecificaActivity.class);

        Ruta ruta = rutasAdapter.getElement(itemIndex);
        String personJsonString = Utils.getGsonParser().toJson(ruta);

        Bundle args = new Bundle();
        args.putString("RutaSeleccionada", personJsonString);
        intent.putExtras(args);

        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.b_aniadir) {
            Intent intent = new Intent(this, CrearRutaActivity.class);
            startActivity(intent);
        }
    }
}
