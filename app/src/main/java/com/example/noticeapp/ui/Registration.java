package com.example.noticeapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.noticeapp.R;
import com.example.noticeapp.model.Student;
import com.example.noticeapp.model.Validate;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    private FirebaseAuth mAuth;


    private EditText name;
    private EditText dept;
    private Spinner year;
    private EditText email;
    private EditText roll;
    private EditText passwd;
    private EditText cpasswd;
    private Button register;

    private TextInputLayout txt_name;
    private TextInputLayout txt_dept;
    private TextInputLayout txt_email;
    private TextInputLayout txt_roll;
    private TextInputLayout txt_passwd;
    private TextInputLayout txt_cpasswd;

    Validate v = new Validate();

    String val_name, val_email, val_roll, val_dept, val_year, val_passwd, val_cpasswd;
    private ProgressDialog pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Toolbar toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("Registration");

        register = findViewById(R.id.registration);
        name = findViewById(R.id.name);
        dept = findViewById(R.id.add_dept);
        email = findViewById(R.id.email);
        roll = findViewById(R.id.roll);
        year = findViewById(R.id.year);
        passwd = findViewById(R.id.password);
        cpasswd = findViewById(R.id.cpasswd);
        // Initialize Firebase Auth


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "clicked on register button", Toast.LENGTH_LONG).show();
                Log.i("info", "clicked on register button");

                if(v.isValidEmail(email) && v.isValidPassword(passwd)) {
                    pg = new ProgressDialog(Registration.this);
                    pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pg.setTitle("Register Process");
                    pg.setMessage("In Progress");
                    pg.show();
                    register();
                }else {
                    Toast.makeText(Registration.this,"Credentials not valid..!",Toast.LENGTH_LONG).show();
                }

            }
        });

//        name.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (!charSequence.toString().equals("")) {
//                    if (v.isValidString(name, txt_name)) {
//                        txt_name.setError(null);
//                    }
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        email.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (v.isValidEmail(email, txt_email)) {
//                    txt_email.setError(null);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        dept.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (v.isValidString(dept, txt_dept)) {
//                    txt_dept.setError(null);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        roll.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (v.isValidNumber(roll, txt_roll)) {
//                    txt_roll.setError(null);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        passwd.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (v.isValidPassword(passwd, txt_passwd)) {
//                    txt_passwd.setError(null);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        cpasswd.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (v.isValidCPassword(cpasswd, passwd, txt_cpasswd)) {
//                    txt_cpasswd.setError(null);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
    }

    private void register() {


        val_name = name.getText().toString();
        val_email = email.getText().toString();
        val_dept = dept.getText().toString();
        val_roll = roll.getText().toString();
        val_passwd = passwd.getText().toString();
        val_cpasswd = cpasswd.getText().toString();
        val_year = year.getSelectedItem().toString();

        Student student=new Student(val_name, val_dept, val_year, val_roll, val_email);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(val_name.trim())){
                    pg.dismiss();

                    Toast.makeText(Registration.this,"User already exists!",Toast.LENGTH_LONG).show();

                }else{
//                    pg.dismiss();
                    signup(val_email,val_passwd, student);
//                    Toast.makeText(Registration.this,"Not a Valid Student Or Nam",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Registration.this,databaseError.toString(),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void signup(String val_email, String val_passwd, Student student) {

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(val_email, val_passwd).addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
//                Toast.makeText(Registration.this, "Code succeed..", Toast.LENGTH_SHORT).show();
                Log.v("dash","onSuccess Triggered!");
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(Registration.this, "On Failure Triggered..", Toast.LENGTH_SHORT).show();
                Log.v("dash","onFailure Triggered!");
            }
        }).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {
//                    Snackbar.make(, "   Sign-up success..   ",Snackbar.LENGTH_LONG).show();
                    //reset();
                    FirebaseUser user;
                    user = mAuth.getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(val_name)
                            .build();
                    user.updateProfile(profileUpdates);

                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users");
                    db.child(val_name).setValue(student);

                    pg.dismiss();
                    Toast.makeText(Registration.this, "User Account created..", Toast.LENGTH_SHORT).show();

                    Intent route = new Intent(Registration.this, LoginActivity.class);
                    startActivity(route.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();

                } else {
                    Toast.makeText(Registration.this, "Sign-up failed..", Toast.LENGTH_SHORT).show();

                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(Registration.this,"User Already Exists!!",Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(Registration.this, "Error..", Toast.LENGTH_SHORT).show();
                        Log.i("Error", task.getException().getMessage());

                    }
                    pg.dismiss();
                }
               reset();

            }
        }).addOnCanceledListener(this, new OnCanceledListener() {
            @Override
            public void onCanceled() {
//                Toast.makeText(Registration.this, "Execution cancelled..", Toast.LENGTH_SHORT).show();
                Log.v("dash","onCancelled Triggered!");
            }
        })
        ;
    }

    private void reset() {
        name.setText(null);
        email.setText(null);
        dept.setText(null);
        roll.setText(null);
        passwd.setText(null);
        cpasswd.setText(null);
    }
}

