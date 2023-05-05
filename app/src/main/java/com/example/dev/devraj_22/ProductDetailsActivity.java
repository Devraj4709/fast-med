package com.example.dev.devraj_22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dev.devraj_22.Model.Products;
import com.example.dev.devraj_22.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.internal.cache.DiskLruCache;

public class ProductDetailsActivity extends AppCompatActivity {

    Button add_to_cart;
    TextView quantity,productname,productprice,productcontent;
    String Productid="";
    ImageView productimage;
    ImageButton increment,decrement ;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
         Productid =getIntent().getStringExtra("pid");
        increment =findViewById(R.id.productdetails_increment_btn);
        decrement =findViewById(R.id.productdetails_decrement_btn);
        quantity =findViewById(R.id.productdetails_quantity);
        productname=findViewById(R.id.productdetails_pname);
        productcontent =findViewById(R.id.productdetails_content);
        productprice =findViewById(R.id.productdetails_price);
        add_to_cart=findViewById(R.id.productdetails_add_tocart);
        productimage =findViewById(R.id.productdetails_image);
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Currentvalue=quantity.getText().toString();
                int value =Integer.parseInt(Currentvalue);

                    value++;


                quantity.setText(String.valueOf(value));
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Currentvalue=quantity.getText().toString();
                int value =Integer.parseInt(Currentvalue);
                if(value==0)
                {
                    value=0;
                }
                else {
                    value--;
                }

                quantity.setText(String.valueOf(value));
            }
        });
        
        getproductdetails(Productid);

    }

    private void getproductdetails(String productid) {

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Products");
        databaseReference.child(Productid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Products products =snapshot.getValue(Products.class);
                    productname.setText(products.getPname());
                    productcontent.setText("Available At: "+products.getDescription());
                    productprice.setText("Rs."+products.getPrice());

                    Picasso.get().load(products.getImage()).into(productimage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();
            }

            private void addingToCartList() {

                String saveCurrentTime,saveCurrentDate;

                Calendar calForDate =Calendar.getInstance();
                SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd,yyy");
                saveCurrentDate = currentdate.format(calForDate.getTime());


                SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentTime.format(calForDate.getTime());

                final DatabaseReference cartListref = FirebaseDatabase.getInstance().getReference().child("Cart List");
                 final HashMap<String,Object> cartMap =new HashMap<>();
                 cartMap.put("pid",productid);
                cartMap.put("pname",productname.getText().toString());
                cartMap.put("price",productprice.getText().toString());
                cartMap.put("quantity",quantity.getText().toString());
                cartMap.put("date",saveCurrentDate);
                cartMap.put("discount","");
                cartMap.put("time",saveCurrentTime);
                cartListref.child("User view").child(Prevalent.currentOnlineUser.getPhone()).child("Products").child(productid)
                        .updateChildren(cartMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    cartListref.child("Admin view").child(Prevalent.currentOnlineUser.getPhone()).child("Products").child(productid)
                                            .updateChildren(cartMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {


                                                    if(task.isSuccessful())
                                                    {
                                                        Toast.makeText(ProductDetailsActivity.this, "Added to cart ", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(ProductDetailsActivity.this,Homepage.class);
                                                        startActivity(intent);
                                                    }

                                                }
                                            });
                                }

                            }
                        });

            }
        });
    }
}