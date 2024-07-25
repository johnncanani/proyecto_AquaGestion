package com.example.proyecto_aquagestion;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_aquagestion.BaseDatos.BD_Producto;
import com.example.proyecto_aquagestion.Entidades.Producto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Activity_realizar_venta extends AppCompatActivity {

    private BD_Producto dbHelper;
    private TextView tvFecha;
    private Spinner spinnerProductos;
    private TextView tvDescripcionProducto;
    private TextView tvPrecioProducto;
    private EditText etCantidad;
    private TextView tvTotalVenta;
    private Button btnRealizarVenta;
    private Button btnCancelarVenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_realizar_venta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new BD_Producto(this);

        // Inicializar las vistas
        tvFecha = findViewById(R.id.tvFecha);
        spinnerProductos = findViewById(R.id.spinnerProductos);
        tvDescripcionProducto = findViewById(R.id.tvDescripcionProducto);
        tvPrecioProducto = findViewById(R.id.tvPrecioProducto);
        etCantidad = findViewById(R.id.etCantidad);
        tvTotalVenta = findViewById(R.id.tvTotalVenta);
        btnRealizarVenta = findViewById(R.id.btnRealizarVenta);
        btnCancelarVenta = findViewById(R.id.btnCancelarVenta);

        // Configurar la fecha actual
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        tvFecha.setText(currentDate);

        // Cargar los productos en el spinner
        cargarProductosEnSpinner();

        // Configurar los botones
        btnRealizarVenta.setOnClickListener(this::realizarVenta);
        btnCancelarVenta.setOnClickListener(this::cancelar_venta);

        // Verificar si se están editando datos
        Intent intent = getIntent();
        if (intent.hasExtra("datosVenta")) {
            // Cargar datos para edición
            String datosVenta = intent.getStringExtra("datosVenta");
            cargarDatosParaEdicion(datosVenta);
        }
    }

    private void cargarProductosEnSpinner() {
        List<Producto> productos = dbHelper.getAllProducts();
        ArrayAdapter<Producto> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, productos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProductos.setAdapter(adapter);

        spinnerProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Producto productoSeleccionado = (Producto) parent.getItemAtPosition(position);
                tvDescripcionProducto.setText(productoSeleccionado.getDescription());
                tvPrecioProducto.setText(productoSeleccionado.getPrice());
                calcularTotalVenta();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tvDescripcionProducto.setText("");
                tvPrecioProducto.setText("");
                tvTotalVenta.setText("");
            }
        });

        etCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                calcularTotalVenta();
            }
        });
    }

    private void calcularTotalVenta() {
        String precioString = tvPrecioProducto.getText().toString();
        String cantidadString = etCantidad.getText().toString();

        if (!precioString.isEmpty() && !cantidadString.isEmpty()) {
            double precio = Double.parseDouble(precioString);
            int cantidad = Integer.parseInt(cantidadString);
            double total = precio * cantidad;
            tvTotalVenta.setText(String.format(Locale.getDefault(), "%.2f", total));
        } else {
            tvTotalVenta.setText("");
        }
    }

    public void realizarVenta(View view) {
        String producto = spinnerProductos.getSelectedItem().toString();
        String cantidad = etCantidad.getText().toString();
        String fecha = tvFecha.getText().toString();
        String descripcion = tvDescripcionProducto.getText().toString();
        String total = tvTotalVenta.getText().toString();

        // Formar el string de datos de la venta
        String datosVenta = "Producto: " + producto + "\nCantidad: " + cantidad + "\nFecha: " + fecha + "\nDescripción: " + descripcion + "\nTotal: " + total;

        // Pasar los datos a la actividad de confirmación
        Intent confirmarVentaIntent = new Intent(Activity_realizar_venta.this, Activity_confirmar_venta.class);
        confirmarVentaIntent.putExtra("datosVenta", datosVenta);
        startActivity(confirmarVentaIntent);
    }

    public void cancelar_venta(View view) {
        Toast.makeText(this, "Venta cancelada", Toast.LENGTH_SHORT).show();
        Intent venta_cancelar = new Intent(this, Activity_Menu_Principal.class);
        startActivity(venta_cancelar);
    }

    private void cargarDatosParaEdicion(String datosVenta) {
        // Dividir los datos de la venta para obtener los valores individuales
        String[] datos = datosVenta.split("\n");
        String producto = datos[0].split(": ")[1];
        String cantidad = datos[1].split(": ")[1];
        String fecha = datos[2].split(": ")[1];
        String descripcion = datos[3].split(": ")[1];
        String total = datos[4].split(": ")[1];

        // Rellenar los campos con los datos obtenidos
        tvFecha.setText(fecha);
        etCantidad.setText(cantidad);
        tvDescripcionProducto.setText(descripcion);
        tvTotalVenta.setText(total);

        // Seleccionar el producto en el spinner
        ArrayAdapter<Producto> adapter = (ArrayAdapter<Producto>) spinnerProductos.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).toString().equals(producto)) {
                spinnerProductos.setSelection(i);
                break;
            }
        }
    }
}
