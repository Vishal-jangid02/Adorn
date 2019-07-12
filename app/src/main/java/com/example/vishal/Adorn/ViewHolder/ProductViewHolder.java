package com.example.vishal.Adorn.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vishal.Adorn.Interface.ItemClickListener;
import com.example.vishal.Adorn.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView product_name;
    public ImageView product_image;

    private ItemClickListener itemClickListener;

    public ProductViewHolder(View itemView) {
        super(itemView);

        product_name=(TextView)itemView.findViewById(R.id.product_name);
        product_image=(ImageView)itemView.findViewById(R.id.product_image);

        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
