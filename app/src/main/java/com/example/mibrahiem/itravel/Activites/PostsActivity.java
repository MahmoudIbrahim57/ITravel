package com.example.mibrahiem.itravel.Activites;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mibrahiem.itravel.R;
import com.example.mibrahiem.itravel.adapters.Model;
import com.example.mibrahiem.itravel.adapters.ViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class PostsActivity extends AppCompatActivity {
    RecyclerView postsList;
     FloatingActionButton addPost;
    private String countryPosts,title;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    EditText etContent;
     FirebaseAuth auth;
     FirebaseDatabase asd;
     DatabaseReference asd2, databaseReferenc2, databaseReferenc;
    String name,key,content;
    Dialog d;
TextView tvCountry;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        initializeComponent();
        getPosts();
         progressBar.setProgress(10);
         postsList.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 getPosts();
             }
         });
         addPost.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        displayInputDialog();


    }
});

    }

    @Override
    protected void onResume() {
        super.onResume();
        getPosts();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getPosts();

    }

    private void getPosts(){

    FirebaseRecyclerAdapter<Model,ViewHolder> firebaseRecyclerAdapter=
            new FirebaseRecyclerAdapter<Model,ViewHolder>(

                    Model.class,

                    R.layout.post_layout,

                    ViewHolder.class,

                    asd2



            )
            {
                @Override
                protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {
                    viewHolder.setDetails(getApplicationContext() , model.getName() , model.getContent());
                    progressBar.setVisibility(View.GONE);
                }
            };
    postsList.setAdapter(firebaseRecyclerAdapter);

};
    private void displayInputDialog()
    {
        d=new Dialog(this);
        d.setTitle("  خبراتك فى سطور ");
        d.setContentView(R.layout.activity_creat_post);

        etContent= (EditText) d.findViewById(R.id.et_postContent);
        Button saveBtn= (Button) d.findViewById(R.id.bt_createPost);

         saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 content  =etContent.getText().toString();
                nameOfUser();


            }
        });

        d.show();
    }

            private void nameOfUser() {
        auth= FirebaseAuth.getInstance();
        databaseReferenc  = FirebaseDatabase.getInstance().getReference().getRoot();

        String userId=auth.getCurrentUser().getUid();

        DatabaseReference CurrentUserName =databaseReferenc.child("users").child(userId).child("name");
        CurrentUserName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                name=snapshot.getValue().toString();

                addPost();


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void addPost() {

        progressDialog.setMessage(" Posting");
        progressDialog.show();


        databaseReferenc2  = FirebaseDatabase.getInstance().getReference().child(countryPosts);
        DatabaseReference databaseReference2 = databaseReferenc2.child("posts");
        Map<String,Object> map = new HashMap<String,Object>();
        key = databaseReference2.push().getKey();
        databaseReference2.updateChildren(map);

        DatabaseReference databaseReference1 = databaseReference2.child(key);
        Map<String, Object> map11 = new HashMap<String, Object>();


        map11.put("content", content);

        map11.put("name", name);

        databaseReference1.updateChildren(map11);
        Toast.makeText(this, "تم النشر", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        d.dismiss();


    }


    private void initializeComponent() {
        progressBar=findViewById(R.id.pb_posts);
        addPost= findViewById(R.id.add_post_btn);
        progressDialog=new ProgressDialog(this);
        postsList = findViewById(R.id.posts_list);
        postsList.setLayoutManager(new LinearLayoutManager(this));
        postsList.setHasFixedSize(true);
        tvCountry=findViewById(R.id.tv_TitleOfCountryName);
        countryPosts =   getIntent().getStringExtra("countryPosts");
        title =   getIntent().getStringExtra("title");
        tvCountry.setText(title);
        asd2=FirebaseDatabase.getInstance().getReference().getRoot().child(countryPosts).child("posts");

    }
}
