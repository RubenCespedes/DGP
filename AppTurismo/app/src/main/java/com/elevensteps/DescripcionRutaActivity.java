package com.elevensteps;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elevensteps.model.PuntoInteres;
import com.elevensteps.model.Ruta;
import com.elevensteps.provider.sqlite.SqliteProvider;

import java.util.Collection;

public class DescripcionRutaActivity extends AppCompatActivity implements DescripcionRutasAdapter.ListItemClickListener, View.OnClickListener  {
    DescripcionRutasAdapter mAdapter;
    FloatingActionButton next;
    Ruta ruta;
    RecyclerView mRecyclerView;
    SqliteProvider provider;
    ImageView imagen_ruta;
    private String tituloStr;
    private int tipoColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_ruta);

        mRecyclerView = findViewById(R.id.rv_puntosinteres);
        next = findViewById(R.id.ContinueToRoute);
        provider = new SqliteProvider(this);
        imagen_ruta = findViewById(R.id.iv_puntosinteres);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        Bundle args = getIntent().getExtras();
        tituloStr = args.get("RutaSeleccionada").toString();
        tipoColor = args.getInt("TipoColor");

        ruta = Utils.getGsonParser().fromJson(tituloStr, Ruta.class);
        this.setTitle(ruta.getNombre());

        next.setOnClickListener(this);
        mAdapter = new DescripcionRutasAdapter( this, getBaseContext(), ruta);
        mRecyclerView.setAdapter(mAdapter);

        Context context = imagen_ruta.getContext();
        int id = context.getResources().getIdentifier( ruta.getImagen(), "drawable", context.getPackageName() );
        imagen_ruta.setImageResource(id);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(tipoColor);
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
            case R.id.ic_admin:
                Intent i = new Intent(this, AdminOptionsActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.ContinueToRoute:
                Collection<PuntoInteres> puntosBD = provider.retrieveCamino(ruta);
                int contador = 0;
                for (PuntoInteres pto : puntosBD) {
                    if (pto.hasCoordinates())
                        contador++;
                }
                if (contador>=2) {
                    Intent intent = new Intent(this, MapsActivity.class);
                    Bundle args = new Bundle();
                    int punto_inicio_ruta = 0;
                    String personJsonString = Utils.getGsonParser().toJson(ruta);
                    args.putString("RutaSeleccionada", personJsonString);
                    args.putString("NombreRuta", ruta.getNombre());
                    args.putInt("TipoColor", tipoColor);
                    args.putInt("PuntoInicial", punto_inicio_ruta);
                    intent.putExtras(args);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Las coordenadas de los puntos aún no han sido cargadas", Toast.LENGTH_LONG).show();
                }

                break;

        }
    }

    @Override
    public void onListItemClick(int itemIndex) {
        Intent intent = new Intent(this, PuntoDeInteresActivity.class);

        Bundle args = new Bundle();
        PuntoInteres pi = mAdapter.getItemAt(itemIndex);

        String arg = Utils.getGsonParser().toJson(pi);
        args.putString("PuntoInteres", arg);
        args.putInt("TipoColor", tipoColor);
        args.putString("EnRuta", "No");
        intent.putExtras(args);
        startActivity(intent);

    }
}
