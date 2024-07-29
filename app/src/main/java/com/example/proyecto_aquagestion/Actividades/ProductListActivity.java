package com.example.proyecto_aquagestion.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_aquagestion.DAO.ProductoDAO;
import com.example.proyecto_aquagestion.Entidades.Producto;
import com.example.proyecto_aquagestion.Adaptadores.ProductAdapter;
import com.example.proyecto_aquagestion.R;

import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ProductAdapter.OnProductActionListener {

    private ListView listViewProducts;
    private Button btnAddProduct;
    private EditText etSearchProduct;
    private ProductoDAO productoDAO;
    private List<Producto> productoList;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Enlaza los elementos de la interfaz
        listViewProducts = findViewById(R.id.listViewProducts);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        etSearchProduct = findViewById(R.id.etSearchProduct);

        // Inicializa el DAO y carga los productos
        productoDAO = new ProductoDAO(this);
        productoList = productoDAO.getAllProducts();

        // Configura el adaptador y lo enlaza a la lista
        productAdapter = new ProductAdapter(this, productoList, this);
        listViewProducts.setAdapter(productAdapter);

        // Configura el botón para agregar un producto
        btnAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(ProductListActivity.this, AddProductActivity.class);
            startActivityForResult(intent, 1); // Espera resultados para actualizar la lista de productos
        });

        // Configura el texto de búsqueda para filtrar productos
        etSearchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                // Actualizar la lista de productos después de agregar uno nuevo
                productoList = productoDAO.getAllProducts();
                productAdapter.updateList(productoList);
            } else if (requestCode == 2) {
                // Actualizar la lista de productos después de editar uno existente
                productoList = productoDAO.getAllProducts();
                productAdapter.updateList(productoList);
            }
        }
    }

    @Override
    public void onEditProduct(Producto producto) {
        Intent intent = new Intent(this, EditProductActivity.class);
        intent.putExtra("PRODUCT_ID", producto.getId());
        startActivityForResult(intent, 2);
    }

    @Override
    public void onDeleteProduct(Producto producto) {
        showDeleteDialog(producto);
    }

    private void showDeleteDialog(Producto producto) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Producto")
                .setMessage("¿Estás seguro de que deseas eliminar este producto?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    productoDAO.deleteProduct(producto.getId());
                    productoList = productoDAO.getAllProducts();
                    productAdapter.updateList(productoList);
                })
                .setNegativeButton("No", null)
                .show();
    }
}
