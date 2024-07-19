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

public class Activity_Menu_Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void cerrar_sesion(View view) {
        Toast.makeText(this, "cerrar sesion", Toast.LENGTH_SHORT).show();
        Intent cerrar_sesion = new Intent(this, MainActivity.class);
        startActivity(cerrar_sesion);
        finish();
    }

    public void ingresar_productos(View view) {
        Toast.makeText(this, "ingresar productos", Toast.LENGTH_SHORT).show();
        Intent ingresar_productos= new Intent(this, Activity_Productos.class);
        startActivity(ingresar_productos);
    }

    public void ingreso_inventario(View view) {
        Toast.makeText(this, "ingreso inventario", Toast.LENGTH_SHORT).show();
        Intent ingreso_inventario= new Intent(this, Activity_Inventario.class);
        startActivity(ingreso_inventario);

    }

    public void ingreso_reportes(View view) {
        Toast.makeText(this, "ingreso reportes", Toast.LENGTH_SHORT).show();
        Intent ingreso_reportes= new Intent(this, Activity_Reporte_Ventas.class);
        startActivity(ingreso_reportes);

    }

    public void registro_producto(View view) {
        Toast.makeText(this, "registro producto", Toast.LENGTH_SHORT).show();
        Intent registro_producto= new Intent(this, Activity_registrarProducto.class);
        startActivity(registro_producto);
    }
}