package com.example.store.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.store.adapter.ProductsAdapter;
import com.example.store.adapter.PupulerAdapter;
import com.example.store.databinding.FragmentHomeBinding;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    Context context;
    private FragmentHomeBinding binding;
    PupulerAdapter adapterP;

    ArrayList<Products> product = new ArrayList<>();
    ProductsAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }






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
        slideList.add(new Slide(0,"https://znoonstyle.com/wp-content/uploads/2021/09/Screenshot-20.png" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(1,"http://cssslider.com/sliders/demo-12/data1/images/picjumbo.com_hnck1995.jpg" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(2,"http://cssslider.com/sliders/demo-19/data1/images/picjumbo.com_hnck1588.jpg" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(3,"http://wowslider.com/sliders/demo-18/data1/images/shanghai.jpg" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));

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



/*        binding.swp.setColorSchemeResources(R.color.main);
        binding.swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swp.setRefreshing(false);
                new Refresh().execute();


            }
        });*/




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
                        products.price = data.getString("price" )+" تومان ";
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