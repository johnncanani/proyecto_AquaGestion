package com.example.proyecto_aquagestion.BaseDatos;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "gestion.db";
    private static final int DATABASE_VERSION = 4; // Incrementa la versión de la base de datos

    // Tablas y columnas
    public static final String TABLA_PRODUCTOS = "productos";
    public static final String COLUMNA_PRODUCTO_ID = "id";
    public static final String COLUMN_PRODUCTO_NOMBRE = "nombre";
    public static final String COLUMN_PRODUCTO_TIPO = "tipo";
    public static final String COLUMN_PRODUCTO_VOLUMEN = "volumen";
    public static final String COLUMN_PRODUCTO_PRECIO = "precio";
    public static final String COLUMN_PRODUCTO_IMAGEN = "imagen";
    public static final String COLUMN_PRODUCTO_CANTIDAD = "cantidad";

    public static final String TABLA_VENTAS = "ventas";
    public static final String COLUMNA_VENTA_ID = "id_venta";
    public static final String COLUMNA_VENTA_PRODUCTO_ID = "id_producto";
    public static final String COLUMNA_VENTA_CANTIDAD = "cantidad";
    public static final String COLUMNA_VENTA_FECHA = "fecha";
    public static final String COLUMNA_VENTA_TOTAL = "total_pagado";

    public static final String TABLA_USUARIOS = "usuarios";
    public static final String COLUMNA_USUARIO_ID = "id_usu";
    public static final String COLUMNA_USUARIO_NOMBRE = "nombre_usu";
    public static final String COLUMNA_USUARIO_EMAIL = "email_usu";
    public static final String COLUMNA_USUARIO_PASSWORD = "contrasenia_usu";

    // Consultas para crear tablas
    private static final String TABLA_CREAR_PRODUCTOS =
            "CREATE TABLE " + TABLA_PRODUCTOS + " (" +
                    COLUMNA_PRODUCTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PRODUCTO_NOMBRE + " TEXT NOT NULL, " +
                    COLUMN_PRODUCTO_TIPO + " TEXT, " +
                    COLUMN_PRODUCTO_VOLUMEN + " REAL, " +
                    COLUMN_PRODUCTO_PRECIO + " REAL, " +
                    COLUMN_PRODUCTO_IMAGEN + " BLOB, " +
                    COLUMN_PRODUCTO_CANTIDAD + " INTEGER NOT NULL);";

    private static final String TABLA_CREAR_VENTAS =
            "CREATE TABLE " + TABLA_VENTAS + " (" +
                    COLUMNA_VENTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMNA_VENTA_PRODUCTO_ID + " INTEGER NOT NULL, " +
                    COLUMNA_VENTA_CANTIDAD + " INTEGER NOT NULL, " +
                    COLUMNA_VENTA_FECHA + " TEXT NOT NULL, " +
                    COLUMNA_VENTA_TOTAL + " REAL NOT NULL, " +
                    "FOREIGN KEY(" + COLUMNA_VENTA_PRODUCTO_ID + ") REFERENCES " +
                    TABLA_PRODUCTOS + "(" + COLUMNA_PRODUCTO_ID + "));";

    private static final String TABLA_CREAR_USUARIOS =
            "CREATE TABLE " + TABLA_USUARIOS + " (" +
                    COLUMNA_USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMNA_USUARIO_NOMBRE + " TEXT NOT NULL UNIQUE, " +
                    COLUMNA_USUARIO_EMAIL + " TEXT NOT NULL UNIQUE, " +
                    COLUMNA_USUARIO_PASSWORD + " TEXT NOT NULL);";

    public BaseDeDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_CREAR_PRODUCTOS);
        db.execSQL(TABLA_CREAR_VENTAS);
        db.execSQL(TABLA_CREAR_USUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 4) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_VENTAS);
            db.execSQL(TABLA_CREAR_VENTAS);
        }
    }
}
