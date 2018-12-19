package com.elevensteps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.elevensteps.model.Ruta;

public class SeleccionRutaActivity extends AppCompatActivity implements RutasAdapter.ListItemClickListener {

    RutasAdapter mAdapter;
    String tipoNombre;
    int tipoColor;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_seleccion_ruta );

        mRecyclerView = findViewById( R.id.rv_rutas );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager( linearLayoutManager );

        mRecyclerView.setHasFixedSize( true );

        Bundle extra= getIntent().getExtras();
        this.tipoNombre = extra.getString( "TipoFiltro" , "");
        this.tipoColor = extra.getInt("ColorFiltro", Color.WHITE);

        mAdapter = new RutasAdapter( this, getBaseContext(), tipoNombre);
        mRecyclerView.setAdapter( mAdapter );

        Toolbar toolbar = findViewById( R.id.toolbar );
        toolbar.setBackgroundColor(tipoColor);
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( String.format( getResources().getString( R.string.titulo_seleccion_ruta ), tipoNombre) );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
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
    public void onListItemClick(int itemIndex) {
        Context context = SeleccionRutaActivity.this;

        Class DescriptionActivity = DescripcionRutaActivity.class;

        Intent intent = new Intent(context, DescriptionActivity);

        Ruta ruta = mAdapter.getElement(itemIndex);

        Bundle args = new Bundle();
        String personJsonString = Utils.getGsonParser().toJson(ruta);
        args.putString("RutaSeleccionada", personJsonString);
        args.putInt("TipoColor", tipoColor);
        intent.putExtras(args);

        startActivity(intent);
    }
}
