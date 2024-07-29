package com.example.proyecto_aquagestion.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.proyecto_aquagestion.BaseDatos.BaseDeDatos;
import com.example.proyecto_aquagestion.Entidades.Venta;

import java.util.ArrayList;
import java.util.List;

public class VentaDAO {
    private final BaseDeDatos baseDeDatos;

    public VentaDAO(Context context) {
        baseDeDatos = new BaseDeDatos(context);
    }

    // Registrar una nueva venta
    public long registrarVenta(Venta venta) {
        long id = -1;
        try (SQLiteDatabase db = baseDeDatos.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(BaseDeDatos.COLUMNA_VENTA_PRODUCTO_ID, venta.getIdProducto());
            values.put(BaseDeDatos.COLUMNA_VENTA_CANTIDAD, venta.getCantidad());
            values.put(BaseDeDatos.COLUMNA_VENTA_FECHA, venta.getFecha());
            values.put(BaseDeDatos.COLUMNA_VENTA_TOTAL, venta.getTotalPagado());

            id = db.insert(BaseDeDatos.TABLA_VENTAS, null, values);
        } catch (Exception e) {
            Log.e("VentaDAO", "Error al registrar la venta: ", e);
        }
        return id;
    }

    // Obtener todas las ventas
    public List<Venta> obtenerTodasLasVentas() {
        List<Venta> listaVentas = new ArrayList<>();
        try (SQLiteDatabase db = baseDeDatos.getReadableDatabase();
             Cursor cursor = db.query(BaseDeDatos.TABLA_VENTAS, null, null, null, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") Venta venta = new Venta(
                            cursor.getInt(cursor.getColumnIndex(BaseDeDatos.COLUMNA_VENTA_ID)),
                            cursor.getInt(cursor.getColumnIndex(BaseDeDatos.COLUMNA_VENTA_PRODUCTO_ID)),
                            cursor.getInt(cursor.getColumnIndex(BaseDeDatos.COLUMNA_VENTA_CANTIDAD)),
                            cursor.getString(cursor.getColumnIndex(BaseDeDatos.COLUMNA_VENTA_FECHA)),
                            cursor.getDouble(cursor.getColumnIndex(BaseDeDatos.COLUMNA_VENTA_TOTAL))
                    );
                    listaVentas.add(venta);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("VentaDAO", "Error al obtener todas las ventas: ", e);
        }
        return listaVentas;
    }

    // Obtener ventas por ID de producto
    public List<Venta> obtenerVentasPorProductoId(int productoId) {
        List<Venta> listaVentas = new ArrayList<>();
        try (SQLiteDatabase db = baseDeDatos.getReadableDatabase();
             Cursor cursor = db.query(BaseDeDatos.TABLA_VENTAS, null, BaseDeDatos.COLUMNA_VENTA_PRODUCTO_ID + " = ?",
                     new String[]{String.valueOf(productoId)}, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") Venta venta = new Venta(
                            cursor.getInt(cursor.getColumnIndex(BaseDeDatos.COLUMNA_VENTA_ID)),
                            cursor.getInt(cursor.getColumnIndex(BaseDeDatos.COLUMNA_VENTA_PRODUCTO_ID)),
                            cursor.getInt(cursor.getColumnIndex(BaseDeDatos.COLUMNA_VENTA_CANTIDAD)),
                            cursor.getString(cursor.getColumnIndex(BaseDeDatos.COLUMNA_VENTA_FECHA)),
                            cursor.getDouble(cursor.getColumnIndex(BaseDeDatos.COLUMNA_VENTA_TOTAL))
                    );
                    listaVentas.add(venta);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("VentaDAO", "Error al obtener las ventas por ID de producto: ", e);
        }
        return listaVentas;
    }
}
