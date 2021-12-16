package com.example.estoriassemhapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.estoriassemhapp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btLogin = findViewById(R.id.btLogin);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etUsername = findViewById(R.id.etUsername);
                String username = etUsername.getText().toString();

                EditText etPassword = findViewById(R.id.etPassword);
                String password = etPassword.getText().toString();

                if (username.equals("Lelio") && password.equals("1234")) {
                    Log.i("teste", "sadasdasd");
                    login();
                }
            }
        });

        Button btnCreateUser = findViewById(R.id.btnCreateUser);

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    void login() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }

}
