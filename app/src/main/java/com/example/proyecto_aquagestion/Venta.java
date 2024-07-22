package com.example.proyecto_aquagestion;

public class Venta {
    private final int id;
    private final int idProducto;
    private final int cantidad;
    private final String fecha;

    public Venta(int id, int idProducto, int cantidad, String fecha) {
        this.id = id;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getFecha() {
        return fecha;
    }
}
