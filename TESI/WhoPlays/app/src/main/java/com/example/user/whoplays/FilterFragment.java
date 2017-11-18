package com.example.user.whoplays;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class FilterFragment extends Fragment implements OnClickListener{

    private TextView testoProva;
    private Button provaBottone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        testoProva = (TextView) view.findViewById(R.id.testoProva);
        provaBottone = (Button) view.findViewById(R.id.provaBottone);
        provaBottone.setOnClickListener(this);

        return view;
    }

    public void changeText() {
        testoProva.setText("Rusconi Coglione");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.provaBottone) {
            changeText();
        }
    }
}