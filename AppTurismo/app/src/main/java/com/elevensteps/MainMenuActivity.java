package com.elevensteps;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton botonMonumentos;
    private ImageButton botonMiradores;
    private ImageButton botonRestaurantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        botonMonumentos = (ImageButton) findViewById(R.id.imageButtonIconoMonumentos);
        botonMiradores = (ImageButton) findViewById(R.id.imageButtonIconoMiradores);
        botonRestaurantes = (ImageButton) findViewById(R.id.imageButtonIconoRestaurantes);

        botonMonumentos.setOnClickListener(this);
        botonMiradores.setOnClickListener(this);
        botonRestaurantes.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.titulo_elegir_categoria);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButtonIconoMonumentos:
                FiltradoPorTipos(Filtro.Cultura);
                break;
            case R.id.imageButtonIconoMiradores:
                FiltradoPorTipos(Filtro.Ocio);
                break;
            case R.id.imageButtonIconoRestaurantes:
                FiltradoPorTipos(Filtro.Gastronomia);
                break;
        }
    }

    private void FiltradoPorTipos(Filtro tipo) {
        Context context = MainMenuActivity.this;

        Class destinationActivity = SeleccionRutaActivity.class;

        Intent intent = new Intent(context, destinationActivity);

        intent.putExtra("TipoFiltro", tipo.name());
        intent.putExtra("ColorFiltro", tipo.getColor());
        startActivity(intent);
    }

}
