package com.example.statslegend;

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
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in

        } else {
            // No user is signed in

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);
        mLoginBtn = findViewById(R.id.log_in);
        mCreateBtn = findViewById(R.id.login_create_account);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.login_progressBar);

        mLoginBtn.setOnClickListener(view -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            //Display errors when email or password are empty, or password is too short
            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is Required.");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is Required.");
                return;
            }
            if (password.length() < 4) {
                mPassword.setError("Password Must Be At Least 4 Characters.");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            //authenticate the user
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                //Check if login is successful or not
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(Login.this, "Error!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        });

        mCreateBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), CreateAccount.class)));
    }
}
