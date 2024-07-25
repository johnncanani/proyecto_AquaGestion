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
import com.example.proyecto_aquagestion.Entidades.Venta;

public class Activity_confirmar_venta extends AppCompatActivity {

    private TextView tvDatosVenta;
    private Button btnConfirmarVenta;
    private Button btnEditarVenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmar_venta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar las vistas
        tvDatosVenta = findViewById(R.id.tvDatosVenta);
        btnConfirmarVenta = findViewById(R.id.btnConfirmarVenta);
        btnEditarVenta = findViewById(R.id.btnEditarVenta);

        // Obtener los datos de la venta
        Intent intent = getIntent();
        String datosVenta = intent.getStringExtra("datosVenta");
        tvDatosVenta.setText(datosVenta);

        // Configurar los botones
        btnConfirmarVenta.setOnClickListener(this::confirmarVenta);
        btnEditarVenta.setOnClickListener(this::editarVenta);
    }

    public void confirmarVenta(View view) {
        BD_Producto dbHelper = new BD_Producto(this);

        // Obtener los datos de la venta del TextView
        String[] datos = tvDatosVenta.getText().toString().split("\n");
        String producto = datos[0].split(": ")[1];
        int cantidad = Integer.parseInt(datos[1].split(": ")[1]);
        String fecha = datos[2].split(": ")[1];
        double total = Double.parseDouble(datos[4].split(": ")[1]);

        // Obtener el ID del producto
        int idProducto = dbHelper.getProductByName(producto).getId();

        // Crear la venta y guardarla en la base de datos
        Venta venta = new Venta(0, idProducto, cantidad, fecha, total);
        dbHelper.realizar_Venta(venta);

        Toast.makeText(this, "Venta confirmada", Toast.LENGTH_SHORT).show();

        // Pasar los datos de la venta a Activity_Reporte_Ventas
        Intent confirmar_venta = new Intent(this, Activity_Reporte_Ventas.class);
        confirmar_venta.putExtra("producto", producto);
        confirmar_venta.putExtra("cantidad", cantidad);
        confirmar_venta.putExtra("fecha", fecha);
        confirmar_venta.putExtra("total", total);
        startActivity(confirmar_venta);
        finish();
    }

    public void editarVenta(View view) {
        // Obtener los datos de la venta del TextView
        String datosVenta = tvDatosVenta.getText().toString();

        // Pasar los datos a la actividad de realizar venta
        Intent realizarVentaIntent = new Intent(Activity_confirmar_venta.this, Activity_realizar_venta.class);
        realizarVentaIntent.putExtra("datosVenta", datosVenta);
        startActivity(realizarVentaIntent);
    }
}
