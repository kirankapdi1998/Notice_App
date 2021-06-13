package com.example.noticeapp.ui;

import androidx.annotation.NonNull;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noticeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
//            loginProgressBar.dismiss();
            if (mAuth.getCurrentUser().getEmail().equals("admin_support@dashboard.com")){
                startActivity(new Intent(LoginActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }else {
                startActivity(new Intent(LoginActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }finish();
        }

        final EditText usernameEditText = findViewById(R.id.email);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = findViewById(R.id.register);
//        final ProgressDialog loadingProgressBar = findViewById(R.id.loading);
        loginButton.setEnabled(true);
        loginButton.setClickable(true);
        loginButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               ProgressDialog loginProgressBar = new ProgressDialog(v.getContext());
                                               loginProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                               loginProgressBar.setTitle("Log-IN Process");
                                               loginProgressBar.setMessage("In Progress");
                                               loginProgressBar.show();

                                               final String email = usernameEditText.getText().toString();
                                               final String pass = passwordEditText.getText().toString();
                                               mAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(LoginActivity.this, new OnSuccessListener<AuthResult>() {
                                                   @Override
                                                   public void onSuccess(AuthResult authResult) {

//                                                       Toast.makeText(LoginActivity.this, "WELCOME", Toast.LENGTH_LONG);
                                                       Log.v("Dash", "User Logged In");
                                                   }
                                               }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                                                   @Override
                                                   public void onFailure(@NonNull Exception e) {
                                                       Log.v("Dash", e.getMessage());
                                                       loginProgressBar.dismiss();
                                                   }
                                               }).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<AuthResult> task) {

                                                       if (task.isSuccessful()) {
                                                           loginProgressBar.dismiss();
                                                           if (task.getResult().getUser().getEmail().equals("admin_support@dashboard.com")){
                                                               startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                           }else{
                                                               startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                           }
                                                           Toast.makeText(LoginActivity.this, "sign-in success..", Toast.LENGTH_SHORT).show();
                                                       } else {
                                                           Toast.makeText(LoginActivity.this, "sign-in failed..", Toast.LENGTH_SHORT).show();
                                                           loginProgressBar.dismiss();
                                                       }
                                                   }
                                               });
                                           }
                                       }
        );

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent route = new Intent(v.getContext(), Registration.class);
                startActivity(route);

            }
        });
    }


}
