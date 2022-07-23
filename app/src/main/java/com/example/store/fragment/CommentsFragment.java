package com.example.store.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Fade;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.store.activity.Details;
import com.example.store.adapter.CommentsAdapter;
import com.example.store.databinding.FragmentCommentsBinding;
import com.example.store.model.Coment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CommentsFragment extends Fragment {

    Context context;
    private FragmentCommentsBinding binding;
    int id;
    ArrayList<Coment> coments = new ArrayList<>();
    CommentsAdapter adapter;



    public static CommentsFragment newInstance(String param1, String param2) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {






        // Inflate the layout for this fragment
        this.context = container.getContext();
        binding = FragmentCommentsBinding.inflate(inflater, container, false);

        LinearLayoutManager vertiLayoutManager = new LinearLayoutManager(context,
                RecyclerView.VERTICAL,false);
        binding.comments.setLayoutManager(vertiLayoutManager);
        adapter = new CommentsAdapter(coments);
        binding.comments.setAdapter(adapter);

        binding.backGround.setOnClickListener(view -> {
            ((Details)getActivity()).onBackPressed();
        });

        final  Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // start animation
                toggleFade(binding.backGround);
                toggle(binding.parentt);
            }
        }, 10);




        return binding.getRoot();
    }

    private void toggle(View view) {
        Transition transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(300);
        transition.addTarget(view);

        TransitionManager.beginDelayedTransition((ViewGroup) view, transition);
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void toggleFade(View view) {
        Transition transition = new Fade();
        transition.setDuration(300);
        transition.addTarget(view);

        TransitionManager.beginDelayedTransition((ViewGroup) view, transition);
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void closeAction() {
        toggle(binding.parentt);
        toggleFade(binding.backGround);
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
                    .url("https://znoonstyle.com/wp-json/public-woo/v1/products/"+coments.get(id))
                    .header("consumer_key", "ck_8ac626d90b818c8be49d0170f296a1c866eed7e9")
                    .header("consumer_secret", "cs_3738bad272c9c29a212c503867ef0cb89f66d255")
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
                        Coment coment = new Coment();
                        coment.nameC = data.getString("reviewer");
                        coment.gmail = data.getString("reviewer_email");
                        coment.desc = data.getString("review");
                        coment.idC = data.getInt("id");
                        coments.add(coment);
                    }

                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {



                }
            }

        }

    }

}