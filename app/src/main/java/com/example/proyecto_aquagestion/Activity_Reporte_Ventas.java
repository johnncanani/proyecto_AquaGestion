package com.example.proyecto_aquagestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_aquagestion.BaseDatos.BD_Producto;

public class Activity_Reporte_Ventas extends AppCompatActivity {

    private TextView tvProductName, tvProductQuantity, tvProductPrice, tvTotalPago, tvFecha;
    private Button buttonSalir;
    private BD_Producto bdProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reporte_ventas);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvProductName = findViewById(R.id.tvProductName);
        tvProductQuantity = findViewById(R.id.tvProductQuantity);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvTotalPago = findViewById(R.id.tvTotalPago);
        tvFecha = findViewById(R.id.etFecha);
        buttonSalir = findViewById(R.id.button4);

        // Obtener datos pasados desde Activity_confirmar_venta
        Intent intent = getIntent();
        String producto = intent.getStringExtra("producto");
        int cantidad = intent.getIntExtra("cantidad", 0);
        String fecha = intent.getStringExtra("fecha");
        double total = intent.getDoubleExtra("total", 0.0);

        // Mostrar los datos en los TextView
        tvProductName.setText("Nombre del porducto "+producto);
        tvProductQuantity.setText(String.valueOf("cantidad del producto: "+cantidad));
        tvProductPrice.setText(String.valueOf("precio: "+total / cantidad)); // Asumiendo que total es la suma de precios
        tvTotalPago.setText(String.valueOf("Total S./ "+total));
        tvFecha.setText("Fecha: "+fecha);

        buttonSalir.setOnClickListener(this::salir_ventas);
    }

    public void salir_ventas(View view) {
        Toast.makeText(this, "Salir de ventas", Toast.LENGTH_SHORT).show();
        Intent salir_ventas = new Intent(this, Activity_Menu_Principal.class);
        startActivity(salir_ventas);
        finish();
    }
}
