package com.example.haf.myfreeze;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Welcome extends AppCompatActivity {

    private Button next;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        next = (Button)findViewById(R.id.GoBtn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user ==null)
                    startActivity(new Intent(Welcome.this,MainActivity.class));
                else
                    startActivity(new Intent(Welcome.this,Devices.class));
            }
        });
    }
}
