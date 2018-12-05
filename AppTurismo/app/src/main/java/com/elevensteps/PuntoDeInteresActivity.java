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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punto_de_interes);


        descripcion = findViewById(R.id.descripcion);
        video = findViewById(R.id.videoView2);
        audioButton = findViewById(R.id.AudioButton);


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

        if(puntoInteres.getVideo() instanceof String) {
            Uri myUri = Uri.parse(puntoInteres.getVideo());
            video.setVideoURI(myUri);
        }



    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.AudioButton:
                mediaPlayer = MediaPlayer.create(this, R.raw.fachada);
                mediaPlayer.start();
                break;
            case R.id.NextButton:


                break;
        }
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
        }
        return super.onOptionsItemSelected(item);
    }

}
