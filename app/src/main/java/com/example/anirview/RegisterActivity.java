package com.example.anirview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private final String FILE_NAME = "users.json";
    private sesion userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        userSession = new sesion(this);

        emailInput = findViewById(R.id.emailInputRegister);
        passwordInput = findViewById(R.id.passwordInputRegister);

        // Register Button
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Button to go to log in
        Button logIn = findViewById(R.id.logIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

        // Button to go back to main activity
        Button goBack = findViewById(R.id.goBack2);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            JSONArray usersArray = readUsersFromFile();

            // Check if user already exists
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (user.getString("email").equals(email)) {
                    Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Add new user
            JSONObject newUser = new JSONObject();
            newUser.put("email", email);
            newUser.put("password", password);
            usersArray.put(newUser);

            // Save user list
            saveUsersToFile(usersArray);

            // Sets the data for the session
            userSession.setUser(email);
            userSession.open();

            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            emailInput.setText("");
            passwordInput.setText("");

            Intent intent = new Intent(RegisterActivity.this, MainMenuActivity.class);
            startActivity(intent);

        } catch (JSONException | IOException e) {
            Log.e("RegisterActivity", "Error: ", e);
            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
        }
    }

    private JSONArray readUsersFromFile() throws IOException, JSONException {
        FileInputStream fis = openFileInput(FILE_NAME);
        int size = fis.available();
        byte[] buffer = new byte[size];
        fis.read(buffer);
        fis.close();

        String json = new String(buffer, "UTF-8");
        return new JSONArray(json);
    }

    private void saveUsersToFile(JSONArray usersArray) throws IOException {
        FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
        fos.write(usersArray.toString().getBytes());
        fos.close();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Creates the json if it doesn't exist already
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            fis.close();
        } catch (IOException e) {
            try {
                saveUsersToFile(new JSONArray());
            } catch (IOException ioException) {
                Log.e("RegisterActivity", "Error initializing file", ioException);
            }
        }
    }
}
