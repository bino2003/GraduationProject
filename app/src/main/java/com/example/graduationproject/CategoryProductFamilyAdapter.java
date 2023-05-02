package com.example.graduationproject;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.graduationproject.model.ProductiveFamily;

import java.util.ArrayList;

public class CategoryProductFamilyAdapter extends RecyclerView.Adapter<CategoryProductFamilyVH>{
    Context context;
    ListenerOnClickItem onClickItem;
    ArrayList<ProductiveFamily> productiveFamilyArrayList=new ArrayList<>();

    public CategoryProductFamilyAdapter(Context context, ListenerOnClickItem onClickItem, ArrayList<ProductiveFamily> productiveFamilyArrayList) {
        this.context = context;
        this.onClickItem = onClickItem;
        this.productiveFamilyArrayList = productiveFamilyArrayList;
    }

    @NonNull
    @Override
    public CategoryProductFamilyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view=  LayoutInflater.from(parent.getContext()).inflate(R.layout.productivefamilyitem,null);
        return new  CategoryProductFamilyVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryProductFamilyVH holder, int position) {
        int pos=position;
        holder.location.setText(productiveFamilyArrayList.get(position).getLocation());
if (productiveFamilyArrayList.get(pos).getEvaluation()!=null&&productiveFamilyArrayList.get(pos).getEvaluation()!=""){
    holder.ratingBar.setRating(Integer.parseInt(productiveFamilyArrayList.get(position).getEvaluation()));

}
//if (productiveFamilyArrayList.get(position).getImage()!=null){
//    Glide.with(context).load(productiveFamilyArrayList.get(position).getImage()).circleCrop().into(holder.imageView);
//
//}else if (productiveFamilyArrayList.get(position).getImage().equals(null)){
//}
        Glide.with(context).load(R.drawable.mcdonalds).circleCrop().into(holder.imageView);



        holder.name.setText(productiveFamilyArrayList.get(position).getName());
        holder.details.setText(productiveFamilyArrayList.get(position).getDetails());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItem.OnClickItem(productiveFamilyArrayList.get(pos).getName());
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
    public CategoryProductFamilyVH(@NonNull View view) {

        super(view);
        name=view.findViewById(R.id.tv_name);
        imageView=view.findViewById(R.id.imageproductivecat);
        details =view.findViewById(R.id.tv_details);
        location=view.findViewById(R.id.tv_location);
        ratingBar=view.findViewById(R.id.ratingBar);
    }
}
