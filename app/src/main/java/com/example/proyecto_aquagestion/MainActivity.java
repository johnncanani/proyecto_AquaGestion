package com.example.proyecto_aquagestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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
    }
    
    public void registrar(View view) {
        Toast.makeText(this, "regitrar", Toast.LENGTH_SHORT).show();
        Intent registrar = new Intent(this, Activity_registroUsuario.class);
        startActivity(registrar);
    }

    public void ingresar(View view) {
        Toast.makeText(this, "ingresar", Toast.LENGTH_SHORT).show();
        Intent ingresar = new Intent(this, Activity_Menu_Principal.class);
        startActivity(ingresar);
    }
}