package com.elevensteps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elevensteps.model.PuntoInteres;
import com.elevensteps.model.Ruta;
import com.elevensteps.provider.sqlite.SqliteProvider;

public class CrearRutaActivity extends AppCompatActivity implements View.OnClickListener {

    Button button_crear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        button_crear = (Button) findViewById(R.id.button_crear);
        button_crear.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Crear Ruta");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        if (view.getId() == R.id.button_crear) {
            createRuta();
        }
    }


    public void createRuta() {

        EditText nombreText = (EditText) findViewById(R.id.crear_nombre);
        String nombre = nombreText.getText().toString();

        Ruta ruta = Ruta.builder()
                .nombre(nombre)
                .descripcion("PRUEBA")
                .imagen("PRUEBA")
                .categoria("Ocio")
                .nivelAccesibilidad(1.0)
                .nivelCoste(1.0)
                .build();

        SqliteProvider prov = new SqliteProvider(this);

        boolean result = prov.insertRuta(ruta);

        if(result) {
            Toast toast = Toast.makeText(this, "RUTA INTRODUCIDA", Toast.LENGTH_LONG);
            toast.show();

            Intent intent = new Intent(this, GestionRutaEspecificaActivity.class);
            String personJsonString = Utils.getGsonParser().toJson(ruta);

            Bundle args = new Bundle();
            args.putString("RutaSeleccionada", personJsonString);
            intent.putExtras(args);

            startActivity(intent);
        }
        else {
            nombreText.setText("");

            Toast toast = Toast.makeText(this, "ESE RUTA YA EXISTE", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}