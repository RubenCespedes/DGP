package com.elevensteps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.elevensteps.model.Ruta;

public class DescripcionRutaActivity extends AppCompatActivity implements DescripcionRutasAdapter.ListItemClickListener  {
    DescripcionRutasAdapter mAdapter;

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_ruta);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mRecyclerView = findViewById(R.id.rv_puntosinteres);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        Bundle args = getIntent().getExtras();
        String str = args.get("RutaSeleccionada").toString();
        Ruta ruta= Utils.getGsonParser().fromJson(str, Ruta.class);

        Log.d("MiDebug", ruta.getNombre());

        mAdapter = new DescripcionRutasAdapter( this, getBaseContext(), ruta);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public void onListItemClick(int itemIndex) {

    }
}
