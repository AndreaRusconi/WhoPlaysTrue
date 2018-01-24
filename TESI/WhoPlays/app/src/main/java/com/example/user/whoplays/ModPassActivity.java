package com.example.user.whoplays;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by User on 24/01/2018.
 */

public class ModPassActivity extends Activity {
    EditText oldpass;
    EditText newpass;
    Button modifica;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_newpassword);

        oldpass = (EditText) findViewById(R.id.edit_text_oldpass);
        newpass = (EditText) findViewById(R.id.edit_text_newpass);
        modifica = (Button) findViewById(R.id.button_newpass);
        super.onCreate(savedInstanceState);
    }

  protected void onClick( View view)  {

        if ( view.getId() == R.id.button_newpass) {

            if ( oldpass.getText().toString().equals(null) || newpass.getText().toString().equals(null)){

                if (oldpass.getText().toString().equals(newpass.getText().toString())) {
                    Toast.makeText(this, "la nuova password deve essere diversa", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(this, "password modificata con successo ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getBaseContext(), WhoPlaysActivity.class));
            }
           else
                Toast.makeText(this, "campi vuoti ", Toast.LENGTH_LONG).show();

        }
  }
}
