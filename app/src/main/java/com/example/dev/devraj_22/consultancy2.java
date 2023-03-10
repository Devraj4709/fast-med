package com.example.dev.devraj_22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class consultancy2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultancy2);

        ImageView physician = findViewById(R.id.physician);
        ImageView dietician = findViewById(R.id.dietician);
        ImageView cardeologist = findViewById(R.id.cardeologist);
        ImageView dentist = findViewById(R.id.dentist);
        physician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(consultancy2.this,Doctordetails.class);
                intent.putExtra("title","Physician");
                startActivity(intent);

            }
        });
        dietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(consultancy2.this,Doctordetails.class);
                intent.putExtra("title","Dietician");
                startActivity(intent);

            }
        });
        cardeologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(consultancy2.this,Doctordetails.class);
                intent.putExtra("title","Cardeologist");
                startActivity(intent);

            }
        });
        dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(consultancy2.this,Doctordetails.class);
                intent.putExtra("title","Dentist");
                startActivity(intent);

            }
        });

    }
}