package com.elevensteps;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.elevensteps.model.Ruta;

public class activity_seleccion_ruta extends AppCompatActivity implements RutasAdapter.ListItemClickListener {

    RutasAdapter mAdapter;
    String tipo;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_ruta);

        mRecyclerView = findViewById(R.id.rv_rutas);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.setHasFixedSize(true);



        Bundle extra= getIntent().getExtras();
        String tipo = extra.get("TipoFiltro").toString();

        mAdapter = new RutasAdapter( this, getBaseContext(), tipo);
        mRecyclerView.setAdapter(mAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Toast.makeText(this, tipo, Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle(tipo);
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

        Class DescriptionActivity = DescripcionRutaActivity.class;

        Intent intent = new Intent(context, DescriptionActivity);

        Ruta ruta = mAdapter.getElement(itemIndex);

        Log.d("MiDebug", ruta.getNombre());

        Bundle args = new Bundle();
        String personJsonString = Utils.getGsonParser().toJson(ruta);
        args.putString("RutaSeleccionada", personJsonString);
        intent.putExtras(args);

        startActivity(intent);
    }
}
