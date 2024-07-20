package com.example.proyecto_aquagestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Activity_lista_Productos extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Carac_Producto productAdapter;
    private BD_Producto dbHelper;
    private Button btnAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        recyclerView = findViewById(R.id.recyclerView);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        SearchView searchView = findViewById(R.id.searchView);

        dbHelper = new BD_Producto(this);
        List<Producto> productList = dbHelper.getAllProducts();

        productAdapter = new Carac_Producto(productList, new Carac_Producto.ProductClickListener() {
            @Override
            public void onProductClick(Producto product) {
                Intent intent = new Intent(Activity_lista_Productos.this, Editar_Producto.class);
                intent.putExtra("productId", product.getId());
                startActivity(intent);
            }
        }, new Carac_Producto.ProductDeleteListener() {
            @Override
            public void onProductDelete(int productId) {
                dbHelper.deleteProduct(productId);
                productAdapter.updateList(dbHelper.getAllProducts());
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_lista_Productos.this, Activity_agregar_Producto.class);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapter.filter(newText);
                return false;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        productAdapter.updateList(dbHelper.getAllProducts());
    }

    public void salina_list(View view) {
        Toast.makeText(this, "salina list", Toast.LENGTH_SHORT).show();
        Intent salir_registro = new Intent(this, Activity_Menu_Principal.class);
        startActivity(salir_registro);
        finish();
    }
}
