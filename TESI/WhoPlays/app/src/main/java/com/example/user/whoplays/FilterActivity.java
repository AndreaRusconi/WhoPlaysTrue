package com.example.user.whoplays;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by User on 03/11/2017.
 */

public class FilterActivity extends Activity{
    @Override
   public void onCreate (Bundle saveIntsanceState){
        super.onCreate(saveIntsanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new FilterFragment())
                .commit();
    }
}
