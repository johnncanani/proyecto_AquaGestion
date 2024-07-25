package com.example.proyecto_aquagestion.Entidades;

public class Producto {
    private int id;
    private String name;
    private String capacity;
    private String description;
    private String price;
    private String imageUri;

    public Producto(int id, String name, String capacity, String description, String price, String imageUri) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.description = description;
        this.price = price;
        this.imageUri = imageUri;
    }

    public Producto(String name, String capacity, String description, String price, String imageUri) {
        this.name = name;
        this.capacity = capacity;
        this.description = description;
        this.price = price;
        this.imageUri = imageUri;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCapacity() { return capacity; }
    public void setCapacity(String capacity) { this.capacity = capacity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getImageUri() { return imageUri; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }

    @Override
    public String toString() {
        return name; // Retorna solo el nombre del producto
    }
}
