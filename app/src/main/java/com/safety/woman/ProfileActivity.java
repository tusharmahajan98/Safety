package com.safety.woman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        UserPreference userPreference = new UserPreference(getApplicationContext());

        TextView fullName = findViewById(R.id.full_name);
        TextView email = findViewById(R.id.email);
        TextView phone_number = findViewById(R.id.phone_number);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            email.setText(currentUser.getEmail());
        }
        String fullNameValue = userPreference.read(UserPreference.FULL_NAME, "");
        String mobileValue = userPreference.read(UserPreference.MOBILE, "");
        if (!fullNameValue.equals("")) {
            fullName.setText(fullNameValue);
        }
        if (!mobileValue.equals("")) {
            phone_number.setText(mobileValue);
        }
    }
}