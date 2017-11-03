package com.example.user.whoplays;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.view.MenuInflater;
import android.view.View.OnClickListener;


public class WhoPlaysFragment extends Fragment implements  OnClickListener {

    private TextView homeBarTextView;
    private ListView itemListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_who_plays, container, false);

        homeBarTextView = (TextView) view.findViewById(R.id.homeBarTextView);
        itemListView = (ListView) view.findViewById(R.id.itemListView);

        itemListView.setOnClickListener(this);


        return view;
    }
    @Override
    public void onClick (View v){

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.filter_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();
        if (id == R.id.menu_filter){
            return true;
        }
       return super.onOptionsItemSelected(item);
    }
}
