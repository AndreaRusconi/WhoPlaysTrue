package com.example.user.whoplays;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by User on 01/12/2017.
 */

public class FilterActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new FilterFragment())
                .commit();
    }
}