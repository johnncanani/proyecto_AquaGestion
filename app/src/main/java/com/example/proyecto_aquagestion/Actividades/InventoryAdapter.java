package com.example.proyecto_aquagestion.Actividades;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyecto_aquagestion.Entidades.Producto;
import com.example.proyecto_aquagestion.R;

import java.util.ArrayList;
import java.util.List;

public class InventoryAdapter extends ArrayAdapter<Producto> implements Filterable {
    private Context context;
    private List<Producto> productos;
    private List<Producto> productsFull; // Lista completa para el filtrado

    public InventoryAdapter(Context context, List<Producto> productos) {
        super(context, 0, productos);
        this.context = context;
        this.productos = new ArrayList<>(productos);
        this.productsFull = new ArrayList<>(productos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.inventory_item, parent, false);
        }

        Producto producto = getItem(position);

        ImageView productImage = convertView.findViewById(R.id.productImage);
        TextView productName = convertView.findViewById(R.id.productName);
        TextView productVolume = convertView.findViewById(R.id.productVolume);
        TextView productStock = convertView.findViewById(R.id.productStock);

        productName.setText(producto.getName());
        productVolume.setText("Volumen: " + producto.getVolume());
        productStock.setText("Stock: " + producto.getCantidad());

        // Convertir el byte array a Bitmap y establecer la imagen del producto
        if (producto.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(producto.getImage(), 0, producto.getImage().length);
            productImage.setImageBitmap(bitmap);
        } else {
            productImage.setImageResource(R.drawable.ic_launcher_background); // Imagen predeterminada si no hay imagen
        }

        // Verificar si el stock está por debajo del umbral (por ejemplo, 10 unidades)
        if (producto.getCantidad() < 10) {
            productStock.setTextColor(Color.RED);
        } else {
            productStock.setTextColor(Color.BLACK);
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return productFilter;
    }

    private Filter productFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Producto> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(productsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Producto producto : productsFull) {
                    if (producto.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(producto);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productos.clear();
            productos.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void updateList(List<Producto> newList) {
        productos.clear();
        productos.addAll(newList);
        productsFull.clear();
        productsFull.addAll(newList);
        notifyDataSetChanged();
    }
}
