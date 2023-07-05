package com.example.graduationproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.graduationproject.Interface.DetailsProductAction;


import com.example.graduationproject.Model.Product2;
import com.example.graduationproject.R;
import com.example.graduationproject.databinding.ItemdetailsproductivefamileproductBinding;
import com.example.graduationproject.Model.Product;


import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class ItemDetailsProductAdapter extends RecyclerView.Adapter<DetailsProductAdapterVH> {
    ArrayList<Product2> productArrayList = new ArrayList<>();
    Context context;
    DetailsProductAction detailsProductAction;
    boolean isfavarite;

    public ItemDetailsProductAdapter(ArrayList<Product2> productArrayList, Context context, DetailsProductAction detailsProductAction, boolean isfavarite) {
        this.productArrayList = productArrayList;
        this.context = context;
        this.isfavarite = isfavarite;
        this.detailsProductAction = detailsProductAction;
    }

    @NonNull
    @Override
    public DetailsProductAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DetailsProductAdapterVH detailsProductAdapterVH = new DetailsProductAdapterVH(ItemdetailsproductivefamileproductBinding.inflate(LayoutInflater.from(parent.getContext())));

        return detailsProductAdapterVH;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsProductAdapterVH holder, int position) {
        int pos = position;
        holder.productprice.setText(productArrayList.get(position).getPrice());
        holder.productname.setText(productArrayList.get(position).getName());
        if (productArrayList.get(pos).getImage() != null) {
            Glide.with(context).load(productArrayList.get(position).getImage()).into(holder.imageViewProduct);
        } else {
            Glide.with(context).load(productArrayList.get(position).getImageUrls().get(0)).into(holder.imageViewProduct);
        }
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isfavarite == true) {
                    holder.fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                    detailsProductAction.onfav(productArrayList.get(pos));
                    isfavarite = false;
                } else if (isfavarite == false) {
                    holder.fav.setImageResource(R.drawable.heart);
                    detailsProductAction.onfav(productArrayList.get(pos));
                    isfavarite = true;
                }
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

class DetailsProductAdapterVH extends RecyclerView.ViewHolder {
    ImageView imageViewProduct;
    TextView productname;
    TextView productprice;

    ImageView fav;

    public DetailsProductAdapterVH(@NonNull ItemdetailsproductivefamileproductBinding binding) {
        super(binding.getRoot());
        imageViewProduct = binding.imageViewdetailsproduct;
        productname = binding.tvDetailsproductName;
        productprice = binding.tvDetailsproductPrice;
        fav = binding.fav;
    }
}
