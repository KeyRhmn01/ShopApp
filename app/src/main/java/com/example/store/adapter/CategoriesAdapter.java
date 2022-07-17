package com.example.store.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store.R;
import com.example.store.activity.Details;
import com.example.store.activity.ProductsCat;
import com.example.store.model.Categories;
import com.example.store.model.Products;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyView> {

    List<Categories> categories;
    Context context;

    public CategoriesAdapter(ArrayList<Categories> categories){
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoriesAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new CategoriesAdapter.MyView(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.MyView holder, @SuppressLint("RecyclerView") int position) {

        holder.lab.setText(categories.get(position).label);
        holder.lab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductsCat.class);
                intent.putExtra("id", categories.get(position).id);
                intent.putExtra("label", categories.get(position).label);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        public TextView lab;
        public int id;

        public MyView(@NonNull View itemView) {
            super(itemView);

            lab = itemView.findViewById(R.id.text1);

        }
    }
}
