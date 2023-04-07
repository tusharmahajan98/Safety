package com.safety.woman;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final ActivityResultLauncher<String[]> multiplePermissions = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<>() {
        @Override
        public void onActivityResult(Map<String, Boolean> result) {
            for (Map.Entry<String, Boolean> entry : result.entrySet())
                if (!entry.getValue()) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Permission Must Be Granted!", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Grant Permission", v -> {
                        multiplePermissions.launch(new String[]{entry.getKey()});
                        snackbar.dismiss();
                    });
                    snackbar.show();
                }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialCardView addNumber = findViewById(R.id.card_add_number);
        MaterialCardView contacts = findViewById(R.id.card_contacts);
        MaterialCardView profile = findViewById(R.id.card_profile);
        MaterialCardView nearbyPolice = findViewById(R.id.card_nearby_police);
        MaterialCardView logout = findViewById(R.id.card_log_out);

        addNumber.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RegisterNumberActivity.class));
        });
        contacts.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ContactsActivity.class));
        });
        profile.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        });
        nearbyPolice.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, NearbyPoliceWebActivity.class));
        });
        logout.setOnClickListener(v -> {
            stopService();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, SplashActivity.class));
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MYID", "CHANNELFOREGROUND", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager m = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            m.createNotificationChannel(channel);
        }

        startServiceV();
    }


    public void stopService() {
        Intent notificationIntent = new Intent(this, ServiceMine.class);
        notificationIntent.setAction("stop");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getApplicationContext().startForegroundService(notificationIntent);
            Snackbar.make(findViewById(android.R.id.content), "Service Stopped!", Snackbar.LENGTH_LONG).show();
        }
    }

    public void startServiceV() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Intent notificationIntent = new Intent(this, ServiceMine.class);
            notificationIntent.setAction("Start");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                getApplicationContext().startForegroundService(notificationIntent);
                Snackbar.make(findViewById(android.R.id.content), "Service Started!", Snackbar.LENGTH_LONG).show();
            }
        } else {
            multiplePermissions.launch(new String[]{Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION});
        }
    }

}