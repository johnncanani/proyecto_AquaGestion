package com.example.proyecto_aquagestion.BaseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.example.proyecto_aquagestion.Entidades.Producto;
import com.example.proyecto_aquagestion.Entidades.ReporteVenta;
import com.example.proyecto_aquagestion.Entidades.Venta;

import java.util.ArrayList;
import java.util.List;

public class BD_Producto extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "aqua_gest.db";
    private static final int DATABASE_VERSION = 2;  // Incrementa la versión para incluir cambios

    // Tablas de Productos
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CAPACITY = "capacity";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE_URI = "image_uri";

    // Tablas de Usuarios
    private static final String TABLE_USERS = "usuario";
    private static final String COLUMN_USERNAME = "nombre_usu";
    private static final String COLUMN_EMAIL = "email_usu";
    private static final String COLUMN_PASSWORD = "contrasenia_usu";

    // Tabla de Ventas
    private static final String TABLE_SALES = "ventas";
    private static final String COLUMN_SALE_ID = "id_venta";
    private static final String COLUMN_PRODUCT_ID = "id_producto";
    private static final String COLUMN_QUANTITY = "cantidad";
    private static final String COLUMN_DATE = "fecha";
    private static final String COLUMN_TOTAL_PAGADO = "total_pagado";

    public BD_Producto(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_CAPACITY + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_PRICE + " TEXT,"
                + COLUMN_IMAGE_URI + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + "id_usu INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT NOT NULL UNIQUE,"
                + COLUMN_EMAIL + " TEXT NOT NULL UNIQUE,"
                + COLUMN_PASSWORD + " TEXT NOT NULL" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_SALES_TABLE = "CREATE TABLE " + TABLE_SALES + "("
                + COLUMN_SALE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PRODUCT_ID + " INTEGER,"
                + COLUMN_QUANTITY + " INTEGER,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_TOTAL_PAGADO + " REAL,"
                + "FOREIGN KEY(" + COLUMN_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + COLUMN_ID + ")" + ")";
        db.execSQL(CREATE_SALES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_SALES + " ADD COLUMN " + COLUMN_TOTAL_PAGADO + " REAL DEFAULT 0");
        }
    }

    // Métodos de Gestión de Productos
    public void addProduct(Producto product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_CAPACITY, product.getCapacity());
        values.put(COLUMN_DESCRIPTION, product.getDescription());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_IMAGE_URI, product.getImageUri());

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public Producto getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_CAPACITY, COLUMN_DESCRIPTION, COLUMN_PRICE, COLUMN_IMAGE_URI},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Producto product = new Producto(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
        );
        cursor.close();
        return product;
    }

    public Producto getProductByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_CAPACITY, COLUMN_DESCRIPTION, COLUMN_PRICE, COLUMN_IMAGE_URI},
                COLUMN_NAME + "=?", new String[]{name}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Producto product = new Producto(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );
            cursor.close();
            return product;
        } else {
            return null;
        }
    }

    public List<Producto> getAllProducts() {
        List<Producto> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Producto product = new Producto(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                );
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productList;
    }

    public void updateProduct(Producto product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_CAPACITY, product.getCapacity());
        values.put(COLUMN_DESCRIPTION, product.getDescription());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_IMAGE_URI, product.getImageUri());

        db.update(TABLE_PRODUCTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(product.getId())});
        db.close();
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Métodos de Gestión de Ventas
    public void realizar_Venta(@NonNull Venta sale) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ID, sale.getIdProducto());
        values.put(COLUMN_QUANTITY, sale.getCantidad());
        values.put(COLUMN_DATE, sale.getFecha());
        values.put(COLUMN_TOTAL_PAGADO, sale.getTotalPagado());

        db.insert(TABLE_SALES, null, values);
        db.close();
    }

    // Obtener reporte de ventas
    public List<ReporteVenta> reporte_ventas() {
        List<ReporteVenta> reportList = new ArrayList<>();
        String selectQuery = "SELECT " + "p." + COLUMN_NAME + ", s." + COLUMN_DATE +
                ", p." + COLUMN_PRICE + ", s." + COLUMN_QUANTITY + ", s." + COLUMN_TOTAL_PAGADO + " " +
                "FROM " + TABLE_SALES + " s " +
                "JOIN " + TABLE_PRODUCTS + " p ON s." + COLUMN_PRODUCT_ID + " = p." + COLUMN_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ReporteVenta reporte = new ReporteVenta(
                        cursor.getString(0), // Nombre del producto
                        cursor.getString(1), // Fecha
                        cursor.getString(2), // Precio
                        cursor.getInt(3),    // Cantidad
                        cursor.getDouble(4)  // Total a pagar
                );
                reportList.add(reporte);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return reportList;
    }

    // Métodos de Gestión de Usuarios
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USERNAME, COLUMN_PASSWORD},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password}, null, null, null, null);
        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        return userExists;
    }

    public void addUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        db.insert(TABLE_USERS, null, values);
        db.close();
    }
}
