package com.example.proyecto_aquagestion.Actividades;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_aquagestion.DAO.ProductoDAO;
import com.example.proyecto_aquagestion.Entidades.Producto;
import com.example.proyecto_aquagestion.R;

import java.util.ArrayList;
import java.util.List;

public class InventoryActivity extends AppCompatActivity {
    private ProductoDAO productoDAO;
    private ListView inventoryListView;
    private List<Producto> productoList;
    private InventoryAdapter inventoryAdapter;
    private EditText etSearchInventory;
    private Button btnAddStock, btnRemoveStock;
    private static final int LOW_STOCK_THRESHOLD = 10; // Umbral de bajo inventario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        productoDAO = new ProductoDAO(this);
        inventoryListView = findViewById(R.id.inventoryListView);
        etSearchInventory = findViewById(R.id.etSearchInventory);
        btnAddStock = findViewById(R.id.btnAddStock);
        btnRemoveStock = findViewById(R.id.btnRemoveStock);

        loadInventoryData();

        inventoryListView.setOnItemClickListener((parent, view, position, id) -> {
            Producto selectedProducto = productoList.get(position);
            showUpdateDialog(selectedProducto);
        });

        etSearchInventory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inventoryAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnAddStock.setOnClickListener(v -> showStockDialog(true));
        btnRemoveStock.setOnClickListener(v -> showStockDialog(false));
    }

    private void loadInventoryData() {
        productoList = productoDAO.getAllProducts();
        inventoryAdapter = new InventoryAdapter(this, productoList);
        inventoryListView.setAdapter(inventoryAdapter);
        checkLowStock();
    }

    private void checkLowStock() {
        List<String> lowStockProducts = new ArrayList<>();
        for (Producto producto : productoList) {
            if (producto.getCantidad() < LOW_STOCK_THRESHOLD) {
                lowStockProducts.add(producto.getName());
            }
        }

        if (!lowStockProducts.isEmpty()) {
            showLowStockAlert(lowStockProducts);
        }
    }

    private void showLowStockAlert(List<String> lowStockProducts) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alerta de Bajo Inventario");
        builder.setMessage("Los siguientes productos tienen bajo inventario:\n\n" + String.join("\n", lowStockProducts));
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void showUpdateDialog(final Producto producto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Actualizar Stock");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Agregar", (dialog, which) -> {
            int newStock = Integer.parseInt(input.getText().toString());
            int updatedStock = producto.getCantidad() + newStock;
            producto.setCantidad(updatedStock);
            productoDAO.updateProductQuantity(producto.getId(), updatedStock);
            inventoryAdapter.updateList(productoDAO.getAllProducts());
            checkLowStock();
        });

        builder.setNegativeButton("Quitar", (dialog, which) -> {
            int newStock = Integer.parseInt(input.getText().toString());
            int updatedStock = producto.getCantidad() - newStock;
            if (updatedStock < 0) {
                updatedStock = 0;
            }
            producto.setCantidad(updatedStock);
            productoDAO.updateProductQuantity(producto.getId(), updatedStock);
            inventoryAdapter.updateList(productoDAO.getAllProducts());
            checkLowStock();
        });

        builder.setNeutralButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showStockDialog(boolean isAdding) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(isAdding ? "Agregar Stock" : "Quitar Stock");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            int stockChange = Integer.parseInt(input.getText().toString());
            for (Producto producto : productoList) {
                int updatedStock = isAdding ? producto.getCantidad() + stockChange : producto.getCantidad() - stockChange;
                if (updatedStock < 0) {
                    updatedStock = 0;
                }
                producto.setCantidad(updatedStock);
                productoDAO.updateProductQuantity(producto.getId(), updatedStock);
            }
            inventoryAdapter.updateList(productoDAO.getAllProducts());
            checkLowStock();
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
