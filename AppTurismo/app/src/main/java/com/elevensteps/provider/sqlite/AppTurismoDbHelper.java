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

public class AppTurismoDbHelper extends SQLiteOpenHelper {
    private static String loadFileIntoString(InputStream... streams) {
        StringBuilder sb = new StringBuilder();

        for(InputStream is : streams) {
            try (BufferedReader r = new BufferedReader(
                    new InputStreamReader(is))) {

                String line;
                while ((line = r.readLine()) != null) {
                    sb.append(line).append('\n');
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return sb.toString();
    }

    private final String createSql;

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
        if(!db.isReadOnly()) {
            for(String stmt : createSql.split(";")) {
                if(stmt != null) {
                    //Log.i(getClass().getName(), "Ejecuto <" + stmt + ">");
                    db.execSQL(stmt);
                }
            }
            Log.i(getClass().getName(), "Creado BBDD con éxito");
        }
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
