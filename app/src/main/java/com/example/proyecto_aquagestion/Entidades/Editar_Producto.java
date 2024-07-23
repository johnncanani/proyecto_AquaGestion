package com.example.proyecto_aquagestion.Entidades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_aquagestion.BaseDatos.BD_Producto;
import com.example.proyecto_aquagestion.R;

import java.io.IOException;

public class Editar_Producto extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText etProductName, etProductCapacity, etProductDescription, etProductPrice;
    private ImageView ivProductImage;
    private Button btnUpdateProduct, btnSelectImage;
    private Uri imageUri;
    private BD_Producto dbHelper;
    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        etProductName = findViewById(R.id.etProductName);
        etProductCapacity = findViewById(R.id.etProductCapacity);
        etProductDescription = findViewById(R.id.etProductDescription);
        etProductPrice = findViewById(R.id.etProductPrice);
        ivProductImage = findViewById(R.id.ivProductImage);
        btnUpdateProduct = findViewById(R.id.btnUpdateProduct);
        btnSelectImage = findViewById(R.id.btnSelectImage);

        dbHelper = new BD_Producto(this);

        productId = getIntent().getIntExtra("productId", -1);
        if (productId != -1) {
            loadProductDetails(productId);
        }

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct();
            }
        });
    }

    private void loadProductDetails(int productId) {
        Producto product = dbHelper.getProduct(productId);
        if (product != null) {
            etProductName.setText(product.getName());
            etProductCapacity.setText(product.getCapacity());
            etProductDescription.setText(product.getDescription());
            etProductPrice.setText(product.getPrice());
            if (product.getImageUri() != null) {
                imageUri = Uri.parse(product.getImageUri());
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    ivProductImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ivProductImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateProduct() {
        String name = etProductName.getText().toString();
        String capacity = etProductCapacity.getText().toString();
        String description = etProductDescription.getText().toString();
        String price = etProductPrice.getText().toString();

        if (name.isEmpty() || capacity.isEmpty() || description.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Producto product = new Producto(productId, name, capacity, description, price, imageUri != null ? imageUri.toString() : null);
        dbHelper.updateProduct(product);
        Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
