package com.example.dev.devraj_22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Doctor_signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);

        TextView signin = findViewById(R.id.dsignIn_text_view);
        EditText fullname = (EditText)findViewById(R.id.dfirstname_signup) ;
        EditText Inputphone= (EditText)findViewById(R.id.dsign_phone_1);
        EditText InputPassword= (EditText)findViewById(R.id.dpassword_signup) ;
        Button RegisterBtn = (Button)findViewById(R.id.dsignup_button);
        EditText InputEmail = (EditText)findViewById(R.id.demail_signup);
        ProgressDialog loadingBar =new ProgressDialog(this);

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateAccount();
            }

            private void CreateAccount() {
                String name =fullname.getText().toString();
                String phone =Inputphone.getText().toString();
                String password=InputPassword.getText().toString();
                String email =InputEmail.getText().toString();

                if(TextUtils.isEmpty(name))
                {
                    Toast.makeText(Doctor_signup.this, "pleaase enter your name", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(phone))
                {
                    Toast.makeText(Doctor_signup.this, "please enter your phone number", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(Doctor_signup.this, "please enter your email", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(Doctor_signup.this, "please enter your password", Toast.LENGTH_SHORT).show();
                }
                else {
                    loadingBar.setTitle("create account");
                    loadingBar.setMessage("Please wait,while we are checking the credentials.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    ValidatephoneNumber(name ,phone,email,password);
                }
            }

            private void ValidatephoneNumber(String name, String phone, String email, String password) {

                final DatabaseReference Rootref;
                Rootref = FirebaseDatabase.getInstance().getReference();
                Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!(snapshot.child("Doctors").child(phone).exists()))
                        {
                            HashMap<String,Object> userdataMap =new HashMap<>();
                            userdataMap.put("phone",phone);
                            userdataMap.put("name",name);
                            userdataMap.put("email",email);
                            userdataMap.put("password",password);
                            Rootref.child("Users").child(phone).updateChildren(userdataMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {

                                                Toast.makeText(Doctor_signup.this, "Congratulations ,your account created", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                                Intent intent =new Intent(Doctor_signup.this,MainActivity.class);
                                                startActivity(intent);
                                            }
                                            else {
                                                Toast.makeText(Doctor_signup.this, "Network error : try again later", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else {
                            Toast.makeText(Doctor_signup.this, "This number already have an account", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Doctor_signup.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    }
