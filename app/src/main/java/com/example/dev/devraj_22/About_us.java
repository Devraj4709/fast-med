package com.example.dev.devraj_22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class About_us extends AppCompatActivity {

    TextView privacy,terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        privacy =findViewById(R.id.privacy_policy);
        terms =findViewById(R.id.terms_of_use);

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(About_us.this,Privacy_policy.class);
                startActivity(intent);
            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(About_us.this,Privacy_policy.class);
                startActivity(intent);
            }
        });



    }
}