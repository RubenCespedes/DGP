package com.elevensteps;

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        botonMonumentos = (ImageButton) findViewById(R.id.imageButtonIconoMonumentos);
        botonMiradores = (ImageButton) findViewById(R.id.imageButtonIconoMiradores);
        botonRestaurantes = (ImageButton) findViewById(R.id.imageButtonIconoRestaurantes);

        botonMonumentos.setOnClickListener(this);
        botonMiradores.setOnClickListener(this);
        botonRestaurantes.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButtonIconoMonumentos:
                FiltradoPorTipos(Filtro.MONUMENTOS);
                break;
            case R.id.imageButtonIconoMiradores:
                FiltradoPorTipos(Filtro.MIRADORES);
                break;
            case R.id.imageButtonIconoRestaurantes:
                FiltradoPorTipos(Filtro.RESTAURANTES);
                break;

        }
    }

    private void FiltradoPorTipos(Filtro tipo) {
        Intent intent = new Intent(this, MenuRutasActivity.class);
        intent.putExtra("TipoFiltro", tipo.name());
        startActivity(intent);
    }

}
