package com.example.proyecto_aquagestion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BD_Producto extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CAPACITY = "capacity";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE_URI = "image_uri";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

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
}
