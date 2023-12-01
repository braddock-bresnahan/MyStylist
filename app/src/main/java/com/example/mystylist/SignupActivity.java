package com.example.mystylist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mystylist.structures.Account;
import com.example.mystylist.structures.Profile;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity {
    EditText signupName, signupUsername, signupEmail, signupPassword;
    Button signupButton, loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        loginButton = findViewById(R.id.login_button);
        signupButton = findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername()) {
                    Snackbar snackbar = Snackbar.make(view, "Username is invalid.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else if (!validatePassword()) {
                    Snackbar snackbar = Snackbar.make(view, "Password is invalid.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else if (!validateEmail()) {
                    Snackbar snackbar = Snackbar.make(view, "Email is invalid.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else if (!validateName()) {
                    Snackbar snackbar = Snackbar.make(view, "Name is invalid.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    String name = signupName.getText().toString();
                    String email = signupEmail.getText().toString();
                    String username = signupUsername.getText().toString();
                    String password = signupPassword.getText().toString();
                    Database.addAccount(new Account(username, password, email, name));
                    Database.addProfile(username, new Profile(name));
                    finish();
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public Boolean validateUsername() {
        String val = signupUsername.getText().toString();
        if (val.isEmpty()) {
            signupUsername.setError("Username cannot be empty");
            return false;
        } else {
            signupUsername.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = signupPassword.getText().toString();
        if (val.isEmpty()) {
            signupPassword.setError("Password cannot be empty");
            return false;
        } else {
            signupPassword.setError(null);
            return true;
        }
    }

    public Boolean validateName() {
        String val = signupName.getText().toString();
        if (val.isEmpty()) {
            signupName.setError("Name cannot be empty");
            return false;
        } else {
            signupName.setError(null);
            return true;
        }
    }
    public Boolean validateEmail(){
        String val = signupEmail.getText().toString();
        if (val.isEmpty()) {
            signupEmail.setError("Email cannot be empty");
            return false;
        } else {
            signupEmail.setError(null);
            return true;
        }
    }
}
