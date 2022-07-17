package com.example.store.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.store.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView t = findViewById(R.id.text);

        //animation
        Animation animation = AnimationUtils
                .loadAnimation(getApplicationContext(), R.anim.fade_in);
        t.startAnimation(animation);

        //progressTime
        Handler h = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Home.class);
                startActivity(intent);
                finish();

            }
        };
        h.postDelayed(r, 3000);
    }


}