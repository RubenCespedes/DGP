package com.elevensteps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.Toast;

public class MenuRutasActivity extends AppCompatActivity {
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_rutas);
        
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle extras = ((Intent) intent).getExtras();

        list = (ListView) findViewById(R.id.listaRutas);

        if(extras != null) {
            String type = extras.getString("TipoFiltro");
            Toast.makeText(this, type, Toast.LENGTH_LONG).show();
            populateList(type);
        }else{
            Toast.makeText(this, "vacio", Toast.LENGTH_LONG).show();
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    private void populateList(String type){

    }
}
