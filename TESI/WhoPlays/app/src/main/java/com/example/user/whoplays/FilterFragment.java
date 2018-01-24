package com.example.user.whoplays;

/**
 * Created by User on 01/12/2017.
 */


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class FilterFragment extends Fragment implements OnClickListener{
    private Button ripristina;
    private Button provaBottone;
    private Spinner spinnerOrder;
    private Spinner spinnerMatchType;
    private SeekBar seekBarDistance;
    private TextView t;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        spinnerOrder = (Spinner) view.findViewById(R.id.spinnerOrder);
        spinnerMatchType = (Spinner) view.findViewById(R.id.spinnerMatchType);
        seekBarDistance = (SeekBar) view.findViewById(R.id.seekBarDistance);
        provaBottone = (Button) view.findViewById(R.id.provaBottone);
        t = view.findViewById(R.id.percent_textView);
        ripristina = view.findViewById(R.id.ripristina);


        ArrayAdapter<CharSequence> adapter0 = ArrayAdapter.createFromResource(getActivity(), R.array.array_order, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.array_match_type, android.R.layout.simple_spinner_item);
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOrder.setAdapter(adapter0);
        spinnerMatchType.setAdapter(adapter1);
        seekBarDistance.setProgress(0);
        t.setText(0 +" km");
        provaBottone.setOnClickListener(this);
         ripristina.setOnClickListener(this);




        seekBarDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                t.setText(String.valueOf(progress) +" km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });


        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ripristina){
            seekBarDistance.setProgress(0);
        }

        if (v.getId() == R.id.provaBottone) {
            Intent intent = new Intent(getActivity(), WhoPlaysActivity.class);

            intent.putExtra("sort", spinnerOrder.getSelectedItem().toString());
            intent.putExtra("type", spinnerMatchType.getSelectedItem().toString());
            intent.putExtra("distance", seekBarDistance.getProgress());

            this.startActivity(intent);
        }
    }
}