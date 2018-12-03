package com.example.xin.pre_project.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xin.pre_project.Dog;
import com.example.xin.pre_project.DogRVAdapter;
import com.example.xin.pre_project.HomeActivity;
import com.example.xin.pre_project.ImageManager;
import com.example.xin.pre_project.R;
import com.example.xin.pre_project.SQLiteManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    TextView tvUsername, tvEmail;
    RecyclerView rvDogs;
    FloatingActionButton fab;

    AddDogFAB mCallback;

    de.hdodenhof.circleimageview.CircleImageView profilePic;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        bindViews(view);
        getAndSetUserInfo();
        getAndSetDogs(null);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProfileFragment.AddDogFAB) {
            mCallback = (ProfileFragment.AddDogFAB) context;
        }
   /*     else if (context instanceof MealDetailFragment.ScheduleMealListener) {
            scheduleMealListener = (MealDetailFragment.ScheduleMealListener) context;
        }*/
        else {
            throw new RuntimeException(context.toString()
                    + " must implement MadeMealListener and/or ScheduleMealListener");
        }
    }

    private void bindViews(View view) {
        tvUsername = view.findViewById(R.id.tvUsername);
        tvEmail = view.findViewById(R.id.tvEmail);
        rvDogs = view.findViewById(R.id.rvMyDogs);
        fab = view.findViewById(R.id.fabAddDog);
        profilePic = view.findViewById(R.id.myProfilePic);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewDog();
            }
        });
    }

    private void getAndSetUserInfo() {
        // pull from firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            String uid = user.getUid();
            String email = user.getEmail();
            String name;

            //tvUsername.setText(name);
            tvEmail.setText(email);

            ImageManager im = new ImageManager(HomeActivity.gContext);
            profilePic.setImageBitmap(im.loadFromStorage(""));
        }
        else {
            Toast t = Toast.makeText(getContext(), "Error loading user profile", Toast.LENGTH_SHORT);
            t.setGravity(Gravity.TOP, 0,0);
            t.show();
        }
    }

    private void getAndSetDogs(String username) {
        SQLiteManager db = new SQLiteManager(getContext());
        ArrayList<Dog> dogs = db.getAllDogs("a");
        DogRVAdapter dogAdapter = new DogRVAdapter(getContext(), dogs);
        rvDogs.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDogs.setAdapter(dogAdapter);
    }

    private void addNewDog() {
        mCallback.navToAddDog();
    }

    public interface AddDogFAB {
        void navToAddDog();
    }

}