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

public class Activity_Inventario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inventario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void agregar_productos_invetario(View view) {
        Toast.makeText(this, "agregar productos inventario", Toast.LENGTH_SHORT).show();
        Intent agregar_productos_inventario = new Intent(this, Activity_lista_Productos.class);
        startActivity(agregar_productos_inventario);
    }

    public void salir_inventario(View view) {
        Toast.makeText(this, "salir inventario", Toast.LENGTH_SHORT).show();
        Intent salir_inventario = new Intent(this, Activity_Menu_Principal.class);
        startActivity(salir_inventario);
        finish();
    }
}