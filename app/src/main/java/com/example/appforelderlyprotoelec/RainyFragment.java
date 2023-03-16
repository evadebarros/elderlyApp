package com.example.appforelderlyprotoelec;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class RainyFragment extends Fragment implements View.OnClickListener {

    Button vinyasaButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_rainy, container, false);

        vinyasaButton=(Button)view.findViewById(R.id.vinyasaButton);
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