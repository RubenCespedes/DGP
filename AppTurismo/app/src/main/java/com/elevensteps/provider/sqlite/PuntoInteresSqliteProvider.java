package com.elevensteps.provider.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.elevensteps.model.PuntoInteres;
import com.elevensteps.provider.Provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public final class PuntoInteresSqliteProvider implements Provider<PuntoInteres, String> {

    private static final String RETRIEVE_SQL = "SELECT nombre, horario, precio, url, texto, direccion, valoracion, audio, imagen, video FROM punto_interes WHERE nombre = ?";
    private static final String RETRIEVE_ALL_SQL = "SELECT nombre, horario, precio, url, texto, direccion, valoracion, audio, imagen, video FROM punto_interes";

    public static Provider<PuntoInteres, String> getForContext(Context ctx) {
        return new PuntoInteresSqliteProvider(new AppTurismoDbHelper(ctx));
    }

    private final SQLiteOpenHelper dbHelper;

    PuntoInteresSqliteProvider(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public boolean create(PuntoInteres obj) {
        throw new UnsupportedOperationException("'Update' unsupported");
    }

    @Override
    public Optional<PuntoInteres> retrieve(String id) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try(Cursor c = db.rawQuery(RETRIEVE_SQL, new String[] { id })) {


            if(c.getCount() == 0) {
                return Optional.empty();
            }


            String  nombre      = c.getString(c.getColumnIndex("nombre"));
            String  descripcion = c.getString(c.getColumnIndex("descripcion"));
            String  horario     = c.getString(c.getColumnIndex("horario"));
            String  url         = c.getString(c.getColumnIndex("url"));
            String  texto       = c.getString(c.getColumnIndex("texto"));
            String  direccion   = c.getString(c.getColumnIndex("direccion"));
            int     valoracion  = c.getInt(   c.getColumnIndex("valoracion"));
            String  audio       = c.getString(c.getColumnIndex("audio"));
            String  imagen      = c.getString(c.getColumnIndex("imagen"));
            String  video       = c.getString(c.getColumnIndex("video"));


            PuntoInteres obj = new PuntoInteres(nombre, descripcion, horario, url, texto, direccion,
                    valoracion, audio, imagen, video);

            return Optional.of(obj);
        }
    }

    @Override
    public Collection<PuntoInteres> retrieveAll() {
        // Collection of objects
        ArrayList<PuntoInteres> objectList = new ArrayList<>();

        // Fetch the DB handle and open a Cursor
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try(Cursor c = db.rawQuery(RETRIEVE_ALL_SQL, new String[]{})) {

            objectList.ensureCapacity(c.getCount());

            // For each row
            for (; !c.isAfterLast(); c.moveToNext()) {

                // Fetch the information
                String nombre = c.getString(c.getColumnIndex("nombre"));
                String descripcion = c.getString(c.getColumnIndex("descripcion"));
                String horario = c.getString(c.getColumnIndex("horario"));
                String url = c.getString(c.getColumnIndex("url"));
                String texto = c.getString(c.getColumnIndex("texto"));
                String direccion = c.getString(c.getColumnIndex("direccion"));
                int valoracion = c.getInt(c.getColumnIndex("valoracion"));
                String audio = c.getString(c.getColumnIndex("audio"));
                String imagen = c.getString(c.getColumnIndex("imagen"));
                String video = c.getString(c.getColumnIndex("video"));

                // Build an object
                PuntoInteres obj = new PuntoInteres(nombre, descripcion, horario, url, texto, direccion,
                        valoracion, audio, imagen, video);

                // Add the object
                objectList.add(obj);
            }

            return objectList;
        }
    }

    @Override
    public boolean update(String id, PuntoInteres obj) {
        throw new UnsupportedOperationException("'Update' unsupported");
    }

    @Override
    public boolean delete(String id) {
        throw new UnsupportedOperationException("'Delete' unsupported");
    }
}
