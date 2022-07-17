package com.example.store.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bhargavms.dotloader.DotLoader;
import com.example.store.R;
import com.example.store.adapter.CategoriesAdapter;
import com.example.store.adapter.ProductsAdapter;
import com.example.store.fragment.CatalogFragment;
import com.example.store.model.Categories;
import com.example.store.model.Products;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProductsCat extends AppCompatActivity {

    ProductsAdapter adapter;
    ArrayList<Categories> categories = new ArrayList<>();
    ArrayList<Products> product = new ArrayList<>();
    int id;
    String label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_cat);
        TextView title = findViewById(R.id.textCat);

        //recycler and adapter
        RecyclerView rec = findViewById(R.id.recyclerCat);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL,false);
        rec.setLayoutManager(verticalLayoutManager);
        adapter = new ProductsAdapter(product);
        rec.setAdapter(adapter);

        //ID and LABALE find
        id = getIntent().getIntExtra("id", -1);
        label = getIntent().getStringExtra("label");
        //title
        title.setText(label);

        new Refresh().execute();

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
                    .url("https://znoonstyle.com/wp-json/public-woo/v1/products?category=" + id)
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
            } catch (IOException e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (o != null) {
                try {
                    JSONArray res =new JSONArray(String.valueOf(o));
                    for (int i = 0; i < res.length(); i++) {
                        JSONObject data =res.getJSONObject(i);
                        JSONArray imagearray = data.getJSONArray("images");
                        JSONObject imageObject = imagearray.getJSONObject(0);
                        Products products = new Products();
                        products.name = data.getString("name");
                        products.price = data.getString("price" )+" تومان ";
                        products.img1 = imageObject.getString("src");
                        products.description = android.text.Html.fromHtml(data.getString("short_description")).toString();
                        products.id = data.getInt("id");
                        product.add(products);
                        DotLoader dotLoader = findViewById(R.id.text_dot_loaderCat);
                        dotLoader.setVisibility(View.GONE);
                    }

                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {



                }
            }
        }


    }

}