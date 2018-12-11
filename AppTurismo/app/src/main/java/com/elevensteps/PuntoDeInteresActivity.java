package com.elevensteps;

<<<<<<< HEAD
import android.content.Intent;
=======
import android.media.MediaPlayer;
import android.net.Uri;
>>>>>>> 4b86fdd0060987f12e5c5181f65c3b60ede90f53
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
<<<<<<< HEAD
import android.widget.Toast;

import com.elevensteps.model.PuntoInteres;
import com.elevensteps.model.Ruta;
import com.elevensteps.provider.sqlite.SqliteProvider;

import java.util.Collection;

public class PuntoDeInteresActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView descripcion;
    private FloatingActionButton siguiente;
    private PuntoInteres puntoInteres;
    private int puntoActual;
    private Ruta ruta;
    private Bundle args;
    SqliteProvider provider;
=======
import android.widget.VideoView;

import com.elevensteps.model.PuntoInteres;

public class PuntoDeInteresActivity extends AppCompatActivity implements View.OnClickListener {
    TextView descripcion;
    VideoView video;
    PuntoInteres puntoInteres;
    MediaPlayer mediaPlayer;
    FloatingActionButton audioButton;
    FloatingActionButton nextButton;
    boolean sonando;
>>>>>>> 4b86fdd0060987f12e5c5181f65c3b60ede90f53

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punto_de_interes);

        sonando = false;

        descripcion = findViewById(R.id.descripcion);
        video = findViewById(R.id.videoView2);
        audioButton = findViewById(R.id.AudioButton);
        nextButton = findViewById(R.id.NextButton);

        audioButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        descripcion.setText(R.string.cadena_prueba_descripcion);
        siguiente = findViewById(R.id.siguiente_camino);
        provider = new SqliteProvider(this);
        siguiente.setOnClickListener(this);
        args = getIntent().getExtras();
        String str2 = args.get("PuntoDeInteres").toString();
        puntoInteres = Utils.getGsonParser().fromJson(str2, PuntoInteres.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(puntoInteres.getNombre());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle args = getIntent().getExtras();
        String str = args.get("PuntoInteres").toString();
        puntoInteres = Utils.getGsonParser().fromJson(str, PuntoInteres.class);

        getSupportActionBar().setTitle(puntoInteres.getNombre());
        descripcion.setText(puntoInteres.getTexto());

        mediaPlayer = MediaPlayer.create(PuntoDeInteresActivity.this, R.raw.fachada);

        if(puntoInteres.getVideo() instanceof String) {
            Uri myUri = Uri.parse(puntoInteres.getVideo());
            video.setVideoURI(myUri);
        }



    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.AudioButton:

                if(!sonando) {
                    mediaPlayer.start();
                    sonando = true;
                }
                else{
                    sonando = false;
                    mediaPlayer.pause();
                }
                break;
            case R.id.NextButton:
                //TODO pasar al siguiente punto de interes con el mapa

                break;
        }
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.siguiente_camino:
                String str = args.get("RutaSeleccionada").toString();
                ruta = Utils.getGsonParser().fromJson(str, Ruta.class);
                puntoActual = args.getInt("PuntoActual");
                Collection<PuntoInteres> puntosBD = provider.retrieveCamino(ruta);
                if (puntoActual == puntosBD.size() - 2) {
                    Intent intent = new Intent(this, MainMenuActivity.class);
                    Toast.makeText(this, "¡Ruta finalizada! Esperamos que haya sido de su agrado", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, MapsActivity.class);
                    String str2 = args.get("RutaSeleccionada").toString();
                    ruta = Utils.getGsonParser().fromJson(str2, Ruta.class);
                    Bundle args2 = new Bundle();
                    String personJsonString = Utils.getGsonParser().toJson(ruta);
                    args2.putString("RutaSeleccionada", personJsonString);
                    args2.putInt("PuntoInicial", puntoActual);
                    intent.putExtras(args2);
                    startActivity(intent);

                    break;
                }
        }

    }
}
