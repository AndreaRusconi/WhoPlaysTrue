package com.example.user.whoplays;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by User on 03/11/2017.
 */

public class FilterFragment extends Fragment{
    private TextView filtro1;

    @Override
    public void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.filter_fragment, container, false);

        filtro1 = (TextView) view.findViewById(R.id.filtro1);

        return view;
    }
}
