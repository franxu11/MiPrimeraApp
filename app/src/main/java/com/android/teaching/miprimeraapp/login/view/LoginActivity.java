package com.android.teaching.miprimeraapp.login.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.android.teaching.miprimeraapp.ProfileActivity;
import com.android.teaching.miprimeraapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.user_preferences),
                Context.MODE_PRIVATE
        );
        String savedUsername = sharedPref.getString("username_key", "");
        usernameEditText.setText(savedUsername);
    }

    /**
     * Método que se ejecuta cuando el usuario pulsa en "Login"
     *
     * @param view  -
     */
    public void onLogin(View view) {

        // Obtener valores
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Check empty values
        if (TextUtils.isEmpty(username)) {
            // El campo "username" está vacío
            usernameEditText.setError(getString(R.string.username_error));
        } else if (TextUtils.isEmpty(password)) {
            passwordEditText.setError(getString(R.string.password_error));
        } else {
            /**
             * Creo un SharedPreferences para que guarde el nombre de usuario
             */
            SharedPreferences sharedPref = getSharedPreferences(
                    getString(R.string.user_preferences),
                    Context.MODE_PRIVATE
            );
            SharedPreferences.Editor myEditor = sharedPref.edit();
            myEditor.putString("username_key", username);
            myEditor.apply();

            // Do login
            Intent profileIntent = new Intent(this, ProfileActivity.class);
            startActivity(profileIntent);
        }
    }

    /**
     * Método que se ejecuta cuando el usuario pulsa en "Register"
     *
     * @param view  -
     */
    public void doRegister(View view) {
        Intent registerIntent = new Intent(this, ProfileActivity.class);
        startActivity(registerIntent);
    }

    /**
     * Método que se ejecuta cuando el usuario pulsa en "Cancel"
     *
     * @param view  -
     */
    public void onCancel(View view) {
        usernameEditText.setText("");
        passwordEditText.setText("");
    }
}
