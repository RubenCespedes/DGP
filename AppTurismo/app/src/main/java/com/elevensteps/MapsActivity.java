package com.elevensteps;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener {

    private GoogleMap mMap;
    private Ruta ruta;
    SqliteProvider provider;
    private static final float ZOOM = 18;
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
        ruta = Utils.getGsonParser().fromJson(str, Ruta.class);
        ArrayList<PuntoInteres> puntos = (ArrayList) provider.retrieveCamino(ruta);

        LatLng punto1 = new LatLng(puntos.get(0).getLat(), puntos.get(0).getLng());
        LatLng punto2 = new LatLng(puntos.get(1).getLat(), puntos.get(1).getLng());
        mMap.addMarker(new MarkerOptions().position(punto1).title(puntos.get(0).getNombre()));
        mMap.addMarker(new MarkerOptions().position(punto2).title(puntos.get(1).getNombre()));
        LatLng ptoMedio = new LatLng((punto1.latitude+punto2.latitude)/2,(punto1.longitude+punto2.longitude)/2);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ptoMedio, ZOOM-2));
        /*polyline1 = mMap.addPolyline(new PolylineOptions(). clickable(true).add(
                new LatLng(37.175741, -3.597475),
                new LatLng(37.176736, -3.598078),
                new LatLng(37.176661, -3.598375),
                new LatLng(37.176935, -3.598539))
        );
        polyline1.setColor(0xffff0000);
        polyline1.setTag("A");
        polyline1.setClickable(true);
        polyline1.setWidth(20);
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