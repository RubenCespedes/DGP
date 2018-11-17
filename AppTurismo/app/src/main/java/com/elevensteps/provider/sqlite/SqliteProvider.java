package com.elevensteps.provider.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.elevensteps.model.PuntoInteres;
import com.elevensteps.model.Ruta;

import java.util.ArrayList;
import java.util.Collection;

public final class SqliteProvider {

    private final SQLiteOpenHelper dbHelper;

    public SqliteProvider(Context context) {
        this.dbHelper = new AppTurismoDbHelper(context);
    }

    private static final String RETRIEVE_ALL_RUTAS_SQL = "SELECT * FROM ruta;";
    public Collection<Ruta> retrieveAllRutas() {
        ArrayList<Ruta> arrayList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try(Cursor c = db.rawQuery(RETRIEVE_ALL_RUTAS_SQL, new String[] {})) {

            arrayList.ensureCapacity(c.getCount());

            for (; !c.isAfterLast(); c.moveToNext()) {

                Ruta obj = Ruta.builder()
                        .nombre(c.getString(c.getColumnIndex("nombre")))
                        .categoria(c.getString(c.getColumnIndex("categoria")))
                        .nivelCoste(c.getDouble(c.getColumnIndex("nivel_coste")))
                        .nivelAccesibilidad((c.getDouble(c.getColumnIndex("nivel_accesibilidad"))))
                        .imagen(c.getString(c.getColumnIndex(("imagen"))))
                        .build();

                arrayList.add(obj);
            }
        }

        return arrayList;
    }

    private static final String RETRIEVE_CAMINO_SQL = "SELECT * FROM punto_interes WHERE nombre IN (SELECT punto_de_interes FROM contiene WHERE ruta = ?)";
    public Collection<PuntoInteres> retrieveCamino(Ruta id) {
        ArrayList<PuntoInteres> arrayList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try(Cursor c = db.rawQuery(RETRIEVE_CAMINO_SQL, new String[] {id.getNombre()})) {

            arrayList.ensureCapacity(c.getCount());

            for (; !c.isAfterLast(); c.moveToNext()) {

                PuntoInteres obj = PuntoInteres.builder()
                        .nombre(c.getString(c.getColumnIndex("nombre")))
                        .lng(c.getDouble(c.getColumnIndex("lng")))
                        .lat(c.getDouble(c.getColumnIndex("at")))
                        .texto(c.getString(c.getColumnIndex("texto")))
                        .horario(c.getString(c.getColumnIndex("horario")))
                        .url(c.getString(c.getColumnIndex("url")))
                        .texto(c.getString(c.getColumnIndex("texto")))
                        .direccion(c.getString(c.getColumnIndex("direccion")))
                        .valoracion(c.getDouble(c.getColumnIndex("valoracion")))
                        .audio(c.getString(c.getColumnIndex("audio")))
                        .imagen(c.getString(c.getColumnIndex("imagen")))
                        .video(c.getString(c.getColumnIndex("video")))
                        .build();

                arrayList.add(obj);
            }
        }

        return arrayList;
    }
}
