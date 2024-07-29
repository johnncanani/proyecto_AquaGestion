package com.example.proyecto_aquagestion.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.proyecto_aquagestion.BaseDatos.BaseDeDatos;


public class UsuarioDAO {
    private final BaseDeDatos baseDeDatos;

    public UsuarioDAO(Context context) {
        baseDeDatos = new BaseDeDatos(context);
    }

    public boolean verificarUsuario(String nombreUsuario, String contrasenia) {
        SQLiteDatabase db = baseDeDatos.getReadableDatabase();
        Cursor cursor = db.query(BaseDeDatos.TABLA_USUARIOS, new String[]{BaseDeDatos.COLUMNA_USUARIO_NOMBRE, BaseDeDatos.COLUMNA_USUARIO_PASSWORD},
                BaseDeDatos.COLUMNA_USUARIO_NOMBRE + "=? AND " + BaseDeDatos.COLUMNA_USUARIO_PASSWORD + "=?", new String[]{nombreUsuario, contrasenia}, null, null, null, null);
        boolean usuarioExiste = cursor.getCount() > 0;
        cursor.close();
        return usuarioExiste;
    }

    public void agregarUsuario(String nombreUsuario, String email, String contrasenia) {
        SQLiteDatabase db = baseDeDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BaseDeDatos.COLUMNA_USUARIO_NOMBRE, nombreUsuario);
        values.put(BaseDeDatos.COLUMNA_USUARIO_EMAIL, email);
        values.put(BaseDeDatos.COLUMNA_USUARIO_PASSWORD, contrasenia);

        db.insert(BaseDeDatos.TABLA_USUARIOS, null, values);
        db.close();
    }
}
