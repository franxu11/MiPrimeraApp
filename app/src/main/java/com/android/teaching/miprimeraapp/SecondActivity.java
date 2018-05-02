package com.android.teaching.miprimeraapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String paco = intent.getStringExtra("paco"); // "VALOR DE PACO"
        int dinero = intent.getIntExtra("dinero", 0); // 200
        Log.d("SecondActivity", "El extra 'dinero' vale: " + dinero);
    }











}
