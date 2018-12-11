package com.elevensteps;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;


import com.elevensteps.model.PuntoInteres;
import com.elevensteps.model.Ruta;
import com.elevensteps.provider.sqlite.SqliteProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Collection;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, View.OnClickListener  {


    private static final float ZOOM = 14;
    private GoogleMap mMap;
    private Ruta ruta;
    private FloatingActionButton sig_punto;
    private ArrayList<PuntoInteres> puntos = new ArrayList<PuntoInteres>();
    private ArrayList<Marker> marcadores = new ArrayList<Marker>();
    private PuntoInteres punto_actual, punto_siguiente;
    private int punto_inicial_int, punto_destino_int;
    private SqliteProvider provider;
    Polyline polyline1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        provider = new SqliteProvider(this);
        sig_punto = findViewById(R.id.sig_punto);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Bundle args = getIntent().getExtras();
        String str = args.get("RutaSeleccionada").toString();
        punto_inicial_int = args.getInt("PuntoInicial");
        punto_destino_int = punto_inicial_int+1;
        ruta = Utils.getGsonParser().fromJson(str, Ruta.class);
        Collection<PuntoInteres> puntosBD = provider.retrieveCamino(ruta);

        for( PuntoInteres pto : puntosBD) {
            puntos.add(pto);
            LatLng punto = new LatLng(pto.getLat(),pto.getLng());
            marcadores.add(mMap.addMarker(new MarkerOptions().position(punto).title(pto.getNombre())));

        }
        punto_actual = puntos.get(punto_inicial_int);
        punto_siguiente = puntos.get(punto_destino_int);
        marcadores.get(punto_inicial_int).setIcon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        marcadores.get(punto_destino_int).setIcon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        marcadores.get(punto_inicial_int).setTitle("Punto inicio: " + marcadores.get(punto_inicial_int).getTitle());
        marcadores.get(punto_inicial_int).showInfoWindow();
        marcadores.get(punto_destino_int).setTitle("Punto destino: " + marcadores.get(punto_destino_int).getTitle());

        double latitud_zoom = (punto_actual.getLat() + punto_siguiente.getLat()) / 2;
        double longitud_zoom = (punto_actual.getLng() + punto_siguiente.getLng()) / 2;
        LatLng zoom = new LatLng (latitud_zoom, longitud_zoom);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( zoom, ZOOM));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mapa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        polyline1 = mMap.addPolyline(new PolylineOptions(). clickable(true).add(
                new LatLng (punto_actual.getLat(), punto_actual.getLng()),
                new LatLng(punto_siguiente.getLat(), punto_siguiente.getLng())));
        polyline1.setColor(0xff0000ff);
        polyline1.setClickable(true);
        polyline1.setWidth(20);

        sig_punto.setOnClickListener(this);
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
        switch(view.getId()){
            case R.id.sig_punto :

                Intent intent = new Intent(this, PuntoDeInteresActivity.class);
                Bundle args = new Bundle();

                String personJsonString = Utils.getGsonParser().toJson(ruta);
                String personJsonString2 = Utils.getGsonParser().toJson(punto_siguiente);
                args.putString("RutaSeleccionada", personJsonString);
                args.putString("PuntoInteres", personJsonString2);
                args.putInt("PuntoActual", punto_destino_int);
                intent.putExtras(args);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        switch(polyline.getTag().toString()) {
            case "A":
                polyline1.setColor(0xffff0000);
                polyline1.setWidth(20);
                //polyline2.setColor(0x800000ff);
                //polyline2.setWidth(10);
                Toast.makeText(this, "Ruta con accesibilidad para todos los públicos", Toast.LENGTH_SHORT).show();
                break;
            case "B":
                polyline1.setColor(0x80ff0000);
                polyline1.setWidth(10);
                //polyline2.setColor(0xff0000ff);
                //polyline2.setWidth(20);
                Toast.makeText(this, "Ruta no accesible para personas con movilidad reducida", Toast.LENGTH_SHORT).show();
        }
    }


}