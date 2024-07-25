package com.example.proyecto_aquagestion.Entidades;

public class Venta {
    private int id;
    private int idProducto;
    private int cantidad;
    private String fecha;
    private double totalPagado; // Agregado

    public Venta() {
    }

    public Venta(int id, int idProducto, int cantidad, String fecha, double totalPagado) {
        this.id = id;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.totalPagado = totalPagado;
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

    public double getTotalPagado() {
        return totalPagado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setTotalPagado(double totalPagado) {
        this.totalPagado = totalPagado;
    }
}
