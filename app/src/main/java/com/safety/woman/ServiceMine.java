package com.safety.woman;

import static android.app.PendingIntent.FLAG_IMMUTABLE;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.IBinder;
import android.telephony.SmsManager;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.github.tbouron.shakedetector.library.ShakeDetector;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class ServiceMine extends Service {

    boolean isRunning = false;

    FusedLocationProviderClient fusedLocationClient;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    SmsManager manager = SmsManager.getDefault();
    String myLocation;

    @Override
    public void onCreate() {
        super.onCreate();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        // Logic to handle location object
                        location.getAltitude();
                        location.getLongitude();
                        myLocation = "http://maps.google.com/maps?q=loc:" + location.getLatitude() + "," + location.getLongitude();
                    } else {
                        myLocation = "Unable to Find Location :(";
                    }
                });


        ShakeDetector.create(this, () -> {
            DatabaseHelper myDB = new DatabaseHelper(this);
            Cursor data = myDB.getListContents();
            if (data.getCount() != 0) {
                while (data.moveToNext()) {
                    manager.sendTextMessage(data.getString(1), null, "Im in Trouble!\nSending My Location :\n" + myLocation, null, null);
                }
            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equalsIgnoreCase("STOP")) {
            if (isRunning) {
                this.stopForeground(true);
                this.stopSelf();
            }

        } else {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, FLAG_IMMUTABLE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("MYID", "CHANNELFOREGROUND", NotificationManager.IMPORTANCE_DEFAULT);

                NotificationManager m = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                m.createNotificationChannel(channel);

                Notification notification = new Notification.Builder(this, "MYID")
                        .setContentTitle("Safety")
                        .setContentText("Shake Device to Send SOS")
                        .setSmallIcon(R.drawable.girl_vector)
                        .setContentIntent(pendingIntent)
                        .build();
                this.startForeground(115, notification);
                isRunning = true;
                return START_NOT_STICKY;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
