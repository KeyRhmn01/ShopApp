package com.example.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store.R;
import com.example.store.model.Products;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PupulerAdapter extends RecyclerView.Adapter<PupulerAdapter.MyView> {

    List<Products> pr;
    Context context;

    public PupulerAdapter(ArrayList<Products> pr) {
        this.pr = pr;
    }

    @NonNull
    @Override
    public PupulerAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizhome_row, parent, false);
        return new MyView(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull PupulerAdapter.MyView holder, int position) {

        holder.name.setText(pr.get(position).name);
        holder.price.setText(pr.get(position).price);
        Picasso.get().load(pr.get(position).img1).into(holder.avatar);
        holder.dit.setText(pr.get(position).description);

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

        public MyView(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            avatar = itemView.findViewById(R.id.picture);
            dit = itemView.findViewById(R.id.desc);


        }
    }
}
