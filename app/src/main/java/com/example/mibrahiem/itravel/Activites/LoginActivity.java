package com.example.mibrahiem.itravel.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mibrahiem.itravel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText etEmail,etPassword;
    private String email,password;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeComponent();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth= FirebaseAuth.getInstance();
                if(mAuth.getCurrentUser()!=null){
                    finish();
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }
                getValues();
             userLogin();

            }
        });
    }
    private void userLogin() {
        progressDialog.setMessage("sign in");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        }
                        else
                            Toast.makeText(LoginActivity.this, "Eror", Toast.LENGTH_SHORT).show();                    }
                });
    }

    private void getValues() {
        email= etEmail.getText().toString();
         password= etPassword.getText().toString();


    }

    private void initializeComponent() {
        progressDialog=new ProgressDialog(this);
        databaseReference  = FirebaseDatabase.getInstance().getReference().getRoot();
        mAuth = FirebaseAuth.getInstance();
        login=findViewById(R.id.bt_login);
        etEmail=findViewById(R.id.et_logMail);
        etPassword=findViewById(R.id.et_logpass);
    }
}
