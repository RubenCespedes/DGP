package com.elevensteps;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.elevensteps.model.Ruta;

public class DescripcionRutaActivity extends AppCompatActivity implements DescripcionRutasAdapter.ListItemClickListener, View.OnClickListener  {
    DescripcionRutasAdapter mAdapter;
    FloatingActionButton next;
    Ruta ruta;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_ruta);

        mRecyclerView = findViewById(R.id.rv_puntosinteres);
        next = findViewById(R.id.floatingActionButton2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        Bundle args = getIntent().getExtras();
        String str = args.get("RutaSeleccionada").toString();
        ruta = Utils.getGsonParser().fromJson(str, Ruta.class);
        this.setTitle(ruta.getNombre());

        Log.d("MiDebug", ruta.getNombre());
        next.setOnClickListener(this);
        mAdapter = new DescripcionRutasAdapter( this, getBaseContext(), ruta);
        mRecyclerView.setAdapter(mAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.floatingActionButton2:
                Intent intent = new Intent(this, MapsActivity.class);

                Bundle args = new Bundle();
                String personJsonString = Utils.getGsonParser().toJson(ruta);
                args.putString("RutaSeleccionada", personJsonString);
                intent.putExtras(args);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onListItemClick(int itemIndex) {

    }
}
