package com.example.store.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.store.R;
import com.example.store.adapter.CartAdapter;
import com.example.store.adapter.ProductsAdapter;
import com.example.store.database.SqlCards;
import com.example.store.database.SqlDatabase;
import com.example.store.model.Products;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ir.apend.slider.model.Slide;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Cart extends AppCompatActivity {

    ArrayList<Products> product = new ArrayList<>();
    CartAdapter adapter;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        RecyclerView rec = findViewById(R.id.recCart);
        ImageView bak = findViewById(R.id.back);

        bak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //recyclerView and SqLite
        SqlCards sqlDatabase = new SqlCards(Cart.this);
        ArrayList<Products> productslist = sqlDatabase.getData();
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(Cart.this,
                RecyclerView.VERTICAL,false);
        rec.setLayoutManager(verticalLayoutManager);
        adapter = new CartAdapter(this,productslist);
        rec.setAdapter(adapter);
        new Refresh().execute();


    }

    //refresh the activity on time
    public void hi(){

        RecyclerView rec = findViewById(R.id.recCart);
        ImageView bak = findViewById(R.id.back);
        adapter.notifyDataSetChanged();
        new Refresh().execute();

        SqlCards sqlDatabase = new SqlCards(Cart.this);
        ArrayList<Products> productslist = sqlDatabase.getData();
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(Cart.this,
                RecyclerView.VERTICAL,false);
        rec.setLayoutManager(verticalLayoutManager);
        adapter = new CartAdapter(this, productslist);
        rec.setAdapter(adapter);

    }

    //api
    class Refresh extends AsyncTask {



        Response response;
        OkHttpClient client;
        Request request;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            request = new Request.Builder()
                    .url("https://znoonstyle.com/wp-json/public-woo/v1/products")
                    .get()
                    .build();

        }

        @Override
        protected Object doInBackground(Object[] objects) {


            try {
                response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    return response.body().string();
                }
            }catch (IOException e) {

            }
            return null;
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (o != null) {
                Products products = new Products();
                List<Slide> slideList = new ArrayList<>();
                try {
                    JSONArray res =new JSONArray(String.valueOf(o));
                    for (int i = 0; i < res.length(); i++) {
                        JSONObject data =res.getJSONObject(i);
                        if(data.getInt("id") == id){
                            JSONArray imagearray = data.getJSONArray("images");
                            JSONObject imageObject = imagearray.getJSONObject(0);
                            products.name = data.getString("name");
                            products.price = data.getString("price" )+" تومان ";
                            products.img1 = imageObject.getString("src");
                            products.description = android.text.Html.fromHtml(data.getString("short_description")).toString();
                            products.id = data.getInt("id");
                            product.add(products);


                        }

                    }


                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {


                }
            }
        }
    }


}