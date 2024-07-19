package com.example.proyecto_aquagestion;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_registroUsuario extends AppCompatActivity {

    private EditText et_usuario_registro;
    private EditText et_correo_registro;
    private EditText et_contrasenia1;
    private EditText et_contrasenia2;

    private SQLiteDatabase BD;
    private archivos_staticos consultas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_usuario_registro = findViewById(R.id.txt_usuario_registro);
        et_correo_registro = findViewById(R.id.txt_correo_registro);
        et_contrasenia1 = findViewById(R.id.txt_contrasenia1);
        et_contrasenia2 = findViewById(R.id.txt_contrasenia2);
    }

    public void volver(View view) {
        Toast.makeText(this, "volver", Toast.LENGTH_SHORT).show();
        Intent volver = new Intent(this, MainActivity.class);
        startActivity(volver);
    }

    public void registro(View view) {

        String usuario = et_usuario_registro.getText().toString();
        String correo = et_correo_registro.getText().toString();
        String contrasenia1 = et_contrasenia1.getText().toString();
        String contrasenia2 = et_contrasenia2.getText().toString();

        String datos_llenos_validados = validar_datos_llenos(usuario, correo, contrasenia1, contrasenia2);

        if (datos_llenos_validados.isEmpty()){

            consultas = new archivos_staticos();

            try {

                DataBase admin = new DataBase(this, consultas.nombre_bd, null, 1);
                BD = admin.getWritableDatabase();

                ContentValues contenedor = new ContentValues();
                contenedor.put("nombre_usu", usuario);
                contenedor.put("email_usu", correo);
                contenedor.put("contrasenia_usu", contrasenia1);

                BD.insert("usuario", null, contenedor);

                Toast.makeText(this, "registro exitoso", Toast.LENGTH_SHORT).show();
                Intent registro = new Intent(this, MainActivity.class);
                startActivity(registro);

            } catch (SQLException ex){
                Toast.makeText(this, "Error en: " + ex.getClass().getName(), Toast.LENGTH_SHORT).show();
            } finally {
                BD.close();
            }

        } else {
            Toast.makeText(this, datos_llenos_validados, Toast.LENGTH_SHORT).show();
        }



    }

    private String validar_datos_llenos(String usuario, String correo, String cont1, String cont2){

        if (usuario.isEmpty()){
            return "rellene su usuario";
        } else if (correo.isEmpty()){
            return "rellene su correo";
        } else if (cont1.isEmpty()) {
            return "rellene su contraseña";
        } else if (cont2.isEmpty()) {
            return "vuelva a ingresar su contraseña";
        } else if (!cont1.equals(cont2)) {
            return "las contraseñas deben ser iguales";
        } else {
            return "";
        }

    }

}