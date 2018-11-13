package com.elevensteps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class activity_seleccion_ruta extends AppCompatActivity {

    // Creamos el modelo fake
    String [] mFakeNames = {
            "MONUMENTOS HISTORICOS",
            "MUSEOS DE ARTE",
            "PARQUES DE CIENCIAS",
            "MIRADORES",
            "BARES",
            "RESTAURANTES"
    };

    RutasAdapter mAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_ruta);

        mRecyclerView = findViewById(R.id.rv_rutas);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RutasAdapter(mFakeNames);
        mRecyclerView.setAdapter(mAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idOfItemSelected = item.getItemId();

        if(idOfItemSelected == R.id.backArrow){
            // Intent a la pantalla anterior
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
