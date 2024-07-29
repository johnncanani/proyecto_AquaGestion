package com.example.proyecto_aquagestion.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.proyecto_aquagestion.BaseDatos.BaseDeDatos;
import com.example.proyecto_aquagestion.Entidades.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private final BaseDeDatos baseDeDatos;

    public ProductoDAO(Context context) {
        baseDeDatos = new BaseDeDatos(context);
    }

    // Agrega un nuevo producto a la base de datos
    public long addProduct(Producto producto) {
        long id = -1;
        try (SQLiteDatabase db = baseDeDatos.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(BaseDeDatos.COLUMN_PRODUCTO_NOMBRE, producto.getName());
            values.put(BaseDeDatos.COLUMN_PRODUCTO_TIPO, producto.getType());
            values.put(BaseDeDatos.COLUMN_PRODUCTO_VOLUMEN, producto.getVolume());
            values.put(BaseDeDatos.COLUMN_PRODUCTO_PRECIO, producto.getPrice());
            values.put(BaseDeDatos.COLUMN_PRODUCTO_IMAGEN, producto.getImage());
            values.put(BaseDeDatos.COLUMN_PRODUCTO_CANTIDAD, producto.getCantidad());

            id = db.insert(BaseDeDatos.TABLA_PRODUCTOS, null, values);
        } catch (Exception e) {
            Log.e("ProductDAO", "Error al agregar el producto: ", e);
        }
        return id;
    }

    // Actualiza la información de un producto existente
    public int updateProduct(Producto producto) {
        int rowsAffected = 0;
        try (SQLiteDatabase db = baseDeDatos.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(BaseDeDatos.COLUMN_PRODUCTO_NOMBRE, producto.getName());
            values.put(BaseDeDatos.COLUMN_PRODUCTO_TIPO, producto.getType());
            values.put(BaseDeDatos.COLUMN_PRODUCTO_VOLUMEN, producto.getVolume());
            values.put(BaseDeDatos.COLUMN_PRODUCTO_PRECIO, producto.getPrice());
            values.put(BaseDeDatos.COLUMN_PRODUCTO_IMAGEN, producto.getImage());
            values.put(BaseDeDatos.COLUMN_PRODUCTO_CANTIDAD, producto.getCantidad());

            rowsAffected = db.update(BaseDeDatos.TABLA_PRODUCTOS, values, BaseDeDatos.COLUMNA_PRODUCTO_ID + " = ?",
                    new String[]{String.valueOf(producto.getId())});
        } catch (Exception e) {
            Log.e("ProductDAO", "Error al actualizar el producto: ", e);
        }
        return rowsAffected;
    }

    // Elimina un producto por su ID
    public void deleteProduct(int productId) {
        try (SQLiteDatabase db = baseDeDatos.getWritableDatabase()) {
            db.delete(BaseDeDatos.TABLA_PRODUCTOS, BaseDeDatos.COLUMNA_PRODUCTO_ID + " = ?",
                    new String[]{String.valueOf(productId)});
        } catch (Exception e) {
            Log.e("ProductDAO", "Error al eliminar el producto: ", e);
        }
    }

    // Obtiene un producto por su ID
    @SuppressLint("Range")
    public Producto getProductById(int productId) {
        Producto producto = null;
        try (SQLiteDatabase db = baseDeDatos.getReadableDatabase();
             Cursor cursor = db.query(BaseDeDatos.TABLA_PRODUCTOS, null, BaseDeDatos.COLUMNA_PRODUCTO_ID + " = ?",
                     new String[]{String.valueOf(productId)}, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                producto = new Producto(
                        cursor.getInt(cursor.getColumnIndex(BaseDeDatos.COLUMNA_PRODUCTO_ID)),
                        cursor.getString(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_NOMBRE)),
                        cursor.getString(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_TIPO)),
                        cursor.getDouble(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_VOLUMEN)),
                        cursor.getDouble(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_PRECIO)),
                        cursor.getBlob(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_IMAGEN)),
                        cursor.getInt(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_CANTIDAD))
                );
            }
        } catch (Exception e) {
            Log.e("ProductDAO", "Error al obtener el producto por ID: ", e);
        }
        return producto;
    }

    // Obtener un producto por su nombre
    @SuppressLint("Range")
    public Producto getProductByName(String productName) {
        Producto producto = null;
        try (SQLiteDatabase db = baseDeDatos.getReadableDatabase();
             Cursor cursor = db.query(BaseDeDatos.TABLA_PRODUCTOS, null, BaseDeDatos.COLUMN_PRODUCTO_NOMBRE + " = ?",
                     new String[]{productName}, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                producto = new Producto(
                        cursor.getInt(cursor.getColumnIndex(BaseDeDatos.COLUMNA_PRODUCTO_ID)),
                        cursor.getString(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_NOMBRE)),
                        cursor.getString(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_TIPO)),
                        cursor.getDouble(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_VOLUMEN)),
                        cursor.getDouble(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_PRECIO)),
                        cursor.getBlob(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_IMAGEN)),
                        cursor.getInt(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_CANTIDAD))
                );
            }
        } catch (Exception e) {
            Log.e("ProductoDAO", "Error al obtener el producto por nombre: ", e);
        }
        return producto;
    }

    // Obtiene todos los productos
    @SuppressLint("Range")
    public List<Producto> getAllProducts() {
        List<Producto> productoList = new ArrayList<>();
        try (SQLiteDatabase db = baseDeDatos.getReadableDatabase();
             Cursor cursor = db.query(BaseDeDatos.TABLA_PRODUCTOS, null, null, null, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Producto producto = new Producto(
                            cursor.getInt(cursor.getColumnIndex(BaseDeDatos.COLUMNA_PRODUCTO_ID)),
                            cursor.getString(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_NOMBRE)),
                            cursor.getString(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_TIPO)),
                            cursor.getDouble(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_VOLUMEN)),
                            cursor.getDouble(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_PRECIO)),
                            cursor.getBlob(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_IMAGEN)),
                            cursor.getInt(cursor.getColumnIndex(BaseDeDatos.COLUMN_PRODUCTO_CANTIDAD))
                    );
                    productoList.add(producto);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("ProductDAO", "Error al obtener todos los productos: ", e);
        }
        return productoList;
    }

    // Actualizar la cantidad de un producto
    public int updateProductQuantity(int productId, int cantidad) {
        SQLiteDatabase db = baseDeDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BaseDeDatos.COLUMN_PRODUCTO_CANTIDAD, cantidad);

        int rows = db.update(BaseDeDatos.TABLA_PRODUCTOS, values, BaseDeDatos.COLUMNA_PRODUCTO_ID + " = ?", new String[]{String.valueOf(productId)});
        db.close();
        return rows;
    }
}
