package com.example.proyecto_aquagestion.Entidades;

import java.io.Serializable;

public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String type;
    private double volume;
    private double price;
    private byte[] image;
    private int cantidad;  // Nuevo atributo cantidad

    // Constructor sin parámetros (opcional)
    public Producto() {}

    public Producto(String name, String type, double volume, double price, byte[] image, int cantidad) {
        this.name = name;
        this.type = type;
        this.volume = volume;
        this.price = price;
        this.image = image;
        this.cantidad = cantidad;
    }

    public Producto(int id, String name, String type, double volume, double price, byte[] image, int cantidad) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.volume = volume;
        this.price = price;
        this.image = image;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return name; // Mostrar solo el nombre en el Spinner
    }
}
