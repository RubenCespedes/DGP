package com.elevensteps.provider.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.elevensteps.model.Ruta;
import com.elevensteps.provider.Provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public final class RutaSqliteProvider implements Provider<Ruta, String> {

    private static final String RETRIEVE_SQL = "SELECT nombre, imagen, nivel_coste, nivel_accesibilidad FROM ruta WHERE nombre = ?";
    private static final String RETRIEVE_ALL_SQL = "SELECT nombre, imagen, nivel_coste, nivel_accesibilidad  FROM ruta";

    public static Provider<Ruta, String> getForContext(Context ctx) {
        return new RutaSqliteProvider(new AppTurismoDbHelper(ctx));
    }

    private final SQLiteOpenHelper dbHelper;

    RutaSqliteProvider(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }


    @Override
    public boolean create(Ruta obj) {
        throw new UnsupportedOperationException("'Update' unsupported");
    }

    @Override
    public Optional<Ruta> retrieve(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try(Cursor c = db.rawQuery(RETRIEVE_SQL, new String[]{id})) {

            if (c.getCount() == 0) {
                return Optional.empty();
            }

            String nombre = c.getString(c.getColumnIndex("nombre"));
            int nivelCoste = c.getInt(c.getColumnIndex("nivel_coste"));
            int nivelAccesibilidad = c.getInt(c.getColumnIndex("nivel_accesibilidad"));

            String imagen = c.getString(c.getColumnIndex("imagen"));

            Ruta obj = new Ruta(nombre, nivelCoste, nivelAccesibilidad, imagen);

            return Optional.empty();
        }
    }

    @Override
    public Collection<Ruta> retrieveAll() {
        // Collection of objects
        ArrayList<Ruta> objectList = new ArrayList<>();

        // Fetch the DB handle and open a Cursor
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try(Cursor c = db.rawQuery(RETRIEVE_ALL_SQL, new String[]{})) {

            // Ensure capacity of the collection
            objectList.ensureCapacity(c.getCount());

            // For each row
            for (; !c.isAfterLast(); c.moveToNext()) {

                // Fetch the information
                String nombre = c.getString(c.getColumnIndex("nombre"));
                int nivelCoste = c.getInt(c.getColumnIndex("nivel_coste"));
                int nivelAccesibilidad = c.getInt(c.getColumnIndex("nivel_accesibilidad"));

                String imagen = c.getString(c.getColumnIndex("imagen"));

                // Build an object
                Ruta obj = new Ruta(nombre, nivelCoste, nivelAccesibilidad, imagen);

                objectList.add(obj);
            }

            return objectList;
        }
    }

    @Override
    public boolean update(String id, Ruta obj) {
        throw new UnsupportedOperationException("'Update' unsupported");
    }

    @Override
    public boolean delete(String id) {
        throw new UnsupportedOperationException("'Delete' unsupported");
    }
}
