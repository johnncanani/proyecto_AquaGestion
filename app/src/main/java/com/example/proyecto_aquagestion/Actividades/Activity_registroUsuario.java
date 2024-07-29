package com.example.proyecto_aquagestion.Actividades;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_aquagestion.DAO.UsuarioDAO;
import com.example.proyecto_aquagestion.R;

import java.util.regex.Pattern;

public class Activity_registroUsuario extends AppCompatActivity {

    private EditText et_usuario_registro;
    private EditText et_correo_registro;
    private EditText et_contrasenia1;
    private EditText et_contrasenia2;

    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        usuarioDAO = new UsuarioDAO(this);
    }

    public void volver(View view) {
        Toast.makeText(this, "Volver", Toast.LENGTH_SHORT).show();
        Intent volver = new Intent(this, MainActivity.class);
        startActivity(volver);
    }

    public void registro(View view) {

        String usuario = et_usuario_registro.getText().toString();
        String correo = et_correo_registro.getText().toString();
        String contrasenia1 = et_contrasenia1.getText().toString();
        String contrasenia2 = et_contrasenia2.getText().toString();

        String datos_llenos_validados = validar_datos_llenos(usuario, correo, contrasenia1, contrasenia2);

        if (datos_llenos_validados.isEmpty()) {
            try {
                usuarioDAO.agregarUsuario(usuario, correo, contrasenia1);
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                Intent registro = new Intent(this, MainActivity.class);
                startActivity(registro);
            } catch (SQLException ex) {
                Toast.makeText(this, "Error en: " + ex.getClass().getName(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, datos_llenos_validados, Toast.LENGTH_SHORT).show();
        }
    }

    private String validar_datos_llenos(String usuario, String correo, String cont1, String cont2) {
        if (usuario.isEmpty()) {
            return "Rellene su usuario";
        } else if (correo.isEmpty()) {
            return "Rellene su correo";
        } else if (!validarEmail(correo)) {
            return "correo no valido";
        } else if (cont1.isEmpty()) {
            return "Rellene su contraseña";
        } else if (cont2.isEmpty()) {
            return "Vuelva a ingresar su contraseña";
        } else if (!cont1.equals(cont2)) {
            return "Las contraseñas deben ser iguales";
        } else {
            return "";
        }
    }

    //validando correo
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
