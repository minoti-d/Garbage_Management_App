package com.saam.garbagemanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.saam.garbagemanagement.R;


public class LoginOrRegi extends AppCompatActivity {


    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_or_regi);
        Button regi = (Button) findViewById(R.id.regiNow);


        regi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(LoginOrRegi.this, Regis.class);
                startActivity(intent);
            }
        });





    }




    }

