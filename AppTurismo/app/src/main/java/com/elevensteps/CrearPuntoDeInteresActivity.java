package com.elevensteps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.elevensteps.model.PuntoInteres;
import com.elevensteps.model.Ruta;
import com.elevensteps.provider.sqlite.SqliteProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CrearPuntoDeInteresActivity extends AppCompatActivity  {

    Spinner spinner;
    String ruta_seleccionada;
    private static String NO_ASOCIAR_RUTA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_punto_de_interes);

        spinner = findViewById(R.id.spinner);
        NO_ASOCIAR_RUTA = getResources().getString(R.string.ruta_no_seleccionada);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.titulo_crear_punto_de_interes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        SqliteProvider prov = new SqliteProvider(this);
        Collection<Ruta> rutas = prov.retrieveAllRutas();
        ArrayList<String> rutas2 = new ArrayList();
        rutas2.add(NO_ASOCIAR_RUTA);
        for(Ruta r: rutas){
            rutas2.add(r.getNombre());
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter(this, R.layout.list_item_simple,R.id.textview, rutas2);
        spinner.setAdapter(adaptador);


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


    public void createPI(View view) {

        EditText nombreText = (EditText) findViewById(R.id.pi_nombre);
        String nombre = nombreText.getText().toString();
        ruta_seleccionada = (String) spinner.getSelectedItem();

        

        PuntoInteres puntoInteres = PuntoInteres.builder()
                .nombre(nombre)
                .lng(100.0)
                .lat(100.0)
                .texto("PRUEBA")
                .horario("PRUEBA")
                .url("PRUEBA")
                .texto("PRUEBA")
                .direccion("PRUEBA")
                .precio(100.0)
                .valoracion(2.0)
                .audio("PRUEBA")
                .imagen("PRUEBA")
                .video("PRUEBA")
                .build();



        SqliteProvider prov = new SqliteProvider(this);
        prov.insertPuntoInteres(puntoInteres);

        if(!ruta_seleccionada.equals(NO_ASOCIAR_RUTA)){
            prov.asociarRutaConPunto(ruta_seleccionada, nombre);
        }

        Toast toast = Toast.makeText(this, "PUNTO DE INTERES INTRODUCIDO", Toast.LENGTH_LONG);
        toast.show();

        Intent i = new Intent(this, AdminOptionsActivity.class);
        startActivity(i);
    }


}

