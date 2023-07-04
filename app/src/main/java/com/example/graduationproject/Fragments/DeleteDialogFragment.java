package com.example.graduationproject.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;


import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.graduationproject.Interface.OndeleteProduct;
import com.example.graduationproject.Model.Product;
import com.example.graduationproject.R;


import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

import javax.annotation.Nullable;


public class DeleteDialogFragment extends DialogFragment {


    private static final String ARG_PRODUCT_NAME = "name";
    private static final String ARG_PRODUCT_POS = "position";
    private static final String ARRYProduct = "array";
    OndeleteProduct onDelete;
    private String product_name;
    private int product_pos;
    private ArrayList<Product> arrayList;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onDelete = (OndeleteProduct) context;
    }

    public DeleteDialogFragment() {

    }




    public static DeleteDialogFragment newInstance(String product_name ,int product_pos ) {
        DeleteDialogFragment fragment = new DeleteDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PRODUCT_NAME, product_name);
        args.putInt(ARG_PRODUCT_POS, product_pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product_name = getArguments().getString(ARG_PRODUCT_NAME);
            product_pos = getArguments().getInt(ARG_PRODUCT_POS);

        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("Are You Sure You Want To Delete"+product_name+"Product");
        builder.setTitle("DELETE MESSAGE");
        builder.setIcon(R.drawable.mcdonalds);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                System.out.println("BIANBIAN");
                onDeleteConfirmed();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        //super.onCreateDialog(savedInstanceState);
    return builder.create();
    }
    private void onDeleteConfirmed() {
        Fragment parentFragment = getParentFragment();
        if(parentFragment  instanceof OndeleteProduct){
            System.out.println("bnandd");
            OndeleteProduct listener = (OndeleteProduct) parentFragment;
            listener.OnDelete(product_pos);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_delete_dialog, container, false);
    }
}