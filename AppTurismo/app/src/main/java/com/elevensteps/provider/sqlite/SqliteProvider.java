package com.elevensteps.provider.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.elevensteps.model.PuntoInteres;
import com.elevensteps.model.Ruta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class SqliteProvider {

    private final SQLiteOpenHelper dbHelper;

    public SqliteProvider(Context context) {
        this.dbHelper = new AppTurismoDbHelper(context);
    }

    private static final String RETRIEVE_ALL_RUTAS_SQL = "SELECT * FROM ruta";
    public List<Ruta> retrieveAllRutas() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<Ruta> arrayList = new ArrayList<>(0);
        try(Cursor c = db.rawQuery(RETRIEVE_ALL_RUTAS_SQL, new String[] {})) {

            arrayList.ensureCapacity(c.getCount());
            while ( c.moveToNext() ){
                Ruta obj = Ruta.builder()
                        .nombre( c.getString( c.getColumnIndex( "nombre" ) ) )
                        .descripcion( c.getString( c.getColumnIndex( "descripcion" ) ) )
                        .categoria( c.getString( c.getColumnIndex( "categoria" ) ) )
                        .nivelCoste( c.getDouble( c.getColumnIndex( "nivel_coste" ) ) )
                        .nivelAccesibilidad( c.getDouble( c.getColumnIndex( "nivel_accesibilidad" ) ) )
                        .imagen( c.getString( c.getColumnIndex( "imagen" ) ) )
                        .build();

                arrayList.add( obj );
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    private static final String RETRIEVE_ALL_PUNTO_INTERES_SQL = "SELECT * FROM punto_interes";
    public List<PuntoInteres> retrieveAllPuntoInteres() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<PuntoInteres> arrayList = new ArrayList<>(0);
        try (Cursor c = db.rawQuery(RETRIEVE_ALL_PUNTO_INTERES_SQL, new String[]{})) {
            arrayList.ensureCapacity(c.getCount());

            while (c.moveToNext()) {
                PuntoInteres obj = PuntoInteres.builder()
                        .nombre(c.getString(c.getColumnIndex("nombre")))
                        .lng(c.getDouble(c.getColumnIndex("lng")))
                        .lat(c.getDouble(c.getColumnIndex("lat")))
                        .texto(c.getString(c.getColumnIndex("texto")))
                        .horario(c.getString(c.getColumnIndex("horario")))
                        .url(c.getString(c.getColumnIndex("url")))
                        .texto(c.getString(c.getColumnIndex("texto")))
                        .direccion(c.getString(c.getColumnIndex("direccion")))
                        .precio(c.getDouble(c.getColumnIndex("precio")))
                        .valoracion(c.getDouble(c.getColumnIndex("valoracion")))
                        .audio(c.getString(c.getColumnIndex("audio")))
                        .imagen(c.getString(c.getColumnIndex("imagen")))
                        .video(c.getString(c.getColumnIndex("video")))
                        .build();

                arrayList.add(obj);
            }
        }

        return Collections.unmodifiableList(arrayList);
    }

    private static final String RETRIEVE_ALL_RUTAS_BY_CATEGORIA_SQL = "SELECT * FROM ruta WHERE categoria = ?";
    public List<Ruta> retrieveAllKindOfRutas(String categoria){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<Ruta> arrayList = new ArrayList<>(0);
        try(Cursor c = db.rawQuery(RETRIEVE_ALL_RUTAS_BY_CATEGORIA_SQL, new String[] {categoria})) {
            arrayList.ensureCapacity(c.getCount());

            while (c.moveToNext()) {
                Ruta obj = Ruta.builder()
                        .nombre(c.getString(c.getColumnIndex("nombre")))
                        .descripcion(c.getString(c.getColumnIndex("descripcion")))
                        .categoria(c.getString(c.getColumnIndex("categoria")))
                        .nivelCoste(c.getDouble(c.getColumnIndex("nivel_coste")))
                        .nivelAccesibilidad((c.getDouble(c.getColumnIndex("nivel_accesibilidad"))))
                        .imagen(c.getString(c.getColumnIndex(("imagen"))))
                        .build();

                arrayList.add(obj);
            }
        }

        return Collections.unmodifiableList(arrayList);
    }

    private static final String RETRIEVE_CAMINO_SQL = "SELECT * FROM punto_interes WHERE nombre IN (SELECT punto_de_interes FROM contiene WHERE ruta = ?)";
    public List<PuntoInteres> retrieveCamino(Ruta id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<PuntoInteres> arrayList = new ArrayList<>(0);
        try(Cursor c = db.rawQuery(RETRIEVE_CAMINO_SQL, new String[] { id.getNombre() })) {
            arrayList.ensureCapacity(c.getCount());

            while(c.moveToNext()) {
                PuntoInteres obj = PuntoInteres.builder()
                        .nombre(c.getString(c.getColumnIndex("nombre")))
                        .lng(c.getDouble(c.getColumnIndex("lng")))
                        .lat(c.getDouble(c.getColumnIndex("lat")))
                        .texto(c.getString(c.getColumnIndex("texto")))
                        .horario(c.getString(c.getColumnIndex("horario")))
                        .url(c.getString(c.getColumnIndex("url")))
                        .texto(c.getString(c.getColumnIndex("texto")))
                        .direccion(c.getString(c.getColumnIndex("direccion")))
                        .precio(c.getDouble(c.getColumnIndex("precio")))
                        .valoracion(c.getDouble(c.getColumnIndex("valoracion")))
                        .audio(c.getString(c.getColumnIndex("audio")))
                        .imagen(c.getString(c.getColumnIndex("imagen")))
                        .video(c.getString(c.getColumnIndex("video")))
                        .build();

                arrayList.add(obj);
            }
        }

        return Collections.unmodifiableList(arrayList);
    }

    private static final String DOES_GESTOR_EXIST_SQL = "SELECT 1 FROM gestor WHERE nombre_usuario = (SELECT nombre_usuario FROM usuario WHERE nombre_usuario = ? AND contraseña = ?)";
    public boolean doesGestorExist(String nombre, String contraseña) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try(Cursor c = db.rawQuery(DOES_GESTOR_EXIST_SQL, new String[] { nombre, contraseña })) {
            if(c.getCount() == 0) return false;
        }

        return true;
    }

    private static final String DOES_RUTA_EXIST_SQL = "SELECT 1 FROM ruta WHERE nombre = ?";
    private static final String INSERT_RUTA_SQL =
            "INSERT INTO ruta(nombre, descripcion, categoria, nivel_coste, nivel_accesibilidad, imagen) values(?, ?, ?, ?, ?, ?)";
    public boolean insertRuta(Ruta obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try(Cursor c = db.rawQuery(DOES_RUTA_EXIST_SQL, new String[] { obj.getNombre() })) {
            if(c.getCount() > 0) return false;
        }

        final String[] values = {
                String.valueOf(obj.getNombre()),
                String.valueOf(obj.getDescripcion()),
                String.valueOf(obj.getCategoria()),
                String.valueOf(obj.getNivelCoste()),
                String.valueOf(obj.getNivelAccesibilidad()),
                String.valueOf(obj.getImagen())
        };

        db.execSQL(INSERT_RUTA_SQL, values);

        try(Cursor c = db.rawQuery(DOES_RUTA_EXIST_SQL, new String[] { obj.getNombre() })) {
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

    private static final String INSERT_CAMINO_SQL = "INSERT INTO contiene(ruta, punto_de_interes) values (?, ?)";
    public boolean insertCamino(Ruta ruta, Collection<PuntoInteres> puntos_interes) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try(Cursor c = db.rawQuery(DOES_RUTA_EXIST_SQL, new String[] { ruta.getNombre() })) {
            if(c.getCount() == 0) return false;
        }

        for(PuntoInteres pi : puntos_interes) {
            try(Cursor c = db.rawQuery(DOES_PUNTO_INTERES_EXIST_SQL, new String[] { pi.getNombre() })) {
                if(c.getCount() == 0) return false;
            }


            final String[] values = {
                    String.valueOf(ruta.getNombre()),
                    String.valueOf(pi.getNombre())
            };

            db.execSQL(INSERT_CAMINO_SQL, values);
        }

        return true;
    }

    private static final String ASOCIAR_RUTA_PUNTO = "INSERT INTO contiene(ruta, punto_de_interes) values (?, ?)";
    public boolean asociarRutaConPunto(String nombreRuta, String nombrePuntoInteres) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try(Cursor c = db.rawQuery(DOES_RUTA_EXIST_SQL, new String[] { nombreRuta })) {
            if(c.getCount() == 0) return false;
        }


        try(Cursor c = db.rawQuery(DOES_PUNTO_INTERES_EXIST_SQL, new String[] { nombrePuntoInteres })) {
            if(c.getCount() == 0) return false;
        }


        final String[] values = {
                String.valueOf(nombreRuta),
                String.valueOf(nombrePuntoInteres)
        };

        db.execSQL(ASOCIAR_RUTA_PUNTO, values);


        return true;
    }

    private static final String DELETE_RUTA_SQL = "DELETE FROM ruta WHERE nombre = ?";
    public boolean deleteRuta(Ruta obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try(Cursor c = db.rawQuery(DOES_RUTA_EXIST_SQL, new String[] { obj.getNombre() })) {
            if(c.getCount() == 0) return false;
        }

        db.execSQL(DELETE_RUTA_SQL, new String[] { obj.getNombre() });

        try(Cursor c = db.rawQuery(DOES_RUTA_EXIST_SQL, new String[] { obj.getNombre() })) {
            if(c.getCount() > 0) return false;
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

    private static final String DOES_PUNTO_EXIST_IN_RUTA = "SELECT 1 FROM contiene WHERE (punto_de_interes = ? AND ruta = ?)";
    private static final String DELETE_PUNTO_INTERES_FROM_RUTA_SQL = "DELETE FROM contiene WHERE (punto_de_interes = ? AND ruta = ?)";
    public boolean deletePuntoInteresFromRuta(PuntoInteres puntoInteres, Ruta ruta) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try(Cursor c = db.rawQuery(DOES_PUNTO_EXIST_IN_RUTA, new String[] { puntoInteres.getNombre(), ruta.getNombre() })) {
            if(c.getCount() == 0) return false;
        }

        db.execSQL(DELETE_PUNTO_INTERES_FROM_RUTA_SQL, new String[] { puntoInteres.getNombre(), ruta.getNombre() });

        try(Cursor c = db.rawQuery(DOES_PUNTO_EXIST_IN_RUTA, new String[] { puntoInteres.getNombre(), ruta.getNombre() })) {
            if(c.getCount() > 0) return false;
        }

        return true;
    }
}
