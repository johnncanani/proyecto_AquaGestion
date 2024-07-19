package com.example.proyecto_aquagestion;

public class archivos_staticos {

    public final String nombre_bd = "bd_aqua_gest";

    public final String crear_tabla_usuarios = "CREATE TABLE usuario(" +
            "id_usu INTEGER NOT NULL, " +
            "nombre_usu TEXT NOT NULL UNIQUE, " +
            "email_usu TEXT NOT NULL UNIQUE, " +
            "contrasenia_usu TEXT NOT NULL, " +
            "PRIMARY KEY(id_usu AUTOINCREMENT)" +
            ")";

}
