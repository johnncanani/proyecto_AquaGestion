package com.example.proyecto_aquagestion;

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

public class MainActivity extends AppCompatActivity {

    private EditText et_usuario;
    private EditText et_contrasenia;

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

        if (usuario.isEmpty() || contrasenia.isEmpty()){
            Toast.makeText(this, "Rellene los datos requeridos", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "ingresar", Toast.LENGTH_SHORT).show();
            Intent ingresar = new Intent(this, Activity_Menu_Principal.class);
            startActivity(ingresar);
        }



    }
}