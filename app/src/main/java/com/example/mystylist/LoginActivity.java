package com.example.mystylist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mystylist.structures.Account;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.function.Function;

public class LoginActivity extends AppCompatActivity {
    public static Account activeAccount;
    TextView forgotPassword;

    EditText loginUsername, loginPassword;
    Button loginButton, signupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activeAccount = null;
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupButton = findViewById(R.id.signup_button);
        forgotPassword = findViewById(R.id.forgotPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername()) {
                    Snackbar snackbar = Snackbar.make(view, "Username is invalid.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                else if (!validatePassword()) {
                    Snackbar snackbar = Snackbar.make(view, "Password is invalid.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                else {
                    loadAccount();
                }
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Unused
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        activeAccount = null;
    }

    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }
    public void loadAccount(){
        String username = loginUsername.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        Database.getAccount(username, password, new Function<Account, Void>() {
            @Override
            public Void apply(Account account) {

                if (account != null) {
                    activeAccount = account;
                    Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
                    startActivity(intent);
                }
                return null;
            }
        });
    }
}