package com.example.graduationproject.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.graduationproject.Fragments.DeleteDialogFragment;
import com.example.graduationproject.Interface.OnChangeScroll;
import com.example.graduationproject.Interface.OndeleteProduct;
import com.example.graduationproject.Interface.ProductsAction;

import com.example.graduationproject.databinding.ItemproductBinding;
import com.example.graduationproject.Model.Product;


import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapterVH> {
     ArrayList<Product> productArrayList=new ArrayList<>();
    Context context;
    ProductsAction productsAction;





    public ProductAdapter(ArrayList<Product> productArrayList, Context context, ProductsAction productsAction) {
        this.productArrayList = productArrayList;
        this.context = context;
        this.productsAction = productsAction;
    }

    @NonNull
    @Override
    public ProductAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductAdapterVH productAdapterVH= new ProductAdapterVH(ItemproductBinding.inflate(LayoutInflater.from(parent.getContext())));

        return productAdapterVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapterVH holder, int position) {
        int pos=position;
        holder.productprice.setText(productArrayList.get(position).getPrice());
        holder.productname.setText(productArrayList.get(position).getName());

//        Glide.with(context).load(Uri.parse(productArrayList.get(position).getImage())).into(holder.imageViewProduct);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                productsAction.OnDelete(productArrayList.get(pos).getName(),pos);
            }
        });
        holder.edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productsAction.OnUpdate(productArrayList.get(pos));

            }
        });
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        productsAction.OnClickItem(productArrayList.get(pos));
    }
});
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }
}
class ProductAdapterVH extends RecyclerView.ViewHolder{
ImageView imageViewProduct;
TextView productname;
TextView productprice;
ImageView edite;
ImageView delete;

    public ProductAdapterVH(@NonNull ItemproductBinding binding) {
        super(binding.getRoot());
        imageViewProduct =binding.imageViewproduct;
        productname=binding.tvProductName;
        productprice=binding.tvProductPrice;
        edite=binding.edit;
        delete=binding.delete;


    }
}