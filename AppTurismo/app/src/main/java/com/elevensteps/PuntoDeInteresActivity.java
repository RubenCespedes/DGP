package com.elevensteps;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Alhambra");
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
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
