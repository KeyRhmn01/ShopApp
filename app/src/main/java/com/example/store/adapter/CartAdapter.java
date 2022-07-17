package com.example.store.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store.R;
import com.example.store.activity.Cart;
import com.example.store.activity.Details;
import com.example.store.activity.Saves;

import com.example.store.database.SqlCards;
import com.example.store.model.Products;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyView>{

    List<Products> pr;
    Context context;
    Activity activity;

    public CartAdapter(Activity activity, ArrayList<Products> pr) {
        this.pr = pr;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CartAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row, parent, false);
        return new CartAdapter.MyView(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyView holder, @SuppressLint("RecyclerView") int position) {

        holder.name.setText(pr.get(position).name);
        holder.price.setText(pr.get(position).price);
        Picasso.get().load(pr.get(position).img1).into(holder.avatar);
        holder.dit.setText(pr.get(position).description);

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SqlCards sqlCards = new SqlCards(context);
                int id = pr.get(position).id;
                sqlCards.delete(id);
                Toast.makeText(context, pr.get(position).name + "  باموفقیت حذف شد(;", Toast.LENGTH_SHORT).show();
                ((Cart)activity).hi();


            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Details.class);
                intent.putExtra("id", pr.get(position).id);
                context.startActivity(intent);
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                Products products = new Products();
                //not finished
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
        public Button remove;
        public RelativeLayout relativeLayout;
        public ImageView plus;
        public ImageView neg;
        public TextView numb;

        public MyView(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.titleCart);
            price = itemView.findViewById(R.id.priceCart);
            avatar = itemView.findViewById(R.id.pic);
            dit = itemView.findViewById(R.id.descCart);
            remove = itemView.findViewById(R.id.remove);
            relativeLayout = itemView.findViewById(R.id.clickRelCart);
            plus = itemView.findViewById(R.id.plus);
            neg = itemView.findViewById(R.id.neg);
            numb = itemView.findViewById(R.id.number);


        }
    }
}
