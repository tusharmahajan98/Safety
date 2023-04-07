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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    EditText mFullName, mEmail, mPassword, mPhone;
    Button mRegisterbtn;
    TextView mCreateText;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phone);
        mRegisterbtn = findViewById(R.id.registerbtn);
        mCreateText = findViewById(R.id.createText);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        UserPreference userPreference = new UserPreference(getApplicationContext());

        mRegisterbtn.setOnClickListener(v -> {
            String fullName = mFullName.getText().toString().trim();
            String email = mEmail.getText().toString().trim();
            String phone = mPhone.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is Required.");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is Required");
                return;

            }
            if (password.length() < 6) {
                mPassword.setError("Password Must be >= 6 Characters");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    userPreference.write(UserPreference.FULL_NAME, fullName);
                    userPreference.write(UserPreference.MOBILE, phone);
                    Toast.makeText(RegistrationActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                } else {
                    Toast.makeText(RegistrationActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        });
        mCreateText.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
    }
}

