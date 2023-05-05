package com.example.dev.devraj_22.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dev.devraj_22.Interface.ItemClicklistener;
import com.example.dev.devraj_22.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtproductname,txtproductdescription,txtproductprice ,txtproductaddress;
    public ImageView imageView;
    public ItemClicklistener listener;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.product_image);
        txtproductname =itemView.findViewById(R.id.product_name);
        txtproductdescription =itemView.findViewById(R.id.product_description);
        txtproductprice =itemView.findViewById(R.id.product_price);

    }
    public void setItemClickListener(ItemClicklistener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v,getAdapterPosition(),false);
    }
}
