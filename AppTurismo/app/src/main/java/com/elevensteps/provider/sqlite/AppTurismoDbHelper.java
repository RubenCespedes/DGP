package com.elevensteps.provider.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.elevensteps.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class AppTurismoDbHelper extends SQLiteOpenHelper {

    private final String createSql;

    public AppTurismoDbHelper(@Nullable Context context) {
        super(context, "AppTurismo.db", null, 1);

        StringBuilder sb = new StringBuilder();

        try(Reader r = new BufferedReader(
                    new InputStreamReader(context.getResources().openRawResource(R.raw.sqlite_create_schema)))) {

            char[] bytes = new char[1024];
            for(int len = r.read(); len > 0; len = r.read(bytes)) {
                sb.append(bytes, 0, len);
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        this.createSql = sb.toString();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(createSql);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
