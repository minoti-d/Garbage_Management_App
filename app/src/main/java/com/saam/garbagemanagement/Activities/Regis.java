package com.saam.garbagemanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.saam.garbagemanagement.R;
import com.saam.garbagemanagement.User;

public class Regis extends AppCompatActivity implements View.OnClickListener {

    private TextView banner;
    private EditText name, address, email, password;
    private Button register;
    private ProgressBar progg;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.txt_banner);
        banner.setOnClickListener(this);
        register = (Button) findViewById(R.id.regiNow);
        name = (EditText) findViewById(R.id.txt_name);
        address = (EditText) findViewById(R.id.txt_address);
        email = (EditText) findViewById(R.id.txt_email);
        password = (EditText) findViewById(R.id.txt_pass);

        progg = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.txt_banner:
                startActivity(new Intent(this, LoginOrRegi.class));
                break;
            case R.id.regiNow:
                 registerUser();
                 break;
             }
        }





    private void registerUser()
    {
        String name1 = name.getText().toString().trim();
        String address1 = address.getText().toString().trim();
        String email1 = email.getText().toString().trim();
        String password1 = password.getText().toString().trim();

        if(name1.isEmpty()){
            name.requestFocus();
            name.setError(Html.fromHtml("<font color='blue'>Name is required</font>"));

            return;
        }

        if(address1.isEmpty()){
            address.requestFocus();
            address.setError(Html.fromHtml("<font color='blue'>Address is required</font>"));

            return;
        }

        if(email1.isEmpty()){
            email.requestFocus();
            email.setError(Html.fromHtml("<font color='blue'>Email is required</font>"));

            return;
        }

        if(password1.isEmpty()){
            password.requestFocus();

            password.setError(Html.fromHtml("<font color='blue'>Password required</font>"));

            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            email.requestFocus();
            email.setError(Html.fromHtml("<font color='blue'>Provide valid email</font>"));

            return;
        }

        if(password1.length() < 6){
            password.requestFocus();
            password.setError(Html.fromHtml("<font color='blue'>Password should be atleast 6 characs</font>"));

            return;
        }

        progg.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if(task.isSuccessful()){
                    User user = new User(name1, email1, address1);

                    FirebaseDatabase.getInstance().getReference("User")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>(){
                                @Override
                        public void onComplete(@NonNull Task<Void> task){
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(Regis.this, "User has succesfully registered",Toast.LENGTH_LONG).show();
                                        progg.setVisibility(View.VISIBLE);

                                    }else
                                    {
                                        Toast.makeText(Regis.this, "Failed to register!!",Toast.LENGTH_LONG).show();
                                        progg.setVisibility(View.GONE);
                                    }
                                }
                    });
                }else
                {
                    Toast.makeText(Regis.this, "Failed to register!!",Toast.LENGTH_LONG).show();
                    progg.setVisibility(View.GONE);
                }

            }
        });


    }
}


