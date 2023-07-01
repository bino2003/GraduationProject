package com.example.graduationproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.graduationproject.Model.users;

import com.example.graduationproject.databinding.FragmentUserProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseUser user ;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
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
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentUserProfileBinding binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        binding.tvChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ChangePassword.class));
            }
        });
        Log.d("current email",  firebaseAuth.getCurrentUser().getEmail());
     //   Toast.makeText(getActivity(), firebaseAuth.getUid(), Toast.LENGTH_SHORT).show();
        firebaseFirestore.collection("users").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    binding.etUserName.setText(documentSnapshot.getString("name"));
                    binding.etPhoneNumber.setText(documentSnapshot.getString("phone"));
                    binding.etEmailAddress.setText(firebaseAuth.getCurrentUser().getEmail());
//                binding.etUserName.setText("documentSnapshot.getStri");

                    binding.etLocation.setText(documentSnapshot.getString("location"));
                   // Toast.makeText(getActivity(), "succesfully", Toast.LENGTH_SHORT).show();
                }else {
                   // Toast.makeText(getActivity(), "Not Successfuly", Toast.LENGTH_SHORT).show();
                }



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



//                binding.etPhoneNumber.setText("" + documentSnapshot.getLong("phone").intValue());

                final String email = firebaseAuth.getCurrentUser().getEmail();
                binding.btnCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DocumentReference documentRef = firebaseFirestore.collection("users").document(firebaseAuth.getUid());
                        users user = new users();
                        user.setLatlong(binding.etLocation.getText().toString());
                        user.setPhone(binding.etPhoneNumber.getText().toString());
                        user.setName(binding.etUserName.getText().toString());
                        documentRef.set(user, SetOptions.merge())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Update successful
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Error updating document
                                    }
                                });








//                        firebaseFirestore.collection("users").document(firebaseAuth.getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(Task<Void> task) {
//                           //     Toast.makeText(getActivity(), "Successfuly", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                        if (user1 != null) {
                            firebaseUser.updateEmail(binding.etEmailAddress.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        firebaseUser.reload();
                                       // Toast.makeText(getActivity(), "Email Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            AuthCredential credential = EmailAuthProvider.getCredential(binding.etEmailAddress.getText().toString(), binding.etOldPassword.getText().toString());
                            firebaseUser.reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                firebaseUser.updatePassword(binding.etNewPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                           Toast.makeText(getActivity(), "Successfully", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else {
                                                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

//                            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), "currentPassword");
//                            // Prompt the user to re-provide their sign-in credentials
//                            user.reauthenticate(credential)
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                // User has been re-authenticated, so proceed with changing the email and password
//                                                String newEmail = binding.etEmailAddress.getText().toString();
//                                                String newPassword = binding.etPassword.getText().toString();
//
//                                                user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                        if (task.isSuccessful()) {
//                                                            // Email has been changed successfully
//                                                        } else {
//                                                            // Failed to change the email
//                                                        }
//                                                    }
//                                                });
//
//                                                user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                        if (task.isSuccessful()) {
//                                                            // Password has been changed successfully
//                                                        } else {
//                                                            // Failed to change the password
//                                                        }
//                                                    }
//                                                });
//                                            } else {
//                                                // Failed to re-authenticate user
//                                            }
//                                        }
//                                    });
                        }

//                        AuthCredential credential = EmailAuthProvider.getCredential(binding.etEmailAddress.getText().toString(), binding.etPassword.getText().toString());
//                        firebaseUser.reauthenticate(credential)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            firebaseUser.updatePassword(binding.etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//                                                    if (task.isSuccessful()) {
//                                                        Toast.makeText(getActivity(), "Password Successfully", Toast.LENGTH_SHORT).show();
//                                                    } else {
//                                                        Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//                                            });
//                                        } else {
//                                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });



                    }
                });


            }
        });

        return binding.getRoot();
    }
}