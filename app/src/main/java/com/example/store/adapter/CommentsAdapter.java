package com.example.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store.R;
import com.example.store.model.Categories;
import com.example.store.model.Coment;
import com.example.store.model.Products;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyView> {

    List<Coment> coments;
    Context context;

    public CommentsAdapter(ArrayList<Coment> coments) {
        this.coments = coments;
    }

    @NonNull
    @Override
    public CommentsAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_row, parent, false);
        return new CommentsAdapter.MyView(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.MyView holder, int position) {

        holder.nameC.setText(coments.get(position).nameC);
        holder.gmail.setText(coments.get(position).gmail);
        holder.desc.setText(coments.get(position).desc);


    }

    @Override
    public int getItemCount() {
        return coments.size();
    }

    public class MyView extends RecyclerView.ViewHolder {

        public int idC;
        public TextView nameC;
        public TextView gmail;
        public TextView desc;


        public MyView(@NonNull View itemView) {
            super(itemView);

            nameC = itemView.findViewById(R.id.nameCom);
            gmail = itemView.findViewById(R.id.gmailCom);
            desc = itemView.findViewById(R.id.descCom);


        }
    }
}
