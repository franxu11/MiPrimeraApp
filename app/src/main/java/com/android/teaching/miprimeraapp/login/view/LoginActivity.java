package com.android.teaching.miprimeraapp.login.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.android.teaching.miprimeraapp.R;

public class LoginActivity extends Activity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
    }

    public void onLogin(View view) {
        // Get values
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(username)) {
            // Show username empty error
            usernameEditText.setError("Usuario no válido");
        } else if (TextUtils.isEmpty(password)) {
            // Show password empty error
            passwordEditText.setError("Contraseña no válida");
        } else {
            // Do login
        }
    }
}
