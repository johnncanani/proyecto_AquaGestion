package com.example.proyecto_aquagestion.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_aquagestion.R;

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

    public void ingre_producto(View view) {
        Intent intent;
        intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }

    public void ingreso_inventario(View view) {
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }

    public void registro_ventas(View view) {
        Intent intent = new Intent(this, Activity_realizar_venta.class);
        startActivity(intent);
    }

    public void ingreso_reportes(View view) {
        Intent intent = new Intent(this, Activity_Reporte_Ventas.class);
        startActivity(intent);
    }

    public void cerrar_sesion(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Optional: Finish the current activity to prevent the user from going back to it
    }
}
