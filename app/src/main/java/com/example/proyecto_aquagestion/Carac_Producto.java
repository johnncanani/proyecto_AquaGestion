package com.example.proyecto_aquagestion;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_aquagestion.Entidades.Producto;

import java.util.ArrayList;
import java.util.List;

public class Carac_Producto extends RecyclerView.Adapter<Carac_Producto.ProductViewHolder> {

    private List<Producto> productList;
    private List<Producto> productListFull;
    private ProductClickListener clickListener;
    private ProductDeleteListener deleteListener;

    public interface ProductClickListener {
        void onProductClick(Producto product);
    }

    public interface ProductDeleteListener {
        void onProductDelete(int productId);
    }

    public Carac_Producto(List<Producto> productList, ProductClickListener clickListener, ProductDeleteListener deleteListener) {
        this.productList = productList;
        this.productListFull = new ArrayList<>(productList);
        this.clickListener = clickListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Producto product = productList.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvProductCapacity.setText(product.getCapacity());
        holder.tvProductDescription.setText(product.getDescription());
        holder.tvProductPrice.setText(product.getPrice());

        if (product.getImageUri() != null) {
            holder.ivProductImage.setImageURI(Uri.parse(product.getImageUri()));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onProductClick(product);
            }
        });

        holder.btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListener.onProductDelete(product.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void updateList(List<Producto> newProductList) {
        productList = newProductList;
        productListFull = new ArrayList<>(newProductList);
        notifyDataSetChanged();
    }

    public void filter(String text) {
        List<Producto> filteredList = new ArrayList<>();
        for (Producto product : productListFull) {
            if (product.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(product);
            }
        }
        productList = filteredList;
        notifyDataSetChanged();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductCapacity, tvProductDescription, tvProductPrice;
        ImageView ivProductImage;
        Button btnDeleteProduct;

        ProductViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductCapacity = itemView.findViewById(R.id.tvProductCapacity);
            tvProductDescription = itemView.findViewById(R.id.tvProductDescription);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            btnDeleteProduct = itemView.findViewById(R.id.btnDeleteProduct);
        }
    }
}
