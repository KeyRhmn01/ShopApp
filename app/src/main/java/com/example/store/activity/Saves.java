package com.example.store.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.store.R;
import com.example.store.adapter.ProductsAdapter;
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

public class Saves extends AppCompatActivity {
    private static final String TAG = "kia";
    ArrayList<Products> product = new ArrayList<>();
    ProductsAdapter adapter;
    int id;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saves);
        ImageView bak = findViewById(R.id.back);
        RecyclerView rec = findViewById(R.id.recycleSave);
        SwipeRefreshLayout swp = findViewById(R.id.swap);

        //Buttons
        bak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        swp.setColorSchemeResources(R.color.main);
        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swp.setRefreshing(false);
                new Refresh().execute();


            }
        });

        //SqLite and Recycler and adapter
        SqlDatabase sqlDatabase = new SqlDatabase(Saves.this);
        ArrayList<Products> productslist = sqlDatabase.getData();
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(Saves.this,
                RecyclerView.VERTICAL,false);
        rec.setLayoutManager(verticalLayoutManager);
        adapter = new ProductsAdapter(productslist);
        rec.setAdapter(adapter);

    }

    //onResume
    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: kia");
        super.onResume();
        RecyclerView rec = findViewById(R.id.recycleSave);
        ImageView bak = findViewById(R.id.back);

        //Buttons
        bak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //SqLite and Recycler
        SqlDatabase sqlDatabase = new SqlDatabase(Saves.this);
        ArrayList<Products> productslist = sqlDatabase.getData();
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(Saves.this,
                RecyclerView.VERTICAL,false);
        rec.setLayoutManager(verticalLayoutManager);
        adapter = new ProductsAdapter(productslist);
        rec.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
                            products.price = data.getString("price" );
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