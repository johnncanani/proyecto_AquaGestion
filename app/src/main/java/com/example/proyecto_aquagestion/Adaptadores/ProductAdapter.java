package com.example.proyecto_aquagestion.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyecto_aquagestion.Entidades.Producto;
import com.example.proyecto_aquagestion.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Producto> {

    private final Context context;
    private final List<Producto> originalProductoList; // Lista original para mantener todos los productos
    private List<Producto> filteredProductoList; // Lista filtrada que se muestra en el ListView
    private final OnProductActionListener listener;

    public interface OnProductActionListener {
        void onEditProduct(Producto producto);
        void onDeleteProduct(Producto producto);
    }

    public ProductAdapter(Context context, List<Producto> productoList, OnProductActionListener listener) {
        super(context, 0, productoList);
        this.context = context;
        this.originalProductoList = new ArrayList<>(productoList);
        this.filteredProductoList = new ArrayList<>(productoList);
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        }

        Producto producto = getItem(position);

        TextView tvProductName = convertView.findViewById(R.id.tvProductName);
        TextView tvProductVolume = convertView.findViewById(R.id.tvProductVolume);
        TextView tvProductPrice = convertView.findViewById(R.id.tvProductPrice);
        TextView tvProductQuantity = convertView.findViewById(R.id.tvProductQuantity);
        ImageView ivProductImage = convertView.findViewById(R.id.ivProductImage);
        ImageButton btnEdit = convertView.findViewById(R.id.btnEdit);
        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);

        tvProductName.setText(producto.getName());
        tvProductVolume.setText(String.format("Volumen: %.2f", producto.getVolume()));
        tvProductPrice.setText(String.format("Precio: %.2f", producto.getPrice()));
        tvProductQuantity.setText(String.format("Cantidad: %d", producto.getCantidad()));

        if (producto.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(producto.getImage(), 0, producto.getImage().length);
            ivProductImage.setImageBitmap(bitmap);
        } else {
            ivProductImage.setImageResource(R.drawable.default_image);
        }

        btnEdit.setOnClickListener(v -> listener.onEditProduct(producto));
        btnDelete.setOnClickListener(v -> listener.onDeleteProduct(producto));

        return convertView;
    }

    @Override
    public int getCount() {
        return filteredProductoList.size();
    }

    @Override
    public Producto getItem(int position) {
        return filteredProductoList.get(position);
    }

    public void updateList(List<Producto> newList) {
        originalProductoList.clear();
        originalProductoList.addAll(newList);
        filteredProductoList.clear();
        filteredProductoList.addAll(newList);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        query = query.toLowerCase();
        List<Producto> filteredList = new ArrayList<>();
        for (Producto producto : originalProductoList) {
            if (producto.getName().toLowerCase().contains(query)) {
                filteredList.add(producto);
            }
        }
        filteredProductoList = filteredList;
        notifyDataSetChanged();
    }
}
