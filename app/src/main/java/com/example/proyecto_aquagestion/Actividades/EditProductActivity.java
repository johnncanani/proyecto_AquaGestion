package com.example.proyecto_aquagestion.Actividades;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
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

public class EditProductActivity extends Activity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etProductName;
    private EditText etProductType;
    private EditText etProductVolume;
    private EditText etProductPrice;
    private EditText etProductCantidad; // Nuevo campo
    private ImageView ivSelectedImage;
    private Button btnSelectImage;
    private Button btnSaveProduct;

    private byte[] selectedImage;
    private ProductoDAO productoDAO;
    private Producto productoToEdit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        etProductName = findViewById(R.id.etProductName);
        etProductType = findViewById(R.id.etProductType);
        etProductVolume = findViewById(R.id.etProductVolume);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductCantidad = findViewById(R.id.etProductCantidad); // Nuevo campo
        ivSelectedImage = findViewById(R.id.ivProductImage);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSaveProduct = findViewById(R.id.btnSaveProduct);

        productoDAO = new ProductoDAO(this);

        Intent intent = getIntent();
        if (intent.hasExtra("PRODUCT_ID")) {
            int productId = intent.getIntExtra("PRODUCT_ID", -1);
            if (productId != -1) {
                productoToEdit = productoDAO.getProductById(productId); // Obtener el producto desde el DAO
                if (productoToEdit != null) {
                    populateProductDetails(productoToEdit);
                } else {
                    Toast.makeText(this, "ID de producto no encontrado", Toast.LENGTH_SHORT).show(); // Mensaje de error
                }
            }
        }

        btnSelectImage.setOnClickListener(v -> openImageSelector());

        btnSaveProduct.setOnClickListener(v -> saveProduct());

        // Manejo del botón de Regresar
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra la actividad actual y regresa a la anterior
            }
        });
    }

    private void populateProductDetails(Producto producto) {
        etProductName.setText(producto.getName());
        etProductType.setText(producto.getType());
        etProductVolume.setText(String.valueOf(producto.getVolume()));
        etProductPrice.setText(String.valueOf(producto.getPrice()));
        etProductCantidad.setText(String.valueOf(producto.getCantidad())); // Nuevo campo
        selectedImage = producto.getImage();
        if (selectedImage != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(selectedImage, 0, selectedImage.length);
            ivSelectedImage.setImageBitmap(bitmap);
        }
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
                ivSelectedImage.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                selectedImage = stream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveProduct() {
        if (productoToEdit == null) {
            Toast.makeText(this, "No se puede actualizar el producto.", Toast.LENGTH_SHORT).show();
            return;
        }

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
            cantidad = Integer.parseInt(etProductCantidad.getText().toString()); // Nuevo campo
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error en la entrada de datos.", Toast.LENGTH_SHORT).show();
            return;
        }

        productoToEdit.setName(name);
        productoToEdit.setType(type);
        productoToEdit.setVolume(volume);
        productoToEdit.setPrice(price);
        productoToEdit.setCantidad(cantidad); // Nuevo campo
        if (selectedImage != null) {
            productoToEdit.setImage(selectedImage);
        }

        int result = productoDAO.updateProduct(productoToEdit);

        if (result > 0) {
            Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK); // Notificar que la actualización fue exitosa
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar el producto.", Toast.LENGTH_SHORT).show();
        }
    }
}
