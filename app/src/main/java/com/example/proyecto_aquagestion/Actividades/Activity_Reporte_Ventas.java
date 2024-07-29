package com.example.proyecto_aquagestion.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_aquagestion.R;
import com.example.proyecto_aquagestion.Adaptadores.VentaAdapter;
import com.example.proyecto_aquagestion.DAO.ProductoDAO;
import com.example.proyecto_aquagestion.DAO.VentaDAO;
import com.example.proyecto_aquagestion.Entidades.Producto;
import com.example.proyecto_aquagestion.Entidades.Venta;

import java.util.List;

public class Activity_Reporte_Ventas extends AppCompatActivity {

    private RecyclerView recyclerViewVentas;
    private Button buttonSalir;
    private VentaDAO ventaDAO;
    private ProductoDAO productoDAO;
    private List<Venta> listaVentas;
    private List<Producto> listaProductos;

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

        recyclerViewVentas = findViewById(R.id.recyclerViewVentas);
        buttonSalir = findViewById(R.id.button4);

        // Inicializar DAO
        ventaDAO = new VentaDAO(this);
        productoDAO = new ProductoDAO(this);

        // Obtener datos
        listaVentas = ventaDAO.obtenerTodasLasVentas();
        listaProductos = productoDAO.getAllProducts();

        // Configurar RecyclerView
        VentaAdapter adapter = new VentaAdapter(listaVentas, listaProductos);
        recyclerViewVentas.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewVentas.setAdapter(adapter);

        buttonSalir.setOnClickListener(this::salir_ventas);
    }

    public void salir_ventas(View view) {
        Toast.makeText(this, "Salir de ventas", Toast.LENGTH_SHORT).show();
        Intent salir_ventas = new Intent(this, Activity_Menu_Principal.class);
        startActivity(salir_ventas);
        finish();
    }
}
