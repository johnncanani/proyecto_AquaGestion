package com.example.proyecto_aquagestion;

import android.content.Intent;
import android.database.Cursor;
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

public class MainActivity extends AppCompatActivity {

    private EditText et_usuario;
    private EditText et_contrasenia;

    SQLiteDatabase BD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_usuario = findViewById(R.id.txt_usuario);
        et_contrasenia = findViewById(R.id.txt_contrasenia);

    }
    
    public void registrar(View view) {
        Toast.makeText(this, "regitrar", Toast.LENGTH_SHORT).show();
        Intent registrar = new Intent(this, Activity_registroUsuario.class);
        startActivity(registrar);
    }

    public void ingresar(View view) {

        String usuario = et_usuario.getText().toString();
        String contrasenia = et_contrasenia.getText().toString();

        String campos_validados  =validar_campos(usuario, contrasenia);

        if (campos_validados.isEmpty()){

            archivos_staticos consulta = new archivos_staticos();

            try {

                DataBase admin = new DataBase(this, consulta.nombre_bd, null, 1);
                BD = admin.getWritableDatabase();

                Cursor fila = BD.rawQuery(consulta.consulta_usuario(usuario), null);

                //si en caso lo encuentra
                if (fila.moveToNext()){
                    Toast.makeText(this, "ingresar", Toast.LENGTH_SHORT).show();
                    Intent ingresar = new Intent(this, Activity_Menu_Principal.class);
                    startActivity(ingresar);
                }else {// en caso no lo encuentre
                    Toast.makeText(this, "Este Usuario no existe", Toast.LENGTH_SHORT).show();
                }

            } catch (SQLException ex) {
                Toast.makeText(this, "Error en:" + ex.getClass().getName(), Toast.LENGTH_SHORT).show();
            } finally {
                BD.close();
            }

        } else {
            Toast.makeText(this, campos_validados, Toast.LENGTH_SHORT).show();
        }

    }

    private String validar_campos(String usuario, String contrasenia) {
        if (usuario.isEmpty()) {
            return "Rellene su usuario";
        } else if (contrasenia.isEmpty()) {
            return "Rellene su contraseña";
        } else {
            return "";
        }
    }
}