package com.example.store.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.store.R;
import com.example.store.activity.Cart;
import com.example.store.activity.ProductsCat;
import com.example.store.activity.Saves;
import com.example.store.adapter.ProductsAdapter;
import com.example.store.adapter.PupulerAdapter;
import com.example.store.databinding.FragmentHomeBinding;
import com.example.store.model.Categories;
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


public class HomeFragment extends Fragment {

    Context context;
    private FragmentHomeBinding binding;
    PupulerAdapter adapterP;
    Categories categories = new Categories();
    ArrayList<Products> product = new ArrayList<>();
    ProductsAdapter adapter;
    int id ;
    String lable;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.context = container.getContext();
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        //Slider slider = findViewById(R.id.picture);
        //create list of slides

        Drawable drawable = getResources().getDrawable(R.drawable.baner);

        List<Slide> slideList = new ArrayList<>();
        slideList.add(new Slide(0,"https://dkstatics-public.digikala.com/digikala-adservice-banners/f34f896372f524e487c9f8f7717eb0dd583e3fb3_1657989351.gif" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(1,"https://dkstatics-public.digikala.com/digikala-adservice-banners/88d1043787fbd90a394ee0b41f65b3a80279f8f5_1656704493.jpg?x-oss-process=image/quality,q_95" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(2,"https://dkstatics-public.digikala.com/digikala-adservice-banners/e280286fe6a9d0233b36a2eabd101c50992fae4d_1658336306.jpg?x-oss-process=image/quality,q_95" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(3,"https://dkstatics-public.digikala.com/digikala-adservice-banners/131a7d53b027360d66c80903357ba14432c5ea1e_1656677786.jpg?x-oss-process=image/quality,q_95" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));

//handle slider click listener
        binding.picture.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //do what you want
            }
        });

//add slides to slider
        binding.picture.addSlides(slideList);



        LinearLayoutManager vertiLayoutManager = new LinearLayoutManager(context,
                RecyclerView.VERTICAL,false);
        binding.rec.setLayoutManager(vertiLayoutManager);
        adapter = new ProductsAdapter(product);
        binding.rec.setAdapter(adapter);

        LinearLayoutManager horizLayoutManager = new LinearLayoutManager(context,
                RecyclerView.HORIZONTAL,false);
        binding.recH.setLayoutManager(horizLayoutManager);
        adapterP = new PupulerAdapter(product);
        binding.recH.setAdapter(adapterP);

        binding.buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lable = "پوشاک زنانه";
                id = 79;
                Intent intent = new Intent(context, ProductsCat.class);
                intent.putExtra("id" , id);
                intent.putExtra("label", lable);
                context.startActivity(intent);
            }
        });

        binding.buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lable = "پیراهن مجلسی";
                id = 80;
                Intent intent = new Intent(context, ProductsCat.class);
                intent.putExtra("id" , id);
                intent.putExtra("label", lable);
                context.startActivity(intent);
            }
        });

        binding.buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Cart.class);
                startActivity(intent);
            }
        });

        binding.buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Saves.class);
                startActivity(intent);
            }
        });


        new Refresh().execute();

        return binding.getRoot();
    }

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
                try {
                    JSONArray res =new JSONArray(String.valueOf(o));
                    for (int i = 0; i < res.length(); i++) {
                        JSONObject data =res.getJSONObject(i);
                        JSONArray imagearray = data.getJSONArray("images");
                        JSONObject imageObject = imagearray.getJSONObject(0);
                        Products products = new Products();
                        products.name = data.getString("name");
                        products.price = data.getString("price" );
                        products.img1 = imageObject.getString("src");
                        products.description = android.text.Html.fromHtml(data.getString("short_description")).toString();
                        products.id = data.getInt("id");
                        product.add(products);
                        binding.textDotLoaderH.setVisibility(View.GONE);
                        binding.textDotLoader.setVisibility(View.GONE);
                    }

                    adapter.notifyDataSetChanged();
                    adapterP.notifyDataSetChanged();

                } catch (JSONException e) {



                }
            }

        }

    }

}