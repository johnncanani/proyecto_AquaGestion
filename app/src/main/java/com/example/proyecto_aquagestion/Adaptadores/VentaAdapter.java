package com.example.proyecto_aquagestion.Adaptadores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_aquagestion.Entidades.Producto;
import com.example.proyecto_aquagestion.Entidades.Venta;
import com.example.proyecto_aquagestion.R;

import java.util.List;

public class VentaAdapter extends RecyclerView.Adapter<VentaAdapter.VentaViewHolder> {

    private List<Venta> ventas;
    private List<Producto> productos;

    public VentaAdapter(List<Venta> ventas, List<Producto> productos) {
        this.ventas = ventas;
        this.productos = productos;
    }

    @NonNull
    @Override
    public VentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_venta, parent, false);
        return new VentaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VentaViewHolder holder, int position) {
        Venta venta = ventas.get(position);
        Producto producto = obtenerProductoPorId(venta.getIdProducto());
        holder.bind(venta, producto);
    }

    @Override
    public int getItemCount() {
        return ventas.size();
    }

    private Producto obtenerProductoPorId(int idProducto) {
        for (Producto producto : productos) {
            if (producto.getId() == idProducto) {
                return producto;
            }
        }
        return null;
    }

    static class VentaViewHolder extends RecyclerView.ViewHolder {

        private TextView tvProductName, tvProductQuantity, tvProductPrice, tvTotalPago, tvFecha;
        private ImageView ivProductImage;

        public VentaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductQuantity = itemView.findViewById(R.id.tvProductQuantity);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvTotalPago = itemView.findViewById(R.id.tvTotalPago);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
        }

        public void bind(Venta venta, Producto producto) {
            if (producto != null) {
                tvProductName.setText("Producto: " + producto.getName());
                if (producto.getImage() != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(producto.getImage(), 0, producto.getImage().length);
                    ivProductImage.setImageBitmap(bitmap);
                } else {
                    ivProductImage.setImageResource(R.drawable.default_image); // Imagen por defecto
                }
            } else {
                tvProductName.setText("Producto: " + venta.getIdProducto());
                ivProductImage.setImageResource(R.drawable.default_image); // Imagen por defecto
            }
            tvProductQuantity.setText("Cantidad: " + venta.getCantidad());
            tvProductPrice.setText("Precio: " + (venta.getTotalPagado() / venta.getCantidad()));
            tvTotalPago.setText("Total: " + venta.getTotalPagado());
            tvFecha.setText("Fecha: " + venta.getFecha());
        }
    }
}
