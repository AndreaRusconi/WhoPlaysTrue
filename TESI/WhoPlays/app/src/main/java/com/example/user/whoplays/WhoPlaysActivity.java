package com.example.user.whoplays;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class WhoPlaysActivity extends AppCompatActivity  {

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
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()== R.id.menu_filter){
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new FilterFragment())
                    .commit();
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }
}