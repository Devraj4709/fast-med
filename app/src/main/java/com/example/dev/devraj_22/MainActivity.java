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

import com.example.dev.devraj_22.Model.Users;
import com.example.dev.devraj_22.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

  //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView signup = findViewById(R.id.signup_text_view);
        Button loginbtn = findViewById(R.id.login_button);
        EditText Inputphone= findViewById(R.id.phone_edit_text);
        EditText InputPassword= findViewById(R.id.password_edit_text) ;
        ProgressDialog loadingBar =new ProgressDialog(this);
        String parentDbname ="Users";

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
                   Toast.makeText(MainActivity.this, "please enter your phone number", Toast.LENGTH_SHORT).show();
               }
               else if (TextUtils.isEmpty(password))
               {
                   Toast.makeText(MainActivity.this, "please enter your password", Toast.LENGTH_SHORT).show();
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
                                   Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                   loadingBar.dismiss();
                                   Intent intent = new Intent(MainActivity.this,Homepage.class);

                                   Prevalent.currentOnlineUser =usersData;
                                   startActivity(intent);
                               }
                               else{
                                   Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                   loadingBar.dismiss();
                                   Intent intent = new Intent(MainActivity.this,MainActivity.class);
                                   startActivity(intent);
                               }
                           }
                       }
                       else {
                           loadingBar.dismiss();
                           Toast.makeText(MainActivity.this, "Account not found", Toast.LENGTH_SHORT).show();
                           loadingBar.dismiss();
                           Intent intent = new Intent(MainActivity.this,MainActivity.class);
                           startActivity(intent);
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {


                   }
               });
           }
       });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

    }
}