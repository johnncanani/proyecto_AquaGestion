package com.example.proyecto_aquagestion;

public class archivos_staticos {

    //nombre de la base de datos
    public final String nombre_bd = "bd_aqua_gest";

    //consultas de creacion de tablas
    public final String crear_tabla_usuarios = "CREATE TABLE usuario(" +
            "id_usu INTEGER NOT NULL, " +
            "nombre_usu TEXT NOT NULL UNIQUE, " +
            "email_usu TEXT NOT NULL UNIQUE, " +
            "contrasenia_usu TEXT NOT NULL, " +
            "PRIMARY KEY(id_usu AUTOINCREMENT)" +
            ")";

    //consultas de datos
    public final String consulta_usuario(String usuario) {
        return "SELECT nombre_usu, contrasenia_usu " +
                "from usuario " +
                "WHERE nombre_usu = '" + usuario + "'";
    }

}
