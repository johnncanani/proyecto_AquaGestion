package com.example.proyecto_aquagestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_aquagestion.BaseDatos.BD_Producto;
import com.example.proyecto_aquagestion.Entidades.ReporteVenta;

import java.util.List;

public class Activity_Reporte_Ventas extends AppCompatActivity {

    private ImageView ivProductImage;
    private TextView tvProductName, tvProductQuantity, tvProductPrice, tvTotalPago;
    private Button btnDeleteProduct, buttonSalir;
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

        ivProductImage = findViewById(R.id.ivProductImage);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductQuantity = findViewById(R.id.tvProductQuantity);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvTotalPago = findViewById(R.id.tvTotalPago);
        btnDeleteProduct = findViewById(R.id.btnDeleteProduct);
        buttonSalir = findViewById(R.id.button4);

        bdProducto = new BD_Producto(this);
        cargarReporteVentas();

        buttonSalir.setOnClickListener(this::salir_ventas);
    }

    private void cargarReporteVentas() {
        List<ReporteVenta> reporteVentas = bdProducto.reporte_ventas();

        if (!reporteVentas.isEmpty()) {
            ReporteVenta reporte = reporteVentas.get(0);
            tvProductName.setText(reporte.getNombreProducto());
            tvProductQuantity.setText(String.valueOf(reporte.getCantidad()));
            tvProductPrice.setText(reporte.getPrecio());
            tvTotalPago.setText(String.valueOf(reporte.getTotalPagado()));
            // Cargar imagen si es necesario (por ejemplo, usando Glide o Picasso)
        }
    }

    public void salir_ventas(View view) {
        Toast.makeText(this, "Salir de ventas", Toast.LENGTH_SHORT).show();
        Intent salir_ventas = new Intent(this, Activity_Menu_Principal.class);
        startActivity(salir_ventas);
        finish();
    }
}
