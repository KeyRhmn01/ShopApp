package com.example.store.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhargavms.dotloader.DotLoader;
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
import ir.apend.slider.ui.Slider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Details extends AppCompatActivity {


    int id;
    ImageView star;
    ImageView empyStar;
    Products products = new Products();
    SqlDatabase sqlDatabase = new SqlDatabase(Details.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        star = findViewById(R.id.star);
        empyStar = findViewById(R.id.emptyStar);
        TextView courseName = findViewById(R.id.priceD);
        TextView courseDuration = findViewById(R.id.details);
        TextView courseDescription = findViewById(R.id.nameD);
        Button cart = findViewById(R.id.cartGo);

        //find ID
        id = getIntent().getIntExtra("id", -1);
        new Refresh().execute();

        //buttons
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SqlCards sqlCards = new SqlCards(Details.this);
                if (sqlCards.getById(id)) {
                    Toast.makeText(Details.this, ":) محصول در سبد خرید موجود است!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Details.this, ":) محصول به سبد خرید اضافه شد!!", Toast.LENGTH_SHORT).show();

                }


                sqlCards.Insert(products.id, products.name, products.description, products.count, products.price ,products.price, products.img1);
                ArrayList<Products> productslist = sqlCards.getData();
                productslist = sqlCards.getData();


            }
        });

        ImageView bak = findViewById(R.id.back);
        bak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        empyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sqlDatabase.Insert(products.id, products.name, products.description, products.price, products.img1);
                ArrayList<Products> productslist = sqlDatabase.getData();
                productslist = sqlDatabase.getData();
                star.setVisibility(View.VISIBLE);
                empyStar.setVisibility(View.GONE);


            }
        });

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDatabase.delete(id);
                empyStar.setVisibility(View.VISIBLE);
                star.setVisibility(View.GONE);
            }
        });


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
            } catch (IOException e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (o != null) {

                List<Slide> slideList = new ArrayList<>();
                try {
                    JSONArray res = new JSONArray(String.valueOf(o));
                    for (int i = 0; i < res.length(); i++) {
                        JSONObject data = res.getJSONObject(i);
                        if (data.getInt("id") == id) {
                            products.id = data.getInt("id");
                            JSONArray imagearray = data.getJSONArray("images");
                            JSONObject imageObject = imagearray.getJSONObject(0);
                            products.img1 = imageObject.getString("src");
                            slideList.add(new Slide(0, imageObject.getString("src"),
                                    getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
                            JSONObject imageObject2 = imagearray.getJSONObject(1);
                            products.img2 = imageObject2.getString("src");
                            slideList.add(new Slide(1, imageObject2.getString("src"),
                                    getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
                            JSONObject imageObject3 = imagearray.getJSONObject(2);
                            products.img3 = imageObject3.getString("src");
                            slideList.add(new Slide(2, imageObject3.getString("src"),
                                    getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
                            products.name = data.getString("name");
                            products.description = android.text.Html.fromHtml(data.getString("short_description")).toString();
                            products.price = data.getString("price");
                            ;
                            DotLoader dotLoader = findViewById(R.id.text_dot_loader);
                            dotLoader.setVisibility(View.GONE);
                            View v = findViewById(R.id.v);
                            v.setVisibility(View.VISIBLE);
                            View vb = findViewById(R.id.v2);
                            vb.setVisibility(View.VISIBLE);
                            if (sqlDatabase.getById(id)) {
                                star.setVisibility(View.VISIBLE);
                                empyStar.setVisibility(View.GONE);
                            } else {
                                empyStar.setVisibility(View.VISIBLE);
                                star.setVisibility(View.GONE);
                            }

                        }

                    }


                    TextView n = findViewById(R.id.nameD);
                    TextView d = findViewById(R.id.details);
                    TextView p = findViewById(R.id.priceD);

                    //slider
                    Slider slider = findViewById(R.id.picture);
                    slider.setItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //do what you want
                        }
                    });
                    slider.addSlides(slideList);
                    n.setText(products.name);
                    d.setText(products.description);
                    p.setText(products.price);
                } catch (JSONException e) {

                }
            }
        }
    }
}