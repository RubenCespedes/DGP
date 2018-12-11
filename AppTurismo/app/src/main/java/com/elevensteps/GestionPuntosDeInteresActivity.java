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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.elevensteps.model.PuntoInteres;
import com.elevensteps.model.Ruta;
import com.elevensteps.provider.sqlite.SqliteProvider;

public class GestionPuntosDeInteresActivity extends AppCompatActivity implements DescripcionRutasAdapter.ListItemClickListener, View.OnClickListener {

    DescripcionRutasAdapter adapter;
    FloatingActionButton aniadir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gestionar puntos de interés");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        aniadir = (FloatingActionButton) findViewById(R.id.b_aniadir);
        aniadir.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new DescripcionRutasAdapter(this, getBaseContext(), null);
        recyclerView.setAdapter(adapter);
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
        Intent intent = new Intent(this, PuntoDeInteresActivity.class);

        PuntoInteres pi = adapter.getItemAt(itemIndex);

        deleteDialog(pi);

    }


    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.b_aniadir) {
            Intent intent = new Intent(this, CrearPuntoDeInteresActivity.class);
            startActivity(intent);
        }
    }


    public void deleteDialog(final PuntoInteres puntoInteres) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(puntoInteres.getNombre())
                .setMessage("¿Desea borrar el punto de interés?")
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
        provider.deletePuntoInteres(puntoInteres);

        Toast toast = Toast.makeText(this, puntoInteres.getNombre() + " BORRADO", Toast.LENGTH_LONG);
        toast.show();

        finish();
        startActivity(getIntent());
    }
}
