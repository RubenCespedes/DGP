package com.elevensteps.provider.sqlite;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.elevensteps.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class AppTurismoDbHelper extends SQLiteOpenHelper {
    private static String loadFileIntoString(InputStream is) {
        StringBuilder sb = new StringBuilder();

        try(BufferedReader r = new BufferedReader(
                new InputStreamReader(is))) {

            String line;
            while((line = r.readLine()) != null) {
                sb.append(line);
            }

        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }

    private final String createSql;
    private final String insertSql;

    AppTurismoDbHelper(@NonNull Context context) {
        super(context, "AppTurismo.db", null, 1);

        Resources res = context.getResources();

        this.createSql = loadFileIntoString(res.openRawResource(R.raw.sqlite_create_schema));
        this.insertSql = loadFileIntoString(res.openRawResource(R.raw.sqlite_insert_values));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createSql);
        db.execSQL(insertSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        this.onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
