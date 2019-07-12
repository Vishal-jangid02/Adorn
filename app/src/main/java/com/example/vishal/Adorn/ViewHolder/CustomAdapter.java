package com.example.vishal.Adorn.ViewHolder;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vishal.Adorn.Activity.YourOrder;
import com.example.vishal.Adorn.Interface.ItemClickListener;
import com.example.vishal.Adorn.MyPojo.orderpojo;
import com.example.vishal.Adorn.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter{

    Context context;
    ArrayList<orderpojo> item;
    int resource;



    public CustomAdapter(@NonNull Context context, int resource, ArrayList<orderpojo> item) {
        super(context, resource,item);
        this.context=context;
        this.resource = resource;
        this.item = item;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource,  null, false);
        TextView textName = view.findViewById(R.id.pro_Name);
        TextView textprice = view.findViewById(R.id.pro_pirce);
        TextView textstatus = view.findViewById(R.id.pro_status);
        final orderpojo orderpojo1 = item.get(position);
        textName.setText(orderpojo1.getProductname());
        textprice.setText(orderpojo1.getPrice());
        textstatus.setText(YourOrder.status);

        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        return view;
    }
}
