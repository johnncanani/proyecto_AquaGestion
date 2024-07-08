package com.example.proyecto_aquagestion;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText txt_usuario, txt_contrasenia;
    Button btn_ingresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_usuario = findViewById(R.id.txt_usuario);
        txt_contrasenia = findViewById(R.id.txt_contrasenia);
        btn_ingresar = findViewById(R.id.btn_ingresar);

        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usuario = txt_usuario.getText().toString();
                String contrasenia = txt_contrasenia.getText().toString();

                if (usuario.equals("admin") && contrasenia.equals("12345")) {
                    Toast.makeText(MainActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                    // Aquí puedes añadir la lógica para redirigir a otra actividad si el login es exitoso
                } else {
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}




























