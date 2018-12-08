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
import com.elevensteps.provider.sqlite.SqliteProvider;

public class CrearPuntoDeInteresActivity extends AppCompatActivity implements View.OnClickListener {

    Button button_crear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        button_crear = (Button) findViewById(R.id.button_crear);
        button_crear.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Crear punto de interés");
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
            createPI();
        }
    }


    public void createPI() {

        EditText nombreText = (EditText) findViewById(R.id.crear_nombre);
        String nombre = nombreText.getText().toString();

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

        boolean result = prov.insertPuntoInteres(puntoInteres);

        if(result) {
            Toast toast = Toast.makeText(this, "PUNTO DE INTERES INTRODUCIDO", Toast.LENGTH_LONG);
            toast.show();

            Intent i = new Intent(this, GestionPuntosDeInteresActivity.class);
            startActivity(i);
        }
        else {
            nombreText.setText("");

            Toast toast = Toast.makeText(this, "ESE PUNTO DE INTERÉS YA EXISTE", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}