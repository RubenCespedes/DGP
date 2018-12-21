package com.elevensteps;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.elevensteps.model.PuntoInteres;
import com.elevensteps.model.Ruta;
import com.elevensteps.provider.sqlite.SqliteProvider;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Collection;

public class PuntoDeInteresActivity extends YouTubeBaseActivity implements View.OnClickListener {
    private TextView descripcion;
    private TextView titulo;
    private PuntoInteres puntoInteres;
    private int puntoActual;
    private Ruta ruta;
    private Bundle args;
    private MediaPlayer mediaPlayer;
    private FloatingActionButton audioButton;
    private FloatingActionButton nextButton;
    private YouTubePlayerView video;
    boolean sonando;
    private SqliteProvider provider;
    private int tipoColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punto_de_interes);

        sonando = false;

        titulo = findViewById(R.id.titulo);
        descripcion = findViewById(R.id.descripcion);
        video = findViewById(R.id.youtubeView);
        audioButton = findViewById(R.id.AudioButton);
        nextButton = findViewById(R.id.NextButton);

        audioButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        nextButton = findViewById(R.id.NextButton);
        provider = new SqliteProvider(this);
        nextButton.setOnClickListener(this);
        args = getIntent().getExtras();
        String str2 = args.get("PuntoInteres").toString();
        tipoColor = args.getInt("TipoColor");
        Object en_ruta = args.get("EnRuta");
        if(en_ruta != null){
            nextButton.hide();
        }
        puntoInteres = Utils.getGsonParser().fromJson(str2, PuntoInteres.class);
        titulo.setText(puntoInteres.getNombre());
        descripcion.setText(puntoInteres.getTexto());

        if(puntoInteres.getAudio() != null) {
            mediaPlayer = MediaPlayer.create(PuntoDeInteresActivity.this, R.raw.fachada);
        } else{
            audioButton.setVisibility(View.GONE);
        }

        if(puntoInteres.getVideo() != null) {
            video.initialize("API HERE",
                    new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            YouTubePlayer youTubePlayer, boolean b) {

                            // do any work here to cue video, play video, etc.
                            String url = puntoInteres.getVideo();


                            String video_id = "";
                            int timestamp = 10000;

                            String cadenas[] = url.split("=");

                            if (cadenas.length > 2) {
                                cadenas[1] = cadenas[1].substring(0, cadenas[1].length() - 2); // quitar &t
                                timestamp = Integer.parseInt(cadenas[2]) * 1000;
                            } else {
                                timestamp = 0;
                            }
                            video_id = cadenas[1];

                            youTubePlayer.cueVideo(video_id, timestamp);
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                            YouTubeInitializationResult youTubeInitializationResult) {

                        }
                    });

        } else if(puntoInteres.getImagen() != null) {
            int resId = getResources().getIdentifier(puntoInteres.getImagen(), "drawable", getPackageName());
            if(resId != 0) {
                findViewById(R.id.resContainer).setBackgroundResource(resId);
                findViewById(R.id.resLabel).setVisibility(View.GONE);
                video.setVisibility(View.INVISIBLE);

            }
        } else {
            findViewById(R.id.resContainer).setVisibility(View.GONE);
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
            case R.id.AudioButton:
                if(!sonando) {
                    mediaPlayer.start();
                    sonando = true;
                }
                else {
                    sonando = false;
                    mediaPlayer.pause();
                }
                break;
            case R.id.NextButton:
                String str = args.get("RutaSeleccionada").toString();
                ruta = Utils.getGsonParser().fromJson(str, Ruta.class);
                puntoActual = args.getInt("PuntoActual");
                Collection<PuntoInteres> puntosBD = provider.retrieveCamino(ruta);
                if (puntoActual == puntosBD.size() - 1) {
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
                    args2.putString("NombreRuta", ruta.getNombre());
                    args2.putInt("TipoColor", tipoColor);
                    args2.putInt("PuntoInicial", puntoActual);
                    intent.putExtras(args2);
                    startActivity(intent);

                    break;
                }
        }

    }
}
