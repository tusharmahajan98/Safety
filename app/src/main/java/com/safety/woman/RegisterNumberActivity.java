package com.safety.woman;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterNumberActivity extends AppCompatActivity {

    EditText mName, mPhone;
    Button mAddBtn;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_number);
        mName = findViewById(R.id.name);
        mPhone = findViewById(R.id.phone);
        mAddBtn = findViewById(R.id.addBtn);
        myDB = new DatabaseHelper(this);

        mAddBtn.setOnClickListener(v -> {
            String name = mName.getText().toString().trim();
            String phone = mPhone.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                mPhone.setError("Phone Number is Required");
                return;
            }
            addData(name);
            addData(phone);
        });

    }

    public void addData(String newEntry) {
        boolean insertData = myDB.addData(newEntry);
        if (insertData == true) {
            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
    }

}