package com.safety.woman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new CountDownTimer(1000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, RegistrationActivity.class));
                }
                finish();
            }
        }.start();

    }
}