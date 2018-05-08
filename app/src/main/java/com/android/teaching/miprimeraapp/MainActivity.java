package com.android.teaching.miprimeraapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "onCreate");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.segunda_activity:
                Intent intent = new Intent(this, ProfileActivity.class);
                intent.putExtra("dinero", 200);
                startActivity(intent);
                break;
            case R.id.abrir_google:
                Intent intentGoogle = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(intentGoogle);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy");
    }
}