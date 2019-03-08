package com.example.mibrahiem.itravel.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mibrahiem.itravel.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    Button egypt,emarat,germany,canda, signOut;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        egypt= findViewById(R.id.bt_egy);
        emarat=findViewById(R.id.bt_emara);
        germany=findViewById(R.id.bt_ger);
        canda=findViewById(R.id.bt_canda);

        signOut=findViewById(R.id.bt_signOut);
        auth= FirebaseAuth.getInstance();
         signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        egypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,CountryActivity.class);
                intent.putExtra("country","egypt");
                intent.putExtra("title","EGYPT");
                Toast.makeText(HomeActivity.this, "egypt", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
        emarat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,CountryActivity.class);
                intent.putExtra("country","emarat");
                intent.putExtra("title","UAE");
                startActivity(intent);
            }
        });
        canda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,CountryActivity.class);
                intent.putExtra("country","canada");
                intent.putExtra("title","CANADA");

                startActivity(intent);
            }
        });
        germany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,CountryActivity.class);
                intent.putExtra("country","germany");
                intent.putExtra("title","GERMANY");
                startActivity(intent);
            }
        });

    }
}
