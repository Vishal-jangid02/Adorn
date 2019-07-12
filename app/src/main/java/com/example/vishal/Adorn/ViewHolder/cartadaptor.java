package com.example.vishal.Adorn.ViewHolder;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.vishal.Adorn.Activity.cart;
import com.example.vishal.Adorn.Interface.ItemClickListener;
import com.example.vishal.Adorn.MyPojo.orderpojo;
import com.example.vishal.Adorn.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.view.View.*;

class  cartViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView txt_cart_name,txt_price,txt_quantity;
    public Button btnIncrement,btnDecrement,btnItemCancel;
    private ItemClickListener itemClickListener;
    CardView cartcard;


    public void set_txt_cart_name(TextView txt_cart_name)
    {
        this.txt_cart_name=txt_cart_name;
    }
    public cartViewholder(View itemView) {
        super(itemView);
        txt_cart_name=(TextView)itemView.findViewById(R.id.cart_item_name);
        txt_price=(TextView)itemView.findViewById(R.id.cart_item_price);
        txt_quantity = (TextView) itemView.findViewById(R.id.quantity);
        btnDecrement = (Button) itemView.findViewById(R.id.btnDecrement);
        btnIncrement = (Button) itemView.findViewById(R.id.btnIncrement);
        btnItemCancel = (Button) itemView.findViewById(R.id.btnItemCancel);
        cartcard = (CardView)itemView.findViewById(R.id.cartcard);
    }
        @Override
    public void onClick(View v) {

    }
}
public class cartadaptor  extends  RecyclerView.Adapter<cartViewholder>
{
    cartadaptor cartadap ;
    orderpojo pojo2, orderpojo1 ;
    private ArrayList<orderpojo> listData;
    private Context context;
    String  key;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference1 = firebaseDatabase.getReference("cartitem");
    boolean flag3=false;


    public cartadaptor(ArrayList<orderpojo> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public cartViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.cartlayout,parent,false);
        return new cartViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final cartViewholder holder, final int position) {
     //TextDrawable drawable=TextDrawable.builder().buildRound(""+listData.get(position).getQuantity(), Color.RED);

     orderpojo1 = listData.get(position);
                     /*holder.img_cart_count.setImageDrawable(drawable);*/
/*       Locale locale=new Locale("en","UB");
        NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);
        float price=(Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
         holder.txt_price.setText(fmt.format(price));*/

       holder.txt_cart_name.setText(orderpojo1.getProductname());
       holder.txt_price.setText(orderpojo1.getPrice());

       holder.txt_quantity.setText(orderpojo1.getQuantity());
       holder.btnDecrement.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               if(holder.txt_quantity.getText().toString().equals("1")){
                   Toast.makeText(context, "Order Atleast One ", Toast.LENGTH_LONG).show();
                   }
                   else{
                   int i = Integer.parseInt(holder.txt_quantity.getText().toString());
                   i = i - 1;
                   holder.txt_quantity.setText(""+i);
                   double price = Double.valueOf(holder.txt_price.getText().toString());
                   price = price - Double.valueOf(orderpojo1.getPrice());
                   double comp_price = Double.valueOf(cart.totalprice.getText().toString());
                   comp_price = comp_price - Double.valueOf(orderpojo1.getPrice());
                   holder.txt_price.setText("" + price);
                   cart.totalprice.setText(""+comp_price);

               }

           }
       });

       holder.btnItemCancel.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               pojo2 = listData.get(position);
               /*double comp_price = Double.valueOf(cart.totalprice.getText().toString());
               comp_price = comp_price - Double.valueOf(holder.txt_price.getText().toString());
               cart.totalprice.setText("" + comp_price);*/
              reference1.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       for(DataSnapshot d : dataSnapshot.getChildren())
                       {
                           orderpojo pojo3 = new orderpojo();
                           pojo3 = d.getValue(orderpojo.class);
                           if(pojo2.getEmail().equals(pojo3.getEmail()) && pojo2.getProductid().equals(pojo3.getProductid()))
                           {
                               key = d.getKey();
                              reference1.child(key).removeValue();
                               listData.remove(position);
                               cartadap = new cartadaptor(listData,context);
                               cartadap.notifyDataSetChanged();
                               context.startActivity(new Intent(context, cart.class));

                               Toast.makeText(context, "Data Deleted", Toast.LENGTH_LONG).show();
                               double comp_price = Double.valueOf(cart.totalprice.getText().toString());
                               comp_price = comp_price - Double.valueOf(holder.txt_price.getText().toString());
                               cart.totalprice.setText("" + comp_price);
                               break;
                           }
                       }

                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });



               }
       });
        /*cartadap.notifyDataSetChanged();*/
        holder.btnIncrement.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.txt_quantity.getText().toString().equals("5")){
                    Toast.makeText(context, "Max Quantity can be 5", Toast.LENGTH_LONG).show();
                }
                else{
                    int i = Integer.parseInt(holder.txt_quantity.getText().toString());
                    i = i + 1;
                    Log.e("946087", "onClick: "+ i );
                    holder.txt_quantity.setText(""+i);
                    double price = Double.valueOf(holder.txt_price.getText().toString());
                    price = price + Double.valueOf(orderpojo1.getPrice());
                    double comp_price = Double.valueOf(cart.totalprice.getText().toString());
                    comp_price = comp_price + Double.valueOf(orderpojo1.getPrice());
                    holder.txt_price.setText("" + price);
                    cart.totalprice.setText(""+comp_price);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
