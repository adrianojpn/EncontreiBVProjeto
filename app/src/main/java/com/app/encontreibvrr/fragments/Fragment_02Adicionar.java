package com.app.encontreibvrr.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.encontreibvrr.R;
import com.app.encontreibvrr.activitysUploadInfor.ActivityAchados;
import com.app.encontreibvrr.activitysUploadInfor.ActivityPerdidos;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by adrianojpn on 22/07/2016.
 */

public class Fragment_02Adicionar extends Fragment {

    public Fragment_02Adicionar() {}
    private String mTitle;
    private CardView cardView_Achados;
    private CardView cardView_Perdidos;

    public static Fragment_02Adicionar getInstance(String title) {
        Fragment_02Adicionar sf = new Fragment_02Adicionar();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_02, container, false);

        cardView_Achados = (CardView) rootView.findViewById(R.id.cardView_Achados);
        cardView_Achados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityAchados.class));
            }
        });

        cardView_Perdidos = (CardView) rootView.findViewById(R.id.cardView_Perdidos);
        cardView_Perdidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityPerdidos.class));
            }
        });

        return rootView;
    }
}
