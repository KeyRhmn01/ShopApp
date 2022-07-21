package com.example.store.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.store.R;
import com.example.store.activity.Login;
import com.example.store.adapter.CategoriesAdapter;
import com.example.store.adapter.ProductsAdapter;
import com.example.store.databinding.FragmentCatalogBinding;
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



public class CatalogFragment extends Fragment {


    private FragmentCatalogBinding binding;

    ArrayList<Categories> categories = new ArrayList<>();
    CategoriesAdapter adapter;
    Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                    .url("https://znoonstyle.com/wp-json/public-woo/v1/products/categories")
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
                    JSONArray res = new JSONArray(String.valueOf(o));
                    for (int i = 0; i < res.length(); i++) {
                        JSONObject data = res.getJSONObject(i);
                        Categories cat = new Categories();
                        cat.id = data.getInt("id");
                        cat.label = data.getString("name");
                        categories.add(cat);

                        binding.textDotLoader.setVisibility(View.GONE);

                    }


                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {


                }
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = container.getContext();
        binding = FragmentCatalogBinding.inflate(inflater, container, false);

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(context,
                RecyclerView.VERTICAL,false);
        binding.recycler.setLayoutManager(verticalLayoutManager);
        adapter = new CategoriesAdapter(categories);
        binding.recycler.setAdapter(adapter);



        new Refresh().execute();

        return binding.getRoot();
    }

}