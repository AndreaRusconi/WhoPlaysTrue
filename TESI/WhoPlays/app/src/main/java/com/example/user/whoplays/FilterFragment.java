package com.example.user.whoplays;

/**
 * Created by User on 01/12/2017.
 */


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class FilterFragment extends Fragment implements OnClickListener{

    private Button provaBottone;
    private Spinner spinnerOrder;
    private Spinner spinnerMatchType;
    private Spinner spinnerAdsOf;
    private Spinner spinnerSearchType;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        spinnerOrder = (Spinner) view.findViewById(R.id.spinnerOrder);
        spinnerMatchType = (Spinner) view.findViewById(R.id.spinnerMatchType);
        spinnerSearchType = (Spinner) view.findViewById(R.id.spinnerSearchType);
        spinnerAdsOf = (Spinner) view.findViewById(R.id.spinnerAdsOf);
        provaBottone = (Button) view.findViewById(R.id.provaBottone);


        ArrayAdapter<CharSequence> adapter0 = ArrayAdapter.createFromResource(getActivity(), R.array.array_order, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.array_match_type, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.array_search_type, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.array_ads_of, android.R.layout.simple_spinner_item);

        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOrder.setAdapter(adapter0);
        spinnerMatchType.setAdapter(adapter1);
        spinnerSearchType.setAdapter(adapter2);
        spinnerAdsOf.setAdapter(adapter3);

        provaBottone.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.provaBottone) {
            //changeText();
        }
    }
}