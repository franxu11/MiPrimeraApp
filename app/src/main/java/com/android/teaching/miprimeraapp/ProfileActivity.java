package com.android.teaching.miprimeraapp;

import android.app.DatePickerDialog;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.sql.SQLClientInfoException;
import java.util.Calendar;

import databaseApp.AppDatabase;
import databaseApp.User;

public class ProfileActivity extends AppCompatActivity {

    // Views
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText ageEditText;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.user_preferences),
                Context.MODE_PRIVATE
        );
        String savedUsername = sharedPref.getString("username_key", "");
        String savedEmail = sharedPref.getString("email_key", "");
        //int savedAge = sharedPref.getInt("age_key", 0);
        String savedAge = sharedPref.getString("age_key", "");
        String savedMale = sharedPref.getString("male_key", "");
        String savedFemale = sharedPref.getString("female_key", "");

        usernameEditText.setText(savedUsername);
        emailEditText.setText(savedEmail);
        ageEditText.setText(savedAge);
        radioButtonMale.setChecked(false);
        radioButtonFemale.setChecked(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        ageEditText = findViewById(R.id.age_edit_text);
        ageEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // MOSTRAR DatePickerDialog
                    new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            // Escribir la fecha en el edit text
                            int anoActual = Calendar.getInstance().get(Calendar.YEAR);
                            int edad = anoActual - year;
                            ageEditText.setText(String.valueOf(edad));
                        }
                    }, 1980, 1, 1).show();
                }
            }
        });
        radioButtonMale = findViewById(R.id.radio_button_male);
        radioButtonFemale = findViewById(R.id.radio_button_female);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Añadiendo Imagen de perfil:
        if (isExternalStorageReadable()) {

            File imgFile = new File(getExternalFilesDir(null), "profileimg.png");
            if (imgFile.exists()) {
                ImageView myImage = findViewById(R.id.profile_img_view); //Añadido al XML
                myImage.setImageURI(Uri.fromFile(imgFile));
            }
        }


        //Para comprobar en logcat que tengo memoria externa y puedo hacer uso de ella, no es esencial en el programa.
        Log.d("ProfileActivity", "Existe y puedo escribir? " + isExternalStorageWritable());
        Log.d("ProfileActivity", "Existe y puedo leer? " + isExternalStorageReadable());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater myInflater = getMenuInflater();
        myInflater.inflate(R.menu.menu_profile_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                // Guardar el perfil
                saveInternal();
                break;
            case R.id.action_delete:
                // Borrar el perfil
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveInternal() {
        // Edit Texts
        Log.d("ProfileActivity", "Username: " + usernameEditText.getText());
        Log.d("ProfileActivity", "Email: " + emailEditText.getText());
        Log.d("ProfileActivity", "Password: " + passwordEditText.getText());
        Log.d("ProfileActivity", "Age: " + ageEditText.getText());

        // Radio Buttons
        if (radioButtonMale.isChecked()) {
            // El usuario ha seleccionado "H"
            Log.d("ProfileActivity", "Gender: male");
        } else if(radioButtonFemale.isChecked()) {
            // El usuario ha seleccionado "M"
            Log.d("ProfileActivity", "Gender: female");
        }

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "databaseApp")
                .allowMainThreadQueries()
                .build();
        try {
            User user = new User();
            db.userDao().insert(user);
        } catch (SQLiteConstraintException ex) {
            Toast.makeText(ProfileActivity.this, "Error al ingresar el usuario",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void guardarDatos(View view) {
        saveInternal();

    }

    /**
     * Método que se ejecutará cuando el usuario pulse "Delete"
     *
     * @param view  -
     */
    public void onDelete(View view) {
        // Mostrar un dialogo de confirmación
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.dialog_message);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // El usuario ha pulsado el botón "SÍ"
                Toast.makeText(ProfileActivity.this, "SI QUIERO!",
                        Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // El usuario ha pulsado el botón "NO"
                Toast.makeText(ProfileActivity.this, "NO QUIERO!",
                        Toast.LENGTH_LONG).show();
            }
        });
        builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // El usuario ha pulsado el botón "CANCELAR"
                Toast.makeText(ProfileActivity.this, "Cancelando...",
                        Toast.LENGTH_LONG).show();
            }
        });

        builder.create().show();
    }


    @Override
    protected void onStop() {
        super.onStop();

        /**
         * Creo un SharedPreferences para que guarde los datos en caso de que se pare la app
         */
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.user_preferences),
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor myEditor = sharedPref.edit();
        myEditor.putString("username_key", usernameEditText.getText().toString());
        myEditor.putString("email_key", emailEditText.getText().toString());
        //myEditor.putInt("age_key", Integer.parseInt(ageEditText.getText().toString()));
        myEditor.putString("age_key", ageEditText.getText().toString());
        myEditor.putString("radiomale_key", radioButtonMale.getText().toString());
        myEditor.putString("radiofemale_key", radioButtonFemale.getText().toString());
        myEditor.apply();

    }

    //Comprobar el estado del almacenamiento interno:

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    //Comprobar que existe y se puede leer:
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
        {
            return true;
        }
        return false;
    }
}
