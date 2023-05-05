package com.example.dev.devraj_22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dev.devraj_22.Prevalent.Prevalent;

public class Account extends AppCompatActivity {

    TextView logout,aboutus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        TextView username =findViewById(R.id.textview_show_fullname);
        TextView email =findViewById(R.id.textview_show_email);
        TextView phone =findViewById(R.id.textview_show_phone);

        username.setText(Prevalent.currentOnlineUser.getName());
        email.setText(Prevalent.currentOnlineUser.getEmail());
        phone.setText(Prevalent.currentOnlineUser.getPhone());

        logout=findViewById(R.id.textview_show_logout);
        aboutus=findViewById(R.id.textview_show_about_us);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Account.this,MainActivity.class);
                startActivity(intent);
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Account.this,About_us.class);
                startActivity(intent);
            }
        });
    }
}