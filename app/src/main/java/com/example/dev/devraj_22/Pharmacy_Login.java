package com.example.dev.devraj_22;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dev.devraj_22.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Pharmacy_Login extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_login);


        Button loginbtn = findViewById(R.id.pharmacyLogin_button);
        EditText Inputphone= findViewById(R.id.pharmacyLogin_phone);
        EditText InputPassword= findViewById(R.id.pharmacyLogin_password);
        ProgressDialog loadingBar =new ProgressDialog(this);
        String parentDbname ="Pharmacy";

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginUser();

            }

            private void LoginUser() {

                String phone =Inputphone.getText().toString();
                String password=InputPassword.getText().toString();
                if (TextUtils.isEmpty(phone))
                {
                    Toast.makeText(Pharmacy_Login.this, "please enter your phone number", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(Pharmacy_Login.this, "please enter your email", Toast.LENGTH_SHORT).show();
                }
                else {
                    loadingBar.setTitle("Login Account");
                    loadingBar.setMessage("Please wait,while we are checking the credentials.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();


                    AllowaccessToAccount(phone,password);
                }
            }

            private void AllowaccessToAccount(String phone, String password) {

                final DatabaseReference Rootref;
                Rootref = FirebaseDatabase.getInstance().getReference();
                Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(parentDbname).child(phone).exists()){
                            Users usersData =snapshot.child(parentDbname).child(phone).getValue(Users.class);

                            if(usersData.getPhone().equals(phone))
                            {
                                if(usersData.getPassword().equals(password))
                                {
                                    Toast.makeText(Pharmacy_Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    Intent intent = new Intent(Pharmacy_Login.this,Pharmacy_Page.class);
                                    startActivity(intent);
                                }
                            }
                        }
                        else {
                            Toast.makeText(Pharmacy_Login.this, "Account not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}