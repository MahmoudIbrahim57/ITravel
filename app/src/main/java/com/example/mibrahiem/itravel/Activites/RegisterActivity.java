package com.example.mibrahiem.itravel.Activites;

import android.app.ProgressDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mibrahiem.itravel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;

public class RegisterActivity extends AppCompatActivity {
Button register;
 EditText  etName , etEmail,  etpassword;
String name,phone,password,email;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    StorageReference storageReference;
private final int REQUEST_CODE=1;
Uri image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeComponent();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValues();
                register();
            }
        });

    }

    private void getValues() {
       email= etEmail.getText().toString();
       name=  etName.getText().toString();
       password= etpassword.getText().toString();


    }


    private void initializeComponent() {
        progressDialog=new ProgressDialog(this);
        databaseReference  = FirebaseDatabase.getInstance().getReference().getRoot();
        storageReference= FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        register=findViewById(R.id.bt_reg);
          etName =findViewById(R.id.et_name);
        etEmail=findViewById(R.id.et_loginMail);
        etpassword=findViewById(R.id.et_loginpass);
     }
    private void register() {
        progressDialog.setMessage("registering user");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userId=mAuth.getCurrentUser().getUid();

                            DatabaseReference CurrentUserId =databaseReference.child("users").child(userId);

                            CurrentUserId.child("name").setValue(name);
                            CurrentUserId.child("email").setValue(email);
                            CurrentUserId.child("password").setValue(password);
                            CurrentUserId.child("phone").setValue(phone);
                            Intent intent=new Intent(RegisterActivity.this,HomeActivity.class);
                            startActivity(intent);

                            finish();
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(RegisterActivity.this, " failed."+e.getMessage(),Toast.LENGTH_SHORT).show();
                            Log.i("aaa",e.getMessage());
                        }

                    }
                });
    }
    @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if(requestCode==REQUEST_CODE&&resultCode==RESULT_OK)
                {

                    image = data.getData();

                    Log.i("uri",image.toString());

                }
    }

}
