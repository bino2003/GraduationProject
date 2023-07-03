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
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DistinguishedFamilyAdapter extends RecyclerView.Adapter<DistinguishedFamilyVH> {
    Context context;
    OnClickProductiveFamily onClickProductiveFamily;
    ArrayList<ProductiveFamily> productiveFamilyArrayList=new ArrayList<>();

    public  void  setFilterList(ArrayList<ProductiveFamily> productiveFamilies){
        this.productiveFamilyArrayList=productiveFamilies;
        notifyDataSetChanged();
    }
    public DistinguishedFamilyAdapter(Context context, OnClickProductiveFamily onClickProductiveFamily, ArrayList<ProductiveFamily> productiveFamilyArrayList) {
        this.context = context;
        this.onClickProductiveFamily = onClickProductiveFamily;
        this.productiveFamilyArrayList = productiveFamilyArrayList;
    }


    @Override
    public DistinguishedFamilyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DistinguishedFamilyVH distinguishedFamilyVH= new DistinguishedFamilyVH(ProductivefamilyitemBinding.inflate(LayoutInflater.from(parent.getContext())));

        return distinguishedFamilyVH;
    }

    @Override
    public void onBindViewHolder(@NonNull DistinguishedFamilyVH holder, int position) {
        float rating=0;
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        int pos=position;
        holder.location.setText(productiveFamilyArrayList.get(position).getLocation());
        if (productiveFamilyArrayList.get(pos).getEvaluation()!=null){
            List<String> listreating=productiveFamilyArrayList.get(pos).getEvaluation();
            for (int i=0;i<listreating.size();i++) {
                String num= listreating.get(i);
                rating=rating+Float.parseFloat(num);
                if (i==listreating.size()-1){
                    float avg=rating/listreating.size();
productiveFamilyArrayList.get(pos).setRating(String.valueOf(avg));
firebaseFirestore.collection("Productive family").document(productiveFamilyArrayList.get(pos).getId()).update("rating",String.valueOf(avg));

                    holder.ratingBar.setRating(avg);
                    productiveFamilyArrayList.get(pos).setRating(String.valueOf(avg));
                }

            }
        }




     Glide.with(context).load(productiveFamilyArrayList.get(position).getImage()).circleCrop().into(holder.imageView);

//        if (!productiveFamilyArrayList.get(position).getImage().isEmpty()){
//            Glide.with(context).load(productiveFamilyArrayList.get(position).getImage()).circleCrop().into(holder.imageView);
//
//        }else if (productiveFamilyArrayList.get(position).getImage().isEmpty()){
//
//        }



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
class DistinguishedFamilyVH extends RecyclerView.ViewHolder{

    TextView name;
    TextView details;
    TextView location;
    RatingBar ratingBar;
    ImageView imageView;
    public DistinguishedFamilyVH(@NonNull ProductivefamilyitemBinding binding) {

        super(binding.getRoot());
        name=binding.name;
        imageView=binding.imageView5;
        details =binding.desception;
        location=binding.set;
        ratingBar=binding.ratingBar;
    }
}
