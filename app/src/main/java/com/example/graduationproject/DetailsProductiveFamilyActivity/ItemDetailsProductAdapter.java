package com.example.graduationproject.DetailsProductiveFamilyActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.graduationproject.Interface.DetailsProductAction;

import com.example.graduationproject.databinding.ItemdetailsproductivefamileproductBinding;
import com.example.graduationproject.databinding.ItemproductBinding;
import com.example.graduationproject.model.Product;

import java.util.ArrayList;

public class ItemDetailsProductAdapter extends RecyclerView.Adapter<DetailsProductAdapterVH> {
        ArrayList<Product> productArrayList=new ArrayList<>();
        Context context;
        DetailsProductAction detailsProductAction;





public ItemDetailsProductAdapter(ArrayList<Product> productArrayList, Context context, DetailsProductAction detailsProductAction) {
        this.productArrayList = productArrayList;
        this.context = context;
        this.detailsProductAction = detailsProductAction;
        }

@NonNull
@Override
public DetailsProductAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    DetailsProductAdapterVH detailsProductAdapterVH= new DetailsProductAdapterVH(ItemdetailsproductivefamileproductBinding.inflate(LayoutInflater.from(parent.getContext())));

        return detailsProductAdapterVH;
        }

@Override
public void onBindViewHolder(@NonNull DetailsProductAdapterVH holder, int position) {
        int pos=position;
        holder.productprice.setText(productArrayList.get(position).getPrice());
        holder.productname.setText(productArrayList.get(position).getName());
        Glide.with(context).load(productArrayList.get(position).getImage()).circleCrop().into(holder.imageViewProduct);

   holder.fav.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           detailsProductAction.onfav(productArrayList.get(pos));
       }
   });
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        detailsProductAction.onClickItem(productArrayList.get(pos));
    }
});
        }

@Override
public int getItemCount() {
        return productArrayList.size();
        }
        }
class DetailsProductAdapterVH extends RecyclerView.ViewHolder{
    ImageView imageViewProduct;
    TextView productname;
    TextView productprice;

    ImageView fav;

    public DetailsProductAdapterVH(@NonNull ItemdetailsproductivefamileproductBinding binding) {
        super(binding.getRoot());
        imageViewProduct =binding.imageViewdetailsproduct;
        productname=binding.tvDetailsproductName;
        productprice=binding.tvDetailsproductPrice;
        fav=binding.fav;



    }
}