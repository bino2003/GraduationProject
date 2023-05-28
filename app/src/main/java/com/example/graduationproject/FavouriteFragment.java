package com.example.graduationproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.graduationproject.Interface.UnFavoritve;
import com.example.graduationproject.databinding.FragmentFavouriteBinding;
import com.example.graduationproject.model.Favorites;
import com.example.graduationproject.model.ProductiveFamily;
import com.example.graduationproject.model.users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    Favorites favorites1;
    ArrayList<Favorites> favorites;
    ArrayList<Favorites> favorites2;
    int i;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouriteFragment newInstance(String param1, String param2) {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        favorites1=new Favorites();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentFavouriteBinding binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        firebaseFirestore.collection("Productive family").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    List<ProductiveFamily> productiveFamilyList = task.getResult().toObjects(ProductiveFamily.class);
                    for (int i = 0; i < productiveFamilyList.size(); i++) {
                      //  favorites.get(i).setProductiveFamilyId(productiveFamilyList.get(i).getName());
                        String id = task.getResult().getDocuments().get(i).getId();

                        if (id.equals(firebaseAuth.getUid())) {
                            if (firebaseAuth.getUid() != null) {
                                firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).collection("Favorites").whereEqualTo("user",firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            binding.progressBar.setVisibility(View.GONE);
                                            favorites = (ArrayList<Favorites>) task.getResult().toObjects(Favorites.class);

                                            FavoriteAdpter adpter = new FavoriteAdpter(favorites, getActivity(), new UnFavoritve() {
                                                @Override
                                                public void OnDelete(int pos, String product_id) {
                                                    firebaseFirestore.collection("users").document(firebaseAuth.getUid()).collection("Favorites").document(product_id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                favorites.remove(pos);
                                                                Toast.makeText(getActivity(), "Successfully", Toast.LENGTH_SHORT).show();

                                                                //    adpter.notifyDataSetChanged();
                                                              //  adpter.notifyItemRemoved(pos);
                                                            }
                                                            Log.d("product id ", product_id);
                                                        }
                                                    });
                                                }
                                            });
                                   //         Log.d("favo", favorites.toString());
                                            binding.rv.setAdapter(adpter);
                                            binding.rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                                        }

                                    }
                                });

                            }
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
                            if (firebaseAuth.getUid() != null) {
                                firebaseFirestore.collection("users").document(firebaseAuth.getUid()).collection("Favorites").whereEqualTo("user",firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            FavoriteAdpter adpter = new FavoriteAdpter(favorites, getActivity(), new UnFavoritve() {
                                                @Override
                                                public void OnDelete(int pos, String product_id) {
                                                    firebaseFirestore.collection("users").document(firebaseAuth.getUid()).collection("Favorites").document(product_id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(Task<Void> task) {
                                                            Toast.makeText(getActivity(), "Successfully", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            });
                                            Log.d("favo", favorites.toString());
                                            binding.rv.setAdapter(adpter);
                                            binding.rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                                        }

                                    }
                                });
                                Log.d("favo", favorites.toString());

                            }
                        }

                    }
                }
            }
        });


//        firebaseFirestore.collection("Productive family").document().collection("Favorites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    favorites = (ArrayList<Favorites>) task.getResult().toObjects(Favorites.class);
//                    Log.d("favo", favorites.toString());
//                    FavoriteAdpter adpter = new FavoriteAdpter(favorites, getActivity());
//                    binding.rv.setAdapter(adpter);
//                    binding.rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//
//                }
//            }
//        });
//        binding.carNameTv.setText(CarName);

        return binding.getRoot();
    }
}