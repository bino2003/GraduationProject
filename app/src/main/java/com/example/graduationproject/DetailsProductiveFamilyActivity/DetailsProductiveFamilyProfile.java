package com.example.graduationproject.DetailsProductiveFamilyActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.content.pm.PackageManager;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.graduationproject.databinding.FragmentDetailsProductiveFamilyProfileBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DetailsProductiveFamilyProfile extends Fragment {

    final Map<String, Object> ratting = new HashMap<>();
    private static final String INSTAGRAM_PACKAGE = "com.instagram.android";
    private static final String INSTAGRAM_URL = "https://www.instagram.com/";

    private static final String TWITTER_PACKAGE = "com.twitter.android";
    private static final String TWITTER_URL = "https://twitter.com/";


    private static final String ARG_db_name = "dbName3";
    private static final String ARG_ID_ProductiveFamily = "id2";
    private static final String ARG_ID_Product_Id = "producr_id";
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    List<String> rating;
    // TODO: Rename and change types of parameters
    private String dbname;
    private String id;
    String usernameinstgram;
    String usernameTwitter;

    String phoneNumbers;
    private String product_id;

    public DetailsProductiveFamilyProfile() {
        // Required empty public constructor
    }


    public static DetailsProductiveFamilyProfile newInstance(String dbname, String id,String product_id) {
        DetailsProductiveFamilyProfile fragment = new DetailsProductiveFamilyProfile();
        Bundle args = new Bundle();
        args.putString(ARG_db_name, dbname);
        args.putString(ARG_ID_ProductiveFamily, id);
        args.putString(ARG_ID_Product_Id, product_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dbname = getArguments().getString(ARG_db_name);
            id = getArguments().getString(ARG_ID_ProductiveFamily);
            product_id = getArguments().getString(ARG_ID_Product_Id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (id!=null){
            Log.d("id_", id);
        }
        Log.d("d_", id);
        FragmentDetailsProductiveFamilyProfileBinding binding = FragmentDetailsProductiveFamilyProfileBinding.inflate(inflater, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();

        float r = binding.ratingBar2.getRating();
        firebaseAuth = FirebaseAuth.getInstance();
        String phoneNumber = "+0598681854"; // Replace with the desired phone number
        String message = "Hello, this is a message from my app!"; // Repl
        // ace with your custom message

//        binding.watsapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(), "was", Toast.LENGTH_SHORT).show();
//                String phoneNumber = "0598681854";
//
//                // Create a URI with the phone number
//                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber);
//
//                // Create an Intent with the ACTION_VIEW action and the URI
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//
//                // Set the package name of WhatsApp to ensure only WhatsApp handles this intent
//                intent.setPackage("com.whatsapp");
//
//                // Verify that there's a WhatsApp application available to handle this intent
//                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
//                    startActivity(intent);
//                } else {
//                    // WhatsApp is not installed on the device or no activity can handle the intent
//                    // You can show an error message or redirect the user to the Play Store to install WhatsApp
//                }
//            }
//        });
        binding.ratingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (b) {
                    if (id != null) {
                        firebaseFirestore.collection("Productive family")
                                .document(id).update("evaluation", FieldValue.arrayUnion(String.valueOf(v)))
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "rating", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getActivity(), task.getException().getMessage() + "", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }
            }
        });
        if (id!=null){
            firebaseFirestore.collection(dbname).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot = task.getResult();

//                  firebaseFirestore.collection("Productive Family").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                      @Override
//                      public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
//                          DocumentSnapshot documentSnapshot1=task.getResult();
//                          documentSnapshot1.
////                   ProductiveFamily productiveFamily=       task.getResult().toObject(ProductiveFamily.class);
////                   rating.add(String.valueOf(r));
////                productiveFamily.setEvaluation(rating);
//
//                      }
//                  });


                    binding.tvDesception.setText(documentSnapshot.getString("details"));
                    binding.tvSet.setText(documentSnapshot.getString("location"));
                    binding.tvPhone.setText("" + documentSnapshot.getLong("phone").intValue());


                }
            });
        }
        binding.tvTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("Productive family").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                        usernameTwitter =task.getResult().getString("Twitter");

                        if (isTwitterInstalled()) {
                            openTwitterProfile(usernameTwitter);
                        } else {
                            openTwitterWebsite(usernameTwitter);
                        }
                    }
                });
            }
        });
        binding.tvInstgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("Productive family").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                        usernameinstgram =task.getResult().getString("instgram");

                        if (isInstagramInstalled()) {
                            openInstagramProfile(usernameinstgram);
                        } else {
                            openInstagramWebsite(usernameinstgram);
                        }
                    }
                });
            }
        });
        binding.tvWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseFirestore.collection("Productive family").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                        phoneNumbers = "+972"+task.getResult().getLong("phone").toString();

                        String message = "Hello, let's chat on WhatsApp!";
                        openWhatsAppChat (phoneNumbers, message);
                    }
                });
                // Replace the phoneNumber and message with your desired values

            }
        });





        return binding.getRoot();
    }
    private boolean isTwitterInstalled() {
        PackageManager pm = getActivity().getPackageManager();
        try {
            pm.getPackageInfo(TWITTER_PACKAGE, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void openTwitterProfile(String username) {
        String url = "twitter://user?screen_name=" + username;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setPackage(TWITTER_PACKAGE);
        startActivity(intent);
    }

    private void openTwitterWebsite(String username) {
        String url = TWITTER_URL + username;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
    private boolean isInstagramInstalled() {
        PackageManager pm = getActivity().getPackageManager();
        try {
            pm.getPackageInfo(INSTAGRAM_PACKAGE, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void openInstagramProfile(String username) {
        String url = INSTAGRAM_URL + username;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setPackage(INSTAGRAM_PACKAGE);
        startActivity(intent);
    }

    private void openInstagramWebsite(String username) {
        String url = INSTAGRAM_URL + username;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void openWhatsAppChat(String phoneNumber, String message) {
        try {
            // Format the phone number to include the country code and remove any special characters
            phoneNumber = phoneNumber.replaceAll("[^0-9+]", "");

            // Open WhatsApp with the predefined message
            Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + URLEncoder.encode(message, "UTF-8"));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Open WhatsApp with the specified phone number and message

}