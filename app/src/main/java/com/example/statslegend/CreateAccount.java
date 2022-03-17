package com.example.statslegend;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class CreateAccount extends AppCompatActivity {

    EditText mEmail, mPassword, mTFTName;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mEmail = findViewById(R.id.create_account_email);
        mPassword = findViewById(R.id.create_account_password);
        mTFTName = findViewById(R.id.create_account_tft);
        mRegisterBtn = findViewById(R.id.createAccountBtn);
        mLoginBtn = findViewById(R.id.create_account_login);
        fAuth = FirebaseAuth.getInstance();

    }
}
