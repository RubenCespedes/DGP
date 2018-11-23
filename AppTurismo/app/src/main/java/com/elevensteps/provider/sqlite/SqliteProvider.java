package com.elevensteps.provider.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.elevensteps.model.PuntoInteres;
import com.elevensteps.model.Ruta;

import java.util.ArrayList;
import java.util.Collection;

public final class SqliteProvider {

    private final SQLiteOpenHelper dbHelper;

    public SqliteProvider(Context context) {
        this.dbHelper = new AppTurismoDbHelper(context);
    }

    private static final String RETRIEVE_ALL_RUTAS_SQL = "SELECT * FROM ruta";
    public Collection<Ruta> retrieveAllRutas() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<Ruta> arrayList = new ArrayList<>(0);
        try(Cursor c = db.rawQuery(RETRIEVE_ALL_RUTAS_SQL, new String[] {})) {

            arrayList.ensureCapacity(c.getCount());

            while (c.moveToNext()) {
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
        try(Cursor c = db.rawQuery(RETRIEVE_CAMINO_SQL, new String[] { id.getNombre() })) {

            arrayList.ensureCapacity(c.getCount());

            while(c.moveToNext()) {

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

    private static final String DOES_GESTOR_EXIST_SQL = "SELECT 1 FROM gestor WHERE nombre_usuario = (SELECT nombre_usuario FROM usuario WHERE nombre_usuario = ? AND contraseña = ?)";
    public boolean doesGestorExist(String nombre, String contraseña) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try(Cursor c = db.rawQuery(DOES_GESTOR_EXIST_SQL, new String[] { nombre, contraseña })) {
            if(c.getCount() == 0) return false;
        }

        return true;
    }

    private static final String DOES_PUNTO_INTERES_EXIST_SQL = "SELECT 1 FROM punto_interes WHERE nombre = ?";
    private static final String INSERT_PUNTO_INTERES_SQL =
            "INSERT INTO punto_interes(nombre, lng, lat, url, texto, direccion, horario, precio, valoracion, audio, imagen, video) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public boolean insertPuntoInteres(PuntoInteres obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try(Cursor c = db.rawQuery(DOES_PUNTO_INTERES_EXIST_SQL, new String[] { obj.getNombre() })) {
            if(c.getCount() > 0) return false;
        }

        final String[] values = {
                String.valueOf(obj.getNombre()),
                String.valueOf(obj.getLng()),
                String.valueOf(obj.getLat()),
                String.valueOf(obj.getUrl()),
                String.valueOf(obj.getTexto()),
                String.valueOf(obj.getDireccion()),
                String.valueOf(obj.getHorario()),
                String.valueOf(obj.getPrecio()),
                String.valueOf(obj.getValoracion()),
                String.valueOf(obj.getAudio()),
                String.valueOf(obj.getImagen()),
                String.valueOf(obj.getVideo())
        };

        db.execSQL(INSERT_PUNTO_INTERES_SQL, values);

        try(Cursor c = db.rawQuery(DOES_PUNTO_INTERES_EXIST_SQL, new String[] { obj.getNombre() })) {
            if(c.getCount() == 0) return false;
        }

        return true;
    }

    private static final String DELETE_PUNTO_INTERES_SQL = "DELETE FROM punto_interes WHERE nombre = ?";
    public boolean deletePuntoInteres(PuntoInteres obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try(Cursor c = db.rawQuery(DOES_PUNTO_INTERES_EXIST_SQL, new String[] { obj.getNombre() })) {
            if(c.getCount() == 0) return false;
        }

        db.execSQL(DELETE_PUNTO_INTERES_SQL, new String[] { obj.getNombre() });

        try(Cursor c = db.rawQuery(DOES_PUNTO_INTERES_EXIST_SQL, new String[] { obj.getNombre() })) {
            if(c.getCount() > 0) return false;
        }

        return true;
    }
}
