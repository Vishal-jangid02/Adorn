/*
package com.example.vishal.Adorn.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vishal.Adorn.Interface.ItemClickListener;
import com.example.vishal.Adorn.MyPojo.Request;
import com.example.vishal.Adorn.R;

import java.util.List;

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private Context context;
    private List<Request> list ;
    public RecyclerAdapter(Context context, List<Request> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_layout,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Request req = list.get(position);
        holder.tid.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tid,tstatus,tphone,taddress;

        public ViewHolder(View itemView) {
            super(itemView);

            taddress=(TextView)itemView.findViewById(R.id.orderaddress);
            tid=(TextView)itemView.findViewById(R.id.orderid);
            tstatus=(TextView)itemView.findViewById(R.id.orderstatus);
            tphone=(TextView)itemView.findViewById(R.id.orderphone);

        }
    }
}
*/
