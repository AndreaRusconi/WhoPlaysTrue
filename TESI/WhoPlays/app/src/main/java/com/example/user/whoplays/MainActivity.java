package com.example.user.whoplays;

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.content.Intent;

import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity implements {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
       getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        if(item.getItemId()==R.id.menu_filter){
        startActivity(new Intent(getActivity(), FilterActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
