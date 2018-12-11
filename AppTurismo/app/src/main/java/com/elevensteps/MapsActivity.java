package com.elevensteps;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
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
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.ArrayList;
import java.util.Collection;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, View.OnClickListener  {

    private GoogleMap mMap;
    private Ruta ruta;
    private FloatingActionButton sig_punto;
    private ArrayList<PuntoInteres> puntos = new ArrayList<PuntoInteres>();
    private ArrayList<Marker> marcadores = new ArrayList<Marker>();
    private PuntoInteres punto_actual, punto_siguiente;
    private int punto_inicial, punto_destino;
    SqliteProvider provider;
    private static final float ZOOM = 14;
    LatLng isaCatolica = new LatLng(37.175741, -3.597475);
    LatLng catedral = new LatLng(37.176596, -3.599044);
    Marker plazaIsa, catedraal;
    Polyline polyline1, polyline2;

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
        punto_inicial = args.getInt("PuntoInicial");
        punto_destino = punto_inicial+1;
        ruta = Utils.getGsonParser().fromJson(str, Ruta.class);
        Collection<PuntoInteres> puntosBD = provider.retrieveCamino(ruta);
        for( PuntoInteres pto : puntosBD) {
            if (pto.hasCoordinates()) {
                puntos.add(pto);
                LatLng punto = new LatLng(pto.getLat(),pto.getLng());
                marcadores.add(mMap.addMarker(new MarkerOptions().position(punto).title(pto.getNombre())));
            }
        }
        punto_actual = puntos.get(punto_inicial);
        punto_siguiente = puntos.get(punto_destino);
        marcadores.get(punto_inicial).setIcon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        marcadores.get(punto_destino).setIcon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        marcadores.get(punto_inicial).setTitle("Punto inicio: " + marcadores.get(punto_inicial).getTitle());
        marcadores.get(punto_inicial).showInfoWindow();
        marcadores.get(punto_destino).setTitle("Punto destino: " + marcadores.get(punto_destino).getTitle());

        double latitud_zoom = (punto_actual.getLat() + punto_siguiente.getLat()) / 2;
        double longitud_zoom = (punto_actual.getLng() + punto_siguiente.getLng()) / 2;
        LatLng zoom = new LatLng (latitud_zoom, longitud_zoom);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( zoom, ZOOM));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mapa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sig_punto.setOnClickListener(this);
        polyline1 = mMap.addPolyline(new PolylineOptions(). clickable(true).add(
                new LatLng (punto_actual.getLat(), punto_actual.getLng()),
                new LatLng(punto_siguiente.getLat(), punto_siguiente.getLng())));
        polyline1.setColor(0xff0000ff);
        //polyline1.setTag("A");
        polyline1.setClickable(true);
        polyline1.setWidth(20);
        /*
        polyline2= mMap.addPolyline(new PolylineOptions(). clickable(true).add(
                new LatLng(37.175741, -3.597475),
                new LatLng(37.175177, -3.598401),
                new LatLng(37.175753, -3.599112),
                new LatLng(37.176140, -3.599656))
        );
        polyline2.setColor(0x800000ff);
        polyline2.setTag("B");
        polyline2.setClickable(true);
        polyline2.setWidth(10);
        googleMap.setOnPolylineClickListener(this);
        plazaIsa = mMap.addMarker(new MarkerOptions().position(isaCatolica).title("Plaza Isabel La Católica").snippet("Plaza con estatua de Isabel la Católica"));
        catedraal = mMap.addMarker(new MarkerOptions().position(catedral).title("Catedral de Granada").snippet("Catedral del siglo XVIII"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(isaCatolica, ZOOM));*/
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
                args.putString("PuntoDeInteres", personJsonString2);
                args.putInt("PuntoActual", punto_destino);
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
                polyline2.setColor(0x800000ff);
                polyline2.setWidth(10);
                Toast.makeText(this, "Ruta con accesibilidad para todos los públicos", Toast.LENGTH_SHORT).show();
                break;
            case "B":
                polyline1.setColor(0x80ff0000);
                polyline1.setWidth(10);
                polyline2.setColor(0xff0000ff);
                polyline2.setWidth(20);
                Toast.makeText(this, "Ruta no accesible para personas con movilidad reducida", Toast.LENGTH_SHORT).show();
        }
    }


}