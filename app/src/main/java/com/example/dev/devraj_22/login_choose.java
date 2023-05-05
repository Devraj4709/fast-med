package com.example.dev.devraj_22;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class login_choose extends AppCompatActivity {


    Button userlogin,doctorlogin,pharmacylogin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_choose);

        userlogin=findViewById(R.id.Login_user_btn);
        doctorlogin=findViewById(R.id.Login_doctor_btn);
        pharmacylogin=findViewById(R.id.Login_pharmacy_btn);

        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_choose.this,MainActivity.class);
                startActivity(intent);
            }
        });

        pharmacylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_choose.this,Pharmacy_Login.class);
                startActivity(intent);
            }
        });

        doctorlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_choose.this,Doctor_Signing.class);
                startActivity(intent);
            }
        });

    }
}