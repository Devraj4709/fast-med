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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register_Pharmacy extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pharmacy);

        EditText Shopname = (EditText)findViewById(R.id.Pharmacy_name) ;
        EditText Shopphone= (EditText)findViewById(R.id.Pharmacy_number);
        String parentDbname ="Pharmacy";
        EditText ShopAddress =(EditText)findViewById(R.id.Pharmacy_Address);
         EditText ShopPassword= (EditText)findViewById(R.id.Pharmacy_Password_protect);
        EditText ShopRDL= (EditText)findViewById(R.id.Pharmacy_verificatio);
        Button RegisterBtn = (Button)findViewById(R.id.Pharmacy_register_button);
        ProgressDialog loadingBar =new ProgressDialog(this);

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateAccount();
            }

            private void CreateAccount() {
                String name =Shopname.getText().toString();
                String phone =Shopphone.getText().toString();
                String password=ShopPassword.getText().toString();
                String Rdl =ShopRDL.getText().toString();
                String Address =ShopAddress.getText().toString();

                if(TextUtils.isEmpty(name))
                {
                    Toast.makeText(Register_Pharmacy.this, "pleaase enter name of store", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Rdl))
                {
                    Toast.makeText(Register_Pharmacy.this, "please enter the correct Retail drug licence number", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(phone))
                {
                    Toast.makeText(Register_Pharmacy.this, "please enter contact number of pharmacy", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(Register_Pharmacy.this, "please enter your password", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Address))
                {
                    Toast.makeText(Register_Pharmacy.this, "please enter the address of the store", Toast.LENGTH_SHORT).show();
                }
                else {
                    loadingBar.setTitle("create account");
                    loadingBar.setMessage("Please wait,while we are checking the credentials.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    ValidatephoneNumber(name ,phone,Rdl,password,Address);
                }
            }

            private void ValidatephoneNumber(String name, String phone, String Rdl, String password,String address) {

                final DatabaseReference Rootref;
                Rootref = FirebaseDatabase.getInstance().getReference();
                Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!(snapshot.child(parentDbname).child(phone).exists()))
                        {
                            HashMap<String,Object> userdataMap =new HashMap<>();
                            userdataMap.put("phone",phone);
                            userdataMap.put("name",name);
                            userdataMap.put("RDL",Rdl);
                            userdataMap.put("password",password);
                            userdataMap.put("address",address);
                            Rootref.child(parentDbname).child(phone).updateChildren(userdataMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {

                                                Toast.makeText(Register_Pharmacy.this, "Congratulations ,your account created", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                                Intent intent =new Intent(Register_Pharmacy.this,Pharmacy_Page.class);
                                                startActivity(intent);
                                            }
                                            else {
                                                Toast.makeText(Register_Pharmacy.this, "Network error : try again later", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else {
                            Toast.makeText(Register_Pharmacy.this, "This number already have an account", Toast.LENGTH_SHORT).show();
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