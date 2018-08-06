package com.example.ga.pizzahouse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    public static ArrayList<Pizza> modelArrayList;
    private Context ctx;

    public RecyclerViewAdapter(Context ctx, ArrayList<Pizza> imageModelArrayList) {

        inflater = LayoutInflater.from(ctx);
        this.modelArrayList = imageModelArrayList;
        this.ctx = ctx;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_view_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.pName.setText(modelArrayList.get(position).getPizzaName());
        double pizzaFloat = modelArrayList.get(position).getPizzaPrice();
        holder.pPrice.setText(String.valueOf(pizzaFloat));
        holder.pDescription.setText(modelArrayList.get(position).getPizzaDescription());

        holder.checkBox.setChecked(modelArrayList.get(position).isSelected());
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer) holder.checkBox.getTag();
                if (modelArrayList.get(pos).isSelected()) {
                    modelArrayList.get(pos).setSelected(false);
                } else {
                    modelArrayList.get(pos).setSelected(true);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        protected CheckBox checkBox;
        private TextView pName;
        private TextView pPrice;
        private TextView pDescription;

        public MyViewHolder(View itemView) {
            super(itemView);

            checkBox=itemView.findViewById(R.id.checkbox1);
            pName=itemView.findViewById(R.id.pName);
            pPrice=itemView.findViewById(R.id.pPrice);
            pDescription=itemView.findViewById(R.id.pDescription);
        }

    }
}


