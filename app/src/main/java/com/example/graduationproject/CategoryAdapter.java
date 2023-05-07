package com.example.graduationproject;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.graduationproject.databinding.ItemcategoryBinding;

import java.util.List;

public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    List<String> categories_name;
    List<String> categories_image;
    Context context;
    ListenerOnClickItem listener;
    //ListenerFavarite listenerFavarite;


    public CategoryAdapter(List<String> categories_name,List<String> categories_image, Context context, ListenerOnClickItem listener) {
        this.categories_name = categories_name;
        this.context = context;
        this.listener = listener;
        this.categories_image=categories_image;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemcategoryBinding binding = ItemcategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);


    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int pos = position;
        holder.category_name.setText(categories_name.get(pos));
        Glide.with(context).load(categories_image.get(pos)).into(holder.imageView);
         holder.imageView.setImageURI(Uri.parse(categories_image.get(pos).toString()));;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.OnClickItem(categories_name.get(pos));
            }
        });



    }


    @Override
    public int getItemCount() {
        return categories_name.size();
    }

    public   class MyViewHolder extends RecyclerView.ViewHolder {
        TextView category_name;
        ImageView imageView;


        public MyViewHolder(@NonNull ItemcategoryBinding binding) {
            super(binding.getRoot());

            category_name = binding.category1;
            imageView = binding.image1;



        }

    }
}

