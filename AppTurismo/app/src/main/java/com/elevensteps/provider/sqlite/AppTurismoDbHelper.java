package com.elevensteps.provider.sqlite;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.elevensteps.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class AppTurismoDbHelper extends SQLiteOpenHelper {
    private static Collection<String> loadFileIntoString(InputStream... streams) {
        ArrayList<String> listOfStamments = new ArrayList<>();

        for(InputStream is : streams) {
            try (BufferedReader r = new BufferedReader(
                    new InputStreamReader(is))) {
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = r.readLine()) != null) {
                    line = line.trim();

                    sb.append(line)
                      .append(line.endsWith(";") ? "\n" : "");
                }

                listOfStamments.addAll(Arrays.asList(sb.toString().split("\n")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return Collections.unmodifiableCollection(listOfStamments);
    }

    private final Collection<String> createSql;

    AppTurismoDbHelper(@NonNull Context context) {
        super(context, "AppTurismo.db", null, 1);

        Resources res = context.getResources();

        this.createSql = loadFileIntoString(
                res.openRawResource(R.raw.sqlite_create_schema),
                res.openRawResource(R.raw.sqlite_insert_values)
        );
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        Log.i(AppTurismoDbHelper.class.getName(), "Abriendo BBDD (lectura)");
        return super.getReadableDatabase();
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        Log.i(AppTurismoDbHelper.class.getName(), "Abriendo BBDD (escritura)");
        return super.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(db.isReadOnly()) {
            throw new IllegalStateException("Error en la creación de la BBDD: Sólo lectura.");
        }

        for(String stmt : createSql) {
            Log.v(AppTurismoDbHelper.class.getName(), "stmt=" + stmt);

            db.execSQL(stmt);
        }

        Log.i(getClass().getName(), "Creado BBDD con éxito");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
