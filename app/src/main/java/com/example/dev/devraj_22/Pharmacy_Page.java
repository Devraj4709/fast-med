package com.example.dev.devraj_22;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.appsearch.StorageInfo;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.EventLogTags;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

@SuppressWarnings("deprecation")
@SuppressLint("MissingInflatedId")

public class Pharmacy_Page extends AppCompatActivity {


    private static final int GalleryPick = 1;
    private Uri Imageuri;
    private String productRandomKey,downloadImageUrl;
    private StorageReference ProductImageRef;
    private ImageView uploadImage;
    private String Description,Pname,Price,Address;
    private String saveCurrentDate,saveCurrentTime;
    private DatabaseReference Productref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_page);
        EditText productname = findViewById(R.id.Product_name_page);
        EditText productcontent = findViewById(R.id.Product_content_page);
        EditText productprice = findViewById(R.id.Product_price_page);
        ProgressDialog loadingBar =new ProgressDialog(this);

        Button upload =findViewById(R.id.Pharmacy_upload_button);
               uploadImage =findViewById(R.id.uploadimage);
               ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
               Productref =FirebaseDatabase.getInstance().getReference().child("Products");
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }

        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidateProductData();
            }

            private void ValidateProductData() {
                 Description =productcontent.getText().toString();
                Pname =productname.getText().toString();
                Price =productprice.getText().toString();

                if(Imageuri == null){
                    Toast.makeText(Pharmacy_Page.this, "Product image is mandatory......", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Description)){
                    Toast.makeText(Pharmacy_Page.this, "Product decription is mandatory......", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Pname)){
                    Toast.makeText(Pharmacy_Page.this, "Product name is mandatory......", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Price)){
                    Toast.makeText(Pharmacy_Page.this, "Product Price is mandatory......", Toast.LENGTH_SHORT).show();
                }
                else {

                    loadingBar.setTitle("Uploading medicine");
                    loadingBar.setMessage("Medicine is being uploaded");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    storeProductInformation();

                }

            }


        });

    }

    private void storeProductInformation() {
        Calendar calendar =Calendar.getInstance();
        SimpleDateFormat currentDate =new SimpleDateFormat("MMM dd, yyyy");
        String saveCurrentDate,saveCurrentTime;
        saveCurrentDate  = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime =new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        StorageReference filepath =ProductImageRef.child(Imageuri.getLastPathSegment() + productRandomKey+ ".jpg");
        final UploadTask uploadTask =filepath.putFile(Imageuri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message =e.toString();
                Toast.makeText(Pharmacy_Page.this, "Error:" + message, Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Pharmacy_Page.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask =uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    downloadImageUrl =filepath.getDownloadUrl().toString();
                    return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadImageUrl =task.getResult().toString();

                            Toast.makeText(Pharmacy_Page.this, "got the Product image url succesfully", Toast.LENGTH_SHORT).show();
                            SaveProductInfotoDatabase();

                        }
                    }
                });
            }
        });
    }
    private void SaveProductInfotoDatabase(){
        HashMap<String,Object> productMap =new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",Description );
        productMap.put("image",downloadImageUrl);
        productMap.put("price",Price);
        productMap.put("pname",Pname);


        Productref.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {

                            Intent intent =new Intent(Pharmacy_Page.this,Pharmacy_Page.class);
                            startActivity(intent);
                            Toast.makeText(Pharmacy_Page.this, "Product is added successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String message = task.getException().toString();
                            Toast.makeText(Pharmacy_Page.this, ""+message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void opengallery() {
        Intent galleryintent =new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GalleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==GalleryPick && resultCode==RESULT_OK && data!=null){
            Imageuri =data.getData();
            uploadImage.setImageURI(Imageuri);
        }
    }
}