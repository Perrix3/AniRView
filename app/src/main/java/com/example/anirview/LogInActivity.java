package com.example.anirview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;

public class LogInActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private TextView forgotPassword;
    private final String FILE_NAME = "users.json";
    private sesion userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        userSession = new sesion(this);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        forgotPassword = findViewById(R.id.forgotPassword);

        // Login Button
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        // Button to go to create account
        Button createAcc = findViewById(R.id.createAcc);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Button to go back to the main activity
        Button goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Forgot password, not implemented as of yet
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LogInActivity.this, "Password recovery is not yet implemented", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            JSONArray usersArray = readUsersFromFile();

            boolean userFound = false;
            JSONObject loggedInUser = null;

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                String storedEmail = user.getString("email");
                String storedPassword = user.getString("password");

                if (storedEmail.equals(email) && storedPassword.equals(password)) {
                    userFound = true;
                    loggedInUser = user;
                    break;
                }
            }

            if (userFound && loggedInUser != null) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                // Save session using the sesion class
                userSession.setUser(loggedInUser.getString("email")); // Save user email
                userSession.open(); // Mark session as active

                // Go to MainMenuActivity after successful login
                Intent intent = new Intent(LogInActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException | IOException e) {
            Log.e("LogInActivity", "Error: ", e);
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
}
