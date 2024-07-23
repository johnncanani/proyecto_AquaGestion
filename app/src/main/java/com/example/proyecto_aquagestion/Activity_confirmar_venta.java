package com.example.proyecto_aquagestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_confirmar_venta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_venta);

        TextView tvDatosVenta = findViewById(R.id.tvDatosVenta);
        Button btnEditarVenta = findViewById(R.id.btnEditarVenta);
        Button btnConfirmarVenta = findViewById(R.id.btnConfirmarVenta);

        // Obtener los datos pasados desde la actividad anterior
        Intent intent = getIntent();
        String datosVenta = intent.getStringExtra("datosVenta");
        tvDatosVenta.setText(datosVenta);

        btnEditarVenta.setOnClickListener(v -> {
            // Volver a la actividad de realizar venta
            Intent realizarVentaIntent = new Intent(Activity_confirmar_venta.this, Activity_realizar_venta.class);
            realizarVentaIntent.putExtra("datosVenta", datosVenta);
            startActivity(realizarVentaIntent);
        });

        btnConfirmarVenta.setOnClickListener(v -> {
            // Confirmar la venta y volver al menú principal
            Toast.makeText(Activity_confirmar_venta.this, "Venta realizada", Toast.LENGTH_SHORT).show();
            Intent menuPrincipalIntent = new Intent(Activity_confirmar_venta.this, Activity_Menu_Principal.class);
            startActivity(menuPrincipalIntent);
        });
    }
}
