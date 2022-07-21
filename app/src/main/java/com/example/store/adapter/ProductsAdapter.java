package com.example.store.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store.R;
import com.example.store.activity.Details;
import com.example.store.model.Products;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyView> {

    List<Products> pr;
     Context context;
    private int position;

    public ProductsAdapter(ArrayList<Products> pr){
        this.pr = pr;
    }

    @NonNull
    @Override
    public ProductsAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_home_row, parent, false);
        return new MyView(itemview);
    }



    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyView holder,  int position) {

        holder.name.setText(pr.get(position).name);
        holder.price.setText(pr.get(position).price);
        Picasso.get().load(pr.get(position).img1).into(holder.avatar);
        holder.dit.setText(pr.get(position).description);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Details.class);
                intent.putExtra("id", pr.get(position).id);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return pr.size();
    }

    public class MyView extends RecyclerView.ViewHolder {

        public ImageView avatar;
        public TextView name;
        public TextView price;
        public RelativeLayout layout;
        public TextView dit;


        public MyView( View itemView) {
            super(itemView);

                name = itemView.findViewById(R.id.nameR);
                price = itemView.findViewById(R.id.price);
                avatar = itemView.findViewById(R.id.aks);
                layout = itemView.findViewById(R.id.clickRelative);
                dit = itemView.findViewById(R.id.details);
                layout = itemView.findViewById(R.id.clickRelative);


            }

    }
}
