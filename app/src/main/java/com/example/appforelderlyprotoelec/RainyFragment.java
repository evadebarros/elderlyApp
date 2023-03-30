package com.example.appforelderlyprotoelec;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class RainyFragment extends Fragment implements View.OnClickListener {

    Button circuitButton,vinyasaButton, upperBodyButton, cardioButton, yogaButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_rainy, container, false);

        vinyasaButton=(Button)view.findViewById(R.id.vinyasaButton);
        upperBodyButton=(Button)view.findViewById(R.id.upperBodyButton);
        cardioButton=(Button)view.findViewById(R.id.cardioButton);
        yogaButton=(Button)view.findViewById(R.id.rainyActivity3);
        circuitButton=(Button)view.findViewById(R.id.circuitButton);
        circuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), Exercise_routine_circuit_training.class);

                startActivity(intent);
            }
        });
        yogaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), Exercise_routine_yoga_joint_pain.class);

                startActivity(intent);
            }
        });
        cardioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), Routine_cardio.class);
                startActivity(intent);
            }
        });
        upperBodyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), Routine_upperbody.class);
                startActivity(intent);
            }
        });
        vinyasaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), Exercise_routine.class);
                startActivity(intent);
            }
        });
        return view;

    }


    @Override
    public void onClick(View view) {

    }
}