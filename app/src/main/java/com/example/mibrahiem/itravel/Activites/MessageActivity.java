package com.example.mibrahiem.itravel.Activites;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mibrahiem.itravel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {
    private TextView messageOwner, tvTitle;
    private Button send;
    FirebaseAuth auth;
    DatabaseReference databaseReferenc;
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    EditText etmessage;
    String name, message, key, country,title;
    TextView tvCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        progressBar=findViewById(R.id.pb_messages);
        messageOwner = findViewById(R.id.tv_message);
         tvCountry=findViewById(R.id.tv_TitleOfCountryName);

        etmessage = findViewById(R.id.et_typing);
        send = findViewById(R.id.bt_go);
        country = getIntent().getStringExtra("countrychat");
        title = getIntent().getStringExtra("title");
        tvCountry.setText(title);
        progressBar.setProgress(10);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(country).child("chat");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                nameOfUser();


            }



            private void nameOfUser() {
                auth = FirebaseAuth.getInstance();
                databaseReferenc = FirebaseDatabase.getInstance().getReference().getRoot();

                String userId = auth.getCurrentUser().getUid();

                DatabaseReference CurrentUserName = databaseReferenc.child("users").child(userId).child("name");
                CurrentUserName.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        name = snapshot.getValue().toString();
                        Log.i("22222", name);

                        addMessage();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


            }

            private void addMessage() {

                message = etmessage.getText().toString();
                Map<String, Object> map = new HashMap<String, Object>();
                key = databaseReference.push().getKey();
                databaseReference.updateChildren(map);
                DatabaseReference databaseReference1 = databaseReference.child(key);
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("user ", name);
                map1.put("message", message);
                databaseReference1.updateChildren(map1);
                etmessage.setText("");

            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_text(dataSnapshot);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_text(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private void append_text(DataSnapshot dataSnapshot) {
        Iterator iterator = dataSnapshot.getChildren().iterator();
        while (iterator.hasNext()) {
            name = (String) ((DataSnapshot) iterator.next()).getValue();
            message = (String) ((DataSnapshot) iterator.next()).getValue();
            messageOwner.append("\n"+message + "\n"  +name+ "\n");

    }
}
}