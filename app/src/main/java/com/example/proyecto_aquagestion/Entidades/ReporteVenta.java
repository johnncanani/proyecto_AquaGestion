    package com.example.proyecto_aquagestion.Entidades;

    public class ReporteVenta {
        private String imageUri;
        private String nombreProducto;
        private String fecha;
        private String precio;
        private int cantidad;
        private double totalPagado;

        public ReporteVenta(String imageUri, String nombreProducto, String fecha, String precio, int cantidad, double totalPagado) {
            this.imageUri = imageUri;
            this.nombreProducto = nombreProducto;
            this.fecha = fecha;
            this.precio = precio;
            this.cantidad = cantidad;
            this.totalPagado = totalPagado;
        }

        public String getImageUri() {
            return imageUri;
        }

        public void setImageUri(String imageUri) {
            this.imageUri = imageUri;
        }

        public String getNombreProducto() {
            return nombreProducto;
        }

        public void setNombreProducto(String nombreProducto) {
            this.nombreProducto = nombreProducto;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public String getPrecio() {
            return precio;
        }

        public void setPrecio(String precio) {
            this.precio = precio;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        public double getTotalPagado() {
            return totalPagado;
        }

        public void setTotalPagado(double totalPagado) {
            this.totalPagado = totalPagado;
        }
    }
