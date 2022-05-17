package com.example.registerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView textView7, button;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText editTextName, editTextPhone, editTextEmail, editTextPassword, editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        textView7 = (TextView) findViewById(R.id.textView7);
        textView7.setOnClickListener(this);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        editTextName = (EditText) findViewById(R.id.editTextTextPersonName7);
        editTextPhone = (EditText) findViewById(R.id.editTextTextPersonName9);
        editTextEmail = (EditText) findViewById(R.id.editTextTextPersonName10);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPersonName11);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextTextPersonName12);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
    }

    @Override
    public void onClick(View view) {
    switch(view.getId()){
        case R.id.textView7:
        startActivity(new Intent(MainActivity.this, Login.class));
        break;
        case R.id.button:
            button();
            break;
    }
    }

    private void button() {
        String editTextTextPersonName7 = editTextName.getText().toString().trim();
        String editTextTextPersonName9 = editTextPhone.getText().toString().trim();
        String editTextTextPersonName10 = editTextEmail.getText().toString().trim();
        String editTextTextPersonName11 = editTextPassword.getText().toString().trim();
        String editTextTextPersonName12 = editTextConfirmPassword.getText().toString().trim();

        if(editTextTextPersonName11.isEmpty())
        {
            editTextPassword.setError("Please enter the password");
            editTextPassword.requestFocus();
            return;
        }
        if(editTextTextPersonName12.isEmpty())
        {
            editTextConfirmPassword.setError("Please confirm the password");
            editTextConfirmPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(editTextTextPersonName10, editTextTextPersonName11)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user = new User(editTextTextPersonName7, editTextTextPersonName9, editTextTextPersonName10);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(MainActivity.this, "User has been registered Successfully", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }else
                                    Toast.makeText(MainActivity.this, "Failed to register! Please try after sometime!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                            }
                        });

                    }else{
                        Toast.makeText(MainActivity.this, "Failed to register!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                    }
                });
    }
}




