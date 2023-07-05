package com.example.graduationproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.graduationproject.Interface.OnDelete;
import com.example.graduationproject.Interface.UnFavoritve;
import com.example.graduationproject.Model.Favorite2;
import com.example.graduationproject.databinding.FavouriteitemBinding;
import com.example.graduationproject.Model.Favorites;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdpter extends RecyclerView.Adapter<FavoriteAdpter.MyViewHolder> {
   ArrayList<Favorite2> favorites;
    Context context;
    UnFavoritve unFavoritve;

    public FavoriteAdpter(ArrayList<Favorite2> favorites, Context context, UnFavoritve unFavoritve) {
        this.favorites = favorites;
        this.context = context;
        this.unFavoritve = unFavoritve;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FavouriteitemBinding binding = FavouriteitemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FavoriteAdpter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        int pos = position;
        holder.price.setText(favorites.get(pos).getPrice());
        holder.productive_family_name.setText(favorites.get(pos).getProductiveFamilyId());
        holder.product_name.setText(favorites.get(pos).getName());
        if (favorites.get(pos).getImageUrls().size()!=0){
            Glide.with(context).load(favorites.get(pos).getImageUrls().get(0)).into(holder.imageView);
        }else {
            Glide.with(context).load(favorites.get(pos).getImage()).into(holder.imageView);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unFavoritve.OnDelete(pos,favorites.get(pos).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public   class MyViewHolder extends RecyclerView.ViewHolder {
        TextView    productive_family_name;
        TextView    price;
        TextView    product_name;
        ImageView imageView;
        ImageView delete;


        public MyViewHolder(@NonNull FavouriteitemBinding binding) {
            super(binding.getRoot());

            price = binding.ProductPrice;
            product_name = binding.ProductName;
            productive_family_name = binding.prodectiveFamilyName;
            imageView = binding.imageview;
            delete=binding.delete;

        }

    }
}
