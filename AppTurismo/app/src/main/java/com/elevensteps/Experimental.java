package com.elevensteps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.elevensteps.model.PuntoInteres;
import com.elevensteps.provider.sqlite.SqliteProvider;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

public class Experimental extends AppCompatActivity implements View.OnClickListener {
    private static String locationProvider = LocationManager.GPS_PROVIDER;
    Spinner PuntoA;
    Spinner PuntoB;
    TextView PuntoALabel;
    TextView PuntoBLabel;
    TextView estado;
    LocationManager locationManager;
    LocationListener locationListener;
    Button start;
    Button stop;
    Button record;
    Button addTrayecto;
    String trayecto;  // Cadena del tipo double,double(token)double,double(token)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experimental);


        PuntoALabel = (TextView) findViewById(R.id.PuntoAlabel);
        PuntoBLabel = (TextView) findViewById(R.id.PuntoBlabel);
        estado = (TextView) findViewById(R.id.textViewEstado);

        PuntoALabel.setText(R.string.experimental);
        PuntoBLabel.setText(R.string.experimental);
        start = (Button) findViewById(R.id.ButtonStart);
        stop = (Button) findViewById(R.id.ButtonStop);
        record = (Button) findViewById(R.id.ButtonAddLocation);
        addTrayecto = (Button) findViewById(R.id.ButtonAddTrayecto);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        record.setOnClickListener(this);
        addTrayecto.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.experimental);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        PuntoA = (Spinner) findViewById(R.id.PuntoA);
        PuntoB = (Spinner) findViewById(R.id.PuntoB);

        SqliteProvider prov = new SqliteProvider(this);
        Collection<PuntoInteres> puntos = prov.retrieveAllPuntoInteres();
        ArrayList<String> puntos2 = new ArrayList();
        for(PuntoInteres p: puntos){
            puntos2.add(p.getNombre());
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter(this, R.layout.list_item_simple, R.id.textview, puntos2);


        PuntoA.setAdapter(adaptador);
        PuntoB.setAdapter(adaptador);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        locationListener= new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                //makeUseOfNewLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };



    }
    private void addLocation(double longitude, double latitude, double altitude){
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        String cadena = "Long: " + df.format(longitude) + "Lat: " + df.format(latitude);
        trayecto += df.format(longitude) + "," + df.format(latitude) + ";";
        estado.setText(cadena);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ButtonStart:
                int pid = android.os.Process.myPid();
                int applicationId = 0;
                PackageManager packageManager = getPackageManager();
                try {
                    applicationId = packageManager.getApplicationInfo("com.elevensteps", PackageManager.GET_META_DATA).uid;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                checkPermission("GPS_PROVIDER", pid, applicationId);
                locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
                estado.setText("ESCUCHANDO");
                break;

            case R.id.ButtonStop:
                locationManager.removeUpdates(locationListener);
                estado.setText("PARADO");
                break;

            case R.id.ButtonAddLocation:
                Location lkl = locationManager.getLastKnownLocation(locationProvider);
                addLocation(lkl.getLongitude(), lkl.getAltitude(), lkl.getAltitude());
                break;

            case R.id.ButtonAddTrayecto:
                String primerPunto = (String) PuntoA.getSelectedItem();
                String segundoPunto = (String) PuntoB.getSelectedItem();

                if(primerPunto.equals(segundoPunto)){
                    Toast.makeText(this, "Los dos puntos de interes seleccionados, deben ser diferentes", Toast.LENGTH_SHORT).show();
                }else{
                    SqliteProvider prov = new SqliteProvider(this);

                    // TODO usar la base de datos para crear la entrada en Conecta
                    // Usar primerPunto, segundoPunto y trayecto(string)
                    // prov.conecta
                }


                break;
        }
    }
}
