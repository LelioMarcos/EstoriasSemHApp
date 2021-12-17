package com.example.estoriassemhapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.estoriassemhapp.R;
import com.example.estoriassemhapp.util.Config;

public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Config.getLogin(CheckActivity.this).isEmpty()) {
            Intent i = new Intent(CheckActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        else {
            Intent i = new Intent(CheckActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}