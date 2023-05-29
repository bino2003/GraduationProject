package com.example.graduationproject.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;


import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.graduationproject.Interface.OnDelete;
import com.example.graduationproject.R;


import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nullable;


public class DeleteDialogFragment extends DialogFragment {


    private static final String ARG_PRODUCT_NAME = "name";
    private static final String ARG_PRODUCT_POS = "position";
    OnDelete onDelete;
    private String product_name;
    private int product_pos;

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        onDelete= (OnDelete) context;
//    }

    public DeleteDialogFragment() {
    }



    public static DeleteDialogFragment newInstance(String product_name ,int product_pos) {
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

onDelete.OnDelete(product_pos);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_delete_dialog, container, false);
    }
}