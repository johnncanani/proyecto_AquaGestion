package com.example.proyecto_aquagestion.Actividades;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.proyecto_aquagestion.DAO.ProductoDAO;
import com.example.proyecto_aquagestion.Entidades.Producto;
import com.example.proyecto_aquagestion.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddProductActivity extends Activity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText etProductName, etProductType, etProductVolume, etProductPrice, etProductCantidad;
    private ImageView ivProductImage;
    private Button btnAddProduct, btnSelectImage;
    private byte[] selectedImage;
    private ProductoDAO productoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        etProductName = findViewById(R.id.etProductName);
        etProductType = findViewById(R.id.etProductType);
        etProductVolume = findViewById(R.id.etProductVolume);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductCantidad = findViewById(R.id.etProductCantidad);
        ivProductImage = findViewById(R.id.ivProductImage);
        btnAddProduct = findViewById(R.id.btnSaveProduct);
        btnSelectImage = findViewById(R.id.btnSelectImage);

        productoDAO = new ProductoDAO(this);

        btnSelectImage.setOnClickListener(v -> openImageSelector());

        btnAddProduct.setOnClickListener(v -> saveProduct());
    }

    private void openImageSelector() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar Imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                ivProductImage.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                selectedImage = stream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveProduct() {
        String name = etProductName.getText().toString();
        String type = etProductType.getText().toString();
        double volume;
        double price;
        int cantidad;

        if (name.isEmpty() || type.isEmpty() || etProductVolume.getText().toString().isEmpty() ||
                etProductPrice.getText().toString().isEmpty() || etProductCantidad.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            volume = Double.parseDouble(etProductVolume.getText().toString());
            price = Double.parseDouble(etProductPrice.getText().toString());
            cantidad = Integer.parseInt(etProductCantidad.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error en la entrada de datos.", Toast.LENGTH_SHORT).show();
            return;
        }

        Producto producto = new Producto(name, type, volume, price, selectedImage, cantidad);
        long result = productoDAO.addProduct(producto);

        if (result != -1) {
            Toast.makeText(this, "Producto guardado", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK); // Notificar que el producto se guardó correctamente
            finish();
        } else {
            Toast.makeText(this, "Error al guardar el producto.", Toast.LENGTH_SHORT).show();
        }
    }
}
