package com.example.graduationproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.graduationproject.databinding.FragmentFavouriteBinding;
import com.example.graduationproject.databinding.FragmentSettingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URLEncoder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Setting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Setting extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String INSTAGRAM_PACKAGE = "com.instagram.android";
    private static final String INSTAGRAM_URL = "https://www.instagram.com/";

    private static final String TWITTER_PACKAGE = "com.twitter.android";
    private static final String TWITTER_URL = "https://twitter.com/";
    private final String instgramusername="doaa_mufid";
    private final String twitterusername="@BinoNassar";
    private final String whatsapp="593260095";




    private static final String ARG_PARAM2 = "param2";
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Setting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Setting.
     */
    // TODO: Rename and change types and number of parameters
    public static Setting newInstance(String param1, String param2) {
        Setting fragment = new Setting();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        FragmentSettingBinding binding = FragmentSettingBinding.inflate(inflater, container, false);
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user=auth.getCurrentUser();

                auth.signOut();
                user.reload();

                Toast.makeText(getContext(), "Logout successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(),LoginActivity.class);
                //innnnn
                //rrr
                startActivity(intent);
//                finish();

            }
        });
binding.watsapp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       String phoneNumbers = "+972"+whatsapp;

        String message = "Hello, let's chat on WhatsApp!";
        openWhatsAppChat (phoneNumbers, message);
    }
});
binding.instgram.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (isTwitterInstalled()) {
            openInstagramProfile(instgramusername);
        } else {
            openInstagramWebsite(instgramusername);
        }
    }
});
binding.twitter.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        if (isTwitterInstalled()) {
            openTwitterProfile(twitterusername);
        } else {
            openTwitterWebsite(twitterusername);
        }
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

}