package com.safety.woman;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    FirebaseAuth fAuth;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mLoginBtn =  findViewById(R.id.loginBtn);
        mCreateBtn = findViewById(R.id.createText);
        fAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressBar);

        mLoginBtn.setOnClickListener(v -> {
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is Required.");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is Required");
                return;
            }
            if(password.length() < 6) {
                mPassword.setError("Password Must be >= 6 Characters");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    } else {
                        Toast.makeText(LoginActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        });
        mCreateBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegistrationActivity.class)));
    }
}

