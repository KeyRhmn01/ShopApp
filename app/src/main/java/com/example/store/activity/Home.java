package com.example.store.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.store.R;
import com.example.store.fragment.CatalogFragment;
import com.example.store.fragment.HomeFragment;
import com.example.store.fragment.ProfileFragment;

public class Home extends AppCompatActivity {
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        RelativeLayout home = findViewById(R.id.homee);
        RelativeLayout cat = findViewById(R.id.catalog);
        RelativeLayout prof = findViewById(R.id.account);
        FrameLayout frameLayout = findViewById(R.id.frame);
        ImageView house = findViewById(R.id.imageHome);
        ImageView ticket = findViewById(R.id.imageTicket);
        ImageView account = findViewById(R.id.imageProf);
        ImageView houseb = findViewById(R.id.imageHomeb);
        ImageView ticketb = findViewById(R.id.imageTicketb);
        ImageView accountb = findViewById(R.id.imageProfb);

        //mainFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, new HomeFragment()).commit();
        house.setVisibility(View.GONE);
        houseb.setVisibility(View.VISIBLE);

        //buttons
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                house.setVisibility(View.GONE);
                houseb.setVisibility(View.VISIBLE);
                ticketb.setVisibility(View.GONE);
                ticket.setVisibility(View.VISIBLE);
                accountb.setVisibility(View.GONE);
                account.setVisibility(View.VISIBLE);

                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, new HomeFragment());
                ft.commit();

                if(home.isActivated()){
                    house.getResources().getColor(R.color.main);
                }

            }
        });

        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                houseb.setVisibility(View.GONE);
                house.setVisibility(View.VISIBLE);
                ticket.setVisibility(View.GONE);
                ticketb.setVisibility(View.VISIBLE);
                account.setVisibility(View.VISIBLE);
                accountb.setVisibility(View.GONE);

                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, new CatalogFragment());
                ft.commit();

                if(cat.isActivated()){
                    ticket.getResources().getColor(R.color.main);
                }

            }
        });

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                houseb.setVisibility(View.GONE);
                house.setVisibility(View.VISIBLE);
                ticketb.setVisibility(View.GONE);
                ticket.setVisibility(View.VISIBLE);
                account.setVisibility(View.GONE);
                accountb.setVisibility(View.VISIBLE);

                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, new ProfileFragment());
                ft.commit();

                if(prof.isActivated()){
                    account.getResources().getColor(R.color.main);
                }

            }
        });


    }
}