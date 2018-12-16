package com.elevensteps;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.elevensteps.model.PuntoInteres;
import com.elevensteps.model.Ruta;
import com.elevensteps.provider.sqlite.SqliteProvider;

import java.util.Collection;

public class GestionRutaEspecificaActivity extends AppCompatActivity implements DescripcionRutasAdapter.ListItemClickListener, View.OnClickListener {

    DescripcionRutasAdapter adapter;
    FloatingActionButton aniadir;
    FloatingActionButton borrar;
    Ruta ruta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);

        Bundle args = getIntent().getExtras();
        String str = args.get("RutaSeleccionada").toString();
        ruta = Utils.getGsonParser().fromJson(str, Ruta.class);
        this.setTitle(ruta.getNombre());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gestión " + ruta.getNombre());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        aniadir = (FloatingActionButton) findViewById(R.id.b_aniadir);
        aniadir.setOnClickListener(this);
        borrar = (FloatingActionButton) findViewById(R.id.b_borrar);
        borrar.setVisibility(View.VISIBLE);
        borrar.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new DescripcionRutasAdapter(this, getBaseContext(), ruta);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.b_aniadir:
                String personJsonString = Utils.getGsonParser().toJson(ruta);
                Intent intent = new Intent(GestionRutaEspecificaActivity.this, GestionAniadirPuntosActivity.class);
                Bundle args = new Bundle();
                args.putString("RutaSeleccionada", personJsonString);
                intent.putExtras(args);
                startActivity(intent);
                break;
            case R.id.b_borrar:
                deleteRutaDialog();
                break;
        }
    }


    @Override
    public void onListItemClick(int itemIndex) {
        Intent intent = new Intent(this, PuntoDeInteresActivity.class);

        PuntoInteres pi = adapter.getItemAt(itemIndex);

        deletePuntoInteresDialog(pi);

    }

    public void deletePuntoInteresDialog(final PuntoInteres puntoInteres) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(puntoInteres.getNombre())
                .setMessage("¿Desea borrar el punto de interés de esta ruta?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                borrarPuntoDeInteres(puntoInteres);
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        builder.create();
        builder.show();
    }


    private void borrarPuntoDeInteres(PuntoInteres puntoInteres) {

        SqliteProvider provider = new SqliteProvider(getBaseContext());
        provider.deletePuntoInteresFromRuta(puntoInteres, ruta);

        Toast toast = Toast.makeText(this, puntoInteres.getNombre() + " BORRADO", Toast.LENGTH_LONG);
        toast.show();

        finish();
        startActivity(getIntent());
    }


    public void deleteRutaDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(ruta.getNombre())
                .setMessage("¿Desea borrar la ruta?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                borrarRuta();
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

        builder.create();
        builder.show();
    }

    private void borrarRuta() {

        SqliteProvider provider = new SqliteProvider(getBaseContext());
        provider.deleteRuta(ruta);

        Toast toast = Toast.makeText(this, ruta.getNombre() + " BORRADA", Toast.LENGTH_LONG);
        toast.show();

        Intent intent = new Intent(this, GestionRutasActivity.class);
        startActivity(intent);
    }
}
