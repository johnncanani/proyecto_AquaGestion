package com.example.proyecto_aquagestion.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_aquagestion.DAO.UsuarioDAO;
import com.example.proyecto_aquagestion.R;

public class MainActivity extends AppCompatActivity {

    private EditText et_usuario;
    private EditText et_contrasenia;

    private UsuarioDAO usuarioDAO;

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

        usuarioDAO = new UsuarioDAO(this);
    }

    public void registrar(View view) {
        Toast.makeText(this, "Registrar", Toast.LENGTH_SHORT).show();
        Intent registrar = new Intent(this, Activity_registroUsuario.class);
        startActivity(registrar);
    }

    public void ingresar(View view) {
        String usuario = et_usuario.getText().toString();
        String contrasenia = et_contrasenia.getText().toString();

        String campos_validados = validar_campos(usuario, contrasenia);

        if (campos_validados.isEmpty()) {
            try {
                if (usuarioDAO.verificarUsuario(usuario, contrasenia)) {
                    Toast.makeText(this, "Ingreso exitoso", Toast.LENGTH_SHORT).show();
                    Intent ingresar = new Intent(this, Activity_Menu_Principal.class);
                    startActivity(ingresar);
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
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
