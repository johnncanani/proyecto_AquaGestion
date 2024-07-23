package com.example.proyecto_aquagestion;

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
import com.example.proyecto_aquagestion.Entidades.Producto;

import java.io.IOException;

public class Activity_agregar_Producto extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText etProductName, etProductCapacity, etProductDescription, etProductPrice;
    private ImageView ivProductImage;
    private Button btnAddProduct, btnSelectImage;
    private Uri imageUri;
    private BD_Producto dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        etProductName = findViewById(R.id.etProductName);
        etProductCapacity = findViewById(R.id.etProductCapacity);
        etProductDescription = findViewById(R.id.etProductDescription);
        etProductPrice = findViewById(R.id.etProductPrice);
        ivProductImage = findViewById(R.id.ivProductImage);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnSelectImage = findViewById(R.id.btnSelectImage);

        dbHelper = new BD_Producto(this);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
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

    private void addProduct() {
        String name = etProductName.getText().toString();
        String capacity = etProductCapacity.getText().toString();
        String description = etProductDescription.getText().toString();
        String price = etProductPrice.getText().toString();

        if (name.isEmpty() || capacity.isEmpty() || description.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Producto product = new Producto(name, capacity, description, price, imageUri != null ? imageUri.toString() : null);
        dbHelper.addProduct(product);
        Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
