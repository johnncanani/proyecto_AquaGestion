package com.example.proyecto_aquagestion.Actividades;

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

import com.example.proyecto_aquagestion.DAO.ProductoDAO;
import com.example.proyecto_aquagestion.DAO.VentaDAO;
import com.example.proyecto_aquagestion.Entidades.Producto;
import com.example.proyecto_aquagestion.Entidades.Venta;
import com.example.proyecto_aquagestion.R;

public class Activity_confirmar_venta extends AppCompatActivity {

    private TextView tvDatosVenta;
    private Button btnConfirmarVenta;
    private Button btnEditarVenta;

    private ProductoDAO productoDAO;
    private VentaDAO ventaDAO;

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

        productoDAO = new ProductoDAO(this);
        ventaDAO = new VentaDAO(this);

        // Obtener los datos de la venta
        Intent intent = getIntent();
        String datosVenta = intent.getStringExtra("datosVenta");
        tvDatosVenta.setText(datosVenta);

        // Configurar los botones
        btnConfirmarVenta.setOnClickListener(this::confirmarVenta);
        btnEditarVenta.setOnClickListener(this::editarVenta);
    }

    public void confirmarVenta(View view) {
        // Obtener los datos de la venta del TextView
        String[] datos = tvDatosVenta.getText().toString().split("\n");
        String productoNombre = datos[0].split(": ")[1];
        int cantidad = Integer.parseInt(datos[1].split(": ")[1]);
        String fecha = datos[2].split(": ")[1];
        double total = Double.parseDouble(datos[4].split(": ")[1]);

        // Obtener el producto por su nombre
        Producto producto = productoDAO.getProductByName(productoNombre);

        // Crear la venta y guardarla en la base de datos
        Venta venta = new Venta(producto.getId(), cantidad, fecha, total);
        long idVenta = ventaDAO.registrarVenta(venta);

        if (idVenta != -1) {
            // Actualizar el stock del producto
            productoDAO.updateProductQuantity(producto.getId(), producto.getCantidad() - cantidad);

            Toast.makeText(this, "Venta confirmada", Toast.LENGTH_SHORT).show();

            // Pasar los datos de la venta a Activity_Reporte_Ventas
            Intent confirmar_venta = new Intent(this, Activity_Reporte_Ventas.class);
            confirmar_venta.putExtra("producto", productoNombre);
            confirmar_venta.putExtra("cantidad", cantidad);
            confirmar_venta.putExtra("fecha", fecha);
            confirmar_venta.putExtra("total", total);
            startActivity(confirmar_venta);
            finish();
        } else {
            Toast.makeText(this, "Error al confirmar la venta", Toast.LENGTH_SHORT).show();
        }
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
