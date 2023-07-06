package com.example.graduationproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.graduationproject.Interface.DetailsProductAction;


import com.example.graduationproject.Model.Product2;
import com.example.graduationproject.Model.Favorites;
import com.example.graduationproject.Model.ProductiveFamily;
import com.example.graduationproject.Model.users;
import com.example.graduationproject.R;
import com.example.graduationproject.databinding.ItemdetailsproductivefamileproductBinding;
import com.example.graduationproject.Model.Product;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailsProductAdapter extends RecyclerView.Adapter<DetailsProductAdapterVH> {
    ArrayList<Product2> productArrayList = new ArrayList<>();
    Context context;
    DetailsProductAction detailsProductAction;
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    boolean isfavarite;
boolean isfavarite2=true;

    List<Favorites> favoritesList=new ArrayList<>();


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
        firebaseFirestore.collection("Productive family").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    List<ProductiveFamily> productiveFamilyList = task.getResult().toObjects(ProductiveFamily.class);
                    for (int i = 0; i < productiveFamilyList.size(); i++) {
                        String id = task.getResult().getDocuments().get(i).getId();
                        if (id.equals(firebaseAuth.getUid())) {
                            firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).collection("Favorites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()){

                                      favoritesList=    task.getResult().toObjects(Favorites.class);
                                        for (int j = 0; j <favoritesList.size() ; j++) {
                                            String favuserid=favoritesList.get(j).getId();
                                            String productid=productArrayList.get(pos).getId();
                                            if (favuserid.equals(productid)){
                                                holder.fav.setImageResource(R.drawable.ic_baseline_favorite_24);

                                            }

                                        }



                                    }
                                }
                            });

                        }


                    }
                }
            }
        });
        firebaseFirestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    List<users> usersList = task.getResult().toObjects(users.class);
                    for (int i = 0; i < usersList.size(); i++) {
                        String id = task.getResult().getDocuments().get(i).getId();
                        if (id.equals(firebaseAuth.getUid())) {
                            firebaseFirestore.collection("users").document(firebaseAuth.getUid()).collection("Favorites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()){

                                        List<Favorites> favoritesList=    task.getResult().toObjects(Favorites.class);
                                        for (int j = 0; j <favoritesList.size() ; j++) {
                                            String favuserid=favoritesList.get(j).getId();
                                            String productid=productArrayList.get(pos).getId();
                                            if (favuserid.equals(productid)){
                                                holder.fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                                            }

                                        }



                                    }
                                }
                            });

                        }


                    }
                }
            }
        });


        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favoritesList.size()==0){


                        detailsProductAction.onfav(productArrayList.get(pos));
                    holder.fav.setImageResource(R.drawable.ic_baseline_favorite_24);

                }

                else {
                    firebaseFirestore.collection("Productive family").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                firebaseAuth = FirebaseAuth.getInstance();
                                List<ProductiveFamily> productiveFamilyList = task.getResult().toObjects(ProductiveFamily.class);
                                for (int i = 0; i < productiveFamilyList.size(); i++) {
                                    String id = task.getResult().getDocuments().get(i).getId();
                                    if (id.equals(firebaseAuth.getUid())) {
                                        firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).collection("Favorites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()){

                                                    favoritesList=    task.getResult().toObjects(Favorites.class);
                                                    for (int j = 0; j <favoritesList.size() ; j++) {
                                                        String favuserid=favoritesList.get(j).getId();
                                                        String productid=productArrayList.get(pos).getId();
                                                        if (!favuserid.equals(productid) ){
                                                            holder.fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                                                            detailsProductAction.onfav(productArrayList.get(pos));

                                                        }


                                                    }



                                                }
                                            }
                                        });

                                    }


                                }
                            }
                        }
                    });
                    firebaseFirestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                firebaseAuth = FirebaseAuth.getInstance();
                                List<users> usersList = task.getResult().toObjects(users.class);
                                for (int i = 0; i < usersList.size(); i++) {
                                    String id = task.getResult().getDocuments().get(i).getId();
                                    if (id.equals(firebaseAuth.getUid())) {
                                        firebaseFirestore.collection("users").document(firebaseAuth.getUid()).collection("Favorites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()){

                                                    favoritesList=    task.getResult().toObjects(Favorites.class);
                                                    for (int j = 0; j <favoritesList.size() ; j++) {
                                                        String favuserid=favoritesList.get(j).getId();
                                                        String productid=productArrayList.get(pos).getId();
                                                        if (!favuserid.equals(productid)){
                                                            holder.fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                                                            detailsProductAction.onfav(productArrayList.get(pos));


                                                        }

                                                    }



                                                }
                                            }
                                        });

                                    }


                                }
                            }
                        }
                    });

                    firebaseFirestore.collection("Productive family").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                firebaseAuth = FirebaseAuth.getInstance();
                                List<ProductiveFamily> productiveFamilyList = task.getResult().toObjects(ProductiveFamily.class);
                                for (int i = 0; i < productiveFamilyList.size(); i++) {
                                    String id = task.getResult().getDocuments().get(i).getId();
                                    if (id.equals(firebaseAuth.getUid())) {
                                        firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).collection("Favorites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()){

                                                    favoritesList=    task.getResult().toObjects(Favorites.class);
                                                    for (int j = 0; j <favoritesList.size() ; j++) {
                                                        String favuserid=favoritesList.get(j).getId();
                                                        String productid=productArrayList.get(pos).getId();
                                                        if (favuserid.equals(productid)){
                                                            holder.fav.setImageResource(R.drawable.heart);
                                                            detailsProductAction.onfav(productArrayList.get(pos));

                                                        }


                                                    }



                                                }
                                            }
                                        });

                                    }


                                }
                            }
                        }
                    });
                    firebaseFirestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                firebaseAuth = FirebaseAuth.getInstance();
                                List<users> usersList = task.getResult().toObjects(users.class);
                                for (int i = 0; i < usersList.size(); i++) {
                                    String id = task.getResult().getDocuments().get(i).getId();
                                    if (id.equals(firebaseAuth.getUid())) {
                                        firebaseFirestore.collection("users").document(firebaseAuth.getUid()).collection("Favorites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()){

                                                    favoritesList=    task.getResult().toObjects(Favorites.class);
                                                    for (int j = 0; j <favoritesList.size() ; j++) {
                                                        String favuserid=favoritesList.get(j).getId();
                                                        String productid=productArrayList.get(pos).getId();
                                                        if (favuserid.equals(productid)){
                                                            holder.fav.setImageResource(R.drawable.heart);
                                                            detailsProductAction.onfav(productArrayList.get(pos));


                                                        }

                                                    }



                                                }
                                            }
                                        });

                                    }


                                }
                            }
                        }
                    });

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

