package com.elevensteps;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class activity_seleccion_ruta extends AppCompatActivity implements RutasAdapter.ListItemClickListener {

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

        mAdapter = new RutasAdapter(mFakeNames, this);
        mRecyclerView.setAdapter(mAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extra= getIntent().getExtras();
        String titulo = extra.get("TipoFiltro").toString();
        Toast.makeText(this, titulo, Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle(titulo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backArrow:
                onBackPressed();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onListItemClick(int itemIndex) {
        Context context = activity_seleccion_ruta.this;

        Class destinationActivity = PuntoDeInteresActivity.class;

        Intent intent = new Intent(context, destinationActivity);

        //intent.putExtra("TipoFiltro", tipo.name());
        startActivity(intent);
    }
}
