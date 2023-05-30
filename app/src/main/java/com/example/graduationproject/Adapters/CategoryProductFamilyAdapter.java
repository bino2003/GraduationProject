package com.example.graduationproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.graduationproject.Interface.OnClickProductiveFamily;

import com.example.graduationproject.R;
import com.example.graduationproject.databinding.ProductivefamilyitemBinding;
import com.example.graduationproject.Model.ProductiveFamily;


import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CategoryProductFamilyAdapter extends RecyclerView.Adapter<CategoryProductFamilyVH>{
    Context context;
    OnClickProductiveFamily onClickProductiveFamily;
    ArrayList<ProductiveFamily> productiveFamilyArrayList=new ArrayList<>();

    public CategoryProductFamilyAdapter(Context context, OnClickProductiveFamily onClickProductiveFamily, ArrayList<ProductiveFamily> productiveFamilyArrayList) {
        this.context = context;
        this.onClickProductiveFamily = onClickProductiveFamily;
        this.productiveFamilyArrayList = productiveFamilyArrayList;
    }

    @NonNull
    @Override
    public CategoryProductFamilyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryProductFamilyVH categoryProductFamilyVH= new CategoryProductFamilyVH(ProductivefamilyitemBinding.inflate(LayoutInflater.from(parent.getContext())));

        return categoryProductFamilyVH;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryProductFamilyVH holder, int position) {
        float rating=0;
        int pos=position;
        holder.location.setText(productiveFamilyArrayList.get(position).getLocation());
        if (productiveFamilyArrayList.get(pos).getEvaluation()!=null){
            List<String> listreating=productiveFamilyArrayList.get(pos).getEvaluation();
            for (int i=0;i<listreating.size();i++) {
                String num= listreating.get(i);
                rating=rating+Float.parseFloat(num);
                if (i==listreating.size()-1){
                    float avg=rating/listreating.size();


                    holder.ratingBar.setRating(avg);
                }

            }
        }




 Glide.with(context).load(productiveFamilyArrayList.get(position).getImage()).circleCrop().into(holder.imageView);

//if (!productiveFamilyArrayList.get(position).getImage().isEmpty()){
//    Glide.with(context).load(productiveFamilyArrayList.get(position).getImage()).circleCrop().into(holder.imageView);
//
//}else if (productiveFamilyArrayList.get(position).getImage().isEmpty()){
//
//}



        holder.name.setText(productiveFamilyArrayList.get(position).getName());
        if (productiveFamilyArrayList.get(pos).getDetails()!=null){
            holder.details.setText(productiveFamilyArrayList.get(pos).getDetails());

        }
        holder.details.setText(productiveFamilyArrayList.get(position).getDetails());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickProductiveFamily.onclickproductiveFamily(productiveFamilyArrayList.get(pos));
            }
        });

    }

    @Override
    public int getItemCount() {
        return productiveFamilyArrayList.size();
    }
}
 class CategoryProductFamilyVH extends RecyclerView.ViewHolder {
    TextView name;
    TextView details;
    TextView location;
    RatingBar ratingBar;
    ImageView imageView;
    public CategoryProductFamilyVH(@NonNull ProductivefamilyitemBinding binding) {

        super(binding.getRoot());
        name=binding.name;
        imageView=binding.imageView5;
        details =binding.desception;
        location=binding.set;
        ratingBar=binding.ratingBar;
    }
}
