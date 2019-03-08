package com.example.mibrahiem.itravel.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mibrahiem.itravel.Map.MapsActivity;
import com.example.mibrahiem.itravel.R;

public class CountryActivity extends AppCompatActivity {
Button posts,chat,places;
 String country, title;
    TextView tvCountry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        posts=findViewById(R.id.bt_posts);
        chat=findViewById(R.id.bt_chat);
        places=findViewById(R.id.bt_places);
        tvCountry=findViewById(R.id.tv_TitleOfCountryName);
         country =   getIntent().getStringExtra("country");
         title =   getIntent().getStringExtra("title");
        tvCountry.setText(title);
        places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CountryActivity.this,MapsActivity.class);
                intent.putExtra("countryPosts",country);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CountryActivity.this,PostsActivity.class);
                intent.putExtra("countryPosts",country);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CountryActivity.this,MessageActivity.class);
                intent.putExtra("countrychat",country);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
    }
}
