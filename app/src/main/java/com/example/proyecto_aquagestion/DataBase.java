package com.example.proyecto_aquagestion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    private final String crear_tabla_usuarios = "CREATE TABLE usuario(" +
            "id_usu INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            "nombre_usu TEXT NOT NULL UNIQUE, " +
            "email_usu TEXT NOT NULL UNIQUE, " +
            "contrasenia_usu INTEGER NOT NULL" +
            ")";

    public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase my_data_base) {

        my_data_base.execSQL(crear_tabla_usuarios);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
