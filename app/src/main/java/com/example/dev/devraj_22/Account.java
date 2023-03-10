package com.example.dev.devraj_22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dev.devraj_22.Prevalent.Prevalent;

public class Account extends AppCompatActivity {

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
    }
}